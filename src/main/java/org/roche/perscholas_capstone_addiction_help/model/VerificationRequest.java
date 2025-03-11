package org.roche.perscholas_capstone_addiction_help.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VerificationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean approved = false;

    private String remarks;

    @ManyToOne
    @JoinColumn(name = "approved_By")
    private User approvedBy;

    @OneToOne
    @JoinColumn(name = "treatment_id", nullable = false)
    private Treatment treatment;
}
