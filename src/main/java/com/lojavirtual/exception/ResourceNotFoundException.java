package com.lojavirtual.exception;

/** Exce&ccedil;&atilde;o lançada quando um recurso n&atilde;o é encontrado. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String mensagem) {
        super(mensagem);
    }
}
