package com.lojavirtual.service;

import com.lojavirtual.domain.Cart;
import com.lojavirtual.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Servi&ccedil;o de carrinho de compras (um carrinho por cliente). */
@Service
public class CartService {

    private final CartRepository repository;

    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    /** Retorna o carrinho do cliente, criando-o se ainda n&atilde;o existir. */
    public Cart obterOuCriar(Long clienteId) {
        return repository.obterOuCriar(clienteId);
    }

    /** Adiciona quantidade de um produto ao carrinho. */
    public void adicionarItem(Long clienteId, Long produtoId, Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero.");
        }
        Cart carrinho = obterOuCriar(clienteId);
        carrinho.addItem(produtoId, quantidade);
        repository.save(carrinho);
    }

    /** Remove um produto do carrinho. */
    public void removerItem(Long clienteId, Long produtoId) {
        Cart carrinho = obterOuCriar(clienteId);
        carrinho.removeItem(produtoId);
        repository.save(carrinho);
    }

    /** Limpa todos os itens do carrinho do cliente. */
    public void limpar(Long clienteId) {
        Cart carrinho = obterOuCriar(clienteId);
        carrinho.clear();
        repository.save(carrinho);
    }

    /** Lista todos os carrinhos criados. */
    public List<Cart> listarTodos() {
        return repository.findAll();
    }
}
