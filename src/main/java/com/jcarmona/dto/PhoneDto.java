package com.jcarmona.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PhoneDto {
	@NotNull
    @Schema(description = "Número de teléfono", example = "1234567")
    private long number;

	@NotNull
    @Schema(description = "Código de la ciudad", example = "1")
    private int cityCode;

	@NotNull
    @Schema(description = "Código del país", example = "57")
    private String countryCode;
}