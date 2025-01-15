package by.zemich.kufar.application.service;

import by.zemich.kufar.infrastructure.properties.MinioProperties;
import by.zemich.kufar.application.service.api.FilesHandler;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final FilesHandler filesHandler;
    private final MinioProperties minioProperties;

    public String saveNotificationImage(String base64Image) {
        String fileName = UUID.randomUUID() + ".jpg";
        FileInputStream imageStream = null;
        try {
            imageStream = getFromBase64(base64Image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        filesHandler.upload(fileName, imageStream, minioProperties.getNotificationImageBucketName());
        return fileName;
    }

    public InputStream downloadNotificationImage(String fileName) {
        return filesHandler.download(fileName, minioProperties.getNotificationImageBucketName());
    }

    FileInputStream getFromBase64(String base64Image) throws IOException {
        byte[] imageBytes = Base64.decode(base64Image);
        File tempFile = File.createTempFile("base64_temp", null);
        tempFile.deleteOnExit();
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(imageBytes);
        }
        return new FileInputStream(tempFile);
    }



}
