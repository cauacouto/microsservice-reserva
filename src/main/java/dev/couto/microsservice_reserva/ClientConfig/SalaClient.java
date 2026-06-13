package dev.couto.microsservice_reserva.ClientConfig;

import dev.couto.microsservice_reserva.Dto.SalaDtoResponse;
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


    public SalaDtoResponse buscarSala(Integer id){
        return webClient.get()
                .uri("/sala/{id}",id)
                .retrieve()
                .bodyToMono(SalaDtoResponse.class)
                .block();
    }
}
