
import Edit from "../layout/Admin/Main/Edit/Edit.vue"
import Main from "../layout/Admin/Main/main.vue"
import CreateProduct from "../layout/Admin/Main/Create/CreateProduct.vue"
import StatusBill from "../layout/Admin/Main/StatusBill/StatusBill.vue"
import ManagerBill from "../layout/Admin/Main/StatusBill/ManagerBill.vue"
import LoginAdmin from "../layout/Admin/LoginAdmin.vue"
import CreateAdmin from "../layout/Admin/Main/Create/CreateAdmin.vue"
export default [
    {
        path: '/admin',
        component: Main,
        meta: { requiresAuth: true },
        children: [

            {
                path: 'create',
                component: CreateProduct
            },
            {
                path: 'edit',
                component: Edit,
                children: [
                    {
                        path: ':id',
                        component: () => import("../layout/Admin/Main/Edit/EditProduct.vue")
                    }
                ]
            },
            {
                path: 'bill',
                component: StatusBill
            },
            {
                path: 'manager',
                component: ManagerBill
            },
            {
                path: 'createadmin',
                component: CreateAdmin
            }
        ]
    },
]