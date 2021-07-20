package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/{email}")
    public Account getUser(
            @PathVariable String email
    ) {
        return accountService.getUser(email);
    }

    @GetMapping
    public List<Account> getUsers() {
        return accountService.getUsers();
    }

    @PostMapping
    public void register(@RequestBody Account account) {
        accountService.register(account);
    }

    @PutMapping(path = "/{id}/interests")
    public void updateInterest(@RequestParam("interest") String interest,
                               @PathVariable Long id) {
        accountService.updateInterest(id, interest);
    }

    @PutMapping(path = "/{id}")
    public void updateInfo(@PathVariable Long id,
                           @RequestBody Account account) {
        accountService.updateInfo(id, account.getName(), account.getSurname(),
                account.getPatronymic(), account.getTelegram());
    }

    @GetMapping(path = "/searchByEmail")
    public List<Account> searchByEmail(@RequestParam("email") String email) {
        return accountService.searchByEmail(email);
    }

    @GetMapping(path = "/searchByNameAndSurname")
    public List<Account> searchByNameAndSurname(@RequestParam("name") String name,
                                                @RequestParam("surname") String surname) {
        return accountService.searchByNameAndSurname(name, surname);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }
}
