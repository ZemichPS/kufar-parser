package by.zemich.kufar.infrastructure.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
@EnableAspectJAutoProxy
public class WebClientsConfig {

    @Value("${rest-client.user-agent}")
    String useragent;


    @Bean
    WebClient kufarWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15_000) // время на установление соединения (TCP handshake).
                .responseTimeout(Duration.ofSeconds(10)) // общее время на получение ответа от сервера (оно включает и чтение, и запись).
                .doOnConnected(conn -> conn
                        .addHandlerLast(new ReadTimeoutHandler(10)) // Если сервер не отправляет данные в указанный промежуток времени, возникает исключение io.netty.handler.timeout.ReadTimeoutException.
                        .addHandlerLast(new WriteTimeoutHandler(5)) // Если клиент не успевает отправить данные на сервер в указанный промежуток времени, возникает исключение io.netty.handler.timeout.WriteTimeoutException.
                );

        return WebClient.builder()
                .defaultHeader("User-agent", useragent)
                .defaultHeader("content-type", "application/json")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
