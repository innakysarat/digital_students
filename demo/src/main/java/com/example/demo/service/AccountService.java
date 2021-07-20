package com.example.demo.service;

import com.example.demo.models.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.security.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    public AccountService(AccountRepository accountRepository,
                          PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account getUser(String email) {
        return accountRepository.findByEmail(email).orElse(null);
    }

    public List<Account> getUsers() {
        return accountRepository.findAll();
    }

    public void register(Account account) {
        Account anotherUser
                = accountRepository.findByUsername(account.getUsername()).orElse(null);
        if (anotherUser != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Username is taken"
            );
        }
        account.setRole(UserRole.STUDENT);
        String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        accountRepository.save(account);
    }

    public void updateInterest(Long id, String interest) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"
                ));
        if (interest != null && !Objects.equals(interest, account.getInterests())) {
            account.setInterests(interest);
            accountRepository.save(account);
        }
    }

    public void updateInfo(Long id, String name, String surname, String patronymic, String telegram) {
        Account account = accountRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Account not found"
                        )
                );
        if (name != null && !Objects.equals(name, account.getName())) {
            account.setName(name);
        }
        if (surname != null && !Objects.equals(surname, account.getSurname())) {
            account.setSurname(surname);
        }
        if (patronymic != null && !Objects.equals(patronymic, account.getPatronymic())) {
            account.setPatronymic(patronymic);
        }
        if (telegram != null && !Objects.equals(telegram, account.getTelegram())) {
            account.setTelegram(telegram);
        }
        accountRepository.save(account);
    }


    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepository.findByUsername(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Имя пользователя %s не найдено", username));
        }

        return user;
    }

    public List<Account> searchByEmail(String email) {
        return accountRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<Account> searchByNameAndSurname(String name, String surname) {
        return accountRepository.findByNameAndSurnameContainingIgnoreCase(name, surname);
    }

    public void deleteAccount(Long id) {
        accountRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Account not found"
                        )
                );
        accountRepository.deleteById(id);
    }
}
