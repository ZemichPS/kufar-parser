package by.zemich.kufar.presentation.controller.mapper;

import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.presentation.controller.dto.request.NotificationDto;
import by.zemich.kufar.presentation.controller.dto.response.NotificationResponseDto;

public class NotificationMapper {
    public static Notification toEntity(NotificationDto notificationDto) {
        return Notification.builder()
                .title(notificationDto.getTitle())
                .content(notificationDto.getMessage())
                .build();
    }

    public static NotificationResponseDto toResponseDto(Notification notification) {
        return NotificationResponseDto.builder()
                .title(notification.getTitle())
                .content(notification.getContent())
                .imageName(notification.getImageName())
                .build();
    }
}
