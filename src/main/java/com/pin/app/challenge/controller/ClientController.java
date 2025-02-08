package com.pin.app.challenge.controller;

import com.pin.app.challenge.model.dto.ClientRequestDTO;
import com.pin.app.challenge.model.dto.ClientResponseDTO;
import com.pin.app.challenge.model.dto.KPIResponseDTO;
import com.pin.app.challenge.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Nombre todos los componentes de la API en ingles a excepcion de los endpoints para mantener los nombres que menciona la consigna
@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service){
        this.service = service;
    }

    @Operation(summary = "Create a new client")
    @PostMapping("/creacliente")
    public ResponseEntity<String> createClient(@Valid @RequestBody ClientRequestDTO client) {
        service.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body("Client registered successfully");
    }

    @Operation(summary = "Get clients KPIs (average age and standard deviation)")
    @GetMapping("/kpideclientes")
    public ResponseEntity<KPIResponseDTO> getClientKpis() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getClientsKPI());
    }

    @Operation(summary = "Get clients list with estimated death date")
    @GetMapping("/listclientes")
    public ResponseEntity<List<ClientResponseDTO>>  listCustomers() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllClients());
    }


}
