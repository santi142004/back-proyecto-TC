package com.BackendTc.TuCafe.repository;

import com.BackendTc.TuCafe.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {
    Business findByEmail(String email);

    Business findByName(String name);

    List<Business> findByStatus(boolean status);
}
