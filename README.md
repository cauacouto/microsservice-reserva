<div align="center">

# 🏢 microsservice-reserva

**Microsserviço de reserva de salas** construído em Spring Boot, com comunicação síncrona via Feign e eventos assíncronos via RabbitMQ.

[![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![RabbitMQ](https://img.shields.io/badge/RabbitMQ-4-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)](https://www.rabbitmq.com/)
[![Docker](https://img.shields.io/badge/Docker-ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Maven](https://img.shields.io/badge/Maven-build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)

![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow?style=flat-square)
![License](https://img.shields.io/badge/license-unlicensed-lightgrey?style=flat-square)

</div>

---

## 📑 Sumário

- [Arquitetura](#-arquitetura)
- [Tecnologias](#-tecnologias)
- [Regras de negócio](#-regras-de-negócio)
- [Endpoints](#-endpoints)
- [Configuração](#-configuração)
- [Como rodar](#-como-rodar)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Roadmap](#-roadmap)

## 🏗 Arquitetura

```
Cliente HTTP
     │
     ▼
ReservaController
     │
     ▼
ReservaService ──► SalaClient (Feign)   → sala-service
     │        └──► UsuarioClient (Feign) → user-service
     │
     ▼
ReservaRepository (JPA / MySQL)
     │
     ▼
ReservaProducer ──► RabbitMQ (fanout exchange: reserva.ex)
```

Ao criar uma reserva, o serviço:

1. Valida as datas de início e fim.
2. Busca o usuário no `user-service`.
3. Busca a sala no `sala-service` e verifica se está **ATIVA**.
4. Verifica conflito de horário na sala (com margem de 60 min antes/depois do intervalo solicitado).
5. Persiste a reserva no banco.
6. Publica um evento no RabbitMQ (`reserva.ex`) com os dados da reserva e o e-mail do usuário.

## 🧰 Tecnologias

| Camada                  | Tecnologia |
|--------------------------|------------|
| Linguagem                | Java 21 |
| Framework                | Spring Boot 4.0.6 |
| Persistência              | Spring Data JPA + MySQL |
| Comunicação síncrona      | Spring Cloud OpenFeign (`sala-service`, `user-service`) |
| Service discovery         | Spring Cloud Netflix Eureka Client |
| Mensageria                | Spring AMQP / RabbitMQ |
| Mapeamento                | MapStruct |
| Boilerplate                | Lombok |
| Empacotamento              | Docker / Docker Compose |

## 📋 Regras de negócio

- Uma reserva só pode ser feita em sala com status `ATIVA`.
- Não é permitido reservar uma sala com conflito de horário — considerando uma margem de segurança de **60 minutos** antes e depois do intervalo solicitado.
- Toda reserva criada é publicada como evento na exchange `reserva.ex` (fanout), permitindo que outros serviços consumam (ex.: envio de e-mail de confirmação).

## 🔌 Endpoints

### ![POST](https://img.shields.io/badge/POST-49CC90?style=flat-square) `/reserva`

Cria uma nova reserva.

**Request body**

```json
{
  "dataInicio": "28/08/2026 14:00",
  "dataFim": "28/08/2026 15:00",
  "usuarioId": "d3f1c2a0-1234-4a5b-8c9d-abcdef123456",
  "salaId": 1
}
```

**Response `201 Created`**

```json
{
  "id": 1,
  "dataInicio": "28/08/2026 14:00",
  "dataFim": "28/08/2026 15:00",
  "status": "RESERVADO",
  "usuarioId": "d3f1c2a0-1234-4a5b-8c9d-abcdef123456",
  "salaId": 1
}
```

**Possíveis erros**

| Situação                          | Status esperado |
|------------------------------------|------------------|
| Datas nulas ou início após o fim   | `400 Bad Request` |
| Sala inativa                       | `400 Bad Request` |
| Conflito de horário na sala        | `409 Conflict`     |

> Nota: no momento essas validações lançam `IllegalArgumentException` sem um `@ControllerAdvice` mapeando os status HTTP acima — isso está no roadmap.

## ⚙️ Configuração

Variáveis de ambiente (com valores padrão para desenvolvimento local):

| Variável                     | Padrão                                  | Descrição                          |
|-------------------------------|------------------------------------------|--------------------------------------|
| `SERVER_PORT`                 | `8083`                                    | Porta do serviço                     |
| `SPRING_DATASOURCE_URL`       | `jdbc:mysql://localhost:3306/reserva_db`  | URL do banco MySQL                   |
| `SPRING_DATASOURCE_USERNAME`  | `root`                                    | Usuário do banco                     |
| `SPRING_DATASOURCE_PASSWORD`  | `root1234`                                | Senha do banco                       |
| `URL_USER`                    | `http://localhost:8081`                   | URL do `user-service`                |
| `URL_SALA`                    | `http://localhost:8082`                   | URL do `sala-service`                |
| `SPRING_RABBITMQ_HOST`        | `localhost`                               | Host do RabbitMQ                     |
| `SPRING_RABBITMQ_PORT`        | `5672`                                    | Porta do RabbitMQ                    |
| `SPRING_RABBITMQ_USERNAME`    | `guest`                                   | Usuário do RabbitMQ                  |
| `SPRING_RABBITMQ_PASSWORD`    | `guest`                                   | Senha do RabbitMQ                    |

## ▶️ Como rodar

### Com Docker Compose (recomendado)

Sobe o RabbitMQ e o próprio serviço (assume que o MySQL, `sala-service` e `user-service` já estão rodando na máquina host):

```bash
docker compose up --build
```

O RabbitMQ Management fica disponível em `http://localhost:15672` (usuário/senha: `guest`/`guest`).

### Localmente (sem Docker)

Pré-requisitos: Java 21, Maven, MySQL rodando com o banco `reserva_db`, RabbitMQ, `sala-service` e `user-service` no ar.

```bash
./mvnw spring-boot:run
```

O serviço sobe em `http://localhost:8083`.

## 📂 Estrutura do projeto

```
src/main/java/dev/couto/microsservice_reserva
├── ClientConfig       # configuração de clients HTTP
├── controller          # endpoints REST
├── domin                # entidade JPA (Reserva)
├── Dto                   # DTOs de request/response e eventos
├── Enum                  # enums de status (Reserva, Sala)
├── InfraClient           # Feign clients (sala-service, user-service)
├── Mapping               # mappers MapStruct
├── RabbitmqConfig        # configuração do RabbitMQ e producer de eventos
├── Repository            # repositório JPA
└── Service               # regras de negócio
```

## 🗺 Roadmap

- [ ] `@Transactional` no fluxo de reserva para evitar condição de corrida em reservas simultâneas na mesma sala/horário
- [ ] `@ControllerAdvice` para mapear exceções de negócio para os status HTTP corretos (`400`/`409`)
- [ ] Endpoint para cancelar reserva (`statusReserva.CANCELADA` já existe no enum, falta o endpoint)
- [ ] Endpoint(s) de consulta (`GET /reserva`, `GET /reserva/{id}`)
- [ ] Validação via Bean Validation (`@Valid` + anotações no DTO)
- [ ] Fallback/circuit breaker para os Feign clients (`sala-service`, `user-service`)
- [ ] Testes de unidade/integração para a regra de conflito de horário
- [ ] Substituir `System.out.println` por logging estruturado (SLF4J)

---

<div align="center">

Feito por [Cauã Couto](https://github.com/cauacouto)

</div>
