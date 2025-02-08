package com.pin.app.challenge.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO response of clients KPI")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KPIResponseDTO {

    private double averageAge;
    private double standardDeviation;
}
