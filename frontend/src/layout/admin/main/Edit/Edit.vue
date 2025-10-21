<template>
  <div>
    <div v-if="!route.params.id && !route.path.endsWith('/edit/history')">
      <!-- Bộ lọc -->
      <div
        class="flex flex-wrap justify-between items-center px-10 py-6 bg-white shadow-sm rounded-xl border border-gray-100">
        <!-- Bên trái: Thông tin -->
        <div class="text-gray-600 text-[16px] font-medium mb-3 lg:mb-0">
          Showing <span class="font-semibold text-gray-800">1 - 12</span> of
          <span class="font-semibold text-gray-800">30</span> Results
        </div>

        <!-- Bên phải: Bộ lọc và nút -->
        <div class="flex flex-wrap items-center gap-4">
          <!-- 🔸 Nút Lịch sử xóa hàng -->
          <a :href="`/${admin}/edit/history`">
            <button class="flex items-center gap-2 px-5 py-2.5 bg-gradient-to-r from-amber-400 to-yellow-500 
             text-white font-semibold rounded-xl shadow-md hover:shadow-lg 
             hover:from-amber-500 hover:to-yellow-600 active:scale-95 
             transition-all duration-200 ease-in-out">
              <i class="fa-solid fa-clock-rotate-left text-white text-lg"></i>
              <span>Lịch sử xóa hàng</span>
            </button>
          </a>

          <!-- 🔹 Dropdown sắp xếp -->
          <div class="relative">
            <select class="px-5 py-2.5 appearance-none bg-white text-[#615D5A] border border-gray-300 rounded-lg 
               cursor-pointer focus:outline-none focus:ring-2 focus:ring-amber-400 transition-all duration-200"
              @change="handleChange($event.target.value)">
              <option value="Default Sorting" selected>Default Sorting</option>
              <option value="high">Sort by price: low to high</option>
              <option value="low">Sort by price: high to low</option>
            </select>
            <i class="fa-solid fa-chevron-down absolute right-3 top-3.5 text-gray-400 pointer-events-none"></i>
          </div>

          <!-- 🔸 Biểu tượng hiển thị -->
          <div class="flex items-center gap-3 text-gray-500 text-lg">
            <i class="fa-solid fa-grip hover:text-amber-500 transition"></i>
            <i class="fa-solid fa-list hover:text-amber-500 transition"></i>
          </div>
        </div>
      </div>


      <!-- Danh sách sản phẩm -->
      <div class="bg-[#F4F1EA] flex pb-10">
        <div class="flex flex-wrap gap-11 justify-around">
          <div v-for="item in data1" :key="item.id"
            class="w-60 mt-30 rounded-[15px] bg-white shadow hover:shadow-lg transition-shadow duration-300">
            <!-- Ảnh sản phẩm -->
            <div class="place-items-center place-self-center" style="margin-top: -100px;">
              <div class="w-40 h-40 img rounded-[50%] p-2 left-8"></div>
              <img :src="item.img" alt="" width="160" class="p-2 -mt-40 rounded-full border border-gray-200" />
            </div>

            <!-- Nội dung -->
            <div class="text-center px-5 py-5">
              <div class="font-bold text-[21px]">{{ item.name }}</div>
              <div class="text-[18px] py-2 text-[#7D8490]">{{ item.describe }}</div>
              <div class="font-bold text-[20px] text-[#F43C22]">${{ item.price }}</div>

              <!-- 🟢 Hai nút Sửa & Xóa -->
              <div class="flex justify-center gap-3 mt-4">
                <!-- 🟦 Nút Sửa -->
                <a :href="`/${admin}/edit/${item.id}`">
                  <button
                    class="cursor-pointer flex items-center gap-2 px-4 py-2 bg-blue-100 hover:bg-blue-200 text-blue-700 font-medium rounded-lg shadow-sm transition duration-200">
                    <i class="fa-solid fa-pen-to-square text-blue-600"></i>
                    <span>Sửa</span>
                  </button>
                </a>

                <!-- 🟥 Nút Xóa -->
                <button @click="deleteItem(item)"
                  class="flex cursor-pointer items-center gap-2 px-4 py-2 bg-red-100 hover:bg-red-200 text-red-700 font-medium rounded-lg shadow-sm transition duration-200">
                  <i class="fa-solid fa-trash-can text-red-600"></i>
                  <span>Xóa</span>
                </button>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <ConfirmModal ref="confirmModal" />
    <router-view />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'
import { message } from 'ant-design-vue'
import ConfirmModal from '../../../../components/ConfirmModal.vue'
import { useToast } from 'vue-toastification'

const data = ref([])
const data1 = ref([])
const route = useRoute()
const confirmModal = ref(null)
const admin = import.meta.env.VITE_APP_NAME
const toast = useToast()
// ✅ Hàm tải danh sách
async function loadData() {
  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/menu`)
    data.value = res.data.slice(0, 12)
    data1.value = [...data.value]
  } catch (err) {
    console.error(err)
  }
}

onMounted(loadData)

// ✅ Sắp xếp
function handleChange(type) {
  if (type === 'high') {
    data1.value = [...data.value].sort((a, b) => a.price - b.price)
  } else if (type === 'low') {
    data1.value = [...data.value].sort((a, b) => b.price - a.price)
  } else {
    data1.value = [...data.value]
  }
}

// ✅ Xóa sản phẩm + cập nhật UI
async function deleteItem(item) {
  const roles3 = sessionStorage.getItem("canDelete")
  if (roles3 === 'false') {
    toast.warning("Bạn không có quyền truy cập chức năng này !!!")
    route.push(`/${import.meta.env.VITE_APP_NAME}/edit`)
  }
  const confirmed = await confirmModal.value.open({
    title: 'Xóa sản phẩm',
    message: `Bạn có chắc chắn muốn xóa "${item.name}"?`,
    detail: 'Hành động này không thể hoàn tác.',
    confirmed: true
  })

  if (confirmed) {
    try {
      await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/delete/${item.id}`)

      // 🟢 Xóa ngay trong danh sách mà không reload
      data.value = data.value.filter(p => p.id !== item.id)
      data1.value = data1.value.filter(p => p.id !== item.id)

      // ✅ Hiện thông báo
      message.success(`Đã xóa sản phẩm "${item.name}"`)
    } catch (err) {
      console.error(err)
      message.error('Xóa thất bại, vui lòng thử lại!')
    }
  }
}
</script>