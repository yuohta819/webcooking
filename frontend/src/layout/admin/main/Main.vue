<script setup lang="ts">
import { ref, onMounted, watch, nextTick } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import axios from 'axios'

// 🧭 import icon từ lucide-vue-next
import {
  LayoutDashboard,
  PlusCircle,
  Edit3,
  ShoppingCart,
  Table2,
  BookOpen,
  Users,
  ShieldCheck,
  MessageSquare,
  FileSpreadsheet
} from 'lucide-vue-next'

import HeaderAdmin from '../HeaderAdmin.vue'

const selectedKeys1 = ref(['1'])
const selectedKeys2 = ref([''])
const openKeys = ref([''])
const route = useRoute()
const router = useRouter()
const appName = import.meta.env.VITE_APP_NAME

// ✅ Hàm kiểm tra quyền trước khi chuyển trang
async function checkPermissionAndNavigate(targetPath) {
  const token = localStorage.getItem('adminToken') || sessionStorage.getItem('adminToken');
  const account = localStorage.getItem('admin') || sessionStorage.getItem('admin');

  if (!token) {
    alert('Bạn chưa đăng nhập!');
    updateSelectedKeys(route.path);
    return;
  }

  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/admin/auth/check-permission`, {
      params: { account },
      headers: { Authorization: `Bearer ${token}` }
    });

    const { can_add, can_edit, can_delete } = res.data;
    if (!res.data) {
      alert('Không lấy được thông tin quyền!');
      updateSelectedKeys(route.path);
      return;
    }

    switch (targetPath) {
      // Dashboard — luôn được phép
      case `/${appName}/dashboard`:
        return router.push(`/${appName}/dashboard`);

      // Create
      case `/${appName}/create`:
        if (!can_add) {
          alert('⚠️ Bạn không có quyền thêm sản phẩm.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/create`);

      // Edit
      case `/${appName}/edit`:
        if (!can_edit) {
          alert('⚠️ Bạn không có quyền sửa sản phẩm.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/edit`);

      // Bill
      case `/${appName}/bill`:
        if (!can_edit) {
          alert('⚠️ Bạn không có quyền xem hóa đơn.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/bill`);

      // Manager
      case `/${appName}/manager`:
        if (!can_add) {
          alert('⚠️ Bạn không có quyền truy cập quản lý.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/manager`);

      // List Admin
      case `/${appName}/listadmin`:
        if (!(can_add && can_edit)) {
          alert('⚠️ Bạn không có quyền xem danh sách admin.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/listadmin`);

      // Decentralization
      case `/${appName}/decentralization`:
        if (!(can_add && can_delete && can_edit)) {
          alert('⚠️ Bạn không có quyền phân quyền người dùng.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/decentralization`);

      // Table number
      case `/${appName}/tablenumber`:
        if (!can_add) {
          alert('⚠️ Bạn không có quyền truy cập khu vực này.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/tablenumber`);

      // Menu
      case `/${appName}/menu`:
        if (!can_add) {
          alert('⚠️ Bạn không có quyền truy cập menu.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/menu`);

      // Feedback
      case `/${appName}/feedback`:
        if (!can_add) {
          alert('⚠️ Bạn không có quyền xem phản hồi.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/feedback`);

      // Create Admin
      case `/${appName}/createadmin`:
        if (!(can_add && can_delete && can_edit)) {
          alert('⚠️ Bạn không có quyền tạo tài khoản admin.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/createadmin`);

      // Export Excel
      case `/${appName}/excel/export`:
        if (!(can_add && can_delete && can_edit)) {
          alert('⚠️ Bạn không có quyền xuất dữ liệu.');
          return updateSelectedKeys(route.path);
        }
        return router.push(`/${appName}/excel/export`);

      // Default
      default:
        alert('⚠️ Đường dẫn không được phép truy cập hoặc chưa cấu hình.');
        return updateSelectedKeys(route.path);
    }

  } catch (error) {
    console.error('Lỗi kiểm tra quyền:', error);
    alert('Không thể xác thực quyền truy cập.');
    updateSelectedKeys(route.path);
  }
}

// ✅ Giữ highlight khi load lại hoặc đổi route
onMounted(() => {
  nextTick(() => updateSelectedKeys(route.path));
});

watch(() => route.path, (newPath) => updateSelectedKeys(newPath));

// ✅ Cập nhật trạng thái selected menu
function updateSelectedKeys(path) {
  if (path.includes(`/${appName}/createadmin`)) {
    selectedKeys2.value = ['13']
    openKeys.value = ['sub13']
  } else if (path.includes(`/${appName}/create`)) {
    selectedKeys2.value = ['1']
    openKeys.value = ['sub1']
  } else if (path.includes(`/${appName}/edit`)) {
    selectedKeys2.value = ['2']
    openKeys.value = ['sub2']
  } else if (path.includes(`/${appName}/bill`)) {
    selectedKeys2.value = ['3']
    openKeys.value = ['sub3']
  } else if (path.includes(`/${appName}/manager`)) {
    selectedKeys2.value = ['4']
    openKeys.value = ['sub4']
  } else if (path.includes(`/${appName}/dashboard`)) {
    selectedKeys2.value = ['6']
    openKeys.value = ['sub6']
  } else if (path.includes(`/${appName}/decentralization`)) {
    selectedKeys2.value = ['7']
    openKeys.value = ['sub7']
  } else if (path.includes(`/${appName}/tablenumber`)) {
    selectedKeys2.value = ['9']
    openKeys.value = ['sub9']
  } else if (path.includes(`/${appName}/menu`)) {
    selectedKeys2.value = ['10']
    openKeys.value = ['sub10']
  } else if (path.includes(`/${appName}/feedback`)) {
    selectedKeys2.value = ['11']
    openKeys.value = ['sub11']
  } else if (path.includes(`/${appName}/listadmin`)) {
    selectedKeys2.value = ['12']
    openKeys.value = ['sub12']
  } else {
    selectedKeys2.value = []
    openKeys.value = []
  }
}
</script>

<template>
  <a-layout>
    <a-layout-sider
      width="260"
      style="position: fixed; top: 0; left: 0; height: 100vh; background: #272B35; overflow-y: auto;"
    >
      <div class="py-5 text-3xl text-center text-white bg-[#272B35]">ADMIN</div>

      <a-menu v-model:selectedKeys="selectedKeys2" v-model:openKeys="openKeys" mode="inline" theme="dark" style="background: #272B35; color: #929aac;">

        <!-- 📊 Tổng Quan -->
        <a-sub-menu key="sub6">
          <template #title>
            <div class="flex items-center"><LayoutDashboard size="18" class="mr-3" />Tổng Quan</div>
          </template>
          <a-menu-item key="6" @click="checkPermissionAndNavigate(`/${appName}/dashboard`)">Tổng Quan</a-menu-item>
        </a-sub-menu>

        <!-- 🛍️ Tạo Sản Phẩm -->
        <a-sub-menu key="sub1">
          <template #title>
            <div class="flex items-center"><PlusCircle size="18" class="mr-3" />Tạo Sản Phẩm</div>
          </template>
          <a-menu-item key="1" @click="checkPermissionAndNavigate(`/${appName}/create`)">Tạo Sản Phẩm</a-menu-item>
        </a-sub-menu>

        <!-- ✏️ Sửa Sản Phẩm -->
        <a-sub-menu key="sub2">
          <template #title>
            <div class="flex items-center"><Edit3 size="18" class="mr-3" />Sửa Sản Phẩm</div>
          </template>
          <a-menu-item key="2" @click="checkPermissionAndNavigate(`/${appName}/edit`)">Sửa Sản Phẩm</a-menu-item>
        </a-sub-menu>

        <!-- 📦 Đơn Hàng -->
        <a-sub-menu key="sub3">
          <template #title>
            <div class="flex items-center"><ShoppingCart size="18" class="mr-3" />Đơn Hàng</div>
          </template>
          <a-menu-item key="3" @click="checkPermissionAndNavigate(`/${appName}/bill`)">Trạng Thái Đơn Hàng</a-menu-item>
        </a-sub-menu>

        <!-- 🪑 Số Bàn -->
        <a-sub-menu key="sub9">
          <template #title>
            <div class="flex items-center"><Table2 size="18" class="mr-3" />Số Bàn</div>
          </template>
          <a-menu-item key="9" @click="checkPermissionAndNavigate(`/${appName}/tablenumber`)">Số Bàn</a-menu-item>
        </a-sub-menu>

        <!-- 🍽️ Thực Đơn -->
        <a-sub-menu key="sub10">
          <template #title>
            <div class="flex items-center"><BookOpen size="18" class="mr-3" />Thực Đơn</div>
          </template>
          <a-menu-item key="10" @click="checkPermissionAndNavigate(`/${appName}/menu`)">Thực Đơn</a-menu-item>
        </a-sub-menu>

        <!-- 👤 Tài Khoản -->
        <a-sub-menu key="sub4">
          <template #title>
            <div class="flex items-center"><Users size="18" class="mr-3" />Tài Khoản</div>
          </template>
          <a-menu-item key="4" @click="checkPermissionAndNavigate(`/${appName}/manager`)">Tài Khoản Khách Hàng</a-menu-item>
          <a-menu-item key="12" @click="checkPermissionAndNavigate(`/${appName}/listadmin`)">Tài Khoản Admin</a-menu-item>
          <a-menu-item key="5" @click="checkPermissionAndNavigate(`/${appName}/createadmin`)">Tạo Tài Khoản Admin</a-menu-item>
        </a-sub-menu>

        <!-- ⚙️ Phân Quyền -->
        <a-sub-menu key="sub7">
          <template #title>
            <div class="flex items-center"><ShieldCheck size="18" class="mr-3" />Phân Quyền</div>
          </template>
          <a-menu-item key="7" @click="checkPermissionAndNavigate(`/${appName}/decentralization`)">Phân Quyền</a-menu-item>
        </a-sub-menu>

        <!-- 💬 Bình Luận -->
        <a-sub-menu key="sub11">
          <template #title>
            <div class="flex items-center"><MessageSquare size="18" class="mr-3" />Bình Luận</div>
          </template>
          <a-menu-item key="11" @click="checkPermissionAndNavigate(`/${appName}/feedback`)">Bình Luận</a-menu-item>
        </a-sub-menu>

        <!-- 📤 Xuất File -->
        <a-sub-menu key="sub8">
          <template #title>
            <div class="flex items-center"><FileSpreadsheet size="18" class="mr-3" />Xuất File Excel</div>
          </template>
          <a-menu-item key="8" @click="checkPermissionAndNavigate(`/${appName}/excel/export`)">Xuất File Excel</a-menu-item>
        </a-sub-menu>
      </a-menu>
    </a-layout-sider>

    <!-- Content -->
    <a-layout style="margin-left: 260px;">
      <a-layout-content class="overflow-y-auto" :style="{ minHeight: '100vh', background: '#F4F1EA', position: 'relative' }">
        <div style="position: sticky; top: 0; left: 0; width: 100%; z-index: 9999;">
          <HeaderAdmin />
        </div>
        <div class="pt-20 pb-30 px-6">
          <router-view />
        </div>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>
