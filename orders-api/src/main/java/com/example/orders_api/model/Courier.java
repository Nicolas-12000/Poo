package com.example.orders_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "courier")
@Data
@EqualsAndHashCode(callSuper = true)
public class Courier extends User {
    private String vehicle;
}
