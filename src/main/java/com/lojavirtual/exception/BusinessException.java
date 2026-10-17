package com.lojavirtual.exception;

/** Exce&ccedil;&atilde;o de regra de neg&oacute;cio (estoque insuficiente, valor inv&aacute;lido, etc.). */
public class BusinessException extends RuntimeException {

    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
