<script lang="ts" setup>
import { router } from '@/router';


</script>

<template>
    <div class="flex flex-col items-center justify-center min-h-screen bg-gray-50 p-8">
        <div class="text-center mb-12">
            <h1 class="text-4xl font-bold text-gray-800 mb-4">Escolha o que deseja fazer</h1>
            <p class="text-gray-600">Selecione uma das opções abaixo para continuar</p>
        </div>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 w-full max-w-2xl">
            <button 
                @click="router.push('/')"
                class="flex flex-col items-center cursor-pointer justify-center p-8 bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 border border-gray-100 hover:bg-gray-50 hover:scale-105 active:scale-95 hover:border-primary/20"
            >
                <span class="text-2xl mb-4 transform transition-transform group-hover:scale-110">📊</span>
                <span class="text-xl font-semibold text-gray-800 hover:text-primary">Dashboard</span>
                <span class="text-gray-500 mt-2">Visualize seus dados</span>
            </button>
            <button
                @click="router.push('/sales')" 
                class="flex flex-col items-center cursor-pointer justify-center p-8 bg-white rounded-xl shadow-lg hover:shadow-xl transition-all duration-300 border border-gray-100 hover:bg-gray-50 hover:scale-105 active:scale-95 hover:border-primary/20"
            >
                <span class="text-2xl mb-4 transform transition-transform group-hover:scale-110">💰</span>
                <span class="text-xl font-semibold text-gray-800 hover:text-primary">Vendas</span>
                <span class="text-gray-500 mt-2">Faça suas vendas</span>
            </button>
        </div>
    </div>
</template>
