package com.gusstrik.vkr.fileservice.test.core;

import com.gusstrik.vkr.common.dto.BaseDataResponse;
import com.gusstrik.vkr.fileservice.core.FileService;
import com.gusstrik.vkr.fileservice.core.conf.FileServiceCoreConfig;
import com.gusstrik.vkr.fileservice.dto.FileDto;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;
import java.net.MalformedURLException;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@PropertySource(value = "classpath:/test-app.properties",encoding = "UTF-8")
@Import(FileServiceCoreConfig.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Test
    public void uploadFileTest() throws IOException {

        String path = "C:\\Users\\Илья\\Desktop\\photo_2022-05-16_19-35-59.jpg";
        FileInputStream inputStream = new FileInputStream(path);
        BaseDataResponse<FileDto> dto = fileService.uploadFile( inputStream,".jpg");
        System.out.println(dto);
    }

    @Test
    public void updateFileTest() {
        FileDto fileDto = new FileDto();
        fileDto.setId(8L);
        fileDto.setName("Попа");
        fileDto.setDescription("Фотография попы");
        BaseDataResponse<FileDto> dto = fileService.saveFileInfo(fileDto);
    }

    @Test
    public void deleteFileTest(){
        BaseDataResponse<?> response = fileService.deleteFile(7L);
    }

    @Test
    public void getFileByIdTest(){
        System.out.println(fileService.getFileById(4L));
    }

    @Test
    public void downloadFileTest() throws IOException {
        File file = fileService.downloadFile(8L);
        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream("test"+file.getName());
        IOUtils.copy(inputStream,outputStream);
        outputStream.flush();
    }
}
