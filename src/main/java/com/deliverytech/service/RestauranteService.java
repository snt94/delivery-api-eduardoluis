package com.deliverytech.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.exception.ResourceNotFoundException;
import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.RestauranteRepository;

@Service
public class RestauranteService {

    private final RestauranteRepository repository;

    public RestauranteService(RestauranteRepository repository) {
        this.repository = repository;
    }

    public Restaurante cadastrar(Restaurante restaurante) {
        return repository.save(restaurante);
    }

    public Restaurante buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado"));
    }

    public List<Restaurante> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    public Restaurante atualizar(Long id, Restaurante dados) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.setNome(dados.getNome());
        restaurante.setCategoria(dados.getCategoria());
        restaurante.setAvaliacaoMedia(dados.getAvaliacaoMedia());
        return repository.save(restaurante);
    }

    public void alterarStatus(Long id, boolean ativo) {
        Restaurante restaurante = buscarPorId(id);
        restaurante.setAtivo(ativo);
        repository.save(restaurante);
    }
    public List<Restaurante> buscarPorCategoria(String categoria) {
    return repository.findByCategoriaIgnoreCase(categoria);
    }
}
