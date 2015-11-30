package com.epam.hackathon.entity;

import javax.persistence.*;

import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "role_id", unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = 10)
    private String name;

    public UserRole() {
    }

    public UserRole(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}