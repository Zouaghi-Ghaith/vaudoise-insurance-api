package com.vaudoise.backend_vaudoise.Services;

import com.vaudoise.backend_vaudoise.Dtos.ClientDTO;
import com.vaudoise.backend_vaudoise.Entities.Client;
import com.vaudoise.backend_vaudoise.Repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientDTO createClient(ClientDTO dto) {
        Client client = Client.builder()
                .name(dto.getName())
                .type(dto.getType())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .companyIdentifier(dto.getCompanyIdentifier())
                .build();

        Client saved = clientRepository.save(client);

        return mapToDTO(saved);
    }

    public ClientDTO getClientById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return mapToDTO(client);
    }

    @Transactional
    public ClientDTO updateClient(Long id, ClientDTO dto) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setName(dto.getName());
        client.setPhone(dto.getPhone());
        client.setEmail(dto.getEmail());

        return mapToDTO(client);
    }

    @Transactional
    public void deleteClient(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.getContracts().forEach(c -> c.setEndDate(LocalDate.now()));
        clientRepository.delete(client);
    }

    private ClientDTO mapToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .type(client.getType())
                .phone(client.getPhone())
                .email(client.getEmail())
                .birthdate(client.getBirthdate())
                .companyIdentifier(client.getCompanyIdentifier())
                .build();
    }
}
