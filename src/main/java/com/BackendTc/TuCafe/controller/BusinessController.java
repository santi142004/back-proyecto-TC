package com.BackendTc.TuCafe.controller;

import com.BackendTc.TuCafe.controller.response.TokenResponse;
import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Image;
import com.BackendTc.TuCafe.model.Reservation;
import com.BackendTc.TuCafe.model.request.*;
import com.BackendTc.TuCafe.repository.ReservationRepository;
import com.BackendTc.TuCafe.service.BusinessService;
import com.BackendTc.TuCafe.service.ImageService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("tuCafe/v1/business")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class BusinessController {

    private final BusinessService businessService;
    private final ReservationRepository reservationRepository;
    private final ImageService imageService;


    //Controlador finalizado para DESPLEGAR Y PRESENTAR
    @PostMapping(value = "register")
    public ResponseEntity<String> registerBusiness(@RequestBody RegisterBusinessRequest request) throws MessagingException {
        return businessService.registerBusiness(request);
    }

    //Controlador finalizado para DESPLEGAR Y PRESENTAR
    @PostMapping(value = "login")
    public ResponseEntity<TokenResponse> loginBusiness(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(businessService.loginBusiness(request));
    }

    @PutMapping(value = "{idBusiness}")
    public Business updateBusinessProfile(@PathVariable Long idBusiness, @RequestBody UpdateBusinessRequest request) {
        return businessService.updateProfBusiness(idBusiness, request);
    }

    @GetMapping(value = "getBusinessActive")
    public List<Business> getBusinessActive(){
        return businessService.filterBusinessByActiveStatus();
    }

    @GetMapping(value = "getBusinessNotActive")
    public List<Business> getBusinessNotActive(){
        return businessService.filterBusinessByInactiveStatus();
    }

    @GetMapping("{idBusiness}")
    public ResponseEntity<Business> getBusinessById(@PathVariable Long idBusiness) {
        Business business = businessService.findBusinessById(idBusiness);
        if (business != null) {
            return new ResponseEntity<>(business, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "changeStatusReservation/{reservationId}")
    public ResponseEntity<String> changeBusinessStatus(@PathVariable Long reservationId, @RequestBody ChangeStatusRequest request) {
        boolean statusChanged = businessService.changeReservationStatus(reservationId, request.getStatus());
        if (statusChanged) {
            return ResponseEntity.ok("Estado de la reserva cambiado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("No se pudo cambiar el estado del establecimiento");
        }
    }

    @PutMapping(value = "{idBusiness}/changePassword")
    public ResponseEntity<String> changeBusinessPassword(@PathVariable Long idBusiness, @RequestBody ChangePasswordRequest request) {
        // Llama al servicio para cambiar la contraseña y manejar la respuesta
        return businessService.changeBusinessPassword(idBusiness, request);
    }

    @PostMapping(value = "upload/{idBusiness}")
    public ResponseEntity<String> uploadImage(@PathVariable Long idBusiness, @RequestBody String url) {
        System.out.println("Entro al controlador");
        try {
            System.out.println("Entro al try");
            imageService.uploadImage(idBusiness, url);
            System.out.println("Salio del service");
            return new ResponseEntity<>("Imagen subida con exito", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Entro al catch");
            return new ResponseEntity<>("Error al subir la imagen: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

