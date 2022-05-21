package com.gusstrik.vkr.fileservice.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.fileservice.dto.FileDto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    BaseDataResponse<FileDto> uploadFile(InputStream inputStream, String fileType) throws IOException;

    BaseDataResponse<FileDto> saveFileInfo(FileDto fileDto);

    BaseDataResponse<?> deleteFile(Long id);

    BaseDataResponse<FileDto> getFileById(Long id);

    File downloadFile(Long id) throws FileNotFoundException;

}
