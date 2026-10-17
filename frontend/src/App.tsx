import { useState, useEffect } from 'react';
import { checkout, listarProdutos, processarPagamento } from './api';
import type { Order, Payment, Product } from './types';
import ProductList from './components/ProductList';
import Cart from './components/Cart';
import Checkout from './components/Checkout';
import OrderConfirmed from './components/OrderConfirmed';

type View = 'products' | 'cart' | 'checkout' | 'order';

interface CartEntry {
  produto: Product;
  quantidade: number;
}

export default function App() {
  const [view, setView] = useState<View>('products');
  const [products, setProducts] = useState<Product[]>([]);
  const [cart, setCart] = useState<Record<number, number>>({});
  const [order, setOrder] = useState<Order | null>(null);
  const [payment, setPayment] = useState<Payment | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const CLIENTE_ID = 1;

  useEffect(() => {
    listarProdutos()
      .then(setProducts)
      .catch((e) => setError(e instanceof Error ? e.message : String(e)));
  }, []);

  const cartItems: CartEntry[] = Object.entries(cart)
    .map(([produtoId, quantidade]) => {
      const produto = products.find((p) => p.id === Number(produtoId));
      if (!produto) return null;
      return { produto, quantidade };
    })
    .filter((x): x is CartEntry => x !== null);

  const total = cartItems.reduce(
    (s, { produto, quantidade }) => s + produto.preco * quantidade,
    0,
  );

  const add = (id: number) => {
    setCart((c) => ({ ...c, [id]: (c[id] ?? 0) + 1 }));
  };

  const remove = (id: number) => {
    setCart((c) => {
      const n = { ...c };
      delete n[id];
      return n;
    });
  };

  const handleCheckout = async () => {
    setLoading(true);
    setError(null);
    try {
      const ordem = await checkout(CLIENTE_ID);
      setOrder(ordem);
      setCart({});
      setView('checkout');
    } catch (e) {
      setError(e instanceof Error ? e.message : String(e));
    } finally {
      setLoading(false);
    }
  };

  const handlePay = async (metodo: string) => {
    if (!order) return;
    setLoading(true);
    setError(null);
    try {
      const pag = await processarPagamento(order.id, metodo, order.total);
      setPayment(pag);
      setView('order');
    } catch (e) {
      setError(e instanceof Error ? e.message : String(e));
    } finally {
      setLoading(false);
    }
  };

  const itemCount = Object.values(cart).reduce((a, b) => a + b, 0);

  return (
    <div className="min-h-screen bg-gray-50">
      <header className="bg-white border-b">
        <div className="max-w-6xl mx-auto px-4 py-4 flex justify-between items-center">
          <h1 className="text-xl font-bold text-gray-900">Loja Virtual</h1>
          <div className="flex items-center gap-2">
            <span className="text-sm text-gray-600">Cliente #{CLIENTE_ID}</span>
            <button
              onClick={() => setView('cart')}
              className="px-3 py-1.5 text-sm bg-gray-900 text-white rounded"
            >
              Carrinho ({itemCount})
            </button>
          </div>
        </div>
      </header>

      <main className="max-w-6xl mx-auto px-4 py-8">
        {error && (
          <div className="mb-4 p-3 rounded bg-red-50 text-red-800 text-sm border border-red-100">
            {error}
          </div>
        )}

        {view === 'products' && (
          <section>
            <h2 className="text-lg font-semibold text-gray-900 mb-4">Produtos</h2>
            <ProductList products={products} onAdd={add} />
          </section>
        )}

        {view === 'cart' && (
          <Cart
            items={cartItems}
            total={total}
            onRemove={remove}
            onCheckout={handleCheckout}
            onBack={() => setView('products')}
          />
        )}

        {view === 'checkout' && (
          <Checkout
            order={order}
            onPay={handlePay}
            onBack={() => setView('cart')}
            loading={loading}
          />
        )}

        {view === 'order' && (
          <OrderConfirmed
            order={order}
            payment={payment}
            onNewPurchase={() => setView('products')}
          />
        )}
      </main>
    </div>
  );
}
