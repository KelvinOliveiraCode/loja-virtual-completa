package com.lojavirtual.repository;

import com.lojavirtual.domain.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Implementa&ccedil;&atilde;o em mem&oacute;ria do {@link CartRepository}. */
@Repository
public class InMemoryCartRepository implements CartRepository {

    private final Map<Long, Cart> store = new ConcurrentHashMap<>();

    @Override
    public Cart obterOuCriar(Long clienteId) {
        return store.computeIfAbsent(clienteId, Cart::new);
    }

    @Override
    public void save(Cart cart) {
        store.put(cart.getClienteId(), cart);
    }

    @Override
    public List<Cart> findAll() {
        return List.copyOf(store.values());
    }
}
