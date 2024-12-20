package by.zemich.kufar.service.clients;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdmitadClient {

    public String getAffiliatedLink(String originalLink) {
        // TODO написать логику получение аффилированных ссылок
        String affiliatedLink = originalLink;
        return getReducedLink(affiliatedLink);
    }

    public String getReducedLink(String link) {
        return null;
    }
}
