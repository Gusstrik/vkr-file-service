package com.gusstrik.vkr.fileservice.core.conf;

import com.gusstrik.vkr.fileservice.repository.config.FileRepositoryConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = {"com.gusstrik.vkr.fileservice.core.impl"})
@Import(FileRepositoryConfig.class)
public class FileServiceCoreConfig {
}
