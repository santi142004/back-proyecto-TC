package com.BackendTc.TuCafe.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "client")
public class Client {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id_client;

        @NotNull
        private String name;

        @NotNull
        @Email
        private String email;

        private String phone;

        @NotNull
        private String password;

        private String city;
        private String country;
        private String photo;
        private String role;
}
