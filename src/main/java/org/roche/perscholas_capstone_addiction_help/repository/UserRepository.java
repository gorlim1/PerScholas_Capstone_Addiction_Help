package org.roche.perscholas_capstone_addiction_help.repository;

import org.roche.perscholas_capstone_addiction_help.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
