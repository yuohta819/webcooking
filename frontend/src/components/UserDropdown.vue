<template>
  <div class="relative inline-block text-left">
    <!-- Nút Avatar + Tên -->
    <button 
      @click="toggleDropdown"
      class="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100 transition"
    >
      <img
        :src="user?.avatar || 'https://cdn-icons-png.flaticon.com/512/3135/3135715.png'"
        alt="avatar"
        class="w-10 h-10 rounded-full"
      />
      <div class="text-left">
        <div class="text-sm font-semibold text-gray-800">{{ user?.account || 'Tài khoản' }}</div>
        <div class="text-xs text-gray-500">{{ user?.role === 'admin' ? 'Quản trị viên' : 'Người dùng' }}</div>
      </div>
    </button>

    <!-- Dropdown -->
    <transition name="fade">
      <div
        v-if="isOpen"
        class="absolute right-0 mt-2 w-56 bg-white rounded-xl shadow-lg border border-gray-100 z-50"
      >
        <div class="px-4 py-3 border-b border-gray-100">
          <p class="text-sm text-gray-600">Xin chào, <b>{{ user?.account }}</b>!</p>
          <p class="text-xs text-gray-400">{{ user?.email }}</p>
        </div>

        <ul class="py-2 text-sm text-gray-700">
          <li>
            <button
              @click="logout"
              class="flex items-center w-full text-left px-4 py-2 text-red-600 hover:bg-red-50"
            >
              <i class="ri-logout-box-line mr-2"></i> Đăng xuất
            </button>
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const isOpen = ref(false)
const user = ref(null)

const toggleDropdown = () => {
  isOpen.value = !isOpen.value
}

// 🔹 Lấy thông tin người dùng
onMounted(async () => {
  try {
    const id = sessionStorage.getItem('accountid')
    if (!id) return
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/admin/calll/${id}`)
    user.value = res.data
  } catch (err) {
    console.error('Không thể tải thông tin người dùng:', err)
  }

  document.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})

// 🔹 Ẩn dropdown khi click ra ngoài
const handleClickOutside = (event) => {
  const dropdown = document.querySelector('.relative.inline-block')
  if (dropdown && !dropdown.contains(event.target)) {
    isOpen.value = false
  }
}

// 🔹 Xử lý logout
const logout = () => {
  sessionStorage.clear()
  router.push('/admin/login')
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease, transform 0.2s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(-4px);
}
</style>
