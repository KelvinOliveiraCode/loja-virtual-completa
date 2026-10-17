const API = '/api';

export async function listarProdutos(): Promise<Product[]> {
  const res = await fetch(`${API}/produtos`);
  if (!res.ok) throw new Error(`Falha ao listar produtos: ${res.status}`);
  return (await res.json()) as Product[];
}

export async function criarProduto(body: {
  nome: string;
  descricao: string;
  preco: number;
  estoque: number;
  ativo?: boolean;
}): Promise<Product> {
  const res = await fetch(`${API}/produtos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(body),
  });
  if (!res.ok) throw new Error(`Falha ao criar produto: ${res.status}`);
  return (await res.json()) as Product;
}

export async function checkout(clienteId: number): Promise<Order> {
  const res = await fetch(`${API}/pedidos/checkout?clienteId=${clienteId}`, {
    method: 'POST',
  });
  if (!res.ok) throw new Error(`Falha no checkout: ${res.status}`);
  return (await res.json()) as Order;
}

export async function processarPagamento(
  pedidoId: number,
  metodo: string,
  valor: number,
): Promise<Payment> {
  const res = await fetch(`${API}/pagamentos`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ pedidoId, metodo, valor }),
  });
  if (!res.ok) throw new Error(`Falha no pagamento: ${res.status}`);
  return (await res.json()) as Payment;
}

interface Product {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  estoque: number;
  ativo: boolean;
}

interface Order {
  id: number;
  clienteId: number;
  items: Array<{
    produtoId: number;
    nome: string;
    precoUnitario: number;
    quantidade: number;
    subtotal: number;
  }>;
  status: string;
  total: number;
  createdAt: string;
}

interface Payment {
  id: number;
  pedidoId: number;
  valor: number;
  metodo: string;
  status: string;
  pagoEm: string;
}
