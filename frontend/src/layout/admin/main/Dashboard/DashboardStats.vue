
<template>
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
    <a-card
      v-for="(item, index) in statsComputed"
      :key="index"
      class="shadow-md rounded-xl border-0 bg-white flex justify-between items-center p-5 hover:shadow-lg transition-all duration-300"
    >
      <div>
        <p class="text-gray-500 text-sm font-semibold">{{ item.title }}</p>
        <div class="flex items-center space-x-2">
          <h2 class="text-2xl font-bold text-gray-800">{{ item.value }}</h2>
          <span
            :class="[
              'text-sm font-semibold',
              item.percent > 0 ? 'text-green-500' : item.percent < 0 ? 'text-red-500' : 'text-gray-500'
            ]"
          >
            <template v-if="item.percent > 0">↑ +{{ item.percent }}%</template>
            <template v-else-if="item.percent < 0">↓ {{ item.percent }}%</template>
            <template v-else>+0.00%</template>
          </span>
        </div>
        <a class="text-blue-600 text-sm hover:underline">{{ item.linkText }}</a>
      </div>

      <div
        class="p-3 rounded-xl"
        :class="item.bgColor"
      >
        <component :is="item.icon" class="text-2xl" :class="item.iconColor" />
      </div>
    </a-card>
  </div>
</template>

<script setup>
import { DollarOutlined, ShoppingOutlined, UserOutlined, CreditCardOutlined } from '@ant-design/icons-vue'
import axios from 'axios'
import { onMounted, ref, computed } from 'vue'

const data = ref(null) // sẽ lưu response từ API

onMounted(async () => {
  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/cart/allprice`)
    // đảm bảo gán đúng biến (fix typo)
    data.value = res.data
  } catch (err) {
    console.error('Lấy dữ liệu thất bại', err)
    data.value = null
  }
})

// helper: lấy giá an toàn, nếu API trả mảng với allOfPrice ở index 0
const getAllPrice = () => {
  // thay đổi theo cấu trúc thực tế của res.data
  // ví dụ: res.data = [{ allOfPrice: 1234.56 }] hoặc res.data = { allOfPrice: 1234.56 }
  if (!data.value) return 0
  if (Array.isArray(data.value)) return data.value[0] ?? 0
  return data.value ?? 0
}
// helper: lấy giá an toàn, nếu API trả mảng với allOfPrice ở index 0
const getOrders= () => {
  // thay đổi theo cấu trúc thực tế của res.data
  // ví dụ: res.data = [{ allOfPrice: 1234.56 }] hoặc res.data = { allOfPrice: 1234.56 }
  if (!data.value) return 0
  if (Array.isArray(data.value)) return Math.floor(data.value[1] ?? 0)
  return Math.floor(data.value[1] ?? 0)
}
const getClient= () => {
  // thay đổi theo cấu trúc thực tế của res.data
  // ví dụ: res.data = [{ allOfPrice: 1234.56 }] hoặc res.data = { allOfPrice: 1234.56 }
  if (!data.value) return 0
  if (Array.isArray(data.value)) return data.value[2] ?? 0
  return data.value ?? 0
}
const statsComputed = computed(() => [
  {
    title: 'Tổng Số Tiền',
    value: Math.floor(getAllPrice()).toLocaleString("Vi-en") + 'K', // an toàn: sẽ cập nhật khi data thay đổi
    percent: 16.24,
    linkText: 'View net earnings',
    icon: DollarOutlined,
    bgColor: 'bg-green-100',
    iconColor: 'text-green-500',
  },
  {
    title: 'Tổng Số Đơn Hàng',
    value: getOrders(),
    percent: -3.57,
    linkText: 'View all orders',
    icon: ShoppingOutlined,
    bgColor: 'bg-blue-100',
    iconColor: 'text-blue-500',
  },
  {
    title: 'Tổng Số Khách Hàng',
    value: getClient(),
    percent: 29.08,
    linkText: 'See details',
    icon: UserOutlined,
    bgColor: 'bg-yellow-100',
    iconColor: 'text-yellow-500',
  },
  {
    title: 'Số Tiền Của Tôi',
    value: (Math.floor(getAllPrice()) * 0.1).toLocaleString("Vi-en") + "K",
    percent: 0,
    linkText: 'Withdraw money',
    icon: CreditCardOutlined,
    bgColor: 'bg-gray-100',
    iconColor: 'text-gray-500',
  },
])
</script>

<style scoped>
.ant-card {
  border-radius: 1rem;
  background-color: #fff;
}
</style>