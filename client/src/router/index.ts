import MainLayout from '@/layouts/MainLayout.vue'
import AboutView from '@/pages/about/AboutView.vue'
import ErrorView from '@/pages/ErrorView.vue'
import HomeView from '@/pages/home/HomeView.vue'
import LoginView from '@/pages/login/LoginView.vue'
import CreateProductView from '@/pages/product/CreateProductView.vue'
import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/',
    component: MainLayout,
    children: [
      { path: '', component: HomeView },
      { path: 'about', component: AboutView },
      { path: 'product/create', component: CreateProductView },
    ],
  },
  {
    path: '/login',
    component: LoginView
  },
  { path: '/:catchAll(.*)*', component: ErrorView }
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})
