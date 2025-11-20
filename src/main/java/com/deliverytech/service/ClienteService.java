package com.deliverytech.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.deliverytech.exception.BusinessException;
import com.deliverytech.exception.ResourceNotFoundException;
import com.deliverytech.model.Cliente;
import com.deliverytech.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente cadastrar(Cliente cliente) {
        if (repository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new BusinessException("E-mail já cadastrado.");
        }
        return repository.save(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
    }

    public List<Cliente> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    public Cliente atualizar(Long id, Cliente dados) {
        Cliente cliente = buscarPorId(id);
        cliente.setNome(dados.getNome());
        cliente.setEmail(dados.getEmail());
        return repository.save(cliente);
    }

    public void inativar(Long id) {
        Cliente cliente = buscarPorId(id);
        cliente.setAtivo(false);
        repository.save(cliente);
    }
}
