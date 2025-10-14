package com.vaudoise.backend_vaudoise.Repositories;

import com.vaudoise.backend_vaudoise.Entities.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Query("SELECT c FROM Contract c WHERE c.client.id = :clientId AND (c.endDate IS NULL OR :today < c.endDate)")
    List<Contract> findActiveByClient(@Param("clientId") Long clientId, @Param("today") LocalDate today);

    @Query("SELECT c FROM Contract c WHERE c.client.id = :clientId AND (c.endDate IS NULL OR :today < c.endDate) AND c.updateDate >= :since")
    List<Contract> findActiveByClientAndUpdatedSince(@Param("clientId") Long clientId, @Param("today") LocalDate today, @Param("since") Instant since);

    @Query("SELECT COALESCE(SUM(c.costAmount), 0) FROM Contract c WHERE c.client.id = :clientId AND (c.endDate IS NULL OR :today < c.endDate)")
    BigDecimal sumActiveContractCostsByClient(@Param("clientId") Long clientId, @Param("today") LocalDate today);
}
