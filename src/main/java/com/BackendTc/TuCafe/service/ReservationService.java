package com.BackendTc.TuCafe.service;

import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Client;
import com.BackendTc.TuCafe.model.Reservation;
import com.BackendTc.TuCafe.model.request.ReservationRequest;
import com.BackendTc.TuCafe.repository.BusinessRepository;
import com.BackendTc.TuCafe.repository.ClientRepository;
import com.BackendTc.TuCafe.repository.ReservationRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final JavaMailSender javaMailSender;
    private final BusinessRepository businessRepository;

    public String reservation(ReservationRequest request) throws MessagingException {
        Client client = clientRepository.findByEmail(request.getEmail());
        Business business = businessRepository.findByName(request.getName());
        if (business == null) {
            throw new RuntimeException("Establecimiento no encontrado");
        }

        // Validación de la hora y la fecha
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDateTime = LocalDateTime.of(request.getDate(), request.getHour());
        long hoursDifference = ChronoUnit.HOURS.between(now, reservationDateTime);
        if (hoursDifference < 3) {
            throw new RuntimeException("La reserva debe hacerse al menos 3 horas después de la fecha y hora actual.");
        }

        Reservation reservation = Reservation.builder()
                .clientId(client)
                .businessId(business)
                .date(request.getDate())
                .hour(request.getHour())
                .description(request.getDescription())
                .status(false)
                .build();

        sendRegistrationEmail(client.getEmail(), client.getName(), business.getName(), reservation.getDate(), reservation.getHour());
        reservationRepository.save(reservation);

        return "Reserva realizada con éxito";
    }

    public void sendRegistrationEmail(String to, String name, String name_business, LocalDate date_reser, LocalTime hour_reser) throws MessagingException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject("!Reserva Exitosa¡");

        // HTML del contenido del correo electrónico
        String htmlContent = "<div style='background-image: url(\"cid:background\"); background-size: cover; background-position: center; background-repeat: repeat; background-color: rgba(255, 255, 255, 0.3); height: 500px; display: flex; justify-content: center; align-items: center;'>" +
                "<div style='text-align: center; color: white;'>" +
                "<p style='font-size: 24px; font-weight: bold; color: white;'>¡Hola " + name + ",!</p><br>" +
                "<p style='font-size: 16px; color: white;'>¡Acabas de realizar un reserva en " + name_business + "! en la fecha " + date_reser + " y a esta hora " + hour_reser + "!</p>"+
                "<p style='font-size: 14px; color: white;'>Gracias por reservar en " + name_business + "! Recuerda estar 10 minutos antes en nuestro establecimiento.</p>" +
                "<img src='cid:logo' width='150' style='border: 3px solid #663300; border-radius: 50%;'>" +
                "<p style='font-size: 14px; color: white;'>Queremos agradecerte por reservar con nosotros y confiar en nuestros servicios para disfrutar de una experiencia única. Estamos emocionados de tenerte a bordo y estamos seguros de que te encantará lo que tenemos preparado para ti." +
                "<br>" +
                "¡Gracias por unirte a nuestra comunidad de amantes del café!" +
                "<br>" +
                "Atentamente," +
                "<b style='color: white;'>TuCafe</b></p>"+
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


    public String confirmReservation(long reservationId) throws MessagingException {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservation.setStatus(true);
        reservationRepository.save(reservation);

        Client client = reservation.getClientId();

        sendRegistrationEmail(client.getEmail(), client.getName(), reservation.getBusinessId().getName(), reservation.getDate(), reservation.getHour());

        return "Reserva confirmada con éxito";
    }
}