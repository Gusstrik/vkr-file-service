package com.gusstrik.vkr.fileservice.dto;

import lombok.Data;

@Data
public class FileDto {

    private Long id;

    private String name;

    private String description;

    private String fileType;
}
