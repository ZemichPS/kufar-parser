package by.zemich.kufar.application.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileLoader {
    public File downloadImage(String imageUrl) throws Exception {
        if(imageUrl == null || imageUrl.isEmpty()) throw new RuntimeException("Image URL is empty");

        URL url = new URL(imageUrl);
        File tempFile = Files.createTempFile("telegram_image_"+ UUID.randomUUID(), ".jpg").toFile();

        try (InputStream in = url.openStream();
             FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        tempFile.deleteOnExit(); // Удалить файл после завершения программы
        return tempFile;
    }

    public File loadFileFromResources(String resourcePath) throws Exception {
        // Получение InputStream для файла в resources
        InputStream resourceStream = FileLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (resourceStream == null) {
            throw new IllegalArgumentException("Файл не найден в resources: " + resourcePath);
        }
        // Создание временного файла
        File tempFile = Files.createTempFile("telegram_resource_image", ".jpg").toFile();
        // Копирование ресурса в временный файл
        Files.copy(resourceStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        // Закрываем поток
        resourceStream.close();
        // Возвращаем временный файл
        tempFile.deleteOnExit(); // Удаляем файл после завершения программы
        return tempFile;
    }
}
