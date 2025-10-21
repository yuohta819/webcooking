
<template>
  <div class="p-6 bg-gray-50 min-h-screen" style="background-color: #F4F1EA;">
    <div class="max-w-5xl mx-auto bg-white shadow rounded-lg p-4">
      <div class="flex items-center justify-between mb-4">
        <h2 class="text-lg font-semibold">Thống kê tài khoản</h2>
        <div class="flex items-center gap-2">
          <button @click="exportCurrentPageToExcel"
            class="px-4 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-md shadow-sm transition">
            Xuất Excel (trang hiện tại)
          </button>
          <select v-model.number="rowsPerPage" class="border rounded-md px-2 py-1">
            <option :value="5">5</option>
            <option :value="10">10</option>
            <option :value="20">20</option>
          </select>
        </div>
      </div>

      <table class="min-w-full table-auto divide-y divide-gray-200">
        <thead class="bg-gray-50">
          <tr>
            <th class="px-4 py-2 text-left">ID</th>
            <th class="px-4 py-2 text-left">Tài Khoản</th>
            <th class="px-4 py-2 text-left">Username</th>
            <th class="px-4 py-2  text-center">Tổng số đơn</th>
            <th class="px-4 py-2 text-right">Tổng tiền</th>
            <th class="px-4 py-2 text-left">Ngày tạo</th>
          </tr>
        </thead>
        <tbody class="divide-y divide-gray-100">
          <tr v-for="(item, index) in visibleRows" :key="item.id" class="hover:bg-gray-50">
            <td class="px-4 py-2">{{ index + 1 }}</td>
            <td class="px-4 py-2">{{ item.accountUsername }}</td>
            <td class="px-4 py-2">{{ item.username }}</td>
            <td class="px-4 py-2  text-center">{{ item.tongSoDon }}</td>
            <td class="px-4 py-2 text-right">{{ formatCurrency(item.tongTien) }}</td>
            <td class="px-4 py-2">{{ formatDate(item.thoiGianTao) }}</td>
          </tr>
        </tbody>
      </table>

      <div class="flex items-center justify-between mt-4">
        <div class="text-sm text-gray-600">Hiển thị trang {{ currentPage }} / {{ totalPages }}</div>
        <div class="flex items-center gap-2">
          <button @click="prevPage" :disabled="currentPage === 1"
            class="px-3 py-1 border rounded disabled:opacity-50">Prev</button>
          <button @click="nextPage" :disabled="currentPage === totalPages"
            class="px-3 py-1 border rounded disabled:opacity-50">Next</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import * as XLSX from 'xlsx'
import { useRouter } from 'vue-router'
import { useToast } from 'vue-toastification'
const data = ref([])
const currentPage = ref(1)
const rowsPerPage = ref(10)
const toast = useToast()
const totalPages = computed(() => Math.max(1, Math.ceil(data.value.length / rowsPerPage.value)))

const visibleRows = computed(() => {
  const start = (currentPage.value - 1) * rowsPerPage.value
  return data.value.slice(start, start + rowsPerPage.value)
})
const roles = sessionStorage.getItem("canAdd")
const router = useRouter()
if (roles === 'false') {
  toast.warning("Bạn không có quyền truy cập chức năng này !!!")
  router.push(`/${import.meta.env.VITE_APP_NAME}`)
}
onMounted(async () => {
  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/statistic/call`)
    data.value = res.data
  } catch (err) {
    console.error('Lỗi tải dữ liệu:', err)
  }
})

function prevPage() {
  if (currentPage.value > 1) currentPage.value--
}
function nextPage() {
  if (currentPage.value < totalPages.value) currentPage.value++
}

function formatCurrency(v) {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(v || 0)
}
function formatDate(v) {
  return v ? new Date(v).toLocaleString('vi-VN') : ''
}

function exportCurrentPageToExcel() {
  const rows = visibleRows.value.map((r, index) => ({
    ID: index + 1,
    'Tài Khoản': r.accountUsername,
    Username: r.username,
    'Tổng số đơn': r.tongSoDon,
    'Tổng tiền': r.tongTien,
    'Thời gian tạo': formatDate(r.thoiGianTao)
  }))
  const ws = XLSX.utils.json_to_sheet(rows)
  const wb = XLSX.utils.book_new()
  XLSX.utils.book_append_sheet(wb, ws, 'Trang' + currentPage.value)
  XLSX.writeFile(wb, `Thong_ke_trang_${currentPage.value}.xlsx`)
}
</script>