package com.BackendTc.TuCafe.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
public class
RegisterRequest {

    @NotNull
    String name;

    @Email
    String email;

    @Pattern(regexp = ".*[A-Z].*", message = "La contraseña debe contener al menos una letra mayúscula")
    @Pattern(regexp = ".*[^a-zA-Z0-9].*", message = "La contraseña debe contener al menos un carácter especial")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    String password;

    @Pattern(regexp = "cliente|establecimiento", message = "El rol debe ser 'cliente' o 'establecimiento'")
    String role;
}
