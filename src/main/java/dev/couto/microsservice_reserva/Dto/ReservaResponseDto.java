package dev.couto.microsservice_reserva.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.couto.microsservice_reserva.Enum.statusReserva;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservaResponseDto(
        Integer id,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataInicio,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataFim,
        statusReserva status,
        UUID usuarioId,
        Integer salaId


) {
}
