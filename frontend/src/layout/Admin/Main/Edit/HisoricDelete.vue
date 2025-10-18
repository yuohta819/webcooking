<template>
  <div class="min-h-screen bg-[#F4F1EA] py-10 px-6" v-if="route.path.endsWith('/edit/history')">
    <div class="max-w-6xl mx-auto bg-white shadow-xl rounded-2xl p-8 border border-gray-100">
      <!-- 🏷️ Tiêu đề -->
      <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold text-gray-700 flex items-center gap-3">
          <i class="fa-solid fa-clock-rotate-left text-amber-500"></i>
          Lịch sử xóa hàng
        </h1>

        <!-- 🔍 Thanh tìm kiếm -->
        <div class="relative w-72">
          <input
            type="text"
            v-model="searchTerm"
            placeholder="Tìm kiếm sản phẩm..."
            class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-amber-400 focus:outline-none"
          />
          <i class="fa-solid fa-magnifying-glass absolute right-3 top-2.5 text-gray-400"></i>
        </div>
      </div>

      <!-- 🧾 Bảng dữ liệu -->
      <div class="overflow-x-auto rounded-xl border border-gray-200">
        <table class="min-w-full text-sm text-left text-gray-700">
          <thead class="bg-amber-400 text-white">
            <tr>
              <th class="px-6 py-3">Ảnh</th>
              <th class="px-6 py-3">Tên sản phẩm</th>
              <th class="px-6 py-3">Mô tả</th>
              <th class="px-6 py-3">Giá</th>
              <th class="px-6 py-3">Ngày xóa</th>
              <th class="px-6 py-3 text-center">Hành động</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-gray-200 bg-white">
            <tr
              v-for="item in filteredData"
              :key="item.id"
              class="hover:bg-gray-50 transition"
            >
              <td class="px-6 py-4">
                <img
                  :src="item.img"
                  alt=""
                  class="w-16 h-16 object-cover rounded-lg border border-gray-200"
                />
              </td>
              <td class="px-6 py-4 font-semibold text-gray-800">{{ item.name }}</td>
              <td class="px-6 py-4 text-gray-600">{{ item.describe }}</td>
              <td class="px-6 py-4 text-amber-600 font-semibold">${{ item.price }}</td>
              <td class="px-6 py-4 text-gray-500">
                {{ formatDate(item.isTime) }}
              </td>
              <td class="px-6 py-4 text-center">
                <button
                  @click="restoreItem(item.id)"
                  class="px-4 py-2 bg-gradient-to-r from-green-400 to-emerald-500 
                         text-white rounded-lg shadow-md hover:shadow-lg 
                         hover:from-green-500 hover:to-emerald-600 transition-all duration-200 active:scale-95">
                  <i class="fa-solid fa-rotate-left mr-2"></i>
                  Khôi phục
                </button>
              </td>
            </tr>
            <tr v-if="filteredData.length === 0">
              <td colspan="6" class="text-center py-8 text-gray-500 italic">
                Không có sản phẩm nào trong lịch sử xóa.
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
  <ToastService ref="toast" />
</template>

<script setup>
import { ref, onMounted, computed } from "vue"
import axios from "axios"
import { message } from "ant-design-vue"
import { useRoute } from "vue-router"
import ToastService from "../../../../components/ToastService.vue"
const data = ref([])
const searchTerm = ref("")
const route = useRoute()
const toast = ref(null)
// 🟡 Gọi API lấy danh sách sản phẩm đã xóa
onMounted(async () => {
  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/deleted`) // ✅ API lấy hàng isDeleted = true
    data.value = res.data
  } catch (err) {
    console.error(err)
  }
})

// 🟢 Lọc theo tên
const filteredData = computed(() => {
  return data.value.filter(item =>
    item.name.toLowerCase().includes(searchTerm.value.toLowerCase())
  )
})

// 🔁 Khôi phục sản phẩm
async function restoreItem(id) {
  try {
    await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/api/restore/${id}`)
    message.success("Khôi phục sản phẩm thành công 🎉")
    toast.value.success(`Khôi phục sản phẩm thành công 🎉`) // ✅
    data.value = data.value.filter(item => item.id !== id)
  } catch (err) {
    console.error(err)
    toast.value.warning("Lỗi khi khôi phục sản phẩm ")
    message.error("Lỗi khi khôi phục sản phẩm ❌")
  }
}

// 📅 Format ngày
function formatDate(dateStr) {
  if (!dateStr) return "—"
  const date = new Date(dateStr)
  return date.toLocaleString("vi-VN", { dateStyle: "short", timeStyle: "short" })
}
</script>
