package com.otus.homework.orderservice.controllers;

import com.otus.homework.orderservice.dto.rest.ListOrderResponse;
import com.otus.homework.orderservice.dto.rest.SortAndFilter;
import com.otus.homework.orderservice.exceptions.ValidationException;
import com.otus.homework.orderservice.services.OrderAggregateService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Orders"})
@RequiredArgsConstructor
@RequestMapping("/api/admin/orders")
@RestController
public class AdminOrdersControllers {
    private final OrderAggregateService orderAggregateService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ListOrderResponse getOrders(SortAndFilter sortAndFilter) {
        validateSort(sortAndFilter);
        return new ListOrderResponse(orderAggregateService.getAllForAdmin(sortAndFilter));
    }

    private void validateSort(SortAndFilter sortAndFilter) {
        if (sortAndFilter.getSortBy() == null) {
            return;
        }
        if (!sortAndFilter.getSortBy().equalsIgnoreCase("createTime")
                && !sortAndFilter.getSortBy().equalsIgnoreCase("totalCost")) {
            throw new ValidationException("Невозможно сортировать по " + sortAndFilter.getSortBy());
        }
    }
}
