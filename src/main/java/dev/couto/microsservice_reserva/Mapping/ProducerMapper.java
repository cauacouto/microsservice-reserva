package dev.couto.microsservice_reserva.Mapping;


import dev.couto.microsservice_reserva.Dto.ReservaProducerDto;
import dev.couto.microsservice_reserva.domin.Reserva;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProducerMapper {


    ReservaProducerDto toEvent(Reserva reserva);






}
