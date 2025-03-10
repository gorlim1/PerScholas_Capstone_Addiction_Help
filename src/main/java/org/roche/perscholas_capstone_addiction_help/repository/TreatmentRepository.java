package org.roche.perscholas_capstone_addiction_help.repository;

import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {
    List<Treatment> findByTreatment(User user);
}
