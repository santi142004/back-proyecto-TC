package com.BackendTc.TuCafe.repository;

import com.BackendTc.TuCafe.model.Admin;
import com.BackendTc.TuCafe.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findByEmail(String email);
    Admin findById(long id);
    List<Admin> findAll();

}
