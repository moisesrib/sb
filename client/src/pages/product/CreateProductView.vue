<script setup lang="ts">
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip'
import { Button } from '@/components/ui/button'
import { Textarea } from '@/components/ui/textarea'
import { BadgeDollarSign, BadgeInfo, Loader2 } from 'lucide-vue-next'
import { ref, computed } from 'vue';
import { createProduct } from '@/service/product/productService';
import { z } from 'zod';
import { toastError, toastInfo, toastSuccess } from '@/utils/toastUtils'

const loading = ref(false);
const name = ref('');
const description = ref('');
const price = ref(0);
const promotional = ref(0);
const cost = ref(0);
const stock = ref(0);

type FieldErrors = {
  name: string;
  description: string;
  price: string;
  promotional: string;
  cost: string;
  stock: string;
};

const errors = ref<FieldErrors>({
  name: '',
  description: '',
  price: '',
  promotional: '',
  cost: '',
  stock: ''
});

const productSchema = z.object({
  name: z.string().min(3, { message: 'Nome deve ter no mínimo 3 caracteres' }),
  description: z.string().optional().refine(
    (val) => !val || val.length >= 10,
    { message: 'Descrição deve ter no mínimo 10 caracteres' }
  ),
  price: z.number().min(0.01, { message: 'Preço deve ser maior que zero' }),
  promotional: z.number().optional(),
  cost: z.number().min(0, { message: 'Custo deve ser um valor positivo' }).optional(),
  stock: z.number().int({ message: 'Estoque deve ser um número inteiro' })
    .min(0, { message: 'Estoque deve ser positivo' })
});

const validateFields = () => {
  errors.value = {
    name: '',
    description: '',
    price: '',
    promotional: '',
    cost: '',
    stock: ''
  };
  
  const formData = {
    name: name.value,
    description: description.value,
    price: price.value,
    promotional: promotional.value,
    cost: cost.value,
    stock: stock.value
  };
  
  const result = productSchema.safeParse(formData);
  
  if (!result.success) {
    const formattedErrors = result.error.format();
    
    if (formattedErrors.name?._errors?.length) 
      errors.value.name = formattedErrors.name._errors[0];
    
    if (formattedErrors.description?._errors?.length) 
      errors.value.description = formattedErrors.description._errors[0];
    
    if (formattedErrors.price?._errors?.length) 
      errors.value.price = formattedErrors.price._errors[0];
    
    if (formattedErrors.promotional?._errors?.length) 
      errors.value.promotional = formattedErrors.promotional._errors[0];
    
    if (formattedErrors.cost?._errors?.length) 
      errors.value.cost = formattedErrors.cost._errors[0];
    
    if (formattedErrors.stock?._errors?.length) 
      errors.value.stock = formattedErrors.stock._errors[0];
    
    return false;
  }
  
  return true;
};

const margin = computed(() => {
  const priceValue = Number(price.value);
  const promotionalValue = Number(promotional.value);
  const costValue = Number(cost.value);
  
  if (promotionalValue && promotionalValue > 0) {
    if (!costValue || costValue <= 0) return 0;
    return Number((((promotionalValue - costValue) / promotionalValue) * 100).toFixed(2));
  } else {
    if (!priceValue || !costValue || priceValue <= 0 || costValue <= 0) return 0;
    return Number((((priceValue - costValue) / priceValue) * 100).toFixed(2));
  }
});

const create = async () => {
  if (!validateFields()) {
    toastInfo('Preencha todos os campos corretamente');
    return;
  }

  loading.value = true;
  try {
    const user = localStorage.getItem('user');
    const token = JSON.parse(user || '{}').token;
    
    const response = await createProduct({
      name: name.value,
      description: description.value,
      price: Number(price.value) || 0,
      promotional: Number(promotional.value) || 0,
      cost: Number(cost.value) || 0,
      stock: Number(stock.value) || 0,
      active: true,
    }, token || '');
    
    if (response.status === 400) {
      toastError('Erro ao criar produto');
      return;
    }
    toastSuccess('Produto criado com sucesso');
    resetForm();
  } catch (error) {
    toastError('Erro ao criar produto');
    console.error('Erro ao criar produto:', error);
  } finally {
    loading.value = false;
  }
}

const resetForm = () => {
  name.value = '';
  description.value = '';
  price.value = 0;
  promotional.value = 0;
  cost.value = 0;
  stock.value = 0;
  errors.value = {
    name: '',
    description: '',
    price: '',
    promotional: '',
    cost: '',
    stock: ''
  };
}
</script>

