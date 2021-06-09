package com.otus.homework.orderservice.services;

import com.otus.homework.orderservice.dto.rest.OrderDto;
import com.otus.homework.orderservice.dto.rest.ProductDto;
import com.otus.homework.orderservice.dto.rest.ProductItemDto;
import com.otus.homework.orderservice.dto.rest.SortAndFilter;
import com.otus.homework.orderservice.entities.Order;
import com.otus.homework.orderservice.entities.OrderEvent;
import com.otus.homework.orderservice.enums.EventType;
import com.otus.homework.orderservice.enums.OrderSort;
import com.otus.homework.orderservice.enums.OrderStatus;
import com.otus.homework.orderservice.model.SetItemJson;
import com.otus.homework.orderservice.model.UpdateOrderJson;
import com.otus.homework.orderservice.model.UpdateOrderStatusJson;
import com.otus.homework.orderservice.repositories.OrderEventRepository;
import com.otus.homework.orderservice.services.integration.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderAggregateService {
    private final SnapshotService snapshotService;
    private final OrderEventRepository orderEventRepository;
    private final ProductService productService;

    public List<OrderDto> getAllForAdmin(SortAndFilter sortAndFilter) {
        fillProductCache();
        List<OrderEvent> orderEvents;
        if (sortAndFilter.getCreatedPeriodStart() != null && sortAndFilter.getCreatedPeriodEnd() != null) {
            orderEvents = orderEventRepository.findAllByCreateTimeBetween(sortAndFilter.getCreatedPeriodStart(),
                    sortAndFilter.getCreatedPeriodEnd());
        } else if (sortAndFilter.getCreatedTime() != null) {
            orderEvents = orderEventRepository.findAllByCreateTime(sortAndFilter.getCreatedTime());
        } else {
            orderEvents = orderEventRepository.findAll(Sort.by("orderId", "createTime"));
        }
        Map<Long, List<OrderEvent>> events = createOrderEventsMap(orderEvents);
        List<OrderDto> result = events.values()
                .stream()
                .map(this::getOrder)
                .filter(it -> sortAndFilter.getStatus() == null
                        || it.getStatus() == sortAndFilter.getStatus())
                .collect(Collectors.toList());
        if (sortAndFilter.getSortBy() != null) {
            switch (OrderSort.getBuValue(sortAndFilter.getSortBy())) {
                case COST:
                    result.sort(Comparator.comparingLong(OrderDto::getTotalCost));
                    break;
                case CREATE_TIME:
                    result.sort(Comparator.comparing(OrderDto::getCreateTime));
            }
        }
        return result;
    }

    public List<OrderDto> getForUser(long userId, SortAndFilter sortAndFilter) {
        fillProductCache();
        Map<Long, List<OrderEvent>> events = createOrderEventsMap(
                orderEventRepository.findAllByUserIdOrderByOrderIdAscCreateTimeAsc(userId));
        return events.values()
                .stream()
                .filter(it -> sortAndFilter.getCreatedTime() == null
                        || it.get(0).getCreateTime().equals(sortAndFilter.getCreatedTime()))
                .map(this::getOrder)
                .filter(order -> sortAndFilter.getProductName() == null
                        || order.getItems().stream().map(ProductItemDto::getName).collect(Collectors.toList())
                        .contains(sortAndFilter.getProductName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto getOrder(long id) {
        fillProductCache();
        List<OrderEvent> events = orderEventRepository.findAllByOrderIdOrderByCreateTime(id);
        return getOrder(events);
    }

    private Map<Long, List<OrderEvent>> createOrderEventsMap(List<OrderEvent> orderEvents) {
        return new TreeMap<>(
                orderEvents
                        .stream()
                        .collect(Collectors.groupingBy(OrderEvent::getOrderId, Collectors.toList()))
        );
    }

    private OrderDto getOrder(List<OrderEvent> events) {
        OrderEvent lastEvent = events.get(events.size() - 1);
        Order snapshot = snapshotService.getSnapshot(lastEvent.getOrderId());
        if (snapshot != null && snapshot.getLastUpdate().equals(lastEvent.getCreateTime())) {
            return snapshot.getData();
        }
        Map<String, ProductItemDto> items = new LinkedHashMap<>();
        OrderStatus status = OrderStatus.CREATED;
        LocalDateTime deliveryTime = null;
        String courierName = null;
        for (OrderEvent event : events) {
            if (event.getType() == EventType.ADD_PRODUCT) {
                SetItemJson jsonValue = (SetItemJson) event.getJsonValue();
                ProductDto product = productService.findByName(jsonValue.getName()).orElseThrow();
                items.put(product.getName(),
                        new ProductItemDto(
                                product.getId(),
                                product.getName(),
                                jsonValue.getCount(),
                                product.getPrice(),
                                product.getPrice() * jsonValue.getCount()));
            } else if (event.getType() == EventType.CHANGE_STATUS) {
                UpdateOrderStatusJson jsonValue = (UpdateOrderStatusJson) event.getJsonValue();
                status = jsonValue.getStatus();
            } else if (event.getType() == EventType.UPDATE) {
                UpdateOrderJson jsonValue = (UpdateOrderJson) event.getJsonValue();
                deliveryTime = jsonValue.getDeliveryTime();
                courierName = jsonValue.getCourierName();
            }
        }
        Map<String, ProductItemDto> filtered = items.entrySet().stream()
                .filter(e -> e.getValue().getCount() != 0)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        long totalCost = items.values().stream().mapToLong(ProductItemDto::getCost).sum();
        OrderDto orderDto = restoreOrder(events, status, totalCost, new ArrayList<>(filtered.values()), deliveryTime, courierName);
        snapshotService.addSnapshot(orderDto);
        return orderDto;
    }

    private OrderDto restoreOrder(List<OrderEvent> events, OrderStatus status, long totalCost,
                                  List<ProductItemDto> items, LocalDateTime deliveryTime, String courierName) {
        OrderEvent firstEvent = events.get(0);
        Long id = firstEvent.getOrderId();
        Long userId = firstEvent.getUserId();
        Instant createTime = firstEvent.getCreateTime();
        Instant lastUpdateTime = events.get(events.size() - 1).getCreateTime();
        return new OrderDto(id, userId, createTime, lastUpdateTime, status, items, totalCost, deliveryTime, courierName);
    }

    private void fillProductCache() {
        try {
            productService.fillCache();
        } catch (Exception e) {
            log.error("Ошибка обновления справочника продуктов");
        }
    }
}
