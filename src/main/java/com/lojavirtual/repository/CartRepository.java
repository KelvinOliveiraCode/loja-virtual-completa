package com.lojavirtual.repository;

import com.lojavirtual.domain.Cart;
import java.util.List;

/** Reposit&oacute;rio de carrinhos (um por cliente). */
public interface CartRepository {

    Cart obterOuCriar(Long clienteId);

    void save(Cart cart);

    List<Cart> findAll();
}
