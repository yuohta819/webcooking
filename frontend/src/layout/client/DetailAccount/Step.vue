<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const data = ref<any[]>([])
const router = useRouter()

const currentPage = ref(1)
const itemsPerPage = 6

const paginatedData = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return data.value.slice(start, start + itemsPerPage)
})

const totalPages = computed(() => Math.ceil(data.value.length / itemsPerPage))

onMounted(async () => {
  try {
    const token = localStorage.getItem("token") || sessionStorage.getItem("token")
    if (!token) {
      router.push("/account/login")
      return
    }

    const res = await axios.get(
      `${import.meta.env.VITE_API_URL_BACKEND}/status/client/callstatus`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )

    data.value = res.data
    console.log(data.value)

  } catch (error) {
    console.error("❌ Lỗi khi lấy dữ liệu:", error)
    router.push("/account/login")
  }
})


function getStepDescription(value: boolean, name: string): string {
  if (value && name === "1") return 'Confirmed'
  if (!value && name === "1") return 'Not yet confirmed'
  if (value && name === "2") return 'Cooking done'
  if (!value && name === "2") return 'Cooking...'
  if (value && name === "3") return 'Delivered'
  if (!value && name === "3") return 'Delivering...'
  return 'Pending'
}

function getCurrentStep(item: any): number {
  if (item.status3) return 3
  if (item.status2) return 2
  if (item.status1) return 1
  return 0
}
</script>

<template>
  <div class="min-h-screen bg-gradient-to-b from-[#E9F5EC] to-[#F6FAFB] py-10 px-4">
    <h2 class="text-3xl font-bold mb-8 text-center text-[#1C4B43] uppercase tracking-wide">
      Order Tracking
    </h2>

    <!-- 🧾 Nếu không có đơn hàng -->
    <div v-if="data.length === 0" class="flex flex-col items-center justify-center mt-20">
      <img src="https://cdn-icons-png.flaticon.com/512/4076/4076549.png" alt="no orders"
        class="w-28 h-28 mb-4 opacity-80" />
      <p class="text-gray-600 text-lg font-medium">You haven’t placed any orders yet.</p>
      <p class="text-gray-400 text-sm mt-1">Start shopping to see your order status here!</p>
      <router-link to="/"
        class="mt-6 bg-green-600 hover:bg-green-700 text-white font-semibold px-6 py-2 rounded-lg transition">
        Go Shopping 🛒
      </router-link>
    </div>

    <!-- ✅ Nếu có đơn hàng -->
    <div v-else class="grid grid-cols-1 md:grid-cols-2 xl:grid-cols-3 gap-8 max-w-6xl mx-auto">
      <div v-for="item in paginatedData" :key="item.statusid"
        class="bg-white rounded-2xl shadow-md border border-gray-100 p-6 hover:shadow-lg transition-all duration-300">
        <!-- Thông tin món ăn -->
        <div class="flex items-center gap-4">
          <img :src="item.menu.img" alt="menu" class="w-20 h-20 object-cover rounded-xl border border-gray-200" />
          <div>
            <p class="font-semibold text-lg text-gray-800">{{ item.menu.name }}</p>
            <p class="text-[#1C7C54] font-medium">{{ item.menu.price.toLocaleString() }}₫</p>
          </div>
        </div>

        <!-- Tiến trình trạng thái -->
        <div class="mt-6 space-y-6">
          <!-- Step 1 -->
          <div>
            <div class="flex items-center justify-between text-sm font-medium text-gray-600 mb-1">
              <div class="flex items-center gap-2">
                <span :class="['flex items-center justify-center w-5 h-5 rounded-full border transition-colors',
                  item.status1 ? 'bg-[#1C7C54] text-white border-[#1C7C54]' : 'border-gray-400 text-gray-400']">
                  {{ item.status1 ? '✓' : '⏳' }}
                </span>
                <span>Confirmation</span>
              </div>
              <span :class="item.status1 ? 'text-[#1C7C54]' : 'text-gray-400'">
                {{ getStepDescription(item.status1, '1') }}
              </span>
            </div>
            <div class="w-full bg-gray-200 rounded-full h-2">
              <div :class="item.status1 ? 'bg-[#1C7C54]' : 'bg-gray-300'" class="h-2 rounded-full transition-all"
                :style="{ width: item.status1 ? '100%' : '25%' }"></div>
            </div>
          </div>

          <!-- Step 2 -->
          <div>
            <div class="flex items-center justify-between text-sm font-medium text-gray-600 mb-1">
              <div class="flex items-center gap-2">
                <span :class="['flex items-center justify-center w-5 h-5 rounded-full border transition-colors',
                  item.status2 ? 'bg-[#2D99AE] text-white border-[#2D99AE]' : 'border-gray-400 text-gray-400']">
                  {{ item.status2 ? '✓' : '🍳' }}
                </span>
                <span>Cooking</span>
              </div>
              <span :class="item.status2 ? 'text-[#2D99AE]' : 'text-gray-400'">
                {{ getStepDescription(item.status2, '2') }}
              </span>
            </div>
            <div class="w-full bg-gray-200 rounded-full h-2">
              <div :class="item.status2 ? 'bg-[#2D99AE]' : 'bg-gray-300'" class="h-2 rounded-full transition-all"
                :style="{ width: item.status2 ? '100%' : '50%' }"></div>
            </div>
          </div>

          <!-- Step 3 -->
          <div>
            <div class="flex items-center justify-between text-sm font-medium text-gray-600 mb-1">
              <div class="flex items-center gap-2">
                <span :class="['flex items-center justify-center w-5 h-5 rounded-full border transition-colors',
                  item.status3 ? 'bg-[#A3B72E] text-white border-[#A3B72E]' : 'border-gray-400 text-gray-400']">
                  {{ item.status3 ? '✓' : '🚚' }}
                </span>
                <span>Delivery</span>
              </div>
              <span :class="item.status3 ? 'text-[#A3B72E]' : 'text-gray-400'">
                {{ getStepDescription(item.status3, '3') }}
              </span>
            </div>
            <div class="w-full bg-gray-200 rounded-full h-2">
              <div :class="item.status3 ? 'bg-[#A3B72E]' : 'bg-gray-300'" class="h-2 rounded-full transition-all"
                :style="{ width: item.status3 ? '100%' : '25%' }"></div>
            </div>
          </div>
        </div>
        <div class="text-gray-500 text-sm mt-5">🪑 Table: {{ item.so_ban }}</div>
        <!-- Trạng thái đơn hàng -->
        <div class="mt-6 text-right">
          <span :class="[getCurrentStep(item) === 3 ? 'text-[#1C7C54]' : 'text-[#2D99AE]']"
            class="text-sm font-semibold">
            {{ getCurrentStep(item) === 3 ? 'Completed ✅' : 'In Progress...' }}
          </span>
        </div>
      </div>
    </div>
  </div>
  <!-- 🔢 Pagination -->
  <div v-if="data.length > itemsPerPage" class="flex justify-center mt-10 gap-2">
    <button @click="currentPage--" :disabled="currentPage === 1"
      class="px-4 py-2 rounded-lg bg-gray-200 hover:bg-gray-300 disabled:opacity-40 transition">
      ◀ Prev
    </button>

    <span class="font-medium text-gray-600 mx-2">
      Page {{ currentPage }} / {{ totalPages }}
    </span>

    <button @click="currentPage++" :disabled="currentPage === totalPages"
      class="px-4 py-2 rounded-lg bg-gray-200 hover:bg-gray-300 disabled:opacity-40 transition">
      Next ▶
    </button>
  </div>

</template>
