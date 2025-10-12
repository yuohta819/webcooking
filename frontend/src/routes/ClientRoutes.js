
import "vue-toastification/dist/index.css";
import Main from '../layout/main/Main.vue'
import AboutUs from '../layout/AboutUs/AboutUs.vue'
import FoodMenuPage from '../layout/FoodMenu/FoodMenuPage.vue'
import Shop from '../layout/Shop/Shop.vue'
import Page from '../layout/Page/Page.vue'
import Detail from '../layout/Detail/Detail.vue';
import AccountRegister from '../layout/Page/AccountRegister.vue';
import Cart from '../layout/Cart/Cart.vue';
import Create from '../layout/Create/Create.vue';
import Edit from '../layout/Edit/Edit.vue';
import EditProduct from '../layout/Edit/EditProduct.vue';
import DetailAccount from "../layout/Client/DetailAccount/Detail.vue";
import Step from "../layout/Client/DetailAccount/Step.vue";
import LoginAdmin from "../layout/Admin/LoginAdmin.vue";
import Test from "../layout/Client/DetailAccount/test.vue";
export default [
    { path: '/', name: 'AuthSuccess', component: Main, meta: { layout: 'client' } },
    { path: '/aboutus', component: AboutUs, meta: { layout: 'client' } },
    { path: '/foodmenu', component: FoodMenuPage, meta: { layout: 'client' } },
    { path: '/shop', component: Shop, meta: { layout: 'client' } },
    { path: '/account', component: Page, meta: { layout: 'client' } },
    { path: '/detail/:id', component: Detail, meta: { layout: 'client' } },
    { path: '/register', component: AccountRegister, meta: { layout: 'client' } },
    { path: '/cart', component: Cart, meta: { layout: 'client' } },
    { path: '/create', component: Create, meta: { layout: 'client' } },
    { path: '/edit', component: Edit, meta: { layout: 'client' } },
    { path: '/edit/:id', component: EditProduct, meta: { layout: 'client' } },
    { path: '/test',component: Test},
    {
        path: '/infor', 
        component: DetailAccount,
        meta: { layout: 'client' },
        children: [
            {
                path: 'step',
                component: Step,

            }
        ]
    },
    {
        path: '/admin/login',
        component: LoginAdmin
    }
   
]