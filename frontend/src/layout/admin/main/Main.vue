
<script setup lang="ts">
import { ref, onMounted, watch, nextTick } from 'vue'
import { useRoute } from 'vue-router'
import { UserOutlined, ShoppingOutlined, EditOutlined, QuestionCircleOutlined, LaptopOutlined } from '@ant-design/icons-vue'
import HeaderAdmin from '../HeaderAdmin.vue'

const selectedKeys1 = ref(['1'])
const selectedKeys2 = ref([''])
const openKeys = ref([''])
const route = useRoute()
const name = localStorage.getItem("account")
const appName = import.meta.env.VITE_APP_NAME

onMounted(() => {
  nextTick(() => updateSelectedKeys(route.path))
})

watch(() => route.path, (newPath) => updateSelectedKeys(newPath))

function updateSelectedKeys(path: string) {
  if (path.includes(`/${import.meta.env.VITE_APP_NAME}/createadmin`)) {
    selectedKeys2.value = ['5']
    openKeys.value = ['sub4']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/create`)) {
    selectedKeys2.value = ['1']
    openKeys.value = ['sub1']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/edit`)) {
    selectedKeys2.value = ['2']
    openKeys.value = ['sub2']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/bill`)) {
    selectedKeys2.value = ['3']
    openKeys.value = ['sub3']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/manager`)) {
    selectedKeys2.value = ['4']
    openKeys.value = ['sub4']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/dashboard`)) {
    selectedKeys2.value = ['6']
    openKeys.value = ['sub6']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/decentralization`)) {
    selectedKeys2.value = ['7']
    openKeys.value = ['sub7']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/tablenumber`)) {
    selectedKeys2.value = ['9']
    openKeys.value = ['sub9']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/menu`)) {
    selectedKeys2.value = ['10']
    openKeys.value = ['sub10']
  } else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/feedback`)) {
    selectedKeys2.value = ['11']
    openKeys.value = ['sub11']
  }
   else if (path.includes(`/${import.meta.env.VITE_APP_NAME}/listadmin`)) {
    selectedKeys2.value = ['12']
    openKeys.value = ['sub4']
  }
  else {
    selectedKeys2.value = ['']
    openKeys.value = ['']
  }
}
</script>

<template>
  <a-layout>
    <a-layout-content class="overflow-y-auto">
      <a-layout style="background: #fff;">
        <!-- Sidebar -->
        <a-layout-sider width="260"
          style="position: fixed; top: 0; left: 0; height: 100vh; background: #272B35; overflow-y: auto; z-index: 10;">
          <div class="py-5 text-3xl text-center text-white bg-[#272B35]">
            ADMIN
          </div>
          <a-menu v-model:selectedKeys="selectedKeys2" v-model:openKeys="openKeys" mode="inline" theme="dark"
            style="background: #272B35; color: #929aac;">
            <a-sub-menu key="sub6">
              <template #title>
                <div class="flex items-center">
                  <EditOutlined /><span class="ml-3">Tổng Quan</span>
                </div>
              </template>
              <a-menu-item key="6" @click="$router.push(`/${appName}/dashboard`)">Tổng Quan</a-menu-item>
            </a-sub-menu>

            <a-sub-menu key="sub1">
              <template #title>
                <div class="flex items-center">
                  <ShoppingOutlined /><span class="ml-3">Tạo Sản Phẩm</span>
                </div>
              </template>
              <a-menu-item key="1" @click="$router.push(`/${appName}/create`)">Tạo Sản Phẩm</a-menu-item>
            </a-sub-menu>

            <a-sub-menu key="sub2">
              <template #title>
                <div class="flex items-center">
                  <EditOutlined /><span class="ml-3">Sửa Sản Phẩm</span>
                </div>
              </template>
              <a-menu-item key="2" @click="$router.push(`/${appName}/edit`)">Sửa Sản Phẩm</a-menu-item>
            </a-sub-menu>

            <a-sub-menu key="sub3">
              <template #title>
                <div class="flex items-center">
                  <QuestionCircleOutlined /><span class="ml-3">Đơn Hàng</span>
                </div>
              </template>
              <a-menu-item key="3" @click="$router.push(`/${appName}/bill`)">Trạng Thái Đơn Hàng</a-menu-item>
            </a-sub-menu>

            <a-sub-menu key="sub9">
              <template #title>
                <div class="flex items-center">
                  <QuestionCircleOutlined /><span class="ml-3">Số Bàn</span>
                </div>
              </template>
              <a-menu-item key="9" @click="$router.push(`/${appName}/tablenumber`)">Số Bàn</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub10">
              <template #title>
                <div class="flex items-center">
                  <QuestionCircleOutlined /><span class="ml-3">Thực Đơn</span>
                </div>
              </template>
              <a-menu-item key="10" @click="$router.push(`/${appName}/menu`)">Thực Đơn</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub4">
              <template #title>
                <div class="flex items-center">
                  <LaptopOutlined /><span class="ml-3">Tài khoản</span>
                </div>
              </template>
              <a-menu-item key="4" @click="$router.push(`/${appName}/manager`)">Tài khoản khách hàng</a-menu-item>
              <a-menu-item key="12" @click="$router.push(`/${appName}/listadmin`)">Tài khoản Admin</a-menu-item>
              <a-menu-item key="5" @click="$router.push(`/${appName}/createadmin`)">Tạo Tài khoản Admin</a-menu-item>
            </a-sub-menu>

            <a-sub-menu key="sub7">
              <template #title>
                <div class="flex items-center">
                  <QuestionCircleOutlined /><span class="ml-3">Phân Quyền</span>
                </div>
              </template>
              <a-menu-item key="7" @click="$router.push(`/${appName}/decentralization`)">Phân Quyền</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub11">
              <template #title>
                <div class="flex items-center">
                  <QuestionCircleOutlined /><span class="ml-3">Bình Luận</span>
                </div>
              </template>
              <a-menu-item key="11" @click="$router.push(`/${appName}/feedback`)">Bình Luận</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub8">
              <template #title>
                <div class="flex items-center">
                  <QuestionCircleOutlined /><span class="ml-3">Xuất File Excel</span>
                </div>
              </template>
              <a-menu-item key="8" @click="$router.push(`/${appName}/excel/export`)">Xuất File Excel</a-menu-item>
            </a-sub-menu>
          </a-menu>
        </a-layout-sider>

        <!-- Content -->
        <a-layout style="margin-left: 260px;">
          <a-layout-content class="overflow-y-auto"
            :style="{ minHeight: '100vh', background: '#F4F1EA', position: 'relative' }">
            <div style="position: sticky; top: 0; left: 0; width: 100%; z-index: 9999;">
              <HeaderAdmin />
            </div>
            <div class="pt-20 pb-30 px-6">
              <router-view />
            </div>
          </a-layout-content>

          <a-layout-footer class="pt-10 text-center">
            Ant Design ©2018 Created by Ant UED
          </a-layout-footer>
        </a-layout>
      </a-layout>
    </a-layout-content>
  </a-layout>
</template>

<style scoped>
/* Thanh cuộn cho sidebar */
.ant-layout-sider::-webkit-scrollbar {
  width: 6px;
}

.ant-layout-sider::-webkit-scrollbar-thumb {
  background-color: #666;
  border-radius: 4px;
}

.ant-layout-sider::-webkit-scrollbar-thumb:hover {
  background-color: #aaa;
}

/* Thanh cuộn cho content */
.ant-layout-content::-webkit-scrollbar {
  width: 8px;
}

.ant-layout-content::-webkit-scrollbar-thumb {
  background: #b3b3b3;
  border-radius: 8px;
}

.ant-layout-content::-webkit-scrollbar-thumb:hover {
  background: #888;
}
</style>