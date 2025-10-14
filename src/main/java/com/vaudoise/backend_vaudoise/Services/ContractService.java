package com.vaudoise.backend_vaudoise.Services;

import com.vaudoise.backend_vaudoise.Dtos.ContractDTO;
import com.vaudoise.backend_vaudoise.Entities.Client;
import com.vaudoise.backend_vaudoise.Entities.Contract;
import com.vaudoise.backend_vaudoise.Repositories.ClientRepository;
import com.vaudoise.backend_vaudoise.Repositories.ContractRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public ContractDTO createContractForClient(Long clientId, ContractDTO dto) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Contract contract = Contract.builder()
                .client(client)
                .startDate(dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now())
                .endDate(dto.getEndDate())
                .costAmount(dto.getCostAmount())
                .build();

        Contract saved = contractRepository.save(contract);
        return mapToDTO(saved);
    }

    @Transactional
    public ContractDTO updateContract(Long id, ContractDTO dto) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contract not found"));

        if (dto.getCostAmount() != null) {
            contract.setCostAmount(dto.getCostAmount());
        }

        if (dto.getStartDate() != null) {
            contract.setStartDate(dto.getStartDate());
        }

        if (dto.getEndDate() != null) {
            contract.setEndDate(dto.getEndDate());
        }

        return mapToDTO(contractRepository.save(contract));
    }


    public List<ContractDTO> getActiveContracts(Long clientId, Instant updatedSince) {
        LocalDate today = LocalDate.now();
        List<Contract> contracts;

        if (updatedSince != null) {
            contracts = contractRepository.findActiveByClientAndUpdatedSince(clientId, today, updatedSince);
        } else {
            contracts = contractRepository.findActiveByClient(clientId, today);
        }

        return contracts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Double getActiveContractsSum(Long clientId) {
        LocalDate today = LocalDate.now();
        BigDecimal sum = contractRepository.sumActiveContractCostsByClient(clientId, today);
        return sum != null ? sum.doubleValue() : 0.0;
    }

    private ContractDTO mapToDTO(Contract contract) {
        return ContractDTO.builder()
                .id(contract.getId())
                .startDate(contract.getStartDate())
                .endDate(contract.getEndDate())
                .costAmount(contract.getCostAmount())
                .build();
    }
}
