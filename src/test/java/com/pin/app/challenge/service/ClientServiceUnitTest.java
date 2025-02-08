package com.pin.app.challenge.service;

import com.pin.app.challenge.exception.NoClientsFoundException;
import com.pin.app.challenge.mapper.ClientMapper;
import com.pin.app.challenge.model.Client;
import com.pin.app.challenge.model.dto.ClientRequestDTO;
import com.pin.app.challenge.model.dto.ClientResponseDTO;
import com.pin.app.challenge.model.dto.KPIResponseDTO;
import com.pin.app.challenge.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceUnitTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private MathService mathService;

    @InjectMocks
    private ClientService clientService;

    @Test
    void testSaveClient_ValidRequest_ShouldSaveSuccessfully() {
        ClientRequestDTO request = new ClientRequestDTO("Franco", "Miro", 25, LocalDate.of(1999, 4, 2));
        Client client = new Client();
        client.setFirstName("Franco");
        client.setLastName("Miro");
        client.setAge(25);
        client.setBirthDay(LocalDate.of(1999, 4, 2));

        when(clientMapper.toEntity(request)).thenReturn(client);
        when(clientRepository.save(client)).thenReturn(client);

        Client savedClient = clientService.saveClient(request);

        assertNotNull(savedClient);
        assertEquals("Franco", savedClient.getFirstName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testGetClientsKPI_NoClients_ShouldThrowException() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        NoClientsFoundException exception = assertThrows(NoClientsFoundException.class, () -> clientService.getClientsKPI());
        assertEquals("No clients found", exception.getMessage());
    }

    @Test
    void testGetClientsKPI_WithClients_ShouldReturnKPI() {
        List<Client> clients = Arrays.asList(
                new Client(1L, "Franco", "Miro", 30, LocalDate.of(1995, 3, 2)),
                new Client(2L, "Belen", "Gencarelli", 40, LocalDate.of(1985, 3, 10))
        );
        when(clientRepository.findAll()).thenReturn(clients);
        when(mathService.calculateAverageAge(anyList())).thenReturn(35.0);
        when(mathService.calculateStandardDeviation(anyList(), anyDouble())).thenReturn(5.0);

        KPIResponseDTO kpi = clientService.getClientsKPI();

        assertNotNull(kpi);
        assertEquals(35.0, kpi.getAverageAge());
        assertEquals(5.0, kpi.getStandardDeviation());
    }

    @Test
    void testGetAllClients_NoClients_ShouldThrowException() {
        when(clientRepository.findAll()).thenReturn(Collections.emptyList());

        NoClientsFoundException exception = assertThrows(NoClientsFoundException.class, () -> clientService.getAllClients());
        assertEquals("No clients found", exception.getMessage());
    }

    @Test
    void testGetAllClients_WithClients_ShouldReturnClients() {
        List<Client> clients = Arrays.asList(
                new Client(1L, "Franco", "Miro", 30, LocalDate.of(1995, 3, 2)),
                new Client(2L, "Belen", "Gencarelli", 40, LocalDate.of(1985, 3, 10))
        );
        List<ClientResponseDTO> responseDTOs = Arrays.asList(
                new ClientResponseDTO(1L, "Franco", "Miro", 30, LocalDate.of(1995, 2, 2), LocalDate.of(2070, 2, 2)),
                new ClientResponseDTO(2L, "Belen", "Gencarelli", 40, LocalDate.of(1985, 3, 10), LocalDate.of(2060, 3, 10))
        );

        when(clientRepository.findAll()).thenReturn(clients);
        when(clientMapper.toResponseDTO(any(), any())).thenReturn(responseDTOs.get(0), responseDTOs.get(1));

        List<ClientResponseDTO> result = clientService.getAllClients();

        assertEquals(2, result.size());
        assertEquals("Franco", result.get(0).getFirstName());
        assertEquals("Belen", result.get(1).getFirstName());
    }
}
