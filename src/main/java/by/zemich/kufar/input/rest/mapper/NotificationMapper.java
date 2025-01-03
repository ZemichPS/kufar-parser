package by.zemich.kufar.input.rest.mapper;

import by.zemich.kufar.dao.entity.Notification;
import by.zemich.kufar.input.rest.dto.request.NotificationDto;
import by.zemich.kufar.input.rest.dto.response.NotificationResponseDto;

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
