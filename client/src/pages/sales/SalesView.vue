<script lang="ts" setup>
import { onMounted, onUnmounted, ref } from 'vue';
import { Input } from '@/components/ui/input';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { toastError, toastInfo, toastSuccess } from '@/utils/toastUtils';
import { getProductByBarcode } from '@/service/product/productService';
import { Loader2, LogOut, Trash2 } from 'lucide-vue-next';
import { Button } from '@/components/ui/button';
import { useRouter } from 'vue-router';
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
} from "@/components/ui/dialog";
import { authenticateManager } from '@/service/account/auth/authService';

interface Product {
    id: string;
    name: string;
    price: number;
    quantity: number;
    stock: number;
    barcode: string;
}

interface Manager {
    id: string;
    name: string;
    email: string;
    token: string;
}

const showAuthModal = ref(false);

const handleKeyPress = (event: KeyboardEvent) => {
    if (event.key === 'F9') {
        handleChangeSeller();
    } else if (event.key === 'F12') {
        showAuthModal.value = true;
    }
};

onMounted(() => {
    window.addEventListener('keydown', handleKeyPress);
});

onUnmounted(() => {
    window.removeEventListener('keydown', handleKeyPress);
});

const router = useRouter();
const barcode = ref('');
const products = ref<Product[]>([]);
const authManager = ref<Manager>();
const total = ref(0);
const hasError = ref(false);
const isLoadingInput = ref(false);
const isLoadingManager = ref(false);
const code = ref('');

const user = localStorage.getItem('user');
const name = JSON.parse(user || '{}').name;
const token = JSON.parse(user || '{}').token;

const handleBarcodeInput = async () => {
    
    if (!barcode.value) {
        barcode.value = '';
        toastInfo('Código de barras inválido');
        return;
    }

    isLoadingInput.value = true;

    try {
        const response = await getProductByBarcode(barcode.value, token);

        const newProduct = {
            id: response.id,
            name: response.name,
            price: response.price,
            quantity: 1,
            stock: response.stock,
            barcode: response.barcode
        };

        const existingProduct = products.value.find(product => product.id === newProduct.id);

        if (existingProduct) {
            if (existingProduct.quantity + 1 <= newProduct.stock) {
                existingProduct.quantity += 1;
                products.value = [...products.value];
                hasError.value = false;
            } else {
                toastError('Quantidade excede o estoque disponível');
                hasError.value = true;
                return;
            }
        } else {
            products.value.push(newProduct);
            hasError.value = false;
        }
        calculateTotal();
        barcode.value = '';
    } catch (error: any) {
        hasError.value = true;
    } finally {
        setTimeout(() => {
            hasError.value = false;
        }, 2000);
        barcode.value = '';
        isLoadingInput.value = false;
    }
};

const handleAuth = async () => {
   if(!code.value) {
    toastError('Senha inválida');
    return;
   }

   isLoadingManager.value = true;

    try {
        const response = await authenticateManager({ code: code.value, token: token });
        authManager.value = response.data;
        showAuthModal.value = false;
        toastSuccess('Autenticação realizada com sucesso!');
    } catch (error: any) {
        console.log(error);
    } finally {
        isLoadingManager.value = false;
        code.value = '';
    }
};

const calculateTotal = () => {
    total.value = products.value.reduce((sum, product) => {
        return sum + (product.price * product.quantity);
    }, 0);
};

const handleChangeSeller = () => {
    localStorage.removeItem('user');
    router.push('/login');
};

const removeProduct = (productId: string) => {
    products.value = products.value.filter(product => product.id !== productId);
    calculateTotal();
};

const finishSale = () => {
    if (products.value.length === 0) {
        toastInfo('Adicione produtos à venda');
        return;
    }

    toastSuccess('Venda finalizada com sucesso!');
    authManager.value = undefined;
    products.value = [];
    calculateTotal();
};

const handlePaste = (_: ClipboardEvent) => {
  setTimeout(() => {
    handleAuth();
  }, 10);
};

const handlePasteBarcode = (_: ClipboardEvent) => {
  setTimeout(() => {
    handleBarcodeInput();
  }, 10);
};
</script>

