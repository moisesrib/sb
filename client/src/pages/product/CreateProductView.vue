<script setup lang="ts">
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card'
import { Tooltip, TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip'
import { Button } from '@/components/ui/button'
import { Textarea } from '@/components/ui/textarea'
import { BadgeDollarSign, BadgeInfo, Loader2 } from 'lucide-vue-next'
import { ref } from 'vue';



const loading = ref(false);


const create =  async () => {
  loading.value = true;

  const response = await createProduct({
    name: 'teste',
    description: 'teste',
    price: 10,
    promotional: 10,
    cost: 10,
  });
 
}

</script>

<template class="bg-[#f6f6f6]">
  <main class="flex flex-col items-center gap-6 justify-center m-6">

    <Card class="w-[750px]">
      <CardHeader>
        <CardTitle class="text-2xl">Nome e Descrição</CardTitle>
        <CardDescription>Nome e Descrição do produto</CardDescription>
      </CardHeader>
      <CardContent>
        <div class="grid w-full gap-6">
          <div class="grid w-full gap-1.5">
            <Label for="name">Nome</Label>
            <Input class="border-primary" id="name" type="name" placeholder="Nome" />

          </div>
          <div class="grid w-full gap-1.5">
            <Label for="description">Descrição</Label>
            <Textarea class="border-primary" id="description" placeholder="Descrição do produto" />
            <p class="text-sm text-muted-foreground">
              Descrição do produto
            </p>
          </div>

        </div>
      </CardContent>
    </Card>

    <Card class="w-[750px]">
      <CardHeader>
        <CardTitle class="text-2xl">Preços</CardTitle>
        <CardDescription>Preço e preço de promoção e de custo do produto</CardDescription>
      </CardHeader>
      <CardContent>

        <div class="grid w-full gap-6">
          <div class="flex flex-row gap-4">
            <div class="flex flex-col gap-2 w-full">
              <Label for="price-in" class="text-muted-foreground">
                Preço de venda
              </Label>
              <div class="relative items-center">
                <Input id="price-in" type="number" placeholder="0.00" class="pl-10 border-primary" />
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                  <BadgeDollarSign class="size-6 text-muted-foreground" />
                </span>
              </div>
            </div>
            <div class="flex flex-col gap-2 w-full">
              <Label for="price-in" class="text-muted-foreground">
                Preço promocional
              </Label>
              <div class="relative items-center">
                <Input id="price-in" type="number" placeholder="0.00" class="pl-10 border-primary" />
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                  <BadgeDollarSign class="size-6 text-muted-foreground" />
                </span>
              </div>
            </div>
          </div>
          <div class="flex flex-row gap-4">
            <div class="flex flex-col gap-2 w-full">
              <Label for="price-in" class="text-muted-foreground">
                Custo
              </Label>
              <div class="relative items-center">
                <Input id="price-in" type="number" placeholder="0.00" class="pl-10 border-primary" />
                <span class="absolute start-0 inset-y-0 flex items-center justify-center px-2">
                  <BadgeDollarSign class="size-6 text-muted-foreground" />
                </span>
              </div>
            </div>
            <div class="flex flex-col gap-2 w-full">
              <div class="flex flex-row gap-1 m-0 p-0">
                <Label for="price-in" class="text-muted-foreground">
                  Margin de lucro
                </Label>
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
                <Input disabled id="price-in" type="number" placeholder="0%" class="border-primary" />
              </div>
            </div>
          </div>
          <Label for="description-client" class="text-muted-foreground">É para uso interno, os seus clientes não o verão
            na loja.</Label>
        </div>
      </CardContent>
    </Card>
    <Card class="w-[750px]">
      <CardHeader>
        <CardTitle class="text-2xl">Estoque</CardTitle>
        <CardDescription>Estoque do produto</CardDescription>
      </CardHeader>
      <CardContent>

        <div class="grid w-full gap-6">
          <div class="grid w-full gap-1.5">
            <Label for="name">Estoque</Label>
            <Input class="border-primary" id="stock" type="number" placeholder="0" />
          </div>
        </div>
      </CardContent>
    </Card>
    <Button class="w-[750px] cursor-pointer" :disabled="loading" @click="create">
      <Loader2 v-if="loading" class="size-4 animate-spin" />
      Criar produto
    </Button>
  </main>
</template>
