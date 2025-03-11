package org.roche.perscholas_capstone_addiction_help.repository;

import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.model.User;
import org.roche.perscholas_capstone_addiction_help.model.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {

    List<VerificationRequest> findByApprovedFalse();
}
