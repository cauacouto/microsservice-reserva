package dev.couto.microsservice_reserva.Dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservaProducerDto(
        Integer reservaId,
        UUID usuarioId,
        Integer salaId,
        LocalDateTime dataHoraInico,
        LocalDateTime dataHoraFim
) {
}
