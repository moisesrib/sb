package org.sb.products.model;

import jakarta.persistence.*;
import org.sb.core.structures.BaseEntity;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal promotional = BigDecimal.ZERO;

    @Column(precision = 10, scale = 2)
    private BigDecimal cost = BigDecimal.ZERO;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private boolean active = true;
   
    @Override
    public UUID getId() {
        return id;
    }

}
