package com.pin.app.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "DTO response of clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate birthDate;
    private LocalDate estimatedDeathDate;
}
