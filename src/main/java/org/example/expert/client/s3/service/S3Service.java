package org.example.expert.client.s3.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.ServerException;
import org.example.expert.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {

    private static final long PRESIGNED_URL_EXPIRATION_MINUTES = 10;

    private final AmazonS3 amazonS3;
    private final UserService userService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String upload(MultipartFile file, AuthUser authUser) {
        try {
            String key = "uploads/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());
            amazonS3.putObject(bucket, key, file.getInputStream(), metadata);
            // user에 저장
            userService.updateProfile(authUser.getId(), key);
            return key;
        } catch (IOException e) {
            throw new ServerException("파일 업로드에 실패했습니다.");
        }
    }

    public String getDownloadUrl(String key) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * PRESIGNED_URL_EXPIRATION_MINUTES);
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, key)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return amazonS3.generatePresignedUrl(request).toString();
    }
}
