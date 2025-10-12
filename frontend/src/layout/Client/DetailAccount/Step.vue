<script lang="ts" setup>

import { onMounted, ref } from 'vue';
import axios from 'axios'
const description = 'This is a description.';
const data = ref([])
const account = localStorage.getItem("account")
onMounted(async () => {
  const res = await axios.post('http://localhost:8080/status/follow',
    { username: account }
  )
  data.value = res.data
})
function getStepDescription(value: any, name: String): string {
  if (value === true && name === "1") return 'Confirmed';
  if (value === false && name === "1") return 'Not yet completed';
  if (value === true && name === "2") return 'Are delivering';
  if (value === false && name === "2") return 'Are doing';
  if (value === true && name === "3") return 'Delivered';
  if (value === false && name === "3") return 'Are doing';
  return 'Chưa bắt đầu';
}

function getCurrentStep(item: any): number {
  if (item.status3 === true) return 3;
  if (item.status2 === true) return 2;
  if (item.status1 === true) return 1;
  return 0;
}
</script>
<template>
  <div class="grid grid-cols-10 sm:grid-cols-10 lg:grid-cols-2 gap-6 p-4">
    <a-card
      v-for="item in data"
      :key="item.id"
      hoverable
      class="rounded-xl shadow border border-gray-100"
      :bodyStyle="{ padding: '16px' }"
    >
      <!-- Steps trạng thái -->
      <a-steps :current="getCurrentStep(item)" size="small" direction="horizontal">
        <a-step title="Wait for confirmation" :description="getStepDescription(item.status1, '1')" />
        <a-step title="Cooking" :description="getStepDescription(item.status2,'2')" />
        <a-step title="Deliver food" :description="getStepDescription(item.status3,'3')" />
      </a-steps>

      <!-- Thông tin món ăn -->
      <div class="flex items-center gap-4 mt-4">
        <img
          :src="item.menu.img"
          alt="menu"
          class="w-20 h-20 object-cover rounded-lg border border-gray-200"
        />
        <div class="flex flex-col">
          <span class="text-base font-semibold text-gray-800">
            {{ item.menu.name }}
          </span>
          <span class="text-orange-500 font-medium mt-1">
            {{ item.menu.price.toLocaleString() }}₫
          </span>
        </div>
      </div>
    </a-card>
  </div>
</template>

