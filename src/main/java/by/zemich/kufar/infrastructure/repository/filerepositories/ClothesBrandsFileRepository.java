package by.zemich.kufar.infrastructure.repository.filerepositories;

import by.zemich.kufar.application.service.api.ClothesBrandsRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ClothesBrandsFileRepository implements ClothesBrandsRepository {
    private final String FILENAME = "clothes_brands";

    public List<String> get() {
        try {
            Path path = Path.of(ClassLoader.getSystemResource(FILENAME).toURI());
            return Files.readAllLines(path)
                    .stream()
                    .map(String::toLowerCase)
                    .toList();
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}
