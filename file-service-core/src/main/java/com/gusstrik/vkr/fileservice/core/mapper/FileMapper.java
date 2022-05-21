package com.gusstrik.vkr.fileservice.core.mapper;

import com.gusstrik.vkr.fileservice.dto.FileDto;
import com.gusstrik.vkr.fileservice.model.FileModel;

public class FileMapper {
    public static FileDto toDto(FileModel fileModel) {
        if(fileModel==null)
            return null;
        FileDto dto = new FileDto();
        dto.setDescription(fileModel.getDescription());
        dto.setId(fileModel.getId());
        dto.setName(fileModel.getName());
        dto.setFileType(fileModel.getFileType());
        return dto;
    }

    public static FileModel toModel(FileDto dto) {
        FileModel fileModel = new FileModel();
        fileModel.setId(dto.getId());
        fileModel.setDescription(fileModel.getDescription());
        fileModel.setName(dto.getName());
        return fileModel;
    }
}
