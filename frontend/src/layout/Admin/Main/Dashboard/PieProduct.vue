<template>
    <div class=" bg-white shadow-lg rounded-2xl p-6" style="width: 400px;">
        <div class="flex justify-between items-center mb-4">
            <h2 class="text-xl font-semibold text-gray-800">Số lượng sản phẩm đã bán</h2>
            <button class="text-gray-400 hover:text-gray-600 text-sm">Report ▼</button>
        </div>

        <div class="relative" >
            <canvas ref="chartCanvas"></canvas>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { Chart, PieController, ArcElement, Tooltip, Legend } from 'chart.js'
import ChartDataLabels from 'chartjs-plugin-datalabels'
import axios from 'axios'

Chart.register(PieController, ArcElement, Tooltip, Legend, ChartDataLabels)

const chartCanvas = ref(null)
const chartInstance = ref(null)
const backend = import.meta.env.VITE_API_URL_BACKEND

onMounted(async () => {
  try {
    const response = await axios.get(`${backend}/bill/dashboardproduct`)
    const apiData = response.data  // [{idproduct:1, numberOffTime:2}, ...]
    // Lấy label và dữ liệu từ API
    const labels = apiData.map(item => item.name)
    const values = apiData.map(item => item.numberOffTime)

    // Nếu chart đã tồn tại thì hủy trước khi vẽ lại
    if (chartInstance.value) {
      chartInstance.value.destroy()
    }

    // Khởi tạo biểu đồ với dữ liệu mới
    chartInstance.value = new Chart(chartCanvas.value, {
      type: 'pie',
      data: {
        labels,
        datasets: [{
          data: values,
          backgroundColor: [
            '#3b82f6', '#10b981', '#f59e0b', '#ef4444', '#6366f1',
            '#14b8a6', '#f97316', '#8b5cf6', '#22c55e', '#eab308',
            '#0ea5e9', '#ec4899', '#94a3b8', '#f87171'
          ],
          borderColor: '#ffffff',
          borderWidth: 2,
        }]
      },
      options: {
        plugins: {
          legend: {
            position: 'bottom',
            labels: {
              color: '#374151',
              font: { size: 12 }
            }
          },
          tooltip: {
            callbacks: {
              label: (ctx) => `${ctx.label}: ${ctx.parsed} units`
            }
          },
          datalabels: {
            color: '#374151',
            font: { weight: 'bold', size: 11 },
            anchor: 'end',
            align: 'end',
            offset: -50,
            formatter: (value, ctx) => {
              const total = ctx.chart.data.datasets[0].data.reduce((a, b) => a + b, 0)
              const percent = ((value / total) * 100).toFixed(1)
              return percent + '%'
            }
          }
        }
      },
      plugins: [ChartDataLabels]
    })
  } catch (error) {
    console.error('Lỗi khi gọi API:', error)
  }
})
</script>


<style scoped>
canvas {
  position: relative !important;
  z-index: 0 !important;
  transform: none !important;
  will-change: auto !important;
  pointer-events: none !important; /* ✅ Rất quan trọng: ngăn canvas chặn header */
}
</style>
