package com.lojavirtual.dto;

import com.lojavirtual.domain.Product;
import java.math.BigDecimal;

/** Resumo de produto exposto pela API. */
public record ProductResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        Boolean ativo
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.id(),
                product.nome(),
                product.descricao(),
                product.preco(),
                product.estoque(),
                product.ativo());
    }
}
