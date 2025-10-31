<template>
  <div class="min-h-screen bg-slate-100 py-8 px-4">
    <div class="max-w-4xl mx-auto">
      <h1 class="text-2xl font-semibold text-slate-800 mb-2">Order History</h1>
      <p class="text-sm text-slate-500 mb-6">View your recent completed orders and details</p>

      <div v-if="paginatedOrders.length > 0" class="space-y-4">
        <div v-for="(order, index) in paginatedOrders" :key="index"
          class="bg-white rounded-xl shadow-md border border-slate-200 hover:shadow-lg transition p-5">
          <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-4">
            <div class="flex items-center gap-4">
              <img :src="order.img" alt="Order image"
                class="w-16 h-16 rounded-lg object-cover border border-slate-200" />
              <div>
                <h2 class="text-slate-900 font-semibold text-lg">{{ order.name }}</h2>
                <p class="text-sm text-slate-500">
                  Ordered on {{ formatDate(order.time) }}
                </p>
              </div>
            </div>

            <div class="text-right">
              <div class="text-slate-900 font-semibold text-lg">
                {{ formatCurrency(order.price) }}
              </div>
              <div class="inline-flex items-center gap-2 px-3 py-1 mt-1 rounded-full text-sm font-medium"
                :class="order.status3 ? 'bg-green-100 text-green-700' : 'bg-rose-100 text-rose-700'">
                <span class="w-2 h-2 rounded-full" :class="order.status3 ? 'bg-green-500' : 'bg-rose-500'"></span>
                <span>{{ order.status3 ? 'Delivered' : 'Processing' }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Pagination -->
        <div v-if="totalPages > 1" class="flex justify-center items-center gap-3 mt-8">
          <button @click="prevPage" :disabled="currentPage === 1"
            class="px-3 py-1 rounded-md bg-slate-200 text-slate-700 hover:bg-slate-300 disabled:opacity-50">
            Prev
          </button>
          <span class="text-slate-700 text-sm">
            Page {{ currentPage }} of {{ totalPages }}
          </span>
          <button @click="nextPage" :disabled="currentPage === totalPages"
            class="px-3 py-1 rounded-md bg-slate-200 text-slate-700 hover:bg-slate-300 disabled:opacity-50">
            Next
          </button>
        </div>
      </div>

      <div v-else class="text-center text-slate-600 py-12">No orders found.</div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const data = ref([])
const currentPage = ref(1)
const itemsPerPage = 4

const router = useRouter()
onMounted(async () => {
  try {
    const token = localStorage.getItem("token") || sessionStorage.getItem("token")

    if (!token) {
      router.push("/account/login")
      return
    }

    const res = await axios.get(
      `${import.meta.env.VITE_API_URL_BACKEND}/cart/history`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )

    data.value = res.data
    console.log("🛒 Lịch sử đơn hàng:", data.value)
  } catch (err) {
    console.error("❌ Lỗi khi tải lịch sử đơn hàng:", err)
  }
})


const totalPages = computed(() => Math.ceil(data.value.length / itemsPerPage))

const paginatedOrders = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return data.value.slice(start, start + itemsPerPage)
})

function nextPage() {
  if (currentPage.value < totalPages.value) currentPage.value++
}
function prevPage() {
  if (currentPage.value > 1) currentPage.value--
}

function formatCurrency(value) {
  return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(value)
}

function formatDate(iso) {
  const d = new Date(iso)
  return d.toLocaleDateString('en-US', {
    year: 'numeric',
    month: 'short',
    day: 'numeric'
  })
}
</script>
