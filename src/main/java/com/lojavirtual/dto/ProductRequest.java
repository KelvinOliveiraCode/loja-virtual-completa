package com.lojavirtual.dto;

import java.math.BigDecimal;

/** Requisi&ccedil;&atilde;o de cria&ccedil;&atilde;o/atualiza&ccedil;&atilde;o de produto. */
public record ProductRequest(
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        Boolean ativo
) {
}
