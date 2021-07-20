package com.example.demo.controllers;

import com.example.demo.models.Account;
import com.example.demo.models.Project;
import com.example.demo.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @PostMapping
    public void createProject(@RequestBody Project project) {
        projectService.createProject(project);
    }

    @PostMapping(path = "/{project_id}/participants/{acc_id}")
    public void addAccount(@PathVariable Long project_id,
                           @PathVariable Long acc_id) {
        projectService.addParticipant(project_id, acc_id);
    }

    @DeleteMapping(path = "/{project_id}/participants/{acc_id}")
    public void deleteAccount(@PathVariable Long project_id,
                              @PathVariable Long acc_id) {
        projectService.deleteParticipant(project_id, acc_id);
    }

    @GetMapping(path = "/{project_id}/participants")
    public Set<Account> getParticipants(@PathVariable Long project_id) {
        return projectService.getParticipants(project_id);
    }

    @GetMapping(path = "/search")
    public List<Project> searchByName(@RequestParam("name") String name) {
        return projectService.searchByName(name);
    }

    @PutMapping(path = "/{project_id}")
    public void updateProject(@PathVariable Long project_id,
                              @RequestBody Project project) {
        projectService.updateProject(project_id, project.getName(), project.getDefinition(),
                project.getAim(), project.getStartDate(), project.getFinishDate(),
                project.getStartTeamDate(), project.getFinishTeamDate(),
                project.getStatus());
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProject(@PathVariable Long id) {
        projectService.delete(id);
    }
}
