package com.deliverytech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.deliverytech.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByRestauranteId(Long restauranteId);

    List<Produto> findByCategoriaIgnoreCase(String categoria);

    List<Produto> findByDisponivelTrue();

    @Query("SELECT p FROM Produto p WHERE p.restaurante.id = :restauranteId AND p.disponivel = true")
    List<Produto> findDisponiveisPorRestaurante(Long restauranteId);

    void save(com.deliverytech.entity.Produto produto);
}
