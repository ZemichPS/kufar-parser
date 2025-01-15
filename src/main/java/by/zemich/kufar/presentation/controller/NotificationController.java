package by.zemich.kufar.presentation.controller;

import by.zemich.kufar.domain.model.Notification;
import by.zemich.kufar.presentation.controller.dto.request.NotificationDto;
import by.zemich.kufar.presentation.controller.dto.response.NotificationResponseDto;
import by.zemich.kufar.presentation.controller.mapper.NotificationMapper;
import by.zemich.kufar.application.service.ImageService;
import by.zemich.kufar.application.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final ImageService imageService;


    @PostMapping()
    public ResponseEntity<URI> notify(
            @RequestBody NotificationDto notificationDto
    ) {
        String imageName = imageService.saveNotificationImage(notificationDto.getImage());
        Notification notification = NotificationMapper.toEntity(notificationDto);
        notification.setImageName(imageName);
        UUID uuid = notificationService.notifyAll(notification);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{notificationId}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}")
    public ResponseEntity<URI> notifyById(
            @RequestBody NotificationDto notificationDto, @PathVariable String id
    ) {
        String imageName = imageService.saveNotificationImage(notificationDto.getImage());
        Notification notification = NotificationMapper.toEntity(notificationDto);
        notification.setImageName(imageName);
        UUID uuid = notificationService.notifyById(notification, id);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{notificationId}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{userId}")
    public ResponseEntity<URI> notifyUserById(
            @RequestBody NotificationDto notificationDto,
            @PathVariable UUID userId
    ) {
        String imageName = imageService.saveNotificationImage(notificationDto.getImage());
        Notification notification = NotificationMapper.toEntity(notificationDto);
        notification.setImageName(imageName);
        UUID uuid = notificationService.notifyUserById(userId, notification);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{notificationId}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/users")
    public ResponseEntity<URI> notifyAllUsers(
            @RequestBody NotificationDto notificationDto
    ) {
        String imageName = imageService.saveNotificationImage(notificationDto.getImage());
        Notification notification = NotificationMapper.toEntity(notificationDto);
        notification.setImageName(imageName);
        UUID uuid = notificationService.notifyAllUsers(notification);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{notificationId}")
                .buildAndExpand(uuid)
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping()
    public ResponseEntity<List<NotificationResponseDto>> getAll() {
        List<NotificationResponseDto> responses = notificationService.getAll().stream()
                .map(NotificationMapper::toResponseDto)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/notificationId")
    public ResponseEntity<NotificationResponseDto> getById(@PathVariable UUID notificationId) {
        NotificationResponseDto respons = NotificationMapper.toResponseDto(notificationService.getById(notificationId));
        return ResponseEntity.ok(respons);
    }


}
