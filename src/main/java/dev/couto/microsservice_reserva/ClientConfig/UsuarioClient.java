package dev.couto.microsservice_reserva.ClientConfig;

import dev.couto.microsservice_reserva.Dto.UsuarioDtoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service

public class UsuarioClient {


    private final WebClient webClient;


     public UsuarioClient(
             WebClient.Builder builder,
             @Value("${services.usuario.url}") String baseUrl){

         this.webClient = builder
                 .baseUrl(baseUrl)
                 .build();
     }

     public UsuarioDtoResponse buscarUsuario(UUID id){
         return webClient.get()
                 .uri("/usuario/{id}",id)
                 .retrieve()
                 .bodyToMono(UsuarioDtoResponse.class)
                 .block();
     }




}
