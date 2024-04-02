package com.BackendTc.TuCafe.repository;

import com.BackendTc.TuCafe.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByEmail(String email);

    Client findById(long id);

}
