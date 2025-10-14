package com.vaudoise.backend_vaudoise.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientType type;

    @Column(nullable = false)
    private String name;

    @Column
    private String phone;

    @Column
    private String email;


    @Column
    private LocalDate birthdate;

    // for COMPANY
    @Column(unique = true)
    private String companyIdentifier;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Contract> contracts = new ArrayList<>();
}
