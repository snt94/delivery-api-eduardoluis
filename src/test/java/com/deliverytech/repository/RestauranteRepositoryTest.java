package com.deliverytech.repository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deliverytech.model.Restaurante;

@DataJpaTest
class RestauranteRepositoryTest {

    @Autowired
    private RestauranteRepository repository;

    @Test
    void deveBuscarPorNomeParcial() {
        Restaurante r = new Restaurante();
        r.setNome("Pizza Boa");
        r.setCategoria("Pizzaria");
        r.setAtivo(true);
        repository.save(r);

        List<Restaurante> resultado = repository.findByNomeContainingIgnoreCase("pizza");
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNome()).contains("Pizza");
    }

    @Test
    void deveOrdenarPorAvaliacao() {
        Restaurante r1 = new Restaurante();
        r1.setNome("A");
        r1.setAvaliacaoMedia(4.5);
        repository.save(r1);

        Restaurante r2 = new Restaurante();
        r2.setNome("B");
        r2.setAvaliacaoMedia(5.0);
        repository.save(r2);

        List<Restaurante> resultado = repository.findAllOrderByAvaliacao();
        assertThat(resultado.get(0).getAvaliacaoMedia()).isGreaterThanOrEqualTo(resultado.get(1).getAvaliacaoMedia());
    }
}
