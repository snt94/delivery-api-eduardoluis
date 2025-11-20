package com.deliverytech.repository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.deliverytech.model.Cliente;
import com.deliverytech.model.Pedido;
import com.deliverytech.model.Restaurante;

@DataJpaTest
class PedidoRepositoryTest {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Test
    void deveBuscarPedidosPorCliente() {
        Cliente cliente = new Cliente();
        cliente.setNome("Carlos");
        cliente.setEmail("carlos@teste.com");
        clienteRepository.save(cliente);

        Restaurante rest = new Restaurante();
        rest.setNome("Rest A");
        restauranteRepository.save(rest);

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(rest);
        pedido.setStatus("ENTREGUE");
        pedido.setValorTotal(50.0);
        pedidoRepository.save(pedido);

        List<Pedido> resultado = pedidoRepository.findByClienteId(cliente.getId());
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getStatus()).isEqualTo("ENTREGUE");
    }

    @Test
    void deveFiltrarPedidosPorPeriodoEStatus() {
        Cliente c = new Cliente();
        c.setNome("Joana");
        c.setEmail("joana@teste.com");
        clienteRepository.save(c);

        Restaurante r = new Restaurante();
        r.setNome("Padaria Central");
        restauranteRepository.save(r);

        Pedido p = new Pedido();
        p.setCliente(c);
        p.setRestaurante(r);
        p.setStatus("PENDENTE");
        p.setDataPedido(LocalDateTime.now().minusDays(1));
        pedidoRepository.save(p);

        var inicio = LocalDateTime.now().minusDays(2);
        var fim = LocalDateTime.now();

        List<Pedido> encontrados = pedidoRepository.buscarPorStatusEPeriodo("PENDENTE", inicio, fim);
        assertThat(encontrados).hasSize(1);
        assertThat(encontrados.get(0).getCliente().getNome()).isEqualTo("Joana");
    }
}
