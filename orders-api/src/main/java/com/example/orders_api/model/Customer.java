package com.example.orders_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "customer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Customer extends User {
    private String phone;
}
