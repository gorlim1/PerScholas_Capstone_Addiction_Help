package org.roche.perscholas_capstone_addiction_help.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
@Entity
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank(message = "Name is required")
    private String name;

    @NotNull
    @NotBlank(message = "email is required")
    @Size(min = 3, max = 50, message = "email must be between 3 and 50 characters")
    private String email;

    @NotNull
    @NotBlank(message = "Address is required")
    private String address;

    @NotNull
    @NotBlank(message = "Phone number is required")
    private String phone;

    @NotNull
    @NotBlank(message = "Insurance is required")
    private String insurance;

    @NotNull
    @NotBlank(message = "Verification status is required")
    private boolean verified;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
