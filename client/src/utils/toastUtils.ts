import { XCircle, CheckCircle, Info } from 'lucide-vue-next';
import { type ExternalToast, toast } from 'vue-sonner';

export function notify(message: string, config: ExternalToast) {
    toast(message, config);
}

export function toastError(message: string) {
    notify(message, {
        style: { background: '#F15B5B', color: '#fff' },
        icon: XCircle,
    });
}

export function toastSuccess(message: string) {
    notify(message, {
        style: { background: '#90EE90', color: '#fff' },
        icon: CheckCircle,
    });
}

export function toastInfo(message: string) {
    notify(message, {
        style: { background: '#5AADCB', color: '#fff' },
        icon: Info,
    });
}