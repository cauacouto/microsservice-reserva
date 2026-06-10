package dev.couto.microsservice_reserva.ClientConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SalaClient {

    private final WebClient webClient;

    public SalaClient(
            WebClient.Builder builder,
            @Value("${services.sala.url}") String baseUrl){
        this.webClient = builder
                .baseUrl(baseUrl)
                .build();



    }
}
