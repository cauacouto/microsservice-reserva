package dev.couto.microsservice_reserva.Mapping;


import dev.couto.microsservice_reserva.Dto.ReservaRequestDto;
import dev.couto.microsservice_reserva.Dto.ReservaResponseDto;
import dev.couto.microsservice_reserva.domin.Reserva;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservaMapper {




  ReservaResponseDto toDto(Reserva entity);


  Reserva toEntity(ReservaRequestDto dto);


}
