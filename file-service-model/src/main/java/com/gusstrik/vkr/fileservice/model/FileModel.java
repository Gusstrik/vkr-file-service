package com.gusstrik.vkr.fileservice.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Data
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name = "type")
    private String fileType;

    @Column(name = "path")
    private String path;
}
