package com.lojavirtual.domain;

import java.math.BigDecimal;

/**
 * Produto do cat&aacute;logo. Imut por ser um {@code record}.
 *
 * @author Kelvin Oliveira
 */
public record Product(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        Boolean ativo
) {
}
