package by.zemich.kufar.infrastructure.scheduler;

import by.zemich.kufar.application.service.*;
import by.zemich.kufar.application.service.advertisementhandlers.api.AdvertisementHandler;
import by.zemich.kufar.application.service.api.AdvertisementPublisher;
import by.zemich.kufar.domain.model.Advertisement;
import by.zemich.kufar.domain.model.Category;
import by.zemich.kufar.domain.model.Manufacturer;
import by.zemich.kufar.domain.service.conditionanalizers.ConditionAnalyzer;
import by.zemich.kufar.infrastructure.clients.KufarClient;
import by.zemich.kufar.infrastructure.clients.NIOKufarClient;
import by.zemich.kufar.infrastructure.clients.dto.CategoriesDto;
import by.zemich.kufar.infrastructure.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

@Service
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ManufacturesScheduledService {
    private final KufarClient kufarClient;
    private final ManufactureService manufactureService;
    private final ModelService modelService;


    @Scheduled(initialDelay = 15_000, fixedDelay = 21_600_000)
    public void getAndUpdateManufacturesAndModelsList() {
        kufarClient.getFilledManufacture()
                .forEach(manufacturerDto -> {
                    manufactureService.getById(manufacturerDto.getId())
                            .ifPresentOrElse(
                                    manufacturer -> {
                                        manufacturerDto.getModels().stream()
                                                .filter(modelDto -> !modelService.existsByName(modelDto.getName()))
                                                .map(Mapper::mapToEntity)
                                                .forEach(model -> {
                                                    manufacturer.addModel(model);
                                                    manufactureService.save(manufacturer);
                                                });
                                    },
                                    () -> {
                                        Manufacturer manufacturer = Mapper.mapToEntity(manufacturerDto);
                                        manufacturerDto.getModels().stream()
                                                .filter(Objects::nonNull)
                                                .map(Mapper::mapToEntity)
                                                .forEach(manufacturer::addModel);
                                        manufactureService.save(manufacturer);
                                    });
                });
    }

}
