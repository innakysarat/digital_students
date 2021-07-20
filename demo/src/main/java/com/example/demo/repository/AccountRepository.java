package com.example.demo.repository;

import com.example.demo.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    List<Account> findByEmailContainingIgnoreCase(String email);
    List<Account> findByNameAndSurnameContainingIgnoreCase(String name, String surname);
}