<template>
    <div class="container mx-auto p-4 h-screen overflow-hidden">
        <Card class="h-full flex flex-col">
            <CardHeader>
                <div class="flex justify-between items-center">
                    <div>
                        <CardTitle>Vendas</CardTitle>
                        <CardDescription>Bem-vindo, {{ name }}</CardDescription>
                    </div>
                    <div class="flex gap-2">
                        <Button v-if="products.length > 0" variant="outline" @click="showAuthModal = true" class="flex items-center gap-2 hover:bg-gray-100 transition-colors">
                            <LogOut class="h-4 w-4" />
                            Autenticação (F12)
                        </Button>
                        <Button variant="outline" @click="handleChangeSeller" class="flex items-center gap-2 hover:bg-gray-100 transition-colors">
                            <LogOut class="h-4 w-4" />
                            Trocar Vendedor (F9)
                        </Button>
                    </div>
                </div>
            </CardHeader>
            <CardContent class="flex-1 flex flex-col overflow-hidden">
                <div class="flex gap-4 mb-6 relative">
                    <Input :disabled="isLoadingInput" v-model="barcode"
                        @paste="handlePasteBarcode"
                        :placeholder="isLoadingInput ? 'Buscando produto...' : 'Escaneie o código de barras'"
                        :class="['w-full h-14 !text-2xl font-bold', hasError ? 'focus-visible:border-[#F15B5B]' : 'focus-visible:border-yellow-500']"
                        @keyup.enter="handleBarcodeInput" />
                    <Loader2 v-if="isLoadingInput" class="absolute right-4 top-1/2 -translate-y-1/2 animate-spin" />
                </div>

                <div class="flex-1 overflow-auto rounded-lg border border-gray-200 shadow-sm">
                    <Table>
                        <TableHeader>
                            <TableRow class="bg-gray-50">
                                <TableHead class="font-semibold text-gray-600">Produto</TableHead>
                                <TableHead class="font-semibold text-gray-600">Preço</TableHead>
                                <TableHead class="font-semibold text-gray-600">Quantidade</TableHead>
                                <TableHead class="font-semibold text-gray-600">Subtotal</TableHead>
                                <TableHead v-if="authManager?.token" class="font-semibold text-gray-600">Ações</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            <TableRow v-if="products.length > 0" v-for="product in products" :key="product.id" 
                                    class="hover:bg-gray-50 transition-colors">
                                <TableCell class="font-medium">{{ product.name }}</TableCell>
                                <TableCell class="text-gray-600">R$ {{ product.price.toFixed(2) }}</TableCell>
                                <TableCell>
                                    <div class="flex items-center gap-2">
                                        <span class="font-medium text-gray-700">{{ product.quantity }}</span>
                                    </div>
                                </TableCell>
                                <TableCell class="font-medium text-gray-700">R$ {{ (product.price * product.quantity).toFixed(2) }}</TableCell>
                                <TableCell v-if="authManager?.token">
                                    <Button variant="ghost" size="icon" @click="removeProduct(product.id)" class="text-red-500 hover:text-red-700">
                                        <Trash2 class="h-4 w-4" />
                                    </Button>
                                </TableCell>
                            </TableRow>
                            <TableRow v-else>
                                <TableCell :colspan="5" class="h-24 text-center text-gray-500">
                                    Nenhum produto encontrado
                                </TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </div>

                <div class="mt-6 flex justify-between items-center">
                    <Button @click="finishSale" class="bg-green-600 hover:bg-green-700 text-white">
                        Finalizar Venda
                    </Button>
                    <div class="text-2xl font-bold text-gray-800">
                        Total: R$ {{ total.toFixed(2) }}
                    </div>
                </div>
            </CardContent>
        </Card>

        <Dialog v-model:open="showAuthModal">
            <DialogContent v-on:interact-outside.prevent>
                <DialogHeader>
                    <DialogTitle>Autenticação</DialogTitle>
                    <DialogDescription>
                        Autentique-se para continuar
                    </DialogDescription>
                </DialogHeader>
                <div class="flex justify-end gap-4 mt-4 w-full"> 
                    <div class="relative w-full">
                        <Input
                          type="password"
                          :disabled="isLoadingManager"
                          v-model="code"
                          placeholder="Senha"
                          @paste="handlePaste"
                        />
                        <Loader2 v-if="isLoadingManager" class="absolute right-4 top-1/2 -translate-y-1/2 animate-spin" />
                    </div>
                    <Button variant="destructive" :disabled="isLoadingManager" class="cursor-pointer hover:bg-red-400 transition-colors" @click="showAuthModal = false">
                        <Loader2 v-if="isLoadingManager" class="h-4 w-4 animate-spin mr-2" />
                        Cancelar
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    </div>
</template>
