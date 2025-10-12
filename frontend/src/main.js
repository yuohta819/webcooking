import { createApp } from 'vue'
import { createRouter, createWebHistory } from "vue-router"
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
import './style.css'
import App from './App.vue'
import AdminRoutes from './routes/AdminRoutes';
import ClientRoutes from './routes/ClientRoutes';
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css' // ✅ đúng với v4+
import 'simplebar'; // JS
import 'simplebar/dist/simplebar.css'; // ✅ cần thiết


const routes =
    [...ClientRoutes,
    ...AdminRoutes
    ]


const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to, from, next) => {
    const isLoggedIn = !!localStorage.getItem('admin') // kiểm tra có token không
    const requiresAuth = to.matched.some(record => record.meta.requiresAuth)

    if (requiresAuth && !isLoggedIn) {
        next('/admin/login') // nếu chưa login thì về login
    } else {
        next() // cho phép đi tiếp
    }
})

createApp(App).use(router).use(Toast).use(Antd).mount('#app')
