package org.example.expert.client.s3.controller;

import lombok.RequiredArgsConstructor;
import org.example.expert.client.s3.dto.FileDownloadUrlResponse;
import org.example.expert.client.s3.dto.FileUploadResponse;
import org.example.expert.client.s3.service.S3Service;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @PostMapping("/files/upload")
    public ResponseEntity<FileUploadResponse> upload(
            @RequestParam("file") MultipartFile file,
            @Auth AuthUser authUser
            ) {
        String key = s3Service.upload(file, authUser);
        return ResponseEntity.ok(new FileUploadResponse(key));
    }

    @GetMapping("/files/download-url")
    public ResponseEntity<FileDownloadUrlResponse> getDownloadUrl(@RequestParam String key) {
        String url = s3Service.getDownloadUrl(key);
        return ResponseEntity.ok(new FileDownloadUrlResponse(url));
    }
}
