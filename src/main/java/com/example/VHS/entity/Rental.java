package com.example.VHS.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "RENTALS")
public class Rental {
    @Valid

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "vhs_id", referencedColumnName = "id")
    @NotNull(message = "VHS is mandatroy")
    private Vhs vhs;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull(message = "User is mandatroy")
    private User user;

    @Column(name = "rented_date")
    private LocalDateTime rentedDate;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "upaid_due")
    private Float unpaidDue;

    @Column(name = "due")
    private Float due;
}
