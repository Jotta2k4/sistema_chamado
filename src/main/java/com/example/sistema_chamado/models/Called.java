package com.example.sistema_chamado.models;


import com.example.sistema_chamado.enums.Category;
import com.example.sistema_chamado.enums.Priority;
import com.example.sistema_chamado.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Called {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private Status status;
    private final LocalDateTime dateService = LocalDateTime.now();

    @OneToMany(mappedBy = "called", cascade = CascadeType.ALL, orphanRemoval = true) @JsonManagedReference
    private List<CallHistory> histories = new ArrayList<>();

    public List<CallHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<CallHistory> histories) {
        this.histories = histories;
    }

    @ManyToOne
    private Customer customer;

    @ManyToOne @JsonBackReference
    private Technical technical;

    private String techName;

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDateService() {
        return dateService;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }
}
