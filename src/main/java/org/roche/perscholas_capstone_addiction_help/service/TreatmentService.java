package org.roche.perscholas_capstone_addiction_help.service;

import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.model.User;
import org.roche.perscholas_capstone_addiction_help.repository.TreatmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    public Treatment getTreatmentById(Long id) {
        return treatmentRepository.findById(id).orElse(null);
    }

    public Treatment saveTreatment(Treatment treatment) {
        return treatmentRepository.save(treatment);
    }

    public void deleteTreatment(Long id) {
        treatmentRepository.deleteById(id);
    }

    public void saveTreatments(List<Treatment> treatments) {
        treatmentRepository.saveAll(treatments);

    }

    public List<Treatment> getTreatmentsByUser(User user) {
        return treatmentRepository.findByTreatment(user);
    }

    public void updateTreatmentStatus(Long treatmentId, String verified) {
        Treatment treatment = getTreatmentById(treatmentId);
        treatment.getVerified(verified);
        treatmentRepository.save(treatment);
    }
}
