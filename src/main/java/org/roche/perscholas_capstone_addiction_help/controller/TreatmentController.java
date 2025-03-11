package org.roche.perscholas_capstone_addiction_help.controller;

import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.service.TreatmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/treatments")
public class TreatmentController {

    @Autowired
    private TreatmentService treatmentService;

    @GetMapping("/create")
    public String showCreateTreatmentForm(Model model) {
        model.addAttribute("treatment", new Treatment());
        return "user/create-treatment"; // JSP file for form
    }

    @PostMapping("/create")
    public String createTreatment(@ModelAttribute Treatment treatment, Principal principal) {
        treatment.setEmail(principal.getName()); // Assign logged-in user
        treatmentService.createTreatment(treatment);
        return "redirect:/treatments"; // Redirect to user treatment list
    }

    @GetMapping
    public String viewTreatments(Model model, @RequestParam(required = false) String insurance) {
        List<Treatment> treatments = (insurance == null)
                ? treatmentService.getApprovedTreatments()
                : treatmentService.getTreatmentsByInsurance(insurance);
        model.addAttribute("treatments", treatments);
        return "user/view-treatments"; // JSP for viewing treatments
    }
}