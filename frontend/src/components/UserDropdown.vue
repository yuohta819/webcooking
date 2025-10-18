<template>
  <div class="relative inline-block text-left">
    <!-- Nút bấm: Avatar + tên -->
    <button 
      @click="toggleDropdown"
      class="flex items-center space-x-2 p-2 rounded-lg hover:bg-gray-100 transition"
    >
      <img
        src="https://i.pravatar.cc/40"
        alt="avatar"
        class="w-10 h-10 rounded-full"
      />
      <div class="text-left">
        <div class="text-sm font-semibold text-gray-800">Anna Adame</div>
        <div class="text-xs text-gray-500">Founder</div>
      </div>
    </button>

    <!-- Dropdown -->
    <transition name="fade">
      <div
        v-if="isOpen"
        class="absolute right-0 mt-2 w-56 bg-white rounded-xl shadow-lg border border-gray-100 z-50"
      >
        <div class="px-4 py-3 border-b border-gray-100">
          <p class="text-sm text-gray-600">Welcome Anna!</p>
        </div>

        <ul class="py-2 text-sm text-gray-700">
          <li><a href="#" class="flex items-center px-4 py-2 hover:bg-gray-50"><i class="ri-user-line mr-2"></i> Profile</a></li>
          <li><a href="#" class="flex items-center px-4 py-2 hover:bg-gray-50"><i class="ri-mail-line mr-2"></i> Messages</a></li>
          <li><a href="#" class="flex items-center px-4 py-2 hover:bg-gray-50"><i class="ri-task-line mr-2"></i> Taskboard</a></li>
          <li><a href="#" class="flex items-center px-4 py-2 hover:bg-gray-50"><i class="ri-question-line mr-2"></i> Help</a></li>
        </ul>

        <div class="border-t border-gray-100 px-4 py-3">
          <p class="text-sm text-gray-600">Balance: <span class="font-semibold">$5971.67</span></p>
        </div>

        <ul class="py-2 text-sm text-gray-700">
          <li class="flex items-center justify-between px-4 py-2 hover:bg-gray-50">
            <a href="#" class="flex items-center"><i class="ri-settings-3-line mr-2"></i> Settings</a>
            <span class="text-xs bg-green-100 text-green-600 px-2 py-0.5 rounded-md">New</span>
          </li>
          <li><a href="#" class="flex items-center px-4 py-2 hover:bg-gray-50"><i class="ri-lock-line mr-2"></i> Lock screen</a></li>
          <li><a href="#" class="flex items-center px-4 py-2 hover:bg-gray-50 text-red-600"><i class="ri-logout-box-line mr-2"></i> Logout</a></li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const isOpen = ref(false)

const toggleDropdown = () => {
  isOpen.value = !isOpen.value
}

// Ẩn dropdown khi click ra ngoài
const handleClickOutside = (event) => {
  const dropdown = document.querySelector('.relative.inline-block')
  if (dropdown && !dropdown.contains(event.target)) {
    isOpen.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})
onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})
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
