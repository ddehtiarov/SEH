package com.epam.hackathon.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "code")
public class Code {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "code_id", unique = true, nullable = false)
    private Long id;

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
