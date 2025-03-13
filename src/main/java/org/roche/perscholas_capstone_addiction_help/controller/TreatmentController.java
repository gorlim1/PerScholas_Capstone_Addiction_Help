package org.roche.perscholas_capstone_addiction_help.controller;

import org.roche.perscholas_capstone_addiction_help.model.Treatment;
import org.roche.perscholas_capstone_addiction_help.service.TreatmentService;
import org.roche.perscholas_capstone_addiction_help.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public String showCreateTreatmentForm(Model model) {
        model.addAttribute("treatment", new Treatment());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String cancelUrl = authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))
                ? "/admin/treatment-approvals"
                : "/treatments";
        model.addAttribute("cancelUrl", cancelUrl);
        return "user/create-treatment"; // html file for form
    }

    @PostMapping("/create")
    public String createTreatment(@ModelAttribute Treatment treatment, Principal principal) {
        treatment.setEmail(principal.getName()); // Assign logged-in user
        treatmentService.createTreatment(treatment);
        String role = userService.getUserRole();
        String redirectUrl;
        if (role.equals("ROLE_ADMIN")) {
            redirectUrl = "redirect:/admin/treatment-approvals";
        } else {
            redirectUrl = "redirect:/treatments";
        }

        return redirectUrl; // Redirect to user treatment list
    }

    @GetMapping
    public String viewTreatments(Model model, @RequestParam(required = false) String insurance) {
        List<Treatment> treatments = (insurance == null)
                ? treatmentService.getAllTreatments()
                : treatmentService.getTreatmentsByInsurance(insurance);
        model.addAttribute("treatments", treatments);
        return "user/view-treatments"; // html file for viewing treatments
    }
}