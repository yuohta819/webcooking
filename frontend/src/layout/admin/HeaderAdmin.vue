<template>
  <header class="w-full  bg-gray-50 px-6 py-3 flex items-center justify-between shadow-sm " style="width: 1264px; isolation: isolate;" >
    <!-- Bên trái: Menu icon -->
    <div class="flex items-center space-x-4">
      <button class="p-2 hover:bg-gray-100 rounded-full">
        <svg xmlns="http://www.w3.org/2000/svg" class="w-6 h-6 text-gray-700" fill="none" viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
        </svg>
      </button>

      <!-- Ô tìm kiếm -->
      <div class="relative">
        <input
          type="text"
          placeholder="Search"
          class="pl-10 pr-10 py-2 w-72 rounded-full border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-400 bg-white text-gray-700"
        />
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-5 h-5 text-gray-500 absolute left-3 top-2.5"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-4.35-4.35M11 18a7 7 0 100-14 7 7 0 000 14z" />
        </svg>
      </div>
    </div>

    <!-- Bên phải: các icon -->
    <UserDropdown />
  </header>
</template>

<script setup lang="ts">
// Component con tái sử dụng cho các nút icon
const props = defineProps({})
import UserDropdown from '../../components/UserDropdown.vue';

</script>

<style scoped>
header {
  font-family: 'Inter', sans-serif;
}
</style>
