import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import MainLayout from '@/layouts/MainLayout.vue';
import AboutView from '@/pages/about/AboutView.vue';
import ErrorView from '@/pages/ErrorView.vue';
import HomeView from '@/pages/home/HomeView.vue';
import LoginView from '@/pages/login/LoginView.vue';
import CreateProductView from '@/pages/product/CreateProductView.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', component: HomeView },
      { path: 'about', component: AboutView },
      { path: 'product/create', component: CreateProductView, meta: { requiresAuth: true } },
    ],
  },
  {
    path: '/login',
    component: LoginView
  },
  { path: '/:catchAll(.*)*', component: ErrorView }
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, _, next) => {
  const user = localStorage.getItem('user');
  const token = JSON.parse(user || '{}').token;
  const isAuthenticated = !!token;

  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login');
  } else {
    next();
  }
});
