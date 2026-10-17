package com.lojavirtual.controller;

import com.lojavirtual.domain.enums.PaymentMethod;
import com.lojavirtual.dto.PaymentRequest;
import com.lojavirtual.dto.PaymentResponse;
import com.lojavirtual.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/** Controlador de pagamentos. */
@RestController
@RequestMapping("/pagamentos")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse processar(@RequestBody PaymentRequest request) {
        return PaymentResponse.from(
                paymentService.processar(request.pedidoId(), request.metodo(), request.valor()));
    }

    @GetMapping("/{id}")
    public PaymentResponse buscar(@PathVariable Long id) {
        return PaymentResponse.from(paymentService.buscarPorId(id));
    }
}
