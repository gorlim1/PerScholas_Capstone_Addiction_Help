package org.roche.perscholas_capstone_addiction_help.repository;

import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findByVerified(boolean verified);

    @Query("SELECT t FROM Treatment t WHERE t.insurance = ?1 AND t.verified = true")
    List<Treatment> findByInsurance(String insurance);
}
