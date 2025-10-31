<template>
  <div class="min-h-screen bg-[#F4F1EA] flex items-center justify-center py-12 px-4">
    <div class="bg-white shadow-lg rounded-2xl max-w-lg w-full p-8 border border-gray-200">
      <h1 class="text-2xl font-semibold text-gray-800 mb-6 text-center">Account Details</h1>

      <!-- Account Info -->
      <div class="space-y-4 mb-8">
        <div class="flex justify-between items-center">
          <span class="text-gray-500 font-medium">Username:</span>
          <span class="text-gray-800 font-semibold">{{ username }}</span>
        </div>

        <div class="flex justify-between items-center">
          <span class="text-gray-500 font-medium">Total Spent:</span>
          <span class="text-green-600 font-bold text-lg">{{ formatCurrency(totalSpent) }}</span>
        </div>
      </div>

      <div class="border-t border-gray-200 my-6"></div>

      <!-- Change Password -->
      <h2 class="text-lg font-medium text-gray-700 mb-4">Change Password</h2>

      <!-- Nếu là tài khoản Google -->
      <div v-if="type === 'Google'" class="text-center text-gray-600 bg-yellow-50 border border-yellow-300 rounded-lg p-4">
        <p class="font-medium">🔒 This is a Google account. Password change is not available.</p>
      </div>

      <!-- Nếu là tài khoản thường -->
      <form v-else @submit.prevent="changePassword" class="space-y-4">
        <div>
          <label class="block text-sm font-medium text-gray-600 mb-1">Current Password</label>
          <input
            type="password"
            v-model="oldPassword"
            placeholder="Enter current password"
            class="w-full border border-gray-300 rounded-lg p-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-600 mb-1">New Password</label>
          <input
            type="password"
            v-model="newPassword"
            placeholder="Enter new password"
            class="w-full border border-gray-300 rounded-lg p-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
          />
        </div>

        <div>
          <label class="block text-sm font-medium text-gray-600 mb-1">Confirm Password</label>
          <input
            type="password"
            v-model="confirmPassword"
            placeholder="Confirm new password"
            class="w-full border border-gray-300 rounded-lg p-2.5 focus:outline-none focus:ring-2 focus:ring-orange-400"
          />
        </div>

        <button
          type="submit"
          class="w-full mt-4 py-2.5 bg-orange-500 text-white rounded-lg hover:bg-orange-600 transition-all duration-200 font-semibold"
        >
          Update Password
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from "axios"
import { useToast } from 'vue-toastification'
import { useRouter } from 'vue-router'

const totalSpent = ref(0)
const username = ref("")
const oldPassword = ref('')
const newPassword = ref('')
const confirmPassword = ref('')
const data = ref([])
const router = useRouter()
const toast = useToast()
const type = ref('') // <- lưu loại tài khoản (google / local)
let account = localStorage.getItem("accountid")
if (account == null) {
  account = sessionStorage.getItem("accountid")
}

onMounted(async () => {
  try {
    const token = localStorage.getItem("token") || sessionStorage.getItem("token")

    if (!token) {
      router.push("/account/login")
      return
    }

    const res = await axios.get(
      `${import.meta.env.VITE_API_URL_BACKEND}/cart/total`,
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )

    // 👇 Giả sử backend trả thêm "provider": "google" hoặc "local"
    type.value = res.data.provider || 'local'

    username.value = res.data.name
    totalSpent.value = res.data.total
  } catch (err) {
    console.error("❌ Lỗi khi lấy tổng tiền giỏ hàng:", err)
    router.push("/account/login")
  }
})

async function changePassword() {
  if (!oldPassword.value || !newPassword.value || !confirmPassword.value) {
    toast.warning("Please fill in all password fields!")
    return
  }

  if (oldPassword.value === newPassword.value) {
    toast.warning("The new password cannot be the same as the old password!")
    return
  }

  if (newPassword.value !== confirmPassword.value) {
    toast.warning("The new password and confirmation do not match!")
    return
  }

  const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/account/change`, {
    oldPassword: oldPassword.value,
    newPassword: newPassword.value,
    confirmPassword: confirmPassword.value,
    accountid: account
  })

  if (res.data === 'Error password') {
    toast.warning("The old password does not match!")
  } else if (res.data === 'success') {
    toast.success("Password changed successfully!")
    oldPassword.value = ''
    newPassword.value = ''
    confirmPassword.value = ''
  } else {
    toast.error("An unexpected error occurred!")
  }
}

function formatCurrency(value) {
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD'
  }).format(value)
}
</script>
