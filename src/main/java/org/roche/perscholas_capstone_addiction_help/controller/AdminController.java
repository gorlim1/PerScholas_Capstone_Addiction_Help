package org.roche.perscholas_capstone_addiction_help.controller;

import org.roche.perscholas_capstone_addiction_help.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/treatment-approvals")
    public String viewPendingTreatments(Model model){
        model.addAttribute("treatments", treatmentService.getPendingTreatments());
        return "admin-treatments";
    }

    @PostMapping("/admin/approve/{id}")
    public String approveTreatment(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        treatmentService.approveTreatment(id);
        redirectAttributes.addFlashAttribute("successMessage", "Treatment approved successfully!");
        return "redirect:/admin/treatment-approvals"; // Redirect to the approvals page
    }
}