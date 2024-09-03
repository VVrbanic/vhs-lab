package com.example.VHS.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "VHSES")
public class VhsValidation {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    @NotNull(message = "Name is mandatory")
    private String name;
    @Column(name="total_number")
    @NotNull(message = "Total number is mandatory")
    private Integer totalNumber;


    public VhsValidation(Integer id, String name, Integer totalNumber, Integer numberInStock) {
        this.id = id;
        this.name = name;
        this.totalNumber = totalNumber;
    };

    public VhsValidation(){};
}
