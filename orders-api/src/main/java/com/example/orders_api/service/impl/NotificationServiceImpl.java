package com.example.orders_api.service.impl;

import com.example.orders_api.model.User;
import com.example.orders_api.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void notify(User user, String message) {
        // simple console/log notification - in real app push/email/etc
        log.info("Notify {}: {}", user == null ? "unknown" : user.getName(), message);
    }
}
