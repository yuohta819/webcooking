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

// 🧩 Middleware kiểm tra quyền truy cập
router.beforeEach((to, from, next) => {
  const admin = JSON.parse(localStorage.getItem('admin') || sessionStorage.getItem('admin') || 'null');
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

  // ✅ Nếu route yêu cầu quyền đăng nhập admin
  if (requiresAuth && !admin) {
    next('/admin/login');
  } else if (to.path === '/admin/login' && admin) {
    // ✅ Nếu đã login mà cố vào lại trang login thì đẩy về dashboard
    next('/admin/dashboard');
  } else {
    next();
  }
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
