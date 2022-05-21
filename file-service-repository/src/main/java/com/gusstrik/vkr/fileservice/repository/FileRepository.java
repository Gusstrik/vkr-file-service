package com.gusstrik.vkr.fileservice.repository;

import com.gusstrik.vkr.fileservice.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileModel,Long> {
}
