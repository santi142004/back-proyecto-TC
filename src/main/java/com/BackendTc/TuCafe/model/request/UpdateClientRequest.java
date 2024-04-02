package com.BackendTc.TuCafe.model.request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientRequest {


    private String name;
    private String phone;

    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una letra mayúscula")
    @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "La contraseña debe contener al menos un carácter especial")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private String city;
    private String country;
    private String photo;

}
