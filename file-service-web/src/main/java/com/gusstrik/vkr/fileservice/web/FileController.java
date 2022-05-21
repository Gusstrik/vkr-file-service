package com.gusstrik.vkr.fileservice.web;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.common.dto.OperationError;
import com.gusstrik.vkr.fileservice.core.FileService;
import com.gusstrik.vkr.fileservice.dto.FileDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<FileDto> uploadFile(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            log.info(file.getOriginalFilename());
            return fileService.uploadFile(inputStream, "." + file.getOriginalFilename().replaceFirst(".*\\.", ""));
        } catch (IOException e) {
            log.error("Error in controller. Uploading file", e);
            BaseDataResponse<FileDto> response = new BaseDataResponse();
            response.setSuccess(false);
            OperationError operationError = new OperationError();
            operationError.setMessage("Ошибка загрузки файла");
            response.setErrorList(Arrays.asList(operationError));
            return response;
        }
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<FileDto> saveFileInfo(@RequestBody FileDto fileDto) {
        return fileService.saveFileInfo(fileDto);
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<?> deleteFileInfo(@PathVariable Long id) {
        return fileService.deleteFile(id);
    }

    @GetMapping(value = "getInfo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<FileDto> getInfo(@PathVariable("id") Long id) {
        return fileService.getFileById(id);
    }


    @GetMapping(value = "/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Long id) {
        try {
            File file = fileService.downloadFile(id);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .body(resource);

        } catch (IOException e) {
            log.error("Error getting file", e);
            return null;
        }
    }

}
