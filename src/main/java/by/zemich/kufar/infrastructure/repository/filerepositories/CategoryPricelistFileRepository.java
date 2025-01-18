package by.zemich.kufar.infrastructure.repository.filerepositories;

import by.zemich.kufar.application.service.api.CategoryPriceListRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryPricelistFileRepository implements CategoryPriceListRepository {
    private final String SHOES_TYPE_PRICE_LIST_FILENAME = "shoes_type_price_list.txt";
    private final String CLOTHING_TYPE_PRICE_LIST_FILENAME = "clothing_type_price_list.txt";

    public Map<String, BigDecimal> getCategoryShoesPriceList() {
        try {
            Path pathToFile = Path.of(ClassLoader.getSystemResource(SHOES_TYPE_PRICE_LIST_FILENAME)
                    .toURI());

            return Files.readAllLines(pathToFile).stream()
                    .map(String::toLowerCase)
                    .map(line -> line.split("-"))
                    .collect(Collectors.toMap(
                            array -> array[0].trim(),
                            array -> new BigDecimal(array[1].trim())
                    ));

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<String, BigDecimal> getCategoryClothesPriceList() {
        try {
            Path pathToFile = Path.of(ClassLoader.getSystemResource(CLOTHING_TYPE_PRICE_LIST_FILENAME)
                    .toURI());

            return Files.readAllLines(pathToFile).stream()
                    .map(String::toLowerCase)
                    .map(line -> line.split("-"))
                    .collect(Collectors.toMap(
                            array -> array[0].trim().toLowerCase(),
                            array -> new BigDecimal(array[1].trim())
                    ));

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
