package com.by.zx.manager.service;


import org.springframework.web.multipart.MultipartFile;

//头像上传，返回minio路径
public interface FileUploadService {
    String upload(MultipartFile file);
}
