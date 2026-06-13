package dev.couto.microsservice_reserva.Repository;


import dev.couto.microsservice_reserva.domin.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva,Integer> {

    @Query("select count(r) > 0 from Reserva r " +
            "where r.salaId = :salaId and r.status = 'RESERVADO' " +
            "and r.dataInicio < :dataFimComIntervalo and r.dataFim > :dataInicioMenosIntervalo")
    boolean temConflito(@Param("salaId") Integer salaId,
                        @Param("dataInicioMenosIntervalo") LocalDateTime dataInicioMenosIntervalo,
                        @Param("dataFimComIntervalo") LocalDateTime dataFimComIntervalo);

}
