package com.pin.app.challenge.service;

import com.pin.app.challenge.exception.NoClientsFoundException;
import com.pin.app.challenge.mapper.ClientMapper;
import com.pin.app.challenge.model.Client;
import com.pin.app.challenge.model.dto.ClientRequestDTO;
import com.pin.app.challenge.model.dto.KPIResponseDTO;
import com.pin.app.challenge.model.dto.ClientResponseDTO;
import com.pin.app.challenge.repository.ClientRepository;
import com.pin.app.challenge.utils.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    private final ClientRepository repository;
    private final ClientMapper mapper;
    private final MathService mathService;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper,MathService mathService){
        this.repository = clientRepository;
        this.mapper = clientMapper;
        this.mathService = mathService;
    }

    public Client saveClient(ClientRequestDTO request) {
        ClientUtils.validateAge(request.getAge(), request.getBirthDay());
        logger.info("Saving a new client with name: {}", request.getFirstName());
        return repository.save(mapper.toEntity(request));
    }

    public KPIResponseDTO getClientsKPI() {
        logger.info("Trying to calculate clients KPI...");
        List<Client> clients = repository.findAll();
        if (clients.isEmpty()) {
            logger.warn("No clients found, returning default KPI values.");
            throw new NoClientsFoundException("No clients found");
        }

        List<Integer> ages = clients.stream().map(Client::getAge).toList();
        logger.debug("Calculated ages list for clients: {}", ages);

        double averageAge = mathService.calculateAverageAge(ages);
        double standardDeviation = mathService.calculateStandardDeviation(ages, averageAge);

        logger.info("Calculated KPI: Average Age = {}, Standard Deviation = {}", averageAge, standardDeviation);
        return new KPIResponseDTO(averageAge, standardDeviation);
    }

    public List<ClientResponseDTO> getAllClients() {
        logger.info("Getting all clients...");
        List<ClientResponseDTO> clients = repository.findAll().stream()
                .map(client -> mapper.toResponseDTO(client, mathService.estimateDeathDate(client)))
                .collect(Collectors.toList());

        if (clients.isEmpty()) {
            throw new NoClientsFoundException("No clients found");
        }

        return clients;
    }

}
