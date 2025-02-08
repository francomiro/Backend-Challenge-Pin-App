package com.pin.app.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "DTO for creating a new client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

    @Schema(description = "First name of the client", example = "Franco")
    @NotBlank(message = "FirstName is required")
    private String firstName;

    @Schema(description = "Last name of the client", example = "Miro")
    @NotBlank(message = "LastName is required")
    private String lastName;

    @Schema(description = "Age of the client", example = "25")
    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be a positive number")
    @Max(value = 150, message = "Age must be real")
    private Integer age;

    @Schema(description = "Birthday of the client", example = "1999-03-02")
    @NotNull(message = "BirthDay is required")
    @Past(message = "BirthDay must be a past date")
    private LocalDate birthDay;
}
