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

import axios from 'axios'

router.beforeEach(async (to, from, next) => {
  let token = localStorage.getItem('adminToken') || sessionStorage.getItem('adminToken')
  let account = localStorage.getItem('admin') || sessionStorage.getItem('admin')
  // Nếu đang vào route admin
  if (to.path.startsWith('/admin')) {
    // Không có token → quay lại login
    if (!token && to.path !== '/admin/login') {
      return next({ path: '/admin/login' })
    }

    // Có token → kiểm tra với backend
    if (token) {
      try {
        const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/${import.meta.env.VITE_APP_NAME}/token`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
          params: {account}
        })

        // Token hợp lệ
        if (res.data === 'valid') {
          if (to.path === '/admin/login') {
            return next({ path: '/admin/dashboard' })
          }
          return next()
        } else {
          // Token không hợp lệ → xóa token
          // localStorage.removeItem('adminToken')
          // sessionStorage.removeItem('adminToken')
          return next({ path: '/admin/login' })
        }
      } catch (err) {
        console.error('❌ Lỗi xác minh token:', err)
        localStorage.removeItem('adminToken')
        sessionStorage.removeItem('adminToken')
        return next({ path: '/admin/login' })
      }
    }
  }

  // Route thường thì bỏ qua
  next()
})





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
