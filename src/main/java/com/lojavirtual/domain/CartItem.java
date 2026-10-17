package com.lojavirtual.domain;

/** Item de carrinho: refere-se a um produto por id com uma quantidade. */
public record CartItem(Long produtoId, Integer quantidade) {
}
