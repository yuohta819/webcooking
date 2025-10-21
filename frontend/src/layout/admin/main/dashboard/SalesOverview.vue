
<script setup>
import { Bar } from 'vue-chartjs'
import { Chart, BarElement, CategoryScale, LinearScale, Legend, Tooltip } from 'chart.js'

Chart.register(BarElement, CategoryScale, LinearScale, Legend, Tooltip)

const data = {
  labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun'],
  datasets: [
    { label: 'Total Orders', data: [1200, 900, 1400, 1000, 1300, 1100], backgroundColor: '#A78BFA' },
    { label: 'Total Sales', data: [900, 700, 1200, 900, 1100, 800], backgroundColor: '#EC4899' },
  ],
}
</script>
<template>
  <div class="p-6 bg-white rounded-2xl shadow" style="width: 884px;">
    <Bar :data="data" />
  </div>
</template>