package com.deliverytech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deliverytech.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);

    List<Cliente> findByAtivoTrue();

    @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.ativo = true")
    Optional<Cliente> findAtivoByEmail(String email);
}
