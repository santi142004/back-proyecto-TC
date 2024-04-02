package com.BackendTc.TuCafe.controller;

import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Reservation;
import com.BackendTc.TuCafe.model.request.ReservationRequest;
import com.BackendTc.TuCafe.repository.BusinessRepository;
import com.BackendTc.TuCafe.repository.ClientRepository;
import com.BackendTc.TuCafe.repository.ReservationRepository;
import com.BackendTc.TuCafe.service.ReservationService;
import com.BackendTc.TuCafe.model.Client;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tuCafe/v1/reservation")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final BusinessRepository businessRepository;

    @PostMapping(value = "creation_reservation")
    public ResponseEntity<String> reservation( @RequestBody ReservationRequest request) throws MessagingException {
        return ResponseEntity.ok(reservationService.reservation(request));
    }

    @PostMapping(value = "confirm_reservation")
    public ResponseEntity<String>confirm(@RequestBody Long reservationId) throws MessagingException {
        return ResponseEntity.ok(reservationService.confirmReservation(reservationId));
    }

    @GetMapping("reservaClient/{clientId}")
    public ResponseEntity<List<Reservation>> getReservationsByClientId(@PathVariable Long clientId) {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
            List<Reservation> reservations = reservationRepository.findAllByClientId(client);
            return ResponseEntity.ok(reservations);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }

    @GetMapping("reservaBusiness/{businessId}")
    public ResponseEntity<List<Reservation>> getReservationsByBusinessId(@PathVariable Long businessId) {
        Optional<Business> optionalBusiness = businessRepository.findById(businessId);
        if (optionalBusiness.isPresent()) {
            Business business = optionalBusiness.get();
            List<Reservation> reservations = reservationRepository.findAllByBusinessId(business);
            return ResponseEntity.ok(reservations);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }
}
