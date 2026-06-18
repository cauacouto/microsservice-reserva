package dev.couto.microsservice_reserva.HttpClient;

import dev.couto.microsservice_reserva.Dto.UsuarioDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UsuarioClient {


    @GetMapping("/usuario/{id}")
    UsuarioDtoResponse buscarUsuario(@PathVariable UUID id);







}
