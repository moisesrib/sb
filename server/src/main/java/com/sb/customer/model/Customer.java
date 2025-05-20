package com.sb.customer.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sb.core.structures.BaseEntity;

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

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String phone;

    private String address;

    @Column(unique = true, nullable = false)
    private String document;

    private String observation;

    private Boolean active;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "customer", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private Set<com.sb.sales.model.Sales> sales;

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}