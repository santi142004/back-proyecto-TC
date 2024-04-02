package com.BackendTc.TuCafe.service;

import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Image;
import com.BackendTc.TuCafe.repository.BusinessRepository;
import com.BackendTc.TuCafe.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final BusinessRepository businessRepository;

    public void uploadImage(Long businessId, String url) {
        System.out.println("Entro al servicio");
        Optional<Business> optionalBusiness = businessRepository.findById(businessId);
        System.out.println("Establecimiento: " + optionalBusiness);
        if (optionalBusiness.isEmpty()) {
            throw new IllegalArgumentException("Establecimientio " + businessId + " no encontrado");
        }
        Business business = optionalBusiness.get();
        System.out.println("Establecimiento despues del get: " + business);

        Image image = Image.builder()
                .url(url)
                .business(business)
                .build();

        imageRepository.save(image);
        System.out.println("Imagen: " + image);
    }

    public List<String> getImagesUrlsByBusinessId(Long businessId) {
        Optional<Business> optionalBusiness = businessRepository.findById(businessId);
        if (optionalBusiness.isEmpty()) {
            throw new IllegalArgumentException("Establecimiento " + businessId + " no encontrado");
        }
        Business business = optionalBusiness.get();
        List<String> imageUrls = imageRepository.findByBusiness(business)
                .stream()
                .map(Image::getUrl) // Mapea cada Image a su URL
                .collect(Collectors.toList()); // Colecta las URLs en una lista
        return imageUrls;
    }
}
