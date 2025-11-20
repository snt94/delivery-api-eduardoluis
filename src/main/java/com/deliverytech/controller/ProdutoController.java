package com.deliverytech.controller;

import com.deliverytech.model.Produto;
import com.deliverytech.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/restaurante/{restauranteId}")
    public ResponseEntity<Produto> cadastrar(@PathVariable Long restauranteId, @Valid @RequestBody Produto produto) {
        Produto novo = service.cadastrar(restauranteId, produto);
        return ResponseEntity.status(201).body(novo);
    }

    @GetMapping("/restaurante/{restauranteId}")
    public ResponseEntity<List<Produto>> listarPorRestaurante(@PathVariable Long restauranteId) {
        return ResponseEntity.ok(service.listarPorRestaurante(restauranteId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        return ResponseEntity.ok(service.atualizar(id, produto));
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<Void> alterarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        service.alterarDisponibilidade(id, disponivel);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.alterarDisponibilidade(id, false);
        return ResponseEntity.noContent().build();
    }
}
