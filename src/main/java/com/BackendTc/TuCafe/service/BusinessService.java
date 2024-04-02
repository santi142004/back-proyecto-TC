package com.BackendTc.TuCafe.service;

import com.BackendTc.TuCafe.controller.response.TokenResponse;
import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Image;
import com.BackendTc.TuCafe.model.Reservation;
import com.BackendTc.TuCafe.model.request.*;
import com.BackendTc.TuCafe.repository.*;
import com.BackendTc.TuCafe.security.TokenUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final AdminRepository adminRepository;
    private final BusinessRepository businessRepository;
    private final ReservationRepository reservationRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final JavaMailSender javaMailSender;

    //Registro terminado y LISTO PARA EXPONER
    public ResponseEntity<String> registerBusiness(@RequestBody RegisterBusinessRequest request) throws MessagingException {

        String responseMessage;
        HttpStatus status;
        Business existingBusiness = businessRepository.findByEmail(request.getEmail());

        if(existingBusiness == null){
            Business business = Business.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role("establecimiento")
                    .status(false)
                    .idAdmin(adminRepository.findById(1L))
                    .category(categoryRepository.findByName(request.getCategory()))
                    .build();

            businessRepository.save(business);
            sendRegistrationEmail(business.getEmail(), business.getName());

            responseMessage = "Te has registrado con éxito";
            status = HttpStatus.OK;

        }else{
            responseMessage = "Usuario ya existente";
            status = HttpStatus.CONFLICT;
        }
        return new ResponseEntity<>(responseMessage, status);
    }

    //Login Business terminado y listo para EXPONER
    public TokenResponse loginBusiness(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        Business business = businessRepository.findByEmail(request.getEmail());
        String token = tokenUtils.getTokenBusiness(business);
        return TokenResponse.builder()
                .token(token)
                .role("establecimiento")
                .build();
    }

    public Business updateProfBusiness(Long id_business, UpdateBusinessRequest request){
        Business business = businessRepository.findById(id_business).orElseThrow(() -> new RuntimeException("Negocio no encontrado"));
        if (business != null){
            if (request.getName() != null){
                business.setName(request.getName());
            }

            if (request.getDescription() != null){
                business.setDescription(request.getDescription());
            }

            if (request.getAddress() != null){
                business.setAddress(request.getAddress());
            }

            if (request.getPhone() != null){
                business.setPhone(request.getPhone());
            }

            if (request.getPassword() != null){
                String newPassowrd = passwordEncoder.encode(request.getPassword());
                business.setPassword(newPassowrd);
            }

            if (request.getCity() != null){
                business.setCity(request.getCity());
            }

            if (request.getStart_hour() != null){
                business.setStart_hour(request.getStart_hour());
            }

            if (request.getImage() != null){
                business.setImage(request.getImage());
            }

            if (request.getFinish_hour() != null){
                business.setFinish_hour(request.getFinish_hour());
            }

            businessRepository.save(business);

            return business;
        }else{
            return null;
        }

    }

    public List<Business> filterBusinessByActiveStatus() {
        List<Business> activeBusinesses = businessRepository.findByStatus(true);
        return activeBusinesses;
    }

    // Método para filtrar establecimientos con estado "false"
    public List<Business> filterBusinessByInactiveStatus() {
        List<Business> inactiveBusinesses = businessRepository.findByStatus(false);
        return inactiveBusinesses;
    }

    public void sendRegistrationEmail(String to, String name) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("!Registro Exitoso¡");

        // HTML del contenido del correo electrónico
        String htmlContent = "<div style='background-image: url(\"cid:background\"); background-size: cover; background-position: center; background-repeat: repeat; background-color: rgba(255, 255, 255, 0.3); height: 500px; display: flex; justify-content: center; align-items: center;'>" +
                "<div style='text-align: center; color: black;'>" +
                "<p style='font-size: 24px; font-weight: bold; color: black;'>¡Hola " + name + ",!</p><br>" +
                "<p style='font-size: 16px; color: black;'>¡Bienvenido a TuCafe!</p>"+
                "<p style='font-size: 14px; color: black;'>Gracias por registrarte en TuCafe! Ahora podrás disfrutar de esta maravillosa experiencia.</p>" +
                "<img src='cid:logo' width='150' style='border: 3px solid #663300; border-radius: 50%;'>" +
                "<p style='font-size: 14px; color: white;'>Queremos agradecerte por registrarte con nosotros y confiar en nuestra plataforma para disfrutar de una experiencia única. Estamos emocionados de tenerte a bordo y estamos seguros de que te encantará lo que tenemos preparado para ti." +
                "<br>" +
                "¡Gracias por unirte a nuestra comunidad de amantes del café!" +
                "<br>" +
                "Atentamente," +
                "<b style='color: black;'>TuCafe</b></p>"+
                "</div>" +
                "</div>";

        String cssStyles = "<style>" +
                "@media screen and (max-width: 768px) {" +
                "   div { height: auto !important; }" +
                "   p { font-size: 14px !important; }" +
                "   .logo { max-width: 40px !important; }" +
                "</style>";


        htmlContent = cssStyles + htmlContent;


        helper.setText(htmlContent, true);

        ClassPathResource backgroundImg = new ClassPathResource("images/fondo.jpeg");
        helper.addInline("background", backgroundImg);

        ClassPathResource logoImg = new ClassPathResource("images/tucafe2.jpeg");
        helper.addInline("logo", logoImg);


        javaMailSender.send(message);
    }

    public Business findBusinessById(Long idBusiness) {
        return businessRepository.findById(idBusiness)
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado con ID: " + idBusiness));
    }

    public boolean changeReservationStatus(Long idReservation, boolean newStatus) {
        System.out.println("Entro al servicio " + idReservation + " " + newStatus);
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrado"));

        if (reservation != null) {
            reservation.setStatus(newStatus);
            reservationRepository.save(reservation);
            return true;
        } else {
            return false;
        }
    }

    public void saveBusiness(Business business) {
        businessRepository.save(business);
    }

    public Optional<Business> findBusinessByIdPassword(Long idBusiness) {
        return businessRepository.findById(idBusiness);
    }

    public ResponseEntity<String> changeBusinessPassword(Long idBusiness, ChangePasswordRequest request) {
        Optional<Business> optionalBusiness = businessRepository.findById(idBusiness);

        if (optionalBusiness.isPresent()) {
            Business business = optionalBusiness.get();

            if (passwordEncoder.matches(request.getCurrentPassword(), business.getPassword())) {
                // Encriptar la nueva contraseña
                String newPasswordEncoded = passwordEncoder.encode(request.getNewPassword());
                // Actualizar la contraseña en la entidad del negocio
                business.setPassword(newPasswordEncoded);
                // Guardar el negocio actualizado en la base de datos
                businessRepository.save(business);
                return ResponseEntity.ok("Contraseña cambiada exitosamente");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("La contraseña actual no es correcta");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
