package com.lojavirtual.domain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Carrinho de compras de um cliente (mutable, mantido em mem&oacute;ria).
 *
 * @author Kelvin Oliveira
 */
public class Cart {

    private final Long clienteId;
    private final Map<Long, Integer> items = new LinkedHashMap<>();

    public Cart(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public Map<Long, Integer> getItems() {
        return Map.copyOf(items);
    }

    public void addItem(Long produtoId, Integer quantidade) {
        items.merge(produtoId, quantidade, Integer::sum);
    }

    public void removeItem(Long produtoId) {
        items.remove(produtoId);
    }

    public void clear() {
        items.clear();
    }

    public Integer quantidade(Long produtoId) {
        return items.getOrDefault(produtoId, 0);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int totalItens() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
}
