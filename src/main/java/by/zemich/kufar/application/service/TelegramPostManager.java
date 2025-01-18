package by.zemich.kufar.application.service;

import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.domain.service.textpostprocessors.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramPostManager {

    private final List<PostTextProcessor> postTextProcessors;
    private final PostLimitedCache<UUID, SendPhoto> postLimitedCache = new PostLimitedCache<>(500);
    private final ImageService imageService;
    private final FileLoader fileLoader;

    public SendPhoto createPhotoPostFromAd(Advertisement advertisement) {
        return postLimitedCache.computeIfAbsent(advertisement.getId(), id -> {

            InputFile photo = advertisement.getPhotoLink()
                    .map(fileLoader::downloadFileAsInputStream)
                    .map(inputStream -> new InputFile(inputStream, UUID.randomUUID() + "jpg"))
                    .orElseGet(()-> {
                        InputStream inputStream = fileLoader.loadResourcesFileAsInputStream("default.jpg");
                        return new InputFile(inputStream, UUID.randomUUID() + "jpg");
                    });

            String text = processPostText(advertisement);
            return SendPhoto.builder()
                    .photo(photo)
                    .chatId("54504156056")
                    .parseMode("HTML")
                    .caption(text)
                    .build();
        });
    }

    public SendPhoto createPostFromNotification(Notification notification) {
        String imageName = notification.getImageName();
        try {
            InputStream postImageInputStream = imageService.downloadNotificationImage(imageName);
            InputFile photo = new InputFile(postImageInputStream, imageName);
            String text = processPostText(notification.getTitle(), notification.getContent());
            return SendPhoto.builder()
                    .photo(photo)
                    .chatId("54504156056")
                    .parseMode("HTML")
                    .caption(text)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String processPostText(Advertisement advertisement) {
        return postTextProcessors.stream()
                .filter(postTextProcessor -> postTextProcessor.isApplicable(advertisement))
                .map(processor -> processor.process(advertisement))
                .filter(s -> !s.isEmpty() && !s.isBlank())
                .collect(Collectors.joining("\n"));
    }

    private String processPostText(String title, String content) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(PostTextProcessor.getBoldHtmlStyle(title))
                .append("\n\n")
                .append(content)
                .toString();

    }


}
