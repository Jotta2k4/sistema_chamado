package com.example.sistema_chamado.models;


import com.example.sistema_chamado.enums.Status;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class CallHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String comment;
    private final LocalDateTime dateTime = LocalDateTime.now();

    private Status status;
    @ManyToOne @JoinColumn(name = "called_id", nullable = false) @JsonBackReference
    private Called called;
    @ManyToOne
    private Technical technical;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Called getCalled() {
        return called;
    }

    public void setCalled(Called called) {
        this.called = called;
    }

    public Technical getTechnical() {
        return technical;
    }

    public void setTechnical(Technical technical) {
        this.technical = technical;
    }
}
