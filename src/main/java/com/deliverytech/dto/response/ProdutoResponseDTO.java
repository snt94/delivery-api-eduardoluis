package com.deliverytech.dto.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProdutoResponseDTO {

    private Long id;
    private String nome;
    private String descricao;
    private String categoria;
    private Boolean disponivel;
    private BigDecimal preco;
    private boolean ativo;

}
