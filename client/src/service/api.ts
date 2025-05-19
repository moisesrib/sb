import { router } from '@/router';
import { notify } from '@/utils/toastUtils';
import axios, { AxiosError, type AxiosRequestConfig } from 'axios';
import { type ExternalToast } from 'vue-sonner';

// import { getAuthToken } from '@/utils/cookieUtils.ts';

export function checkAxiosStatus(e: unknown, code: number): boolean {
  return e instanceof AxiosError && e?.response?.status === code;
}

export interface CustomAxiosRequestConfig extends AxiosRequestConfig {
  skipAuth?: boolean;
}

const api = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  config => {
    if ('skipAuth' in config && config.skipAuth) {
      return config;
    }

    const user = localStorage.getItem('user');
    const token = JSON.parse(user || '{}').token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  error => {
    if (error instanceof Error) {
      return Promise.reject(error);
    }
    return Promise.reject(new Error(String(error)));
  },
);

api.interceptors.response.use(
  response => response,
  error => {
    let message = 'An unexpected error occurred. Please try again.';
    const toastOptions: ExternalToast = {
      style: { background: '#F15B5B', color: '#fff' },
    };

    if (axios.isAxiosError(error)) {
      if (error.response?.data?.message) {
        message = error.response.data.message;
      }

      switch (error.response?.status) {
        case 403:
          message = 'Access denied. You do not have permission';
          localStorage.removeItem('user');
          router.push('/login');
          break;
        case 401:
          message = 'User not authorized';
          localStorage.removeItem('user');
          router.push('/login');
          break;
        case 400:
          if (error.response?.data?.violations?.[0]?.message) {
            message = error.response.data.violations[0].message;
          }
          break;
        default:
          console.error('Check axios error handler:', error.response ?? error.message);
          break;
      }
    }

    notify(message, toastOptions);

    if (error instanceof Error) {
      return Promise.reject(error);
    }
    return Promise.reject(new Error(String(error)));
  },
);

export default api;