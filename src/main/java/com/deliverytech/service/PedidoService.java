package com.deliverytech.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.exception.BusinessException;
import com.deliverytech.exception.ResourceNotFoundException;
import com.deliverytech.model.Cliente;
import com.deliverytech.model.Pedido;
import com.deliverytech.model.Produto;
import com.deliverytech.model.Restaurante;
import com.deliverytech.repository.ClienteRepository;
import com.deliverytech.repository.PedidoRepository;
import com.deliverytech.repository.ProdutoRepository;
import com.deliverytech.repository.RestauranteRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final RestauranteRepository restauranteRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ClienteRepository clienteRepository,
                         RestauranteRepository restauranteRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.restauranteRepository = restauranteRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido criar(Long clienteId, Long restauranteId, List<Long> produtosIds) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
        Restaurante restaurante = restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new ResourceNotFoundException("Restaurante não encontrado."));

        List<Produto> produtos = produtoRepository.findAllById(produtosIds);
        if (produtos.isEmpty()) {
            throw new BusinessException("Nenhum produto válido selecionado.");
        }

        double total = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setProdutos(produtos);
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus("PENDENTE");
        pedido.setValorTotal(total);

        return pedidoRepository.save(pedido);
    }

    public Pedido atualizarStatus(Long id, String status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado."));
        pedido.setStatus(status);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    public List<Pedido> buscarPorPeriodoEStatus(LocalDateTime inicio, LocalDateTime fim, String status) {
        return pedidoRepository.buscarPorStatusEPeriodo(status, inicio, fim);
    }
}
