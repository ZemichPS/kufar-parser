package by.zemich.kufar.service;

import by.zemich.kufar.dao.entity.Advertisement;
import by.zemich.kufar.dao.entity.Notification;
import by.zemich.kufar.service.api.PostTextProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostManager {

    private final List<PostTextProcessor> postTextProcessors;
    private final FileLoader fileLoader;
    private final ImageService imageService;
    private final PostLimitedCache<Long, SendPhoto> postLimitedCache = new PostLimitedCache<>(500);

    public SendPhoto createPhotoPostFromAd(Advertisement advertisement) {
        // TODO написать логику создания поста
        return postLimitedCache.computeIfAbsent(advertisement.getAdId(), id -> {
            InputFile photo = getInputFileFromLink(advertisement.getPhotoLink());
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

    private InputFile getInputFileFromLink(String photoLink) {
        File imageFile;
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
