import { type ExternalToast, toast } from 'vue-sonner';

export function notify(message: string, config: ExternalToast) {
    toast.info(message, config);
}

export function toastError(message: string) {
    notify(message, {
        style: { background: '#FFD700', color: '#fff' },
    });
}

export function toastSuccess(message: string) {
    notify(message, {
        style: { background: 'green', color: '#fff' },
    });
}

export function toastInfo(message: string) {
    notify(message, {
        style: { background: 'blue', color: '#fff' },
    });
}