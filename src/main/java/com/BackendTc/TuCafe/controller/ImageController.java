package com.BackendTc.TuCafe.controller;

import com.BackendTc.TuCafe.model.Image;
import com.BackendTc.TuCafe.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tuCafe/v1/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class ImageController {

    private final ImageService imageService;

    @GetMapping(value = "{idBusiness}")
    public ResponseEntity<List<String>> getImagesUrlsByBusinessId(@PathVariable Long idBusiness) {
        List<String> imageUrls = imageService.getImagesUrlsByBusinessId(idBusiness);
        return new ResponseEntity<>(imageUrls, HttpStatus.OK);
    }


}
