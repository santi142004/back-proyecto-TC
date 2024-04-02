package com.BackendTc.TuCafe.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "view")
public class View {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_view;

    @NotNull
    private String comment;

    @NotNull
    private int stars;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;


}
