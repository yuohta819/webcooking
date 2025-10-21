import { createApp } from 'vue';
import { createRouter, createWebHistory } from 'vue-router';
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';
import './style.css';
import App from './App.vue';

import AdminRoutes from './routes/AdminRoutes';
import ClientRoutes from './routes/ClientRoutes';

import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';

import 'simplebar';
import 'simplebar/dist/simplebar.css';

import VueParticles from 'vue3-particles';
import { emitter } from './components/eventBus';

// 🧩 Gộp tất cả route
const routes = [...ClientRoutes, ...AdminRoutes];

// 🧩 Cấu hình Router
const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  let admin = null;

  try {
    const adminData = localStorage.getItem('admin') || sessionStorage.getItem('admin');
    admin = adminData ? JSON.parse(adminData) : null;
  } catch (e) {
    console.warn('⚠️ Lỗi JSON.parse admin:', e);
    // Xóa dữ liệu sai để tránh lặp lại lỗi
    localStorage.removeItem('admin');
    sessionStorage.removeItem('admin');
  }

  // Kiểm tra route
  if (to.path.startsWith('/admin')) {
    if (!admin && to.path !== '/admin/login') {
      return next({ path: '/admin/login' });
    }
    if (admin && to.path === '/admin/login') {
      return next({ path: '/admin/dashboard' });
    }
  }

  next();
});





// 🧩 Khởi tạo app
const app = createApp(App);

app.use(router);
app.use(Toast, {
  position: 'top-right',
  timeout: 2500,
});
app.use(Antd);
app.use(VueParticles);

app.config.globalProperties.$emitter = emitter;

app.mount('#app');
