package com.deliverytech.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private boolean ativo = true;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
}
