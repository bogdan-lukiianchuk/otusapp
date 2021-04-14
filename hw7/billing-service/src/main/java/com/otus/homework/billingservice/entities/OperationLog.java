package com.otus.homework.billingservice.entities;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "operation_log")
@TypeDef(name = "json", typeClass = JsonBinaryType.class)
public class OperationLog {
    @Id
    @Column(name = "operation_uuid")
    private UUID id;
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "execution_time")
    private Instant executionTime;
    @Type(type = "json")
    @Column(name = "data", columnDefinition = "json")
    private String data;
}
