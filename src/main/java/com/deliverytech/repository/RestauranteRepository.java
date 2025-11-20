package com.deliverytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deliverytech.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    List<Restaurante> findByNomeContainingIgnoreCase(String nome);

    List<Restaurante> findByCategoriaIgnoreCase(String categoria);

    List<Restaurante> findByAtivoTrue();

    @Query("SELECT r FROM Restaurante r ORDER BY r.avaliacaoMedia DESC")
    List<Restaurante> findAllOrderByAvaliacao();
}
