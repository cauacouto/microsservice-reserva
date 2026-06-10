package dev.couto.microsservice_reserva.Dto;

import java.util.UUID;

public record UsuarioDtoResponse(
        UUID id,
        String nome,
        String email
) {
}
