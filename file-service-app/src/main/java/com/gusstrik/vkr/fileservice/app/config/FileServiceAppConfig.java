package com.gusstrik.vkr.fileservice.app.config;

import com.gusstrik.vkr.fileservice.core.conf.FileServiceCoreConfig;
import com.gusstrik.vkr.fileservice.web.config.FileServiceWebConfig;
import com.gusstrik.vkr.fileservice.web.config.SwaggerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({FileServiceCoreConfig.class, FileServiceWebConfig.class, SwaggerConfig.class})
public class FileServiceAppConfig {
}
