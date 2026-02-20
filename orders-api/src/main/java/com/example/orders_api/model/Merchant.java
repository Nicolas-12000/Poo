package com.example.orders_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "merchant")
@Data
@EqualsAndHashCode(callSuper = true)
public class Merchant extends User {
    private String merchantCode;
}
