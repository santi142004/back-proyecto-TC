package com.BackendTc.TuCafe.model.request;

import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @NotNull
    String email;

    @NotNull
    String name;

    @NotNull
    LocalDate date;

    @NotNull
    LocalTime hour;

    String description;

}
