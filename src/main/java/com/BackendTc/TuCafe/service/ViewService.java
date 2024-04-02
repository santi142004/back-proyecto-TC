package com.BackendTc.TuCafe.service;

import com.BackendTc.TuCafe.model.View;
import com.BackendTc.TuCafe.model.request.ViewRequest;
import com.BackendTc.TuCafe.repository.ClientRepository;
import com.BackendTc.TuCafe.repository.ViewRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {

    private final ClientRepository clientRepository;
    private final ViewRepository viewRepository;

    public String view(ViewRequest request) throws MessagingException {

        View view = View.builder()
                .stars(request.getStars())
                .comment(request.getComment())
                .client(clientRepository.findById(request.getClient()))
                .build();

        viewRepository.save(view);
        return "Tu comentario ha sido enviado con exito";
    }
}
