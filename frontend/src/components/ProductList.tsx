import type { Product } from '../types';

interface Props {
  products: Product[];
  onAdd: (id: number) => void;
}

export default function ProductList({ products, onAdd }: Props) {
  if (products.length === 0) {
    return <p className="text-gray-500 text-center">Nenhum produto disponível.</p>;
  }

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
      {products.map((p) => (
        <div key={p.id} className="bg-white p-4 rounded border border-gray-200 shadow-sm">
          <h3 className="font-semibold text-gray-900">{p.nome}</h3>
          <p className="text-sm text-gray-600 mt-1">{p.descricao}</p>
          <p className="mt-2 font-bold text-gray-900">R$ {p.preco.toFixed(2)}</p>
          <p className="text-xs text-gray-500">Estoque: {p.estoque}</p>
          <button
            disabled={p.estoque === 0}
            onClick={() => onAdd(p.id)}
            className="mt-3 w-full px-3 py-1.5 text-sm bg-green-600 text-white rounded hover:bg-green-700 disabled:bg-gray-300 disabled:text-gray-500"
          >
            Adicionar ao carrinho
          </button>
        </div>
      ))}
    </div>
  );
}
