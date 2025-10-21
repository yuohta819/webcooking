<template>
  <div v-if="!route.params.id" class="p-6 bg-gray-50 min-h-screen">
    <!-- Tiêu đề -->
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-bold text-gray-800">Danh sách tài khoản</h1>
      <input
        type="text"
        v-model="searchQuery"
        placeholder="Tìm kiếm tài khoản..."
        class="border border-gray-300 rounded-lg px-4 py-2 w-72 focus:ring-2 focus:ring-blue-500 focus:outline-none"
      />
    </div>

    <!-- Bảng -->
    <div class="overflow-x-auto bg-white rounded-xl shadow-md">
      <table class="min-w-full border border-gray-200 rounded-lg">
        <thead class="bg-blue-600 text-white">
          <tr>
            <th class="p-3 text-left">ID</th>
            <th class="p-3 text-left">Tên đăng nhập</th>
            <th class="p-3 text-left">Email</th>
            <th class="p-3 text-left">Điện thoại</th>
            <th class="p-3 text-left">Vai trò</th>
            <th class="p-3 text-center">Hành động</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(acc,index) in paginatedAccounts"
            :key="acc.id"
            class="border-b hover:bg-blue-50 transition-colors"
          >
            <td class="p-3">{{ index + 1 }}</td>
            <td class="p-3 font-medium text-gray-700">{{ acc.account }}</td>
            <td class="p-3 text-gray-600">{{ acc.email }}</td>
            <td class="p-3 text-gray-600">{{ acc.phone }}</td>
            <td>
              <span
                class="px-2 py-1 rounded-full text-sm font-semibold"
                :class="{
                  'bg-green-100 text-green-700': acc.role === 'Admin',
                  'bg-blue-100 text-blue-700': acc.role === 'User',
                  'bg-yellow-100 text-yellow-700': acc.role === 'Manager'
                }"
              >
                {{ acc.role }}
              </span>
            </td>
            <td class="p-3 text-center">
              <button
                class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1 rounded-lg shadow-sm transition-transform hover:scale-105"
                @click="goEdit(acc.id)"
              >
                Sửa
              </button>
            </td>
          </tr>
          <tr v-if="paginatedAccounts.length === 0">
            <td colspan="6" class="text-center p-4 text-gray-500">Không tìm thấy tài khoản nào.</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Phân trang -->
    <div class="flex justify-between items-center mt-6">
      <p class="text-gray-600 text-sm">
        Trang {{ currentPage }} / {{ totalPages }} (Tổng: {{ filteredAccounts.length }})
      </p>
      <div class="flex gap-2">
        <button
          class="px-3 py-1 rounded border border-gray-300 hover:bg-gray-100 disabled:opacity-50"
          :disabled="currentPage === 1"
          @click="currentPage--"
        >
          Trước
        </button>
        <button
          class="px-3 py-1 rounded border border-gray-300 hover:bg-gray-100 disabled:opacity-50"
          :disabled="currentPage === totalPages"
          @click="currentPage++"
        >
          Sau
        </button>
      </div>
    </div>
  </div>
  <router-view />
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { useRoute } from 'vue-router'
const accounts = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const itemsPerPage = 4
const router = useRouter()
const route = useRoute()
onMounted(async () => {
  const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/admin/call`)
  accounts.value = res.data
})

// Lọc theo từ khóa
const filteredAccounts = computed(() => {
  const query = searchQuery.value.toLowerCase()
  return accounts.value.filter(acc =>
    acc.account.toLowerCase().includes(query) ||
    acc.email.toLowerCase().includes(query) ||
    acc.phone?.toLowerCase().includes(query)
  )
})

// Phân trang
const totalPages = computed(() => Math.ceil(filteredAccounts.value.length / itemsPerPage))
const paginatedAccounts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return filteredAccounts.value.slice(start, start + itemsPerPage)
})

const goEdit = (id) => {
  router.push(`/admin/decentralization/${id}`)
}
</script>
