package com.BackendTc.TuCafe.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long id_reservation;

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id_client")
    private Client clientId;

    @ManyToOne
    @JoinColumn(name = "id_business", referencedColumnName = "id_business")
    private Business businessId;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "hour")
    private LocalTime hour;

    @Column(name = "status")
    private boolean status;





}
