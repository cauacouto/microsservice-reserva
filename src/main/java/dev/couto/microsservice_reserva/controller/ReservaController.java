package dev.couto.microsservice_reserva.controller;

import dev.couto.microsservice_reserva.Dto.ReservaRequestDto;
import dev.couto.microsservice_reserva.Dto.ReservaResponseDto;
import dev.couto.microsservice_reserva.Service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDto> reserva(@RequestBody ReservaRequestDto dto){
        var request = reservaService.reservar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }
}
