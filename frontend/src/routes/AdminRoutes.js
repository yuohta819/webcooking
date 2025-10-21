
import Edit from "../layout/admin/main/edit/Edit.vue"
import Main from "../layout/admin/main/Main.vue"
import CreateProduct from "../layout/admin/main/create/CreateProduct.vue"
import StatusBill from "../layout/admin/main/statusBill/StatusBill.vue"
import CreateAdmin from "../layout/admin/main/create/CreateAdmin.vue"
import Dashboard from "../layout/admin/main/dashboard/Dashboard.vue"
import Decentralization from "../layout/admin/main/decentralization/Decentralization.vue"
import ExportExcel from "../layout/admin/main/excel/ExportExcel.vue"
import TableManagement from "../layout/admin/main/tablenumber/TableManagement.vue"
import AddTable from "../layout/admin/main/tablenumber/AddTable.vue"
import Menu from "../layout/admin/main/menu/Menu.vue"
import FeelBack from "../layout/admin/main/feelBack/FeelBack.vue"
import ListAccountAdmin from "../layout/admin/main/account/ListAccountAdmin.vue"
import ManagerCart from "../layout/admin/main/statusBill/ManagerCart.vue"
export default [
    {
        path: `/${import.meta.env.VITE_APP_NAME}`,
        component: Main,
        meta: { requiresAuth: true },
        children: [
            {
                path: 'dashboard',
                component: Dashboard
            },

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
                        component: () => import("../layout/admin/main/edit/EditProduct.vue")
                    },
                    {
                        path: 'history',
                        component: () => import("../layout/admin/main/edit/HistoricDelete.vue")
                    }
                ]
            },
            {
                path: 'bill',
                component: StatusBill
            },
            {
                path: 'tablenumber',
                component: TableManagement
            },
            {
                path: 'addtable',
                component: AddTable
            },
            {
                path: 'manager',
                component: ManagerCart
            },
            {
                path: 'listadmin',
                component: ListAccountAdmin
            },
            {
                path: 'createadmin',
                component: CreateAdmin
            },

            {
                path: 'decentralization',
                component: Decentralization,
            },
            {
                path: 'menu',
                component: Menu
            },
            {
                path: "excel/export",
                component: ExportExcel
            },
            {
                path: "feedback",
                component: FeelBack
            }

        ]
    },
]