package com.deliverytech.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;

@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    void deveBuscarPorCategoria() {
        Restaurante rest = new Restaurante();
        rest.setNome("Burguer King");
        rest.setCategoria("Lanches");
        restauranteRepository.save(rest);

        Produto p = new Produto();
        p.setNome("Cheeseburger");
        p.setCategoria("Lanches");
        p.setPreco(20.0);
        p.setDisponivel(true);
        p.setRestaurante(rest);
        produtoRepository.save(p);

        List<Produto> resultado = produtoRepository.findByCategoriaIgnoreCase("lanches");
        assertThat(resultado).extracting(Produto::getNome).contains("Cheeseburger");
    }

    @Test
    void deveBuscarProdutosDisponiveisPorRestaurante() {
        Restaurante rest = new Restaurante();
        rest.setNome("Sushi Place");
        restauranteRepository.save(rest);

        Produto p1 = new Produto();
        p1.setNome("Sushi");
        p1.setDisponivel(true);
        p1.setRestaurante(rest);
        produtoRepository.save(p1);

        Produto p2 = new Produto();
        p2.setNome("Temaki");
        p2.setDisponivel(false);
        p2.setRestaurante(rest);
        produtoRepository.save(p2);

        List<Produto> disponiveis = produtoRepository.findDisponiveisPorRestaurante(rest.getId());
        assertThat(disponiveis).hasSize(1);
        assertThat(disponiveis.get(0).getNome()).isEqualTo("Sushi");
    }
}
