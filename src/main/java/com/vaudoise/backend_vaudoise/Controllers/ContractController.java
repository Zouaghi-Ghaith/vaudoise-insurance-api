package com.vaudoise.backend_vaudoise.Controllers;


import com.vaudoise.backend_vaudoise.Dtos.ContractDTO;
import com.vaudoise.backend_vaudoise.Services.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PutMapping("/{id}")
    public ContractDTO updateContract(@PathVariable Long id,@Valid @RequestBody ContractDTO contractDTO) {
        return contractService.updateContract(id, contractDTO);
    }
}
