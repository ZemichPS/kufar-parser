package by.zemich.kufar.domain.service;

import org.springframework.stereotype.Service;

@Service
public class EmojiService {

    public String getByCategory(String category) {
        return switch (category){
            case "17010" -> "\uD83D\uDCF1";
            case "8110" -> "\uD83D\uDC8E";
            case "8060" -> "\uD83D\uDC90";
            case "8020" -> "\uD83D\uDC8D";
            case "8100" -> "\uD83D\uDC60";
            default -> "";
        };
    }
}
