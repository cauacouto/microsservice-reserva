package dev.couto.microsservice_reserva.Dto;

import dev.couto.microsservice_reserva.Enum.StatusSala;

public record SalaDtoResponse(
        Integer id,
        StatusSala status

) {
}
