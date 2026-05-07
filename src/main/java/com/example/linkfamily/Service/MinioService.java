package com.example.linkfamily.Service;

import com.example.linkfamily.Exception.FileException;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.endpoint}")
    private String endpoint;

    private static final String PHOTO_FOLDER = "photo/";

    /**
     * Upload file to MinIO
     */
    public String uploadFile(MultipartFile file, String fileName) {
        try {
            // Generate UUID-based filename
            String fileExtension = getFileExtension(fileName);
            String uniqueFileName = PHOTO_FOLDER + UUID.randomUUID() + "." + fileExtension;

            // Upload to MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucket)
                            .object(uniqueFileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            log.info("File uploaded successfully: {}", uniqueFileName);
            return uniqueFileName;

        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("File upload failed: {}", e.getMessage());
            throw new FileException(500, "Failed to upload file to MinIO: " + e.getMessage());
        }
    }

    /**
     * Delete file from MinIO
     */
    public void deleteFile(String filePath) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucket)
                            .object(filePath)
                            .build()
            );
            log.info("File deleted successfully: {}", filePath);
        } catch (MinioException | IOException | InvalidKeyException | NoSuchAlgorithmException e) {
            log.error("File deletion failed: {}", e.getMessage());
            throw new FileException(500, "Failed to delete file from MinIO: " + e.getMessage());
        }
    }

    /**
     * Generate public URL for file
     */
    public String generatePublicUrl(String filePath) {
        // Format: http://endpoint/bucket/object
        return endpoint.endsWith("/") ?
                endpoint +  "storage/v1/object/" + bucket + "/" + filePath :
                endpoint + "/storage/v1/object/" + bucket + "/" + filePath;
    }

    /**
     * Get file extension
     */
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }
}
