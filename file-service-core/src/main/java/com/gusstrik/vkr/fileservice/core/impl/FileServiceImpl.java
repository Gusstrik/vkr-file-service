package com.gusstrik.vkr.fileservice.core.impl;

import com.gusstrik.vkr.fileservice.core.FileService;
import com.gusstrik.vkr.fileservice.core.mapper.FileMapper;
import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.fileservice.dto.FileDto;

import com.gusstrik.vkr.fileservice.model.FileModel;
import com.gusstrik.vkr.fileservice.repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    private String fileCatalog = System.getProperty("user.dir") + "\\uploaded";

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    @Transactional
    public BaseDataResponse<FileDto> uploadFile(InputStream inputStream, String fileType) throws IOException {
        String tmpFileUid = UUID.randomUUID().toString();
        log.info("File path: " + fileCatalog);
        String tmpFilePath = fileCatalog + "\\" + tmpFileUid + fileType;
        BaseDataResponse<FileDto> response = new BaseDataResponse<>();
        File temporaryFile = new File(tmpFilePath);
        Files.createFile(Paths.get(tmpFilePath));
        log.info("Can create file: " + temporaryFile.canWrite());
        try (OutputStream outputStream = new FileOutputStream(temporaryFile)) {
            IOUtils.copy(inputStream,outputStream);
            outputStream.flush();
            log.debug("Save temporary file " + tmpFilePath);
        } catch (IOException e) {
            log.error("Error in core save temporary file", e);
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Ошибка загрузки файла");
            response.setErrorList(Arrays.asList(operationError));
            return response;
        }
        FileModel fileModel = new FileModel();
        fileModel.setName(tmpFileUid);
        fileModel.setPath(tmpFilePath);
        fileModel.setFileType(fileType);
        fileRepository.save(fileModel);
        FileDto fileDto = FileMapper.toDto(fileModel);
        response.setData(fileDto);
        response.setSuccess(true);
        return response;
    }

    @Override
    @Transactional
    public BaseDataResponse<FileDto> saveFileInfo(FileDto fileDto) {
        BaseDataResponse<FileDto> response = new BaseDataResponse<>();
        FileModel fileModel = fileRepository.findById(fileDto.getId()).orElse(null);
        if (fileModel == null) {
            log.error("Can't find file with id " + fileDto.getId());
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Файл не найден");
            return response;
        }
        fileModel.setDescription(fileDto.getDescription());
        fileModel.setName(fileDto.getName());

        File file = new File(fileModel.getPath());
        File updateFile = new File(fileCatalog + "\\" + fileModel.getName() + fileModel.getFileType());
        try (OutputStream outputStream = new FileOutputStream(updateFile);
             InputStream inputStream = new FileInputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
            log.debug("Successful update file");
            fileModel.setPath(fileCatalog + "\\" + fileModel.getName() + fileModel.getFileType());
            fileRepository.save(fileModel);
            inputStream.close();
            boolean result = file.delete();
            if (!result) {
                log.debug("Error deleting old file");
            }
        } catch (IOException e) {
            log.error("Error updating file", e);
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Can't update file");
            response.setErrorList(Arrays.asList(operationError));
            return response;
        }
        log.debug("Successfully update file " + fileDto.getId());
        FileDto updatedFile = FileMapper.toDto(fileModel);
        response.setData(updatedFile);
        response.setSuccess(true);
        return response;
    }

    @Override
    @Transactional
    public BaseDataResponse<?> deleteFile(Long id) {
        FileModel fileModel = fileRepository.findById(id).orElse(null);
        BaseDataResponse<FileDto> response = new BaseDataResponse<>();
        if (fileModel == null) {
            log.error("Can't find file with id " + id);
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Файл не найден");
            return response;
        }
        File file = new File(fileModel.getPath());
        boolean result = file.delete();
        if (result) {
            fileRepository.delete(fileModel);
            response.setSuccess(true);

        } else {
            log.error("Can't delete file from disk");
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public BaseDataResponse<FileDto> getFileById(Long id) {
        FileDto fileDto = FileMapper.toDto(fileRepository.findById(id).orElse(null));
        BaseDataResponse<FileDto> response = new BaseDataResponse<>();
        if(fileDto==null){
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Файл не найден");
            response.setErrorList(Arrays.asList(operationError));
        }else {
            response.setSuccess(true);
            response.setData(fileDto);
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public File downloadFile(Long id) throws FileNotFoundException {
        FileModel fileModel = fileRepository.findById(id).orElse(null);
        if (fileModel == null) {
            log.error("Can't find file with id " + id);
            OperationError operationError = new OperationError();
            operationError.setMessage("Файл не найден");
            return null;
        }
        return new File(fileModel.getPath());
    }
}
