import api, { type CustomAxiosRequestConfig } from "@/service/api";
import type { ResponseAuthDto } from "@/types/account/auth";

export const login = async ({ email, password }: { email: string; password: string }) => {
    return await api.post<ResponseAuthDto>('/auth/login', { email, password }, {
        skipAuth: true,
    } as CustomAxiosRequestConfig);
};