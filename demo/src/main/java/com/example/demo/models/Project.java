package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "projects"
)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(
            name = "project_id",
            updatable = false
    )
    private Long project_id;
    @Column
    private String name;
    @Column
    private String aim;
    @Column
    private String definition;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate finishDate;
    @Column
    private LocalDate startTeamDate;
    @Column
    private LocalDate finishTeamDate;

    @Column
    private String status;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "likedProjects")
    Set<Account> likes = new HashSet<>();

    public Long getProject_id() {
        return project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAim() {
        return aim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public LocalDate getStartTeamDate() {
        return startTeamDate;
    }

    public void setStartTeamDate(LocalDate startTeamDate) {
        this.startTeamDate = startTeamDate;
    }

    public LocalDate getFinishTeamDate() {
        return finishTeamDate;
    }

    public void setFinishTeamDate(LocalDate finishTeamDate) {
        this.finishTeamDate = finishTeamDate;
    }

    public Set<Account> getLikes() {
        return likes;
    }

    public void setLikes(Set<Account> likes) {
        this.likes = likes;
    }

    public void addAccount(Account account) {
        this.likes.add(account);
        account.getLikedProjects().add(this);
    }

    public void removeAccount(Account account) {
        this.likes.remove(account);
        account.getLikedProjects().remove(this);
    }
}
