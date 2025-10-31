<template>
  <div
    class="min-h-screen bg-gray-100 flex items-center justify-center p-6"
    style="background-color: #F4F1EA;"
  >
    <!-- Main Container -->
    <div class="w-full max-w-6xl bg-[#111] text-white rounded-3xl shadow-2xl overflow-hidden">
      <!-- Header -->
      <div class="px-10 py-8 border-b border-white/10 bg-gradient-to-r from-[#211] via-[#120] to-[#121214]">
        <div class="flex items-center justify-between">
          <div>
            <h1 class="text-3xl md:text-4xl font-extrabold text-amber-400 tracking-wide">
              🍽️ RESTAURANT MENU
            </h1>
            <p class="text-sm text-gray-300 mt-1">Traditional taste — Modern presentation</p>
          </div>

          <div class="text-right">
            <p class="text-sm text-gray-300">Last updated</p>
            <p class="text-lg font-semibold text-gray-100">{{ now }}</p>
          </div>
        </div>
      </div>

      <!-- Menu Items Grid -->
      <div class="p-8">
        <div class="grid grid-cols-1 sm:grid-cols-2 gap-6">
          <div
            v-for="item in items"
            :key="item.id"
            class="flex gap-4 items-start bg-gradient-to-br from-white/2 to-white/1 rounded-2xl p-4"
          >
            <!-- Image -->
            <div class="w-28 h-20 flex-shrink-0 relative">
              <img
                :src="item.img || 'https://via.placeholder.com/300x200?text=No+Image'"
                alt="dish image"
                class="w-full h-full object-cover rounded-lg border border-white/10"
              />
            </div>

            <!-- Info -->
            <div class="flex-1">
              <div class="flex items-start justify-between gap-4">
                <div>
                  <h2 class="text-xl font-bold text-amber-300 leading-tight">{{ item.name }}</h2>
                  <p class="text-sm text-gray-300 mt-1 line-clamp-2">{{ item.describe }}</p>
                </div>

                <div class="text-right flex flex-col items-end gap-2">
                  <div class="flex items-center text-amber-400 text-sm">
                    <span v-for="n in 5" :key="n" class="text-base">★</span>
                  </div>
                  <div class="text-green-300 font-semibold">{{ formatPrice(item.price) }}</div>
                </div>
              </div>
            </div>

            <!-- QR -->
            <div class="w-20 flex-shrink-0 flex items-center justify-center">
              <div class="bg-white rounded-md p-1 shadow">
                <img :src="item.qrData" alt="QR" class="w-16 h-16 object-contain" />
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <div class="px-8 py-6 border-t border-white/10 bg-gradient-to-t from-[#0b0b0b] to-transparent">
        <div class="flex items-center justify-between text-sm text-gray-400">
          <div>© Your Restaurant</div>
          <div>Contact: 0123 456 789</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import QRCode from 'qrcode'

const items = ref([])
const now = new Date().toLocaleDateString('en-US')

// Format price
function formatPrice(v) {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
  }).format(v || 0)
}

// Detail page URL
function itemUrl(item) {
  return `https://webcooking.onrender.com/detail/${encodeURIComponent(item.id)}`
}

// Generate QR code for each dish
async function generateQR(item) {
  try {
    const q = await QRCode.toDataURL(itemUrl(item), {
      width: 140,
      margin: 1,
      color: { dark: '#000000', light: '#ffffff' },
    })
    item.qrData = q
  } catch (err) {
    console.error('QR generation error', err)
    item.qrData = 'data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg"></svg>'
  }
}

// Fetch from API and generate QR
async function fetchMenu() {
  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/menu`)
    items.value = Array.isArray(res.data) ? res.data : []
    await Promise.all(items.value.map(i => generateQR(i)))
  } catch (err) {
    console.error('Failed to fetch menu', err)
    items.value = []
  }
}

onMounted(fetchMenu)
</script>

<style scoped>
/* Limit description to 2 lines */
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
