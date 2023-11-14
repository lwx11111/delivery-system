package org.example.controller;

import io.minio.*;
import io.minio.errors.MinioException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @Author 刘文轩
 * @Date 2023/10/16 18:45
 */
@RestController
@RequestMapping("/minio")
public class MinIOTestController {

    @GetMapping("/test")
    public void test() throws Exception {
        System.out.println("Program started.");  // 程序开始

        try {
            System.out.println("Creating MinioClient object.");  // 创建 MinioClient 对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();
            System.out.println("MinioClient object created.");  // MinioClient 对象已创建

            System.out.println("Checking if bucket exists.");  // 检查存储桶是否存在
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket("test").build());
            if (isExist) {
                System.out.println("Bucket exists.");  // 存储桶存在
            } else {
                System.out.println("Bucket does not exist.");  // 存储桶不存在
            }
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);  // 错误发生
        }

        System.out.println("Program finished.");  // 程序结束
    }

    @GetMapping("/test1")
    public void test1() throws Exception {
        try {
            // 创建MinioClient对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("minioadmin", "minioadmin")
                    .build();

            // 定义存储桶和对象名称
            String bucketName = "test";
            String objectName = "test.png";
            String filePath = "C:\\Users\\lwx20\\Desktop\\test.png";

            // 上传对象到存储桶
            minioClient.uploadObject(
                    UploadObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .filename(filePath)
                            .build()
            );
            System.out.println("Uploaded object to bucket.");

            // 下载对象
            try (InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            )) {
                Files.copy(stream, Path.of("C:\\Users\\lwx20\\Desktop\\downloaded-" + objectName), StandardCopyOption.REPLACE_EXISTING);
            }
            System.out.println("Downloaded object from bucket.");

            // 删除对象
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            System.out.println("Deleted object from bucket.");

        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }

    }
}
