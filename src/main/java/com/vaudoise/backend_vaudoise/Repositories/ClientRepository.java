package com.vaudoise.backend_vaudoise.Repositories;

import com.vaudoise.backend_vaudoise.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {

}
