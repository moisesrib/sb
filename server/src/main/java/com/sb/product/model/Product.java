package com.sb.product.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.OneToMany;
import com.sb.sales.model.SalesItem;
import com.sb.core.structures.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    private BigDecimal promotion;
    private BigDecimal cost;
    @Column(nullable = false)
    private Integer stock;
    @Column(nullable = false)
    private Boolean active;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "product", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private Set<SalesItem> salesItems;

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
