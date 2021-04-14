package com.otus.homework.orderservice.interfaces;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.otus.homework.orderservice.model.EmptyJson;
import com.otus.homework.orderservice.model.SetItemJson;
import com.otus.homework.orderservice.model.UpdateOrderJson;

@JsonTypeInfo(property = "TYPE", use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SetItemJson.class, name = "setitem"),
        @JsonSubTypes.Type(value = UpdateOrderJson.class, name = "update"),
        @JsonSubTypes.Type(value = EmptyJson.class, name = "empty"),
})
public interface JsonData {
}
