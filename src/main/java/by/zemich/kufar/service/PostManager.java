package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostManager {

    private final List<PostTextProcessor> postTextProcessors;
    private final FileLoader fileLoader;

    public SendPhoto createPhotoPostFromAd(Advertisement advertisement) {
        // TODO написать логику создания поста
        InputFile photo = getInputFile(advertisement);
        String text = getPostText(advertisement);
        return SendPhoto.builder()
                .photo(photo)
                .caption(text)
                .build();
    }

    private String getPostText(Advertisement advertisement) {
        return postTextProcessors.stream()
                .map(processor -> processor.getLine(advertisement))
                .filter(s -> !s.isEmpty() && !s.isBlank())
                .collect(Collectors.joining("\n"));
    }

    private InputFile getInputFile(Advertisement advertisement) {
        File imageFile = null;
        try {
            imageFile = fileLoader.downloadImage(advertisement.getPhotoLink());
        } catch (Exception e) {
            log.error("Error downloading original advertisement image. Try to download default image from resources", e);
            try {
                imageFile = fileLoader.loadFileFromResources("default.");
            } catch (Exception ex) {
                log.error("Error downloading image from resources", ex);
                return new InputFile();
            }
        }
        return new InputFile(imageFile);
    }


}
