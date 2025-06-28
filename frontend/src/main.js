import { createApp } from 'vue'
import { createRouter, createWebHistory } from "vue-router"
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
import Main from './layout/main/Main.vue'
import AboutUs from './layout/AboutUs/AboutUs.vue'
import FoodMenuPage from './layout/FoodMenu/FoodMenuPage.vue'
import Shop from './layout/Shop/Shop.vue'
import './style.css'
import App from './App.vue'
import Page from './layout/Page/Page.vue'
import CreateProduct from './layout/CreateProduct/CreateProduct.vue';
import Detail from './layout/Detail/Detail.vue';
import AccountRegister from './layout/Page/AccountRegister.vue';
import Cart from './layout/Cart/Cart.vue';
import Create from './layout/Create/Create.vue';
import Edit from './layout/Edit/Edit.vue';
import EditProduct from './layout/Edit/EditProduct.vue';
const routes = [
    { path: '/', name: 'AuthSuccess', component: Main },
    { path: '/aboutus', component: AboutUs },
    { path: '/foodmenu', component: FoodMenuPage },
    { path: '/shop', component: Shop },
    { path: '/account', component: Page },
    { path: '/detail/:id', component: Detail },
    { path: '/register', component: AccountRegister },
    { path: '/cart', component: Cart }, 
    { path: '/create', component: Create},
    { path: '/edit', component: Edit},
    { path: '/edit/:id', component: EditProduct}
]

const router = createRouter({
    history: createWebHistory(),
    routes
})
createApp(App).use(router).use(Toast).mount('#app')
