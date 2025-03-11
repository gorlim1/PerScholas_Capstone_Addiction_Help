package org.roche.perscholas_capstone_addiction_help.service;

import jakarta.transaction.Transactional;
import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    public void createTreatment(Treatment treatment) {
        treatmentRepository.save(treatment);
    }

    public List<Treatment> getApprovedTreatments() {
        return treatmentRepository.findByVerified(true);
    }

    public List<Treatment> getPendingTreatments() {
        return treatmentRepository.findAll();
    }

    @Transactional
    public void approveTreatment(Long id) {
        Treatment treatment = treatmentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Treatment not found"));
        treatment.setVerified(true);
        treatmentRepository.save(treatment);
    }

    public List<Treatment> getTreatmentsByInsurance(String insurance) {
        return treatmentRepository.findByInsurance(insurance);
    }
}