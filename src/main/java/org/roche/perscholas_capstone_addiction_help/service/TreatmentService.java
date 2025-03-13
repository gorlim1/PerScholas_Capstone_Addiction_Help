package org.roche.perscholas_capstone_addiction_help.service;

import jakarta.transaction.Transactional;
import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.model.User;
import org.roche.perscholas_capstone_addiction_help.repository.TreatmentRepository;
import org.roche.perscholas_capstone_addiction_help.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TreatmentService {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private UserRepository userRepository;

    private void setUser(Treatment treatment) {
        // Fetch the logged-in user (Assuming the admin is approving)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminEmail = authentication.getName(); // Get admin email
        User adminUser = userRepository.findByEmail(adminEmail);

        // Set the user who created/approved this treatment
        treatment.setUser(adminUser);
    }

    @Transactional
    public void createTreatment(Treatment treatment) {
        setUser(treatment);
        treatmentRepository.save(treatment);
    }

    public List<Treatment> getAllTreatments() {
        return treatmentRepository.findAll();
    }

    @Transactional
    public void approveTreatment(Long id) {
        Treatment treatment = treatmentRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Treatment not found"));
        setUser(treatment);
        treatment.setVerified(true);
        treatmentRepository.save(treatment);
    }

    public List<Treatment> getTreatmentsByInsurance(String insurance) {
        return treatmentRepository.findByInsurance(insurance);
    }

    public Treatment getTreatmentById(Long id) {
        return treatmentRepository.findById(id).orElse(null);
    }

    public void updateTreatment(Treatment updatedTreatment) {
        Optional<Treatment> existingTreatment = treatmentRepository.findById(updatedTreatment.getId());
        if (existingTreatment.isPresent()) {
            Treatment treatment = existingTreatment.get();
            treatment.setName(updatedTreatment.getName());
            treatment.setEmail(updatedTreatment.getEmail());
            treatment.setPhone(updatedTreatment.getPhone());
            treatment.setAddress(updatedTreatment.getAddress());
            treatment.setInsurance(updatedTreatment.getInsurance());
            treatmentRepository.save(treatment);
        }
    }

    public void deleteTreatment(Long id) {
        treatmentRepository.deleteById(id);
    }
}