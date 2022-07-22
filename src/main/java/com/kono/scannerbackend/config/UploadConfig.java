package com.kono.scannerbackend.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Data
@Configuration
public class UploadConfig {
    private String ip;
    private String person;
    private ConcurrentHashMap<String, String> fileWithPerson;
}
