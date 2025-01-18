package by.zemich.kufar.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
@Slf4j
public class FileLoader {

    public InputStream downloadFileAsInputStream(String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) throw new RuntimeException("File URL is empty");

        try(InputStream inputStream = new URL(imageUrl).openStream()) {
            byte[] bytes = inputStream.readAllBytes();
            return new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            log.error("Failed to download file. Cause: {}", e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public InputStream loadResourcesFileAsInputStream(String resourceFilePath) {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourceFilePath)) {
            assert inputStream != null;
            byte[] bytes = inputStream.readAllBytes();
            return new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            log.error("Failed to download file from resources. Cause: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public File downloadImageTOFile(String imageUrl) throws Exception {
        if (imageUrl == null || imageUrl.isEmpty()) throw new RuntimeException("Image URL is empty");
        URL url = new URL(imageUrl);
        File tempFile = Files.createTempFile("telegram_image_" + UUID.randomUUID(), ".jpg").toFile();

        try (InputStream in = url.openStream();
             FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        return tempFile;
    }

    public File loadFileFromResources(String resourcePath) throws Exception {
        InputStream resourceStream = FileLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            throw new IllegalArgumentException("Файл не найден в resources: " + resourcePath);
        }
        File tempFile = Files.createTempFile("telegram_resource_image", ".jpg").toFile();
        Files.copy(resourceStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        resourceStream.close();
        return tempFile;
    }
}
