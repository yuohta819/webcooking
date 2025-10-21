<script setup>
import { ref, watch, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const collapsed = ref(false)
const selectedKey = ref('1')
const router = useRouter()
const route = useRoute()

const menuItems = [
  { key: '1', label: 'Order Status', icon: '🧾', route: '/infor/step' },
  { key: '2', label: 'Order History', icon: '🎥', route: '/infor/history' },
  { key: '3', label: 'Detail Account', icon: '📤', route: '/infor/account' },
  { key: '4', label: 'Feedback History', icon: '📤', route: '/infor/feedback' },
]

// Khi click menu
function handleSelect(item) {
  selectedKey.value = item.key
  router.push(item.route)
}

// Khi load trang hoặc đổi URL => update selectedKey
function updateSelectedKeyByRoute() {
  const current = menuItems.find(item => route.path.startsWith(item.route))
  if (current) selectedKey.value = current.key
}

onMounted(() => {
  updateSelectedKeyByRoute()
})

// Theo dõi khi route thay đổi (VD: user bấm Back/Forward)
watch(
  () => route.path,
  () => updateSelectedKeyByRoute()
)
</script>

<template>
  <div class="flex min-h-screen bg-[#F4F1EA] py-20">
    <!-- Sidebar -->
    <aside
      :class="[
        'bg-[#161616eb] text-white transition-all duration-300 shadow-lg flex flex-col',
        collapsed ? 'w-20' : 'w-64'
      ]"
    >
      <!-- Header -->
      <div class="flex items-center justify-between px-4 py-4 border-b border-[#F78A81]">
        <div v-if="!collapsed" class="font-black text-lg uppercase tracking-wider">
          My Panel
        </div>
        <button
          @click="collapsed = !collapsed"
          class="text-white hover:text-[#FFE0DC] transition text-2xl"
        >
          {{ collapsed ? '☰' : '×' }}
        </button>
      </div>

      <!-- Menu -->
      <nav class="mt-4 flex-1">
        <div
          v-for="item in menuItems"
          :key="item.key"
          @click="handleSelect(item)"
          class="flex items-center gap-3 px-5 py-3 cursor-pointer rounded-lg mx-3 mb-2 transition-all duration-200"
          :class="selectedKey === item.key
            ? 'bg-white text-[#F0625D] font-semibold'
            : 'hover:bg-[#FF8A65] hover:text-white'"
        >
          <span class="text-xl">{{ item.icon }}</span>
          <span v-if="!collapsed" class="text-sm">{{ item.label }}</span>
        </div>
      </nav>

      <!-- Footer -->
      <div class="p-4 text-xs text-center border-t border-[#F78A81]">
        <span v-if="!collapsed">© 2025 My Account</span>
      </div>
    </aside>

    <!-- Main Content -->
    <main class="flex-1 p-6 bg-white m-4 rounded-2xl shadow-md">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
nav div:hover {
  transform: translateX(4px);
}
</style>
