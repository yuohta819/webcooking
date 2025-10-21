
import "vue-toastification/dist/index.css";
import Main from '../layout/client/main/Main.vue'
import AboutUs from '../layout/client/AboutUs/AboutUs.vue'
import FoodMenuPage from '../layout/client/FoodMenu/FoodMenuPage.vue'
import Shop from '../layout/client/Shop/Shop.vue'
import Page from '../layout/client/Page/Page.vue'
import Detail from '../layout/client/Detail/Detail.vue';
import AccountRegister from '../layout/client/Page/AccountRegister.vue';
import Cart from '../layout/client/Cart/Cart.vue';
import Create from '../layout/client/Create/Create.vue';
import Edit from '../layout/client/Edit/Edit.vue';
import EditProduct from '../layout/client/Edit/EditProduct.vue';
import Detaill from "../layout/client/DetailAccount/Detail.vue";
import Step from "../layout/client/DetailAccount/Step.vue";
import LoginAdmin from "../layout/admin/LoginAdmin.vue";
import OrderHistory from "../layout/client/DetailAccount/OrderHistory.vue";
import AccountDetail from "../layout/client/DetailAccount/AccountDetail.vue";
import ForgotPassword from "../layout/client/Page/ForgotPassword.vue";
import ResetPassword from "../layout/client/Page/ResetPassword.vue";
import Checkout from "../layout/client/Cart/Checkout.vue";
import MenuQr from "../layout/client/Shop/MenuQr.vue";
import Contact from "../layout/client/Page/Contact.vue";
import CommentHistoryPage from "../layout/client/DetailAccount/CommentHistoryPage.vue";
export default [
    { path: '/', name: 'AuthSuccess', component: Main, meta: { layout: 'client' } },
    { path: '/aboutus', component: AboutUs, meta: { layout: 'client' } },
    { path: '/foodmenu', component: FoodMenuPage, meta: { layout: 'client' } },
    { path: '/shop', component: Shop, meta: { layout: 'client' } },
    { path: '/account/login', component: Page, meta: { layout: 'client' } },
    { path: '/detail/:id', component: Detail, meta: { layout: 'client' } },
    { path: '/register', component: AccountRegister, meta: { layout: 'client' } },
    { path: '/cart', component: Cart, meta: { layout: 'client' } },
    { path: '/checkout', component: Checkout, meta: { layout: 'client' } },
    { path: '/create', component: Create, meta: { layout: 'client' } },
    { path: '/edit', component: Edit, meta: { layout: 'client' } },
    { path: '/edit/:id', component: EditProduct, meta: { layout: 'client' } },
    { path: '/forgot', component: ForgotPassword, meta: { layout: 'client' } },
    { path: '/forgot/reset', component: ResetPassword, meta: { layout: 'client' } },
    { path: '/menu', component: MenuQr, meta: { layout: 'client' } },
    { path: '/contact', component: Contact, meta: { layout: 'client' } },

    {
        path: '/infor',
        component: Detaill,
        meta: { layout: 'client' },
        children: [
            {
                path: 'step',
                component: Step,

            },
            {
                path: 'history',
                component: OrderHistory
            },
            {
                path: 'account',
                component: AccountDetail
            },
            {
                path: 'feedback',
                component: CommentHistoryPage
            }
        ]
    },
    {
        path: '/admin/login',
        component: LoginAdmin
    }

]