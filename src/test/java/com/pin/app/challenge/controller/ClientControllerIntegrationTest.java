package com.pin.app.challenge.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pin.app.challenge.model.dto.ClientRequestDTO;
import com.pin.app.challenge.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createClient_ShouldReturn201() throws Exception {
        ClientRequestDTO request = new ClientRequestDTO("Franco", "Miro", 25, LocalDate.of(1999, 3, 2));

        mockMvc.perform(post("/clientes/creacliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Client registered successfully"));
    }
    @Test
    void createClient_ShouldReturn400_WhenAgeIsNotValidBaseDontheBirthDate() throws Exception {
        ClientRequestDTO request = new ClientRequestDTO("Franco", "Miro", 30, LocalDate.of(1999, 3, 2));

        mockMvc.perform(post("/clientes/creacliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.age", is("The age is not valid based on the birth date.")));
    }

    @Test
    void createClient_ShouldReturn400_WhenInvalidData() throws Exception {
        ClientRequestDTO request = new ClientRequestDTO("", "", -5, LocalDate.now().plusDays(1));

        mockMvc.perform(post("/clientes/creacliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.birthDay", is("BirthDay must be a past date")))
                .andExpect(jsonPath("$.age", is("Age must be a positive number")))
                .andExpect(jsonPath("$.firstName", is("FirstName is required")))
                .andExpect(jsonPath("$.lastName", is("LastName is required")));

    }

    @Test
    void getClientKpis_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/clientes/kpideclientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.averageAge", is(notNullValue())))
                .andExpect(jsonPath("$.standardDeviation", is(notNullValue())));
    }

    @Test
    void getClientKpis_ShouldReturn404_WhenNoClients() throws Exception {
        clientRepository.deleteAll();
        mockMvc.perform(get("/clientes/kpideclientes"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("No clients found")));
    }

    @Test
    void listClients_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/clientes/listclientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", isA(List.class)));
    }

    @Test
    void listClients_ShouldReturn404_WhenNoClients() throws Exception {
        clientRepository.deleteAll();
        mockMvc.perform(get("/clientes/listclientes"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error", is("No clients found")));
    }
}

