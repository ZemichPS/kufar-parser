package by.zemich.kufar.application.service.api;

import java.io.FileInputStream;
import java.io.InputStream;

public interface FilesHandler {
    void upload(String fileName, FileInputStream fileInputStream, String to);

    InputStream download(String fileName, String from);
}
