package com.example.orders_api.service;

import com.example.orders_api.model.User;

public interface NotificationService {
    void notify(User user, String message);
}
