import { useState } from 'react';
import type { Order } from '../types';

interface Props {
  order: Order | null;
  onPay: (metodo: string) => void;
  onBack: () => void;
  loading: boolean;
}

const METODOS: { id: string; label: string }[] = [
  { id: 'PIX', label: 'PIX' },
  { id: 'CARTAO_CREDITO', label: 'Cartão de Crédito' },
  { id: 'BOLETO', label: 'Boleto' },
];

export default function Checkout({ order, onPay, onBack, loading }: Props) {
  const [metodo, setMetodo] = useState(METODOS[0].id);

  return (
    <section>
      <div className="flex justify-between items-center mb-4">
        <h2 className="text-lg font-semibold text-gray-900">Pagamento</h2>
        <button onClick={onBack} className="text-sm text-gray-600 hover:underline">
          Voltar ao carrinho
        </button>
      </div>

      {order ? (
        <div className="bg-white p-6 rounded border border-gray-200 shadow-sm">
          <div className="mb-4">
            <div className="text-sm text-gray-500 uppercase tracking-wider">Pedido</div>
            <div className="font-bold text-gray-900">#{order.id}</div>
            <div className="text-sm text-gray-500">Total: R$ {order.total.toFixed(2)}</div>
          </div>

          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Forma de pagamento
            </label>
            <div className="space-y-2">
              {METODOS.map((m) => (
                <label key={m.id} className="flex items-center gap-2 p-2 border rounded cursor-pointer hover:bg-gray-50">
                  <input
                    type="radio"
                    name="metodo"
                    value={m.id}
                    checked={metodo === m.id}
                    onChange={() => setMetodo(m.id)}
                    className="text-blue-600 focus:ring-blue-500"
                  />
                  <span className="text-sm text-gray-700">{m.label}</span>
                </label>
              ))}
            </div>
          </div>

          <button
            onClick={() => onPay(metodo)}
            disabled={loading}
            className="w-full px-4 py-2 text-sm bg-blue-600 text-white rounded hover:bg-blue-700 disabled:opacity-50"
          >
            {loading ? 'Processando...' : 'Confirmar pagamento'}
          </button>
        </div>
      ) : (
        <p className="text-gray-500 text-center py-10">Nenhum pedido para pagar.</p>
      )}
    </section>
  );
}
