package com.pin.app.challenge.mapper;

import com.pin.app.challenge.model.Client;
import com.pin.app.challenge.model.dto.ClientRequestDTO;
import com.pin.app.challenge.model.dto.ClientResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ClientMapper {

    private static final Logger logger = LoggerFactory.getLogger(ClientMapper.class);

    public Client toEntity(ClientRequestDTO request) {
        logger.debug("Mapping ClientRequestDTO to Client: {}", request);

        Client client = new Client();
        client.setFirstName(request.getFirstName());
        client.setLastName(request.getLastName());
        client.setBirthDay(request.getBirthDay());
        client.setAge(request.getAge());

        return client;
    }

    public ClientResponseDTO toResponseDTO(Client client, LocalDate estimatedDeathDate) {
        logger.debug("Mapping Client to ClientResponseDTO: {}", client);

        return new ClientResponseDTO(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getAge(),
                client.getBirthDay(),
                estimatedDeathDate
        );
    }
}
