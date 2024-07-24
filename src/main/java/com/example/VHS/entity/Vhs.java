package com.example.VHS.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "VHSES")
public class Vhs {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    @NotNull(message = "Name is mandatroy")
    private String name;
    @Column(name="total_number")
    @NotNull(message = "Total number is mandatroy")
    private Integer totalNumber;
    @Column(name="number_in_stock")
    private Integer numberInStock;

    public Vhs(Integer id, String name, Integer totalNumber, Integer numberInStock) {
        this.id = id;
        this.name = name;
        this.totalNumber = totalNumber;
        this.numberInStock = numberInStock;
    };

    public Vhs(){};
}
