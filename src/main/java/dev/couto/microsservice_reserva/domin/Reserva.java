package dev.couto.microsservice_reserva.domin;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.couto.microsservice_reserva.Enum.statusReserva;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reserva")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Reserva implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataInicio;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime dataFim;
    @Enumerated(value = EnumType.STRING)
    private statusReserva status;
    private UUID usuarioId;
    private Integer salaId;




}
