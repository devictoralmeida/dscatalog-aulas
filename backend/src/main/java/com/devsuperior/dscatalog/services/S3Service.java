package com.devsuperior.dscatalog.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Instant;


@Service
@RequiredArgsConstructor
public class S3Service {
    private static Logger LOG = LoggerFactory.getLogger(S3Service.class);

    private final AmazonS3 s3client; // Esse @Bean será definido nas config do S3, na pasta config.

    @Value("${s3.bucket}")
    private String bucketName;

    public URL uploadFile(MultipartFile file) {
        try {
            String originalName = file.getOriginalFilename();
            String fileExtension = FilenameUtils.getExtension(originalName);

            // Vamos colocar o nome do arquivo como sendo o Timestamp do upload.extensão do arquivo
            String fileName = Instant.now() + "." + fileExtension;

            InputStream is = file.getInputStream();
            String contentType = file.getContentType();
            return uploadFile(is, fileName, contentType);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private URL uploadFile(InputStream is, String fileName, String contentType) {
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(contentType);

        LOG.info("Iniciando upload");
        s3client.putObject(bucketName, fileName, is, meta);
        LOG.info("Upload finalizado");

        return s3client.getUrl(bucketName, fileName);
    }
}
