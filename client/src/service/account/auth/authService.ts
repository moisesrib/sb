import api, { type CustomAxiosRequestConfig } from "@/service/api";
import type { ResponseAuthDto, ResponseAuthManagerDto } from "@/types/account/auth";

export const login = async ({ email, password }: { email: string; password: string }) => {
    return await api.post<ResponseAuthDto>('/auth/login', { email, password }, {
        skipAuth: true,
    } as CustomAxiosRequestConfig);
};

export const authenticateManager = async ({ code, token }: { code: string; token: string }) => {
    return await api.get<ResponseAuthManagerDto>(`/auth/auth-manager/${code}`, {
        headers: {
            Authorization: `Bearer ${token}`,
        },
    } as CustomAxiosRequestConfig);
};
