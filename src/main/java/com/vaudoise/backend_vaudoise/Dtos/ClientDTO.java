package com.vaudoise.backend_vaudoise.Dtos;


import com.vaudoise.backend_vaudoise.Entities.ClientType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientDTO {

    private Long id;

    @NotNull(message = "Client type is required")
    private ClientType type;

    @NotNull(message = "Name is required")
    private String name;

    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Invalid phone number format")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    @PastOrPresent(message = "Birthdate cannot be in the future")
    private LocalDate birthdate;


    private String companyIdentifier;

}
