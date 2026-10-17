# Loja Virtual Completa

E-commerce full-stack com Java Spring Boot 3 + React/TS. Escopo de MVP: catálogo de produtos, carrinho de compras, checkout e pagamento.

Autor: Kelvin Oliveira
Licença: MIT

## Stack

- Backend: Java 21, Spring Boot 3 (spring-boot-starter-web), JUnit 5 + AssertJ, Maven
- Frontend: React 18, TypeScript, Tailwind CSS, Vite
- Repositórios em memória (sem banco externo) — testes determinísticos e offline

## Estrutura

```
loja-virtual-completa/
  pom.xml
  src/main/java/com/lojavirtual/
    domain/        Product, Order, OrderItem, Payment, Cart, enums
    dto/           ProductRequest/Response, OrderResponse, PaymentRequest/Response, OrderItemResponse
    repository/    interfaces + implementações em memória
    service/       ProductService, CartService, OrderService, PaymentService
    controller/    ProductController, OrderController, PaymentController
    exception/     GlobalExceptionHandler, ResourceNotFoundException, BusinessException, ErrorResponse
  src/test/java/com/lojavirtual/   29 testes JUnit 5
  frontend/      React + TS + Tailwind + Vite
  README.md
  BRIEF.md
```

## Como usar

### Backend

Requisitos: Java 21, Maven 3.

```bash
# Compilar
mvn compile

# Rodar a aplicação (porta 8080)
mvn spring-boot:run

# Executar os testes
./mvnw test
```

### Frontend

Requisitos: Node.js, npm.

```bash
cd frontend
npm install
npm run dev      # http://localhost:5173 (proxy para http://localhost:8080)
```

## Endpoints (backend)

- `GET /produtos` — lista produtos ativos
- `POST /produtos` — cadastra produto
- `PUT /produtos/{id}/estoque` — atualiza estoque
- `DELETE /produtos/{id}` — remove produto
- `POST /pedidos/checkout?clienteId=1` — converte carrinho em pedido
- `GET /pedidos` — lista pedidos
- `GET /pedidos/{id}` — busca pedido
- `POST /pagamentos` — processa pagamento (`{pedidoId, metodo, valor}`)
- `GET /pagamentos/{id}` — busca pagamento

## Testes

```bash
./mvnw test
```

Resultado: 29 testes, 0 falhas, 0 erros (BUILD SUCCESS). Requisito mínimo: 26+.

## Fluxo de compra

1. Listar produtos ativos.
2. Adicionar itens ao carrinho.
3. Checkout (`/pedidos/checkout?clienteId=X`) cria o pedido e baixa o estoque.
4. Pagamento (`/pagamentos`) aprova o pedido e muda o status para PAGO.

## Licença

MIT

## Autor

Kelvin Oliveira

---

# Complete Online Store

Full-stack e-commerce with Java Spring Boot 3 + React/TS. MVP scope: product catalog, shopping cart, checkout, and payment.

Author: Kelvin Oliveira
License: MIT

## Stack

- Backend: Java 21, Spring Boot 3 (spring-boot-starter-web), JUnit 5 + AssertJ, Maven
- Frontend: React 18, TypeScript, Tailwind CSS, Vite
- In-memory repositories (no external database) — deterministic, offline tests

## Structure

```
loja-virtual-completa/
  pom.xml
  src/main/java/com/lojavirtual/
    domain/        Product, Order, OrderItem, Payment, Cart, enums
    dto/           ProductRequest/Response, OrderResponse, PaymentRequest/Response, OrderItemResponse
    repository/    interfaces + in-memory implementations
    service/       ProductService, CartService, OrderService, PaymentService
    controller/    ProductController, OrderController, PaymentController
    exception/     GlobalExceptionHandler, ResourceNotFoundException, BusinessException, ErrorResponse
  src/test/java/com/lojavirtual/   29 JUnit 5 tests
  frontend/      React + TS + Tailwind + Vite
  README.md
  BRIEF.md
```

## How to use

### Backend

Requirements: Java 21, Maven 3.

```bash
# Compile
mvn compile

# Run the application (port 8080)
mvn spring-boot:run

# Run tests
./mvnw test
```

### Frontend

Requirements: Node.js, npm.

```bash
cd frontend
npm install
npm run dev      # http://localhost:5173 (proxy to http://localhost:8080)
```

## Endpoints (backend)

- `GET /produtos` — list active products
- `POST /produtos` — create product
- `PUT /produtos/{id}/estoque` — update stock
- `DELETE /produtos/{id}` — delete product
- `POST /pedidos/checkout?clienteId=1` — convert cart to order
- `GET /pedidos` — list orders
- `GET /pedidos/{id}` — get order
- `POST /pagamentos` — process payment (`{pedidoId, metodo, valor}`)
- `GET /pagamentos/{id}` — get payment

## Tests

```bash
./mvnw test
```

Result: 29 tests, 0 failures, 0 errors (BUILD SUCCESS). Minimum required: 26+.

## Purchase flow

1. List active products.
2. Add items to the cart.
3. Checkout (`/pedidos/checkout?clienteId=X`) creates the order and decrements stock.
4. Payment (`/pagamentos`) approves the order and sets status to PAGO.

## License

MIT

## Author

Kelvin Oliveira
