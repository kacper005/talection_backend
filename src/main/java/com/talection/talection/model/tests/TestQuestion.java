package com.talection.talection.model.tests;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class TestQuestion {

    @Id
    private Long id;

    @NotBlank
    private String questionText;

    @ManyToMany
    private List<TestOption> options;

    @ManyToMany
    private List<TestOption> correctOptions;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
