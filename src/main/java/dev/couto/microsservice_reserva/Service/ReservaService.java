package dev.couto.microsservice_reserva.Service;

import dev.couto.microsservice_reserva.ClientConfig.SalaClient;
import dev.couto.microsservice_reserva.ClientConfig.UsuarioClient;
import dev.couto.microsservice_reserva.Dto.ReservaRequestDto;
import dev.couto.microsservice_reserva.Dto.ReservaResponseDto;
import dev.couto.microsservice_reserva.Dto.SalaDtoResponse;
import dev.couto.microsservice_reserva.Dto.UsuarioDtoResponse;
import dev.couto.microsservice_reserva.Enum.StatusSala;
import dev.couto.microsservice_reserva.Enum.statusReserva;
import dev.couto.microsservice_reserva.Mapping.ReservaMapper;
import dev.couto.microsservice_reserva.Repository.ReservaRepository;
import dev.couto.microsservice_reserva.domin.Reserva;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final SalaClient salaClient;
    private final UsuarioClient usuarioClient;
    private final ReservaMapper reservaMapper;
    private  final ReservaRepository reservaRepository;

    private static final Duration INTERVALO_MINIMO= Duration.ofMinutes(60);


    public ReservaResponseDto reservar(ReservaRequestDto dto){

        if (dto.dataInicio() == null || dto.dataFim() == null){
            throw new IllegalArgumentException("dats não podem ser nulas");
        }

        if (dto.dataInicio().isAfter(dto.dataFim())){
            throw new IllegalArgumentException("datas inicio não pode ser pois  data de fim");
        }

        UsuarioDtoResponse usuario=
                usuarioClient.buscarUsuario(dto.usuarioId());

        SalaDtoResponse sala =
                salaClient.buscarSala(dto.salaId());


        if(sala.status() != StatusSala.ATIVA){
            throw  new IllegalArgumentException("sala inativa");
        }


        LocalDateTime dataInicioMenosIntervalo =
                dto.dataInicio().minus(INTERVALO_MINIMO);

        LocalDateTime dataFimComIntervalo =
                dto.dataFim().plus(INTERVALO_MINIMO);


        if (reservaRepository.temConflito(dto.salaId(), dataInicioMenosIntervalo, dataFimComIntervalo)) {
            throw new IllegalArgumentException("sala ja recervada");
        }


        Reserva reserva = reservaMapper.toEntity(dto);
        reserva.setDataInicio(dto.dataInicio());
        reserva.setDataFim(dto.dataFim());
        reserva.setStatus(statusReserva.RESERVADO);
        reserva.setUsuarioId(usuario.id());
        reserva.setSalaId(sala.id());
        var salvar = reservaRepository.save(reserva);
        return reservaMapper.toDto(salvar);




    }


}
