package com.lojavirtual.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldReturn404_whenResourceNotFound() {
        ResponseEntity<ErrorResponse> resposta = handler.handleNotFound(
                new ResourceNotFoundException("n&atilde;o encontrado"));

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resposta.getBody().mensagem()).isEqualTo("n&atilde;o encontrado");
    }

    @Test
    void shouldReturn400_whenBusinessException() {
        ResponseEntity<ErrorResponse> resposta = handler.handleBusiness(
                new BusinessException("regras violadas"));

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(resposta.getBody().status()).isEqualTo(400);
    }

    @Test
    void shouldReturn500_whenGenericException() {
        ResponseEntity<ErrorResponse> resposta = handler.handleGeneric(new RuntimeException("falha"));

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(resposta.getBody().status()).isEqualTo(500);
    }
}
