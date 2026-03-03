package com.taller.pagos.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class DotenvLoader {

    @PostConstruct
    public void loadDotenv() {
        try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            // Load a few common vars into system properties if not already set
            setIfAbsent("DB_URL", dotenv.get("DB_URL"));
            setIfAbsent("DB_USER", dotenv.get("DB_USER"));
            setIfAbsent("DB_PASS", dotenv.get("DB_PASS"));
            setIfAbsent("DB_DRIVER", dotenv.get("DB_DRIVER"));
            setIfAbsent("JPA_DDL_AUTO", dotenv.get("JPA_DDL_AUTO"));
        } catch (Exception e) {
            // ignore
        }
    }

    private void setIfAbsent(String key, String value) {
        if (value != null && System.getProperty(key) == null && System.getenv(key) == null) {
            System.setProperty(key, value);
        }
    }
}
