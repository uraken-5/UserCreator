package com.jcarmona.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  
public class UserDto {
    @Schema(description = "Nombre del usuario", example = "Juan Rodriguez")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "El correo no cumple con el formato")
    @Schema(description = "Correo electrónico del usuario", example = "juan@rodriguez.org")
    private String email;

    @Pattern(regexp = "^[A-Za-z0-9]{7,12}$", message = "La contraseña solo debe contener letras y números, y debe tener entre 7 y 12 caracteres")
    @Schema(description = "Contraseña del usuario", example = "hunter2")
    private String password;

    @NotNull
    @Schema(description = "Lista de teléfonos del usuario", example = "[{\"number\": 1234567, \"cityCode\": 1, \"countryCode\": \"57\"}]")
    private List<PhoneDto> phones = new ArrayList<>(); 
}