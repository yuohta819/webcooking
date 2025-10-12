<script setup lang="ts">
import { ref, onMounted, watch,nextTick  } from 'vue'
import { useRoute } from 'vue-router'
import { UserOutlined, ShoppingOutlined, EditOutlined, QuestionCircleOutlined, LaptopOutlined } from '@ant-design/icons-vue'

const selectedKeys1 = ref(['1'])
const selectedKeys2 = ref(['']) // mặc định rỗng
const openKeys = ref([''])

const route = useRoute()
const name = localStorage.getItem("account")
onMounted(() => {
  nextTick(() => {
    updateSelectedKeys(route.path)
  })
})

watch(() => route.path, (newPath) => {
  updateSelectedKeys(newPath)
})

function updateSelectedKeys(path: string) {
  if (path.includes('/admin/createadmin')) {
    selectedKeys2.value = ['5']
    openKeys.value = ['sub4']
  } else if (path.includes('/admin/create')) {
    selectedKeys2.value = ['1']
    openKeys.value = ['sub1']
  } else if (path.includes('/admin/edit')) {
    selectedKeys2.value = ['2']
    openKeys.value = ['sub2']
  } else if (path.includes('/admin/bill')) {
    selectedKeys2.value = ['3']
    openKeys.value = ['sub3']
  } else if (path.includes('/admin/manager')) {
    selectedKeys2.value = ['4']
    openKeys.value = ['sub4']
  } else {
    selectedKeys2.value = ['']
    openKeys.value = ['']
  }
}

console.log('Current path:', route.path)
console.log('Selected keys:', selectedKeys2.value)

</script>
<!-- src/layout/AdminLayout.vue -->
<template>
  <a-layout>
    <a-layout-header class="header">
      <div class="logo" />
      <a-menu v-model:selectedKeys="selectedKeys1" theme="dark" mode="horizontal" :style="{ lineHeight: '64px' }">
        <div>{{ name }}</div>
      </a-menu>
    </a-layout-header>

    <a-layout-content>
      <a-layout style="padding: 24px 0; background: #fff">
        <a-layout-sider width="200" style="background: #fff">
          <a-menu v-model:selectedKeys="selectedKeys2" v-model:openKeys="openKeys" mode="inline" style="height: 100%">
            <a-sub-menu key="sub1">
              <template #title>
                <div class="flex items-center">
                  <ShoppingOutlined />
                  <div class="ml-3">Tạo Sản Phẩm</div>
                </div>
              </template>
              <a-menu-item key="1" @click="$router.push('/admin/create')">Tạo Sản Phẩm</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub2">
              <template #title>
                <div class="flex items-center">
                  <EditOutlined />
                  <div class="ml-3">Sửa Sản Phẩm</div>
                </div>
              </template>
              <a-menu-item key="2" @click="$router.push('/admin/edit')">Sửa Sản Phẩm</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub3">
              <template #title>
                <div class="flex items-center">
                  <QuestionCircleOutlined />
                  <div class="ml-3">Đơn Hàng</div>
                </div>
              </template>
              <a-menu-item key="3" @click="$router.push('/admin/bill')">Trạng Thái Đơn Hàng</a-menu-item>
            </a-sub-menu>
            <a-sub-menu key="sub4">
              <template #title>
                <div class="flex items-center">
                  <laptop-outlined />
                  <div class="ml-4">Tài khoản</div>
                </div>
              </template>
              <a-menu-item key="4" @click="$router.push('/admin/manager')">Tài khoản khách hàng</a-menu-item>
              <a-menu-item key="5" @click="$router.push('/admin/createadmin')">Tạo Tài khoản Admin</a-menu-item>
            </a-sub-menu>
          </a-menu>
        </a-layout-sider>

        <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
          <router-view /> <!-- ⚠️ HIỂN THỊ COMPONENT CON TẠI ĐÂY -->
        </a-layout-content>
      </a-layout>
    </a-layout-content>

    <a-layout-footer style="text-align: center">
      Ant Design ©2018 Created by Ant UED
    </a-layout-footer>
  </a-layout>
</template>
