import type { Product } from '../types';

interface CartItem {
  produto: Product;
  quantidade: number;
}

interface Props {
  items: CartItem[];
  total: number;
  onRemove: (id: number) => void;
  onCheckout: () => void;
  onBack: () => void;
}

export default function Cart({ items, total, onRemove, onCheckout, onBack }: Props) {
  return (
    <section>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-lg font-semibold text-gray-900">Carrinho</h2>
        <button onClick={onBack} className="text-sm text-gray-600 hover:underline">
          Voltar aos produtos
        </button>
      </div>

      {items.length === 0 ? (
        <p className="text-gray-500 text-center py-10">Seu carrinho está vazio.</p>
      ) : (
        <>
          <div className="bg-white rounded border border-gray-200 shadow-sm">
            {items.map(({ produto, quantidade }) => (
              <div
                key={produto.id}
                className="flex justify-between items-center p-3 border-b last:border-0"
              >
                <div>
                  <div className="font-medium text-gray-900">{produto.nome}</div>
                  <div className="text-sm text-gray-500">
                    R$ {produto.preco.toFixed(2)} × {quantidade}
                  </div>
                </div>
                <button
                  onClick={() => onRemove(produto.id)}
                  className="text-sm text-red-600 hover:underline"
                >
                  Remover
                </button>
              </div>
            ))}
            <div className="p-3 border-t flex justify-between font-bold text-gray-900">
              <span>Total</span>
              <span>R$ {total.toFixed(2)}</span>
            </div>
          </div>
          <div className="mt-6">
            <button
              onClick={onCheckout}
              disabled={items.length === 0}
              className="px-4 py-2 text-sm bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              Finalizar compra
            </button>
          </div>
        </>
      )}
    </section>
  );
}
