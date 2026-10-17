# BRIEF — loja-virtual-completa

## Projeto

E-commerce full-stack (área Full-Stack): Java Spring Boot 3 + React/TS.
Escopo MVP: carrinho, checkout, pagamento, pedidos.

## Entregas criadas

Backend (Java 21, Spring Boot 3, Maven):
- `pom.xml`
- `src/main/java/com/lojavirtual/LojaVirtualApplication.java`
- `src/main/resources/application.properties`
- domain: Product, Order, OrderItem, Payment, Cart; enums OrderStatus, PaymentStatus, PaymentMethod
- dto: ProductRequest, ProductResponse, OrderItemResponse, OrderResponse, PaymentRequest, PaymentResponse
- repository: ProductRepository, OrderRepository, PaymentRepository, CartRepository + implementações em memória (InMemory*)
- service: ProductService, CartService, OrderService, PaymentService
- controller: ProductController (/produtos), OrderController (/pedidos), PaymentController (/pagamentos)
- exception: GlobalExceptionHandler, ResourceNotFoundException, BusinessException, ErrorResponse

Frontend (React 18 + TypeScript + Tailwind + Vite):
- `frontend/package.json`, `vite.config.ts`, `tailwind.config.js`, `postcss.config.js`, `tsconfig.json`, `index.html`
- `frontend/src/main.tsx`, `index.css`, `App.tsx`, `api.ts`, `types.ts`
- `frontend/src/components/ProductList.tsx`, `Cart.tsx`, `Checkout.tsx`, `OrderConfirmed.tsx`

Testes:
- `src/test/java/com/lojavirtual/service/ProductServiceTest.java` (9)
- `src/test/java/com/lojavirtual/service/CartServiceTest.java` (6)
- `src/test/java/com/lojavirtual/service/OrderServiceTest.java` (6)
- `src/test/java/com/lojavirtual/service/PaymentServiceTest.java` (5)
- `src/test/java/com/lojavirtual/exception/GlobalExceptionHandlerTest.java` (3)
Total: 29 testes JUnit 5 (requisito mínimo: 26+).

Documentação:
- `README.md` — bilíngue PT-BR + EN (título, descrição, stack, estrutura, como usar, endpoints, testes, fluxo de compra, licença MIT, autor Kelvin Oliveira)
- `BRIEF.md` — este arquivo

## Comando de teste usado

```
cd C:\Users\Kelvin\AppData\Local\Temp\opencode\github-staging\projects\loja-virtual-completa
./mvnw test
```
(equivalente a `mvn test`; Maven 3.9.9 + Java 21 disponíveis neste host)

## Resultado dos testes

Tests run: 29, Failures: 0, Errors: 0, Skipped: 0 — BUILD SUCCESS.
Todos os 29 testes passam.

## Caminhos

- Projeto: C:\Users\Kelvin\AppData\Local\Temp\opencode\github-staging\projects\loja-virtual-completa\
- README: C:\Users\Kelvin\AppData\Local\Temp\opencode\github-staging\projects\loja-virtual-completa\README.md
- BRIEF: C:\Users\Kelvin\AppData\Local\Temp\opencode\github-staging\projects\loja-virtual-completa\BRIEF.md

## Notas de ambiente

- O frontend requer `npm install` (dependências não baixadas neste escopo).
- Repositórios em memória: sem banco externo, testes rodam offline e determinísticos.
