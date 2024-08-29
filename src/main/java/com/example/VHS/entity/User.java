package com.example.VHS.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "USERS")
public class User {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    @NotNull(message = "Name is mandatory")
    private String name;

    @Column(name = "user_name")
    @NotNull(message = "User name is mandatory")
    private String userName;

    @Column(name="email")
    @NotNull(message = "Email is mandatory")
    private String email;

    @Column(name="password")
    @NotNull(message = "Password is mandatory")
    private String password;

    @Column(name="total_due")
    private BigDecimal totalDue;
    @Column(name="unpaid_due")
    private BigDecimal unpaidDue;

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    };

    public User(){};
}
