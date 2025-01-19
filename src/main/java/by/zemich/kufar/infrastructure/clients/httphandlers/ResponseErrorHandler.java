package by.zemich.kufar.infrastructure.clients.httphandlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

@Slf4j
@Component
public class ResponseErrorHandler extends DefaultResponseErrorHandler {
    protected void handleError(ClientHttpResponse response, HttpStatusCode statusCode, URI url, HttpMethod method) throws IOException {
        log.error("HTTP Error: {}. Status code: {}", response.getBody(), statusCode);
    }
}
