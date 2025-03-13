package org.roche.perscholas_capstone_addiction_help.controller;

import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/treatment-approvals")
    public String viewAllTreatments(Model model){
        model.addAttribute("treatments", treatmentService.getAllTreatments());
        return "admin-treatments";
    }

    @PostMapping("/treatment/approve/{id}")
    @PreAuthorize("hasRole('ADMIN')") // Ensures only admins can approve
    public ResponseEntity<String> approveTreatment(@PathVariable Long id) {
        try {
            treatmentService.approveTreatment(id);
            return ResponseEntity.ok("Treatment approved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error approving treatment");
        }
    }

    @GetMapping("/treatment/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Treatment treatment = treatmentService.getTreatmentById(id);
        model.addAttribute("treatment", treatment);
        return "edit-treatment";
    }

    @PostMapping("/treatment/update")
    public String updateTreatment(@ModelAttribute Treatment treatment) {
        treatmentService.updateTreatment(treatment);
        return "redirect:/admin/treatment-approvals";
    }

    @DeleteMapping("/treatment/delete/{id}")
    public ResponseEntity<String> deleteTreatment(@PathVariable Long id) {
        treatmentService.deleteTreatment(id);
        return ResponseEntity.ok("Treatment deleted successfully!");
    }
}