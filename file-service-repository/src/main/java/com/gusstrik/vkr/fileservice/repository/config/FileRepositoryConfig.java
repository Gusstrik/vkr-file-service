package com.gusstrik.vkr.fileservice.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = {"com.gusstrik.vkr.fileservice.model"})
@EnableJpaRepositories(basePackages = {"com.gusstrik.vkr.fileservice.repository"})
public class FileRepositoryConfig {
}
