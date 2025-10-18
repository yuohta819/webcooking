
import Edit from "../layout/Admin/Main/Edit/Edit.vue"
import Main from "../layout/Admin/Main/Main.vue"
import CreateProduct from "../layout/Admin/Main/Create/CreateProduct.vue"
import StatusBill from "../layout/Admin/Main/StatusBill/StatusBill.vue"
import ManagerBill from "../layout/Admin/Main/StatusBill/ManagerBill.vue"
import LoginAdmin from "../layout/Admin/LoginAdmin.vue"
import CreateAdmin from "../layout/Admin/Main/Create/CreateAdmin.vue"
import Dashboard from "../layout/Admin/Main/Dashboard/Dashboard.vue"
import Decentralization from "../layout/Admin/Main/Decentralization/decentralization.vue"
import ExportExcel from "../layout/Admin/Main/Excel/ExportExcel.vue"
import TableManagement from "../layout/Admin/Main/Tablenumber/TableManagement.vue"
import AddTable from "../layout/Admin/Main/Tablenumber/AddTable.vue"
import Menu from "../layout/Admin/Main/Menu/Menu.vue"
import FeelBack from "../layout/Admin/Main/FeelBack/FeelBack.vue"
export default [
    {
        path: '/admin',
        component: Main,
        // meta: { requiresAuth: true },
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
                        component: () => import("../layout/Admin/Main/Edit/EditProduct.vue")
                    }, 
                    {
                        path: 'history',
                        component: () => import("../layout/Admin/Main/Edit/HisoricDelete.vue")
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
                component: ManagerBill
            },
            {
                path: 'createadmin',
                component: CreateAdmin
            },
            {
                path: 'decentralization',
                component: Decentralization
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