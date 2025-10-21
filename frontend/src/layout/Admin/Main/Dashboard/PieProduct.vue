<template>
  <div class="bg-white shadow-lg rounded-2xl p-5" style="width: 400px;">
    <!-- Header -->
    <div class="flex justify-between items-center mb-3">
      <h2 class="text-lg font-semibold text-gray-800">Sản phẩm đã bán</h2>
      <button class="text-gray-400 hover:text-gray-600 text-xs">Report ▼</button>
    </div>

    <!-- Biểu đồ -->
    <div class="relative" style="height: 210px;">
      <canvas ref="chartCanvas"></canvas>
    </div>

    <!-- ✅ Thống kê ngắn gọn -->
    <div class="mt-3 border-t pt-2">
      <ul class="divide-y divide-gray-100 max-h-32 overflow-y-auto">
        <li
          v-for="(item, index) in chartData"
          :key="index"
          class="flex justify-between items-center py-1.5 text-sm"
        >
          <div class="flex items-center space-x-2">
            <span
              class="inline-block w-3 h-3 rounded-full"
              :style="{ backgroundColor: colors[index % colors.length] }"
            ></span>
            <span class="text-gray-700 truncate w-28">{{ item.name }}</span>
          </div>
          <span class="text-gray-500">
            {{ item.numberOffTime }}
            <span class="text-gray-400 text-xs">({{ getPercent(item.numberOffTime) }}%)</span>
          </span>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Chart, PieController, ArcElement, Tooltip, Legend } from 'chart.js'
import axios from 'axios'

Chart.register(PieController, ArcElement, Tooltip, Legend)

const chartCanvas = ref(null)
const chartInstance = ref(null)
const chartData = ref([])
const backend = import.meta.env.VITE_API_URL_BACKEND

const colors = [
  '#3b82f6', '#10b981', '#f59e0b', '#ef4444', '#6366f1',
  '#14b8a6', '#f97316', '#8b5cf6', '#22c55e', '#eab308',
  '#0ea5e9', '#ec4899', '#94a3b8', '#f87171'
]

const getPercent = (value) => {
  if (!chartData.value.length) return 0
  const total = chartData.value.reduce((a, b) => a + b.numberOffTime, 0)
  return ((value / total) * 100).toFixed(1)
}

onMounted(async () => {
  try {
    const response = await axios.get(`${backend}/cart/dashboardproduct`)
    chartData.value = response.data

    const labels = chartData.value.map(item => item.name)
    const values = chartData.value.map(item => item.numberOffTime)

    if (chartInstance.value) chartInstance.value.destroy()

    chartInstance.value = new Chart(chartCanvas.value, {
      type: 'pie',
      data: {
        labels,
        datasets: [
          {
            data: values,
            backgroundColor: colors,
            borderColor: '#ffffff',
            borderWidth: 2
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        layout: { padding: 0 },
        plugins: {
          legend: { display: false },
          tooltip: {
            callbacks: {
              label: (ctx) => `${ctx.label}: ${ctx.parsed} sản phẩm`
            }
          }
        }
      }
    })
  } catch (error) {
    console.error('Lỗi khi gọi API:', error)
  }
})
</script>

<style scoped>
canvas {
  width: 100% !important;
  height: 100% !important;
  pointer-events: none !important;
}

/* Cuộn thanh thống kê nếu danh sách quá dài */
ul::-webkit-scrollbar {
  width: 4px;
}
ul::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 10px;
}
</style>
