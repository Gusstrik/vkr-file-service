package com.gusstrik.vkr.fileservice.app;

import com.gusstrik.vkr.fileservice.app.config.FileServiceAppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.io.*;

@SpringBootApplication
@Import(FileServiceAppConfig.class)
@PropertySource(value = "classpath:/application.properties",encoding = "UTF-8")
public class FileServiceApplication {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(FileServiceApplication.class, args);
    }
}
