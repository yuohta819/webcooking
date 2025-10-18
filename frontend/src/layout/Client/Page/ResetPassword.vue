<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-50 to-green-100 px-4">
    <div class="bg-white shadow-lg rounded-2xl w-full max-w-md p-8 border border-gray-200">
      <h1 class="text-3xl font-bold text-center text-green-700 mb-6">Reset Your Password</h1>

      <div class="mb-4">
        <label class="block text-gray-700 font-medium mb-2">New Password</label>
        <input
          v-model="password"
          type="password"
          placeholder="Enter new password"
          class="border border-gray-300 w-full p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 transition"
        />
      </div>

      <div class="mb-6">
        <label class="block text-gray-700 font-medium mb-2">Confirm Password</label>
        <input
          v-model="confirmPassword"
          type="password"
          placeholder="Confirm new password"
          class="border border-gray-300 w-full p-3 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 transition"
        />
      </div>

      <button
        @click="resetPassword"
        :disabled="loading"
        class="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-3 rounded-lg transition disabled:opacity-50 disabled:cursor-not-allowed"
      >
        <span v-if="!loading">Change Password</span>
        <span v-else>Processing...</span>
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue"
import { useRoute, useRouter } from "vue-router"
import axios from "axios"
import { useToast } from "vue-toastification"

const route = useRoute()
const router = useRouter()
const toast = useToast()

const password = ref("")
const confirmPassword = ref("")
const loading = ref(false)
const token = route.query.token
  if (!token) {
    router.push("/account/login")
  }
async function resetPassword() {
  

  if (!password.value || !confirmPassword.value) {
    toast.error("Please fill in all fields.")
    return
  }

  if (password.value !== confirmPassword.value) {
    toast.error("Passwords do not match.")
    return
  }

  try {
    loading.value = true
    await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/auth/reset-password`, {
      token,
      newPassword: password.value,
    })

    toast.success("Password updated successfully!")
    router.push("/account/login")
  } catch (error) {
    toast.error("Invalid or expired link.")
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
/* Hiệu ứng nhẹ cho input khi focus */
input:focus {
  box-shadow: 0 0 5px rgba(34, 197, 94, 0.4);
}

/* Hiệu ứng hover cho nút */
button:hover {
  transform: scale(1.02);
  transition: 0.2s ease;
}
</style>
