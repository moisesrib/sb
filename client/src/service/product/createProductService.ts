import apiClient from "../apiClient";


interface CreateProductDTO {
    name: string;
    description: string;
    price: number;
    promotional: number;
    cost: number;
    stock: number;
    active: boolean;
}

export const createProduct = async (data: CreateProductDTO) => {
  const response = await apiClient.post('/products', data);
  return response.data;
};

