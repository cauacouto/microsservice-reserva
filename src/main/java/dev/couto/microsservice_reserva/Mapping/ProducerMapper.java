package dev.couto.microsservice_reserva.Mapping;


import dev.couto.microsservice_reserva.Dto.ReservaProducerDto;
import dev.couto.microsservice_reserva.domin.Reserva;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProducerMapper {


    @Mapping(source = "reserva.id", target = "reservaId")
//    @Mapping(source = "reserva.usuarioId", target = "usuarioId")
//    @Mapping(source = "reserva.salaId", target = "salaId")
//    @Mapping(source = "reserva.dataInicio", target = "dataHoraInico")
//    @Mapping(source = "reserva.dataFim", target = "dataHoraFim")
    @Mapping(source = "email", target = "email")
    ReservaProducerDto toEvent(Reserva reserva, String email);





    }








