package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostManager {

    private final List<PostTextProcessor> postTextProcessors;
    private final FileLoader fileLoader;
    private final PostLimitedCache<UUID, SendPhoto> postLimitedCache = new PostLimitedCache<>(500);

    public SendPhoto createPhotoPostFromAd(Advertisement advertisement) {
        // TODO написать логику создания поста
        return postLimitedCache.computeIfAbsent(advertisement.getId(), uuid -> {
            InputFile photo = getInputFile(advertisement.getPhotoLink());
            String text = getPostText(advertisement);
            return SendPhoto.builder()
                    .photo(photo)
                    .chatId("54504156056")
                    .parseMode("HTML")
                    .caption(text)
                    .build();
        });
    }

    private String getPostText(Advertisement advertisement) {
        return postTextProcessors.stream()
                .map(processor -> processor.getLine(advertisement))
                .filter(s -> !s.isEmpty() && !s.isBlank())
                .collect(Collectors.joining("\n"));
    }

    private InputFile getInputFile(String photoLink) {
        File imageFile = null;
        try {
            imageFile = fileLoader.downloadImage(photoLink);
        } catch (Exception e) {
            log.error("Error downloading original advertisement image. Try to download default image from resources", e);
            try {
                imageFile = fileLoader.loadFileFromResources("images/default.jpg");
            } catch (Exception ex) {
                log.error("Error downloading image from resources", ex);
                return new InputFile();
            }
        }
        return new InputFile(imageFile);
    }


}
