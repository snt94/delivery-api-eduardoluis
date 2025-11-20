package com.deliverytech.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.exception.BusinessException;
import com.deliverytech.exception.ResourceNotFoundException;
import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.ProdutoRepository;
import com.deliverytech.repository.RestauranteRepository;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final RestauranteRepository restauranteRepository;

    public ProdutoService(ProdutoRepository produtoRepository, RestauranteRepository restauranteRepository) {
        this.produtoRepository = produtoRepository;
        this.restauranteRepository = restauranteRepository;
    }

    public Produto cadastrar(Long restauranteId, Produto produto) {
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            throw new BusinessException("Preço inválido.");
        }

        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado."));

        produto.setRestaurante(restaurante);
        return produtoRepository.save(produto);
    }

    public List<Produto> listarPorRestaurante(Long restauranteId) {
        return produtoRepository.findByRestauranteId(restauranteId);
    }

    public Produto atualizar(Long id, Produto dados) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        produto.setNome(dados.getNome());
        produto.setPreco(dados.getPreco());
        produto.setCategoria(dados.getCategoria());
        produto.setDisponivel(dados.isDisponivel());
        return produtoRepository.save(produto);
    }

    public void alterarDisponibilidade(Long id, boolean disponivel) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        produto.setDisponivel(disponivel);
        produtoRepository.save(produto);
    }
}
