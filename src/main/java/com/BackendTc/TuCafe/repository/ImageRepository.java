package com.BackendTc.TuCafe.repository;

import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository  extends JpaRepository<Image, Long> {

    List<Image> findByBusiness(Business business);
}
