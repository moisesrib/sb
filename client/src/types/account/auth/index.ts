import type { AxiosRequestConfig } from "axios";

export interface ResponseAuthDto {
    id: string;
    name: string;
    email: string;
    token: string;
}

export interface CustomAxiosRequestConfig extends AxiosRequestConfig {
    skipAuth?: boolean;
}



