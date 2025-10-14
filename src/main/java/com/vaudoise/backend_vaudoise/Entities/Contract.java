package com.vaudoise.backend_vaudoise.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "contracts", indexes = {
        @Index(name = "idx_client_active_enddate", columnList = "client_id, end_date"),
        @Index(name = "idx_update_date", columnList = "update_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;


    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false)
    private BigDecimal costAmount;

    @Column(name = "update_date", nullable = false)
    private Instant updateDate  = Instant.now();

    @PrePersist
    public void prePersist() {
        if (startDate == null) startDate = LocalDate.now();
        if (updateDate == null) updateDate = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = Instant.now();
    }



}
