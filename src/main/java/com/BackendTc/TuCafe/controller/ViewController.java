package com.BackendTc.TuCafe.controller;

import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.View;
import com.BackendTc.TuCafe.model.request.ViewRequest;
import com.BackendTc.TuCafe.repository.ViewRepository;
import com.BackendTc.TuCafe.service.ViewService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tuCafe/v1/view")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class ViewController {

    private final ViewService viewService;
    private final ViewRepository viewRepository;

    @PostMapping(value = "newView")
    public String view(@RequestBody ViewRequest request) throws MessagingException {
        return viewService.view(request);
    }


    @GetMapping("views")
    public List<View>getViews(){
        return viewRepository.findAll();
    }

}
