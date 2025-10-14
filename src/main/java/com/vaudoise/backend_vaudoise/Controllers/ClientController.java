package com.vaudoise.backend_vaudoise.Controllers;

import com.vaudoise.backend_vaudoise.Dtos.ClientDTO;
import com.vaudoise.backend_vaudoise.Dtos.ContractDTO;
import com.vaudoise.backend_vaudoise.Services.ClientService;
import com.vaudoise.backend_vaudoise.Services.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ContractService contractService;

    @PostMapping
    public ClientDTO createClient(@Valid @RequestBody ClientDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }


    @GetMapping("/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return clientService.getClientById(id);
    }

    @PutMapping("/{id}")
    public ClientDTO updateClient(@PathVariable Long id, @Valid @RequestBody  ClientDTO clientDTO) {
        return clientService.updateClient(id, clientDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }

    @PostMapping("/{clientId}/contracts")
    public ContractDTO createContract(@PathVariable Long clientId,@Valid @RequestBody ContractDTO contractDTO) {
        return contractService.createContractForClient(clientId, contractDTO);
    }

    @GetMapping("/{clientId}/contracts")
    public List<ContractDTO> getContracts(
            @PathVariable Long clientId,
            @RequestParam(required = false) Instant updatedSince
    ) {
        return contractService.getActiveContracts(clientId, updatedSince);
    }

    @GetMapping("/{clientId}/contracts/sum-active")
    public Double getActiveContractsSum(@PathVariable Long clientId) {
        return contractService.getActiveContractsSum(clientId);
    }
}
