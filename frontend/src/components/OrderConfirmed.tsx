import type { Order, Payment } from '../types';

interface Props {
  order: Order | null;
  payment: Payment | null;
  onNewPurchase: () => void;
}

export default function OrderConfirmed({ order, payment, onNewPurchase }: Props) {
  return (
    <section className="text-center py-10">
      <div className="bg-white p-8 rounded border border-gray-200 shadow-sm inline-block">
        <div className="text-green-600 text-2xl font-bold mb-2">✔ Compra concluída!</div>
        {order && (
          <div className="mt-4 text-left">
            <div className="text-sm text-gray-500 uppercase tracking-wider mb-1">Pedido</div>
            <div className="font-bold text-gray-900">#{order.id}</div>
            <div className="text-sm text-gray-500">Total: R$ {order.total.toFixed(2)}</div>
            <div className="text-sm text-gray-500 mt-1">Status: {order.status}</div>
          </div>
        )}
        {payment && (
          <div className="mt-4 text-left">
            <div className="text-sm text-gray-500 uppercase tracking-wider mb-1">Pagamento</div>
            <div className="font-bold text-gray-900">{payment.status}</div>
            <div className="text-sm text-gray-500">Método: {payment.metodo}</div>
          </div>
        )}
        <button
          onClick={onNewPurchase}
          className="mt-6 px-4 py-2 text-sm bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          Nova compra
        </button>
      </div>
    </section>
  );
}
