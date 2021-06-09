package com.otus.homework.warehouseservice.entities;

import com.otus.homework.warehouseservice.dto.SupplyDto;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@Table(name = "replenishment")
public class Replenishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replenishment_id")
    private Long id;
    @Column(name = "replenishment_code")
    private String replenishmentCode;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb", name = "supplies")
    private List<SupplyDto> supplies;
}
