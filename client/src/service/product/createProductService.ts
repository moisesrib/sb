import api from "../api";


interface CreateProductDTO {
    name: string;
    description: string;
    price: number;
    promotional: number;
    cost: number;
    stock: number;
    active: boolean;
}

export const createProduct = async (data: CreateProductDTO, token: string) => {
  const response = await api.post('/products', data, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });
  return response.data;
};