<template class="bg-[#f6f6f6]">
  <main class="flex flex-col items-center gap-6 justify-center px-4 py-6">
    <Card class="w-full max-w-3xl">
      <CardHeader>
        <CardTitle class="text-2xl">Nome e Descrição</CardTitle>
        <CardDescription>Nome e Descrição do produto</CardDescription>
      </CardHeader>
      <CardContent>
        <div class="grid w-full gap-6">
          <div class="grid w-full gap-1.5">
            <Label for="name">Nome</Label>
            <Input v-model="name" class="border-primary" :class="{ 'border-red-500': errors.name }" id="name" type="text" placeholder="Nome" />
            <p v-if="errors.name" class="text-sm text-red-500">{{ errors.name }}</p>
          </div>
          <div class="grid w-full gap-1.5">
            <Label for="description">Descrição</Label>
            <Textarea v-model="description" class="border-primary" :class="{ 'border-red-500': errors.description }" id="description" placeholder="Descrição do produto" />
            <p v-if="errors.description" class="text-sm text-red-500">{{ errors.description }}</p>
            <p v-else class="text-sm text-muted-foreground">
              Descrição do produto
            </p>
          </div>
        </div>
      </CardContent>
    </Card>

    <Card class="w-full max-w-3xl">
      <CardHeader>
        <CardTitle class="text-2xl">Preços</CardTitle>
        <CardDescription>Preço e preço de promoção e de custo do produto</CardDescription>
      </CardHeader>
      <CardContent>
        <div class="grid w-full gap-6">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="flex flex-col gap-2 w-full">
              <Label for="price" class="text-muted-foreground">Preço de venda</Label>
              <div class="relative items-center">
                <Input v-model="price" id="price" type="number" placeholder="0.00" class="pl-10 border-primary" :class="{ 'border-red-500': errors.price }" />
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                  <BadgeDollarSign class="size-6 text-muted-foreground" />
                </span>
              </div>
              <p v-if="errors.price" class="text-sm text-red-500">{{ errors.price }}</p>
            </div>
            <div class="flex flex-col gap-2 w-full">
              <Label for="promotional" class="text-muted-foreground">Preço promocional</Label>
              <div class="relative items-center">
                <Input v-model="promotional" id="promotional" type="number" placeholder="0.00" class="pl-10 border-primary" />
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                  <BadgeDollarSign class="size-6 text-muted-foreground" />
                </span>
              </div>
            </div>
          </div>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div class="flex flex-col gap-2 w-full">
              <Label for="cost" class="text-muted-foreground">Custo</Label>
              <div class="relative items-center">
                <Input v-model="cost" id="cost" type="number" placeholder="0.00" class="pl-10 border-primary" :class="{ 'border-red-500': errors.cost }" />
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                  <BadgeDollarSign class="size-6 text-muted-foreground" />
                </span>
              </div>
              <p v-if="errors.cost" class="text-sm text-red-500">{{ errors.cost }}</p>
            </div>

            <div class="flex flex-col gap-2 w-full">
              <div class="flex flex-row gap-1 m-0 p-0">
                <Label for="margin" class="text-muted-foreground">Margem de lucro</Label>
                <TooltipProvider>
                  <Tooltip>
                    <TooltipTrigger as-child>
                      <BadgeInfo class="size-3 text-muted-foreground text-black" />
                    </TooltipTrigger>
                    <TooltipContent>
                      <p>A margem é calculada sobre o preço promocional</p>
                    </TooltipContent>
                  </Tooltip>
                </TooltipProvider>
              </div>
              <div class="items-center">
                <Input disabled :value="`${margin}%`" id="margin" type="text" placeholder="0%" class="border-primary" />
              </div>
            </div>
          </div>

          <Label for="description-client" class="text-muted-foreground">
            É para uso interno, os seus clientes não o verão na loja.
          </Label>
        </div>
      </CardContent>
    </Card>

    <Card class="w-full max-w-3xl">
      <CardHeader>
        <CardTitle class="text-2xl">Estoque</CardTitle>
        <CardDescription>Estoque do produto</CardDescription>
      </CardHeader>
      <CardContent>
        <div class="grid w-full gap-6">
          <div class="grid w-full gap-1.5">
            <Label for="stock">Estoque</Label>
            <Input v-model="stock" class="border-primary" :class="{ 'border-red-500': errors.stock }" id="stock" type="number" placeholder="0" />
            <p v-if="errors.stock" class="text-sm text-red-500">{{ errors.stock }}</p>
          </div>
        </div>
      </CardContent>
    </Card>

    <Button class="w-full max-w-3xl cursor-pointer" :disabled="loading" @click="create">
      <Loader2 v-if="loading" class="size-4 animate-spin" />
      Criar produto
    </Button>
  </main>
</template>
