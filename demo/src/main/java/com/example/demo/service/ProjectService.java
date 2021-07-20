package com.example.demo.service;

import com.example.demo.models.Account;
import com.example.demo.models.Project;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final AccountRepository accountRepository;

    public ProjectService(ProjectRepository projectRepository,
                          AccountRepository accountRepository) {
        this.projectRepository = projectRepository;
        this.accountRepository = accountRepository;
    }

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public void createProject(Project project) {
        projectRepository.save(project);
    }

    public void addParticipant(Long project_id, Long account_id) {
        Account account = accountRepository.findById(account_id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Account not found"
                        )
                );
        Project project = projectRepository.findById(project_id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Project not found"
                        )
                );
        project.addAccount(account);
        projectRepository.save(project);
        accountRepository.save(account);
    }

    public void deleteParticipant(Long project_id, Long acc_id) {
        Account account = accountRepository.findById(acc_id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Account not found"
                        )
                );
        Project project = projectRepository.findById(project_id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Project not found"
                        )
                );
        project.removeAccount(account);
        projectRepository.save(project);
        accountRepository.save(account);
    }

    public Set<Account> getParticipants(Long project_id) {
        Project project = projectRepository.findById(project_id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Project not found"
                        )
                );
        return project.getLikes();
    }

    public List<Project> searchByName(String name) {
        return projectRepository.findByNameContainingIgnoreCase(name);
    }

    public void updateProject(Long project_id, String name, String definition,
                              String aim, LocalDate startDate, LocalDate finishDate,
                              LocalDate startTeamDate, LocalDate finishTeamDate, String status) {
        Project project = projectRepository.findById(project_id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Project not found"
                )
        );
        if (name != null && !Objects.equals(name, project.getName())) {
            project.setName(name);
        }
        if (definition != null && !Objects.equals(definition, project.getDefinition())) {
            project.setDefinition(definition);
        }
        if (aim != null & !Objects.equals(aim, project.getAim())) {
            project.setAim(aim);
        }
        if (startDate != null && Objects.equals(startDate, project.getStartDate())) {
            project.setStartDate(startDate);
        }
        if (finishDate != null && Objects.equals(finishDate, project.getFinishDate())) {
            project.setFinishDate(finishDate);
        }
        if (startTeamDate != null && Objects.equals(startTeamDate, project.getStartTeamDate())) {
            project.setStartTeamDate(startTeamDate);
        }
        if (finishTeamDate != null && Objects.equals(finishTeamDate, project.getFinishTeamDate())) {
            project.setFinishTeamDate(finishTeamDate);
        }
        if (status != null & !Objects.equals(status, project.getStatus())) {
            project.setStatus(status);
        }
        projectRepository.save(project);
    }

    public void delete(Long id) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Project not found"
                )
        );
        Set<Account> likes = project.getLikes();
        for (Account account : likes) {
            Account acc = accountRepository.findById(account.getAccount_id()).orElse(null);
            if (acc != null) {
                acc.getLikedProjects().remove(project);
                accountRepository.save(acc);
            }
        }
        project.getLikes().removeAll(likes);
        projectRepository.delete(project);
    }
}
