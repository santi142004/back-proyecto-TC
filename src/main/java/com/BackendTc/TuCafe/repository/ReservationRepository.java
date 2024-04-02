package com.BackendTc.TuCafe.repository;

import com.BackendTc.TuCafe.model.Business;
import com.BackendTc.TuCafe.model.Client;
import com.BackendTc.TuCafe.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByClientId(Client clientId);

    List<Reservation> findAllByBusinessId(Business businessId);
}
