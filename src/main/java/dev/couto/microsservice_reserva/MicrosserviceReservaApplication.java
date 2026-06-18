package dev.couto.microsservice_reserva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MicrosserviceReservaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicrosserviceReservaApplication.class, args);
	}

}
