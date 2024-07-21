package com.example.VHS.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "VHSES")
public class Vhs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="total_number")
    private Integer totalNumber;
    @Column(name="number_in_stock")
    private Integer numberInStock;


}
