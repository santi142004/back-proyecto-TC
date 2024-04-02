package com.BackendTc.TuCafe.model.request;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessRequest {

    private String name;
    private String description;
    private String address;
    private String phone;
    private String image;

    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una letra mayúscula")
    @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "La contraseña debe contener al menos un carácter especial")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private String city;
    private LocalTime start_hour;
    private LocalTime finish_hour;
}
