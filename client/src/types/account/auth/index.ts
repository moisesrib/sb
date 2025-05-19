import type { AxiosRequestConfig } from "axios";

export interface ResponseAuthDto {
    id: string;
    name: string;
    email: string;
    token: string;
    role: string;
}

export interface CustomAxiosRequestConfig extends AxiosRequestConfig {
    skipAuth?: boolean;
}

export interface ResponseAuthManagerDto {
    id: string;
    name: string;
    email: string;
    token: string;
}




