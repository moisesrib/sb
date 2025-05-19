<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { onMounted, onUnmounted, ref } from 'vue';
import { z } from 'zod';
import { toastError, toastInfo, toastSuccess } from '@/utils/toastUtils'
import { EyeIcon, EyeOffIcon, Loader2 } from 'lucide-vue-next'
import { login } from '@/service/account/auth/authService';
import { router } from '@/router';

const handleKeyPress = (event: KeyboardEvent) => {
    console.log(event.key);
    if (event.key === 'Enter') {
        handleLogin();
    }
};

onMounted(() => {
    window.addEventListener('keydown', handleKeyPress);
});

onUnmounted(() => {
    window.removeEventListener('keydown', handleKeyPress);
});

const loading = ref(false);
const email = ref('');
const password = ref('');
const showPassword = ref(false);

type FieldErrors = {
    email: string;
    password: string;
};

const errors = ref<FieldErrors>({
    email: '',
    password: '',
});

const loginSchema = z.object({
    email: z.string().email({ message: 'Email inválido' }),
    password: z.string().min(6, { message: 'Senha deve ter no mínimo 6 caracteres' }),
});

const togglePassword = () => {
    showPassword.value = !showPassword.value;
};

const validateFields = () => {
    errors.value = {
        email: '',
        password: '',
    };

    const formData = {
        email: email.value,
        password: password.value,
    };

    const result = loginSchema.safeParse(formData);

    if (!result.success) {
        const formattedErrors = result.error.format();

        if (formattedErrors.email?._errors?.length)
            errors.value.email = formattedErrors.email._errors[0];

        if (formattedErrors.password?._errors?.length)
            errors.value.password = formattedErrors.password._errors[0];

        return false;
    }

    return true;
};

const handleLogin = async () => {
    if (!validateFields()) {
        toastInfo('Preencha todos os campos corretamente');
        return;
    }
    loading.value = true;
    try {
        const response = await login({ email: email.value, password: password.value });

        const user = response.data;
        localStorage.setItem('user', JSON.stringify(user));
        
        if(user.role === 'SELLER') {
            router.push('/sales');
        } else {
            router.push('/chosen');
        }
           
        toastSuccess('Login realizado com sucesso');
    } catch (error) {
        console.error('Erro ao fazer login:', error);
    } finally {
        loading.value = false;
    }
};
</script>

<template>
    <div class="w-full h-screen overflow-hidden flex flex-col lg:grid lg:grid-cols-2">
        <div class="hidden bg-muted lg:block h-full">
            <img src="../../../public/login.png" alt="Image" width="1920" height="1080"
                class="h-full w-full object-cover dark:brightness-[0.2] dark:grayscale">
        </div>
        <div class="h-full flex items-center justify-center p-4 lg:p-12 overflow-y-auto">
            <div class="mx-auto grid w-[350px] gap-6">
                <div class="grid gap-2 text-center">
                    <h1 class="text-3xl font-bold">
                        Bem-vindo
                    </h1>
                    <p class="text-balance text-muted-foreground">
                        Entre com seu email abaixo para acessar sua conta
                    </p>
                </div>
                <div class="grid gap-4">
                    <div class="grid gap-1">
                        <Label for="email">Email</Label>
                        <Input id="email" type="email" v-model="email" placeholder="exemplo@email.com"
                            :class="{ 'border-red-500': errors.email }" required />
                        <p v-if="errors.email" class="text-sm text-red-500 ">{{ errors.email }}</p>
                    </div>
                    <div class="grid gap-1">
                        <div class="flex items-center">
                            <Label for="password">Password</Label>
                            <a href="/forgot-password" class="ml-auto inline-block text-sm underline">
                                Esqueceu sua senha?
                            </a>
                        </div>
                        <div class="relative">
                            <Input 
                                id="password" 
                                :type="showPassword ? 'text' : 'password'" 
                                v-model="password"
                                :class="{ 'border-red-500': errors.password }" 
                                required 
                                class="pr-10"
                            />
                            <button 
                                type="button"
                                class="absolute inset-y-0 right-0 flex items-center pr-3 text-gray-400 hover:text-gray-600"
                                @click="togglePassword"
                            >
                                <EyeIcon v-if="showPassword" class="h-5 w-5" />
                                <EyeOffIcon v-else class="h-5 w-5" />
                            </button>
                        </div>
                        <p v-if="errors.password" class="text-sm text-red-500">{{ errors.password }}</p>
                    </div>
                    <Button type="submit" class="w-full cursor-pointer" :disabled="loading" @click="handleLogin" @keyup.enter="handleLogin">
                        <Loader2 v-if="loading" class="size-4 animate-spin" />
                        Login
                    </Button>
                </div>
                <!-- <div class="mt-4 text-center text-sm">
          Don't have an account?
          <a href="#" class="underline">
            Sign up
          </a>
        </div> -->
            </div>
        </div>

    </div>
</template>