package com.by.zx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.by.zx.common.exception.DiyException;
import com.by.zx.manager.properties.MinioProperties;
import com.by.zx.manager.service.FileUploadService;
import com.by.zx.model.vo.common.ResultCodeEnum;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties;

    //头像上传，返回minio路径
    @Override
    public String upload(MultipartFile file) {
        try {
            // 创建一个 minioClient对象，其中包含 MinIO 服务器 playground、访问密钥和秘钥。
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpointUrl())//minio访问地址
                            .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())//minio账户和密码
                            .build();

            // 如果不存在，则创建 "zx-bucket "水桶(bucket)。
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                // 新建一个名为 "zx-bucket "的邮筒
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                System.out.println(minioProperties.getBucketName()+" 已存在。");
            }

            //获取上传的文件名称
            String filename = file.getOriginalFilename();
            // 设置存储对象名称
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");//获取当天日期
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String dateDirUuidFilename = dateDir+"/"+uuid+filename; //给每个文件名设置成唯一，且按日期分类

            //获取文件的输入流
            InputStream inputStream = file.getInputStream();
            //获取文件的大小
            long size = file.getSize();
            //文件上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                            .object(dateDirUuidFilename)
                            .stream(inputStream, size, -1)
                            .build());
            //获取上传文件在minio的路径
            String url = minioProperties.getEndpointUrl()+"/"+minioProperties.getBucketName()+"/" + dateDirUuidFilename;
            //返回文件路径
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            throw new DiyException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
