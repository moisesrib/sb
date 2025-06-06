import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router';
import MainLayout from '@/layouts/MainLayout.vue';
import AboutView from '@/pages/about/AboutView.vue';
import ErrorView from '@/pages/ErrorView.vue';
import HomeView from '@/pages/home/HomeView.vue';
import LoginView from '@/pages/login/LoginView.vue';
import CreateProductView from '@/pages/product/CreateProductView.vue';
import ChosenView from '@/pages/chosen/ChosenView.vue';
import SalesView from '@/pages/sales/SalesView.vue';

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: MainLayout,
    meta: { requiresAuth: true },
    children: [
      { path: '', component: HomeView },
      { path: 'about', component: AboutView },
      { path: 'product/create', component: CreateProductView},
    ],
  },
  {
    path: '/login',
    component: LoginView
  },
  {
    path: '/chosen',
    component: ChosenView,
    meta: { requiresAuth: true },
  },
  {
    path: '/sales',
    component: SalesView,
    meta: { requiresAuth: true },
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
