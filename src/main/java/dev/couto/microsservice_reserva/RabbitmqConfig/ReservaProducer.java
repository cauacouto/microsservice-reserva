package dev.couto.microsservice_reserva.RabbitmqConfig;

import dev.couto.microsservice_reserva.Dto.ReservaProducerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservaProducer {


    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange reservaExchange;


    public void enviar(ReservaProducerDto reservaProducer){
        rabbitTemplate.convertAndSend(
                reservaExchange.getName(),
                "",
                reservaProducer
        );
    }
}
