export interface Product {
  id: number;
  nome: string;
  descricao: string;
  preco: number;
  estoque: number;
  ativo: boolean;
}

export interface CartItem {
  produto: Product;
  quantidade: number;
}

export interface OrderItem {
  produtoId: number;
  nome: string;
  precoUnitario: number;
  quantidade: number;
  subtotal: number;
}

export interface Order {
  id: number;
  clienteId: number;
  items: OrderItem[];
  status: string;
  total: number;
  createdAt: string;
}

export interface Payment {
  id: number;
  pedidoId: number;
  valor: number;
  metodo: string;
  status: string;
  pagoEm: string;
}
