<template>
  <div v-if="!route.params.id" class="p-6 bg-gray-50 min-h-screen">
    <!-- Tiêu đề -->
    <div class="flex justify-between items-center mb-6">
      <h1 class="text-2xl font-bold text-gray-800">Danh sách tài khoản</h1>
      <input type="text" v-model="searchQuery" placeholder="Tìm kiếm tài khoản..."
        class="border border-gray-300 rounded-lg px-4 py-2 w-72 focus:ring-2 focus:ring-blue-500 focus:outline-none" />
    </div>

    <!-- Bảng -->
    <div class="overflow-x-auto bg-white rounded-xl shadow-lg">
      <table class="min-w-full rounded-xl overflow-hidden">
        <thead class="bg-gradient-to-r from-blue-600 to-blue-500 text-white">
          <tr>
            <th class="p-4 text-left font-semibold">#</th>
            <th class="p-4 text-left font-semibold">Tên đăng nhập</th>
            <th class="p-4 text-left font-semibold">Email</th>
            <th class="p-4 text-left font-semibold">Điện thoại</th>
            <th class="p-4 text-left font-semibold">Vai trò</th>
            <th class="p-4 text-center font-semibold">Hành động</th>
          </tr>
        </thead>

        <tbody>
          <tr v-for="(acc, index) in paginatedAccounts" :key="rowKey(acc, index)"
            class="transition-all duration-200 even:bg-gray-50 hover:bg-blue-50 hover:shadow-sm">
            <td class="p-4 text-gray-700 font-medium">
              {{ (currentPage - 1) * itemsPerPage + index + 1 }}
            </td>

            <!-- Ô nhập khi sửa -->
            <td class="p-4">
              <input v-if="editingKey === rowKey(acc, index)" v-model="editMap[rowKey(acc, index)].account"
                class="border rounded px-2 py-1 w-full focus:ring-2 focus:ring-blue-400" />
              <span v-else class="font-semibold text-gray-800">{{ acc.account }}</span>
            </td>

            <td class="p-4">
              <input v-if="editingKey === rowKey(acc, index)" v-model="editMap[rowKey(acc, index)].email"
                class="border rounded px-2 py-1 w-full focus:ring-2 focus:ring-blue-400" />
              <span v-else class="text-gray-600">{{ acc.email }}</span>
            </td>

            <td class="p-4 text-gray-600">{{ acc.phone || '-' }}</td>

            <td class="p-4">
              <span class="px-3 py-1 rounded-full text-sm font-semibold" :class="{
                'bg-green-100 text-green-700': isRole(acc.role, 'admin'),
                'bg-blue-100 text-blue-700': isRole(acc.role, 'user'),
                'bg-yellow-100 text-yellow-700': isRole(acc.role, 'manager')
              }">
                {{ acc.role || '—' }}
              </span>
            </td>

            <!-- Nút hành động -->
            <td class="p-4 text-center">
              <template v-if="editingKey === rowKey(acc, index)">
                <button class="bg-green-600 hover:bg-green-700 text-white px-3 py-1.5 rounded-lg mr-2 transition"
                  type="button" @click="saveEdit(rowKey(acc, index), acc)">
                  Lưu
                </button>
                <button class="bg-gray-400 hover:bg-gray-500 text-white px-3 py-1.5 rounded-lg transition" type="button"
                  @click="cancelEdit(rowKey(acc, index))">
                  Hủy
                </button>
              </template>
              <template v-else>
                <button
                  class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-1.5 rounded-lg shadow-sm transition-transform duration-200 hover:scale-105"
                  @click="startEdit(acc, index)">
                  Sửa
                </button>
              </template>
            </td>
          </tr>

          <tr v-if="paginatedAccounts.length === 0">
            <td colspan="6" class="text-center p-6 text-gray-500">
              Không tìm thấy tài khoản nào.
            </td>
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
        <button class="px-3 py-1 rounded border border-gray-300 hover:bg-gray-100 disabled:opacity-50"
          :disabled="currentPage === 1" @click="prevPage">
          Trước
        </button>
        <button class="px-3 py-1 rounded border border-gray-300 hover:bg-gray-100 disabled:opacity-50"
          :disabled="currentPage === totalPages || totalPages === 0" @click="nextPage">
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
import { message } from 'ant-design-vue'
import { useRouter, useRoute } from 'vue-router'
import { useToast } from 'vue-toastification'
const accounts = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const itemsPerPage = 4
const editingKey = ref('')
const editMap = ref({})
const toast = useToast()
const router = useRouter()
const route = useRoute()

// Helper: tạo key duy nhất cho mỗi hàng
const rowKey = (acc, index) => {
  const id = acc?.id ?? acc?._id ?? acc?.accountid
  return id ? String(id) : String((currentPage.value - 1) * itemsPerPage + index)
}

// Helper: so sánh vai trò
const isRole = (role, expect) => String(role || '').toLowerCase() === expect.toLowerCase()

// Gọi API lấy danh sách tài khoản
onMounted(async () => {
  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/admin/call`)
    accounts.value = Array.isArray(res.data) ? res.data : []
  } catch (error) {
    console.error('Lỗi khi tải danh sách tài khoản:', error)
    message.error('Không thể tải danh sách tài khoản!')
  }
})

// Lọc theo từ khóa
const filteredAccounts = computed(() => {
  const q = searchQuery.value.toLowerCase().trim()
  if (!q) return accounts.value
  return accounts.value.filter(acc =>
    String(acc.account || '').toLowerCase().includes(q) ||
    String(acc.email || '').toLowerCase().includes(q) ||
    String(acc.phone || '').toLowerCase().includes(q)
  )
})

// Phân trang
const totalPages = computed(() => Math.ceil(filteredAccounts.value.length / itemsPerPage) || 1)
const paginatedAccounts = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return filteredAccounts.value.slice(start, start + itemsPerPage)
})

const prevPage = () => { if (currentPage.value > 1) currentPage.value-- }
const nextPage = () => { if (currentPage.value < totalPages.value) currentPage.value++ }

// Khi bấm "Sửa"
const startEdit = (acc, index) => {
  const roles = sessionStorage.getItem("canAdd")
  if (roles === 'false') {
    toast.warning("Bạn không có quyền truy cập chức năng này !!!")
    return
  }
  const key = rowKey(acc, index)
  editingKey.value = key
  editMap.value = {
    ...editMap.value,
    [key]: { account: acc.account ?? '', email: acc.email ?? '' }
  }
}

// Hủy sửa
const cancelEdit = (key) => {
  const copy = { ...editMap.value }
  delete copy[key]
  editMap.value = copy
  editingKey.value = ''
  message.info('Đã hủy chỉnh sửa')
}

// Lưu sửa
const saveEdit = async (key, acc) => {
  try {
    const dataToSave = editMap.value[key]
    const payload = {
      account: dataToSave.account,
      email: dataToSave.email
    }

    const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/account/admin/update/${acc.accountid}`, payload)
    if (res.data === 'error') {
      message.warning('Email đã tồn tại!')
    } else {
      // cập nhật lại dữ liệu hiển thị
      const idx = accounts.value.findIndex(a => a.accountid === acc.accountid)
      if (idx !== -1) accounts.value[idx] = { ...accounts.value[idx], ...payload }
      message.success('Cập nhật thành công!')

    }

  } catch (err) {
    console.error(err)
    message.error('Lỗi khi cập nhật tài khoản!')
  }
}
</script>

<style scoped>
table th,
table td {
  border-bottom: 1px solid #e5e7eb;
}
</style>
