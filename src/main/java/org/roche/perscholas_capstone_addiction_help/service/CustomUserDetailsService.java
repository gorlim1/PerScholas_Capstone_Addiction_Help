package org.roche.perscholas_capstone_addiction_help.service;

import org.rma.taskmanagement.model.User;
import org.rma.taskmanagement.repository.UserRepository;
import org.roche.perscholas_capstone_addiction_help.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the employee from the repository
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // Manually prefix the role with 'ROLE_' if it's not already prefixed
        String role = user.getRole().name();
        String roleWithPrefix = "ROLE_" + role;

        // Return the user details with prefixed role
        return new User(user.getEmail(), user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(roleWithPrefix)));
    }
}