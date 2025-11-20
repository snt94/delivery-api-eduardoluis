package com.deliverytech;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deliverytech.model.Cliente;
import com.deliverytech.repository.ClienteRepository;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository repository;

    @Test
    void deveSalvarEBuscarClientePorEmail() {
        Cliente c = new Cliente();
        c.setNome("João");
        c.setEmail("joao@teste.com");
        c.setAtivo(true);
        repository.save(c);

        Optional<Cliente> encontrado = repository.findByEmail("joao@teste.com");
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNome()).isEqualTo("João");
    }

    @Test
    void deveListarSomenteClientesAtivos() {
        Cliente ativo = new Cliente();
        ativo.setNome("Maria");
        ativo.setEmail("maria@teste.com");
        ativo.setAtivo(true);

        Cliente inativo = new Cliente();
        inativo.setNome("José");
        inativo.setEmail("jose@teste.com");
        inativo.setAtivo(false);

        repository.save(ativo);
        repository.save(inativo);

        var ativos = repository.findByAtivoTrue();
        assertThat(ativos).extracting(Cliente::isAtivo).containsOnly(true);
    }
}
