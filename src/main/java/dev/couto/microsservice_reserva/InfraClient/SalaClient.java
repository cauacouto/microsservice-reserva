package dev.couto.microsservice_reserva.InfraClient;


import dev.couto.microsservice_reserva.Dto.SalaDtoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sala-service")
public interface SalaClient {


    @GetMapping("/sala/{id}")
    SalaDtoResponse buscarSala(@PathVariable Integer id);

}
