package com.deliverytech.services;

import java.math.BigDecimal;
import java.util.List;

import com.deliverytech.dto.request.ItemPedidoRequestDTO;
import com.deliverytech.dto.request.PedidoRequestDTO;
import com.deliverytech.dto.response.PedidoResponseDTO;
import com.deliverytech.enums.StatusPedido;



public interface PedidoService {

    PedidoResponseDTO criarPedido(PedidoRequestDTO dto);

    PedidoResponseDTO buscarPorId(Long id);

    List<PedidoResponseDTO> listarPedidosPorCliente(Long clienteId);

    PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido status);

    BigDecimal calcularValorTotalPedido(List<ItemPedidoRequestDTO> itens );

    PedidoResponseDTO cancelarPedido(Long id);

} 