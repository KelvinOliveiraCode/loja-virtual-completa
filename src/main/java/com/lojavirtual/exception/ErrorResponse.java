package com.lojavirtual.exception;

/** Corpo de resposta de erro padronizado. */
public record ErrorResponse(int status, String mensagem) {
}
