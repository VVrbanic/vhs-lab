package com.example.VHS.entity;

import com.example.VHS.validator.BigDecimalPattern;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
@Table(name="PRICES")
public class PriceValidation {

    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @BigDecimalPattern
    @Column(name = "price")
    @NotNull(message = "Price is mandatroy")
    private BigDecimal price;


}
