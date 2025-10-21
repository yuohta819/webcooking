<template>
  <div class="min-h-screen flex flex-col md:flex-row bg-[#F4F1EA] items-center justify-center px-6 py-12">
    <!-- Left Form -->
    <div
      class="bg-white md:w-[420px] w-full shadow-lg rounded-2xl p-8 border border-gray-200 flex flex-col justify-center">
      <div class="flex flex-col items-center mb-6">
        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/accountLogo.png" alt="Logo"
          class="w-32 mb-4" />
        <h1 class="text-2xl font-bold text-gray-800">Forgot Password</h1>
        <p class="text-gray-500 text-sm mt-1 text-center">
          Enter your registered email to reset your password.
        </p>
      </div>

      <form @submit.prevent="handleSubmit" class="space-y-5">
        <div>
          <label class="block text-sm font-medium text-gray-600 mb-1">Email Address</label>
          <input v-model="email"  placeholder="Enter your email"
            class="w-full border border-gray-300 rounded-lg p-2.5 focus:outline-none focus:ring-2 focus:ring-red-500" />
        </div>

        <button type="submit"
          class="w-full py-2.5 bg-red-500 text-white font-semibold rounded-lg hover:bg-red-600 transition-all duration-200">
          Send Reset Link
        </button>

        <div class="text-sm text-center text-gray-600 mt-5">
          Remembered your divassword?
          <a href="/account/login" class="text-red-500 hover:underline font-medium">Back to Login</a>
        </div>
      </form>

      <p v-if="message" :class="['mt-4 text-center text-sm font-medium', messageColor]">
        {{ message }}
      </p>
    </div>

    <!-- Right Image -->
    <div class="hidden md:flex items-center justify-center ml-8">
      <div
        class="rounded-full overflow-hidden border-[10px] border-white shadow-xl w-72 h-72 flex items-center justify-center">
        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/profile/profile.png"
          alt="Forgot Password Illustration" class="object-cover w-full h-full" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useToast } from 'vue-toastification'
import axios from "axios"
const email = ref('')
const message = ref('')
const messageColor = ref('text-gray-600')
const toast = useToast()

async function handleSubmit() {
  if (!email.value) {
    toast.warning('Please enter your email address!')
    return
  }

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(email.value)) {
    toast.warning('Please enter a valid email address!')
    return
  }
  try {
    const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/auth/forgot-password`, { email: email.value })
    if (res.data == null) {
      toast.warning("Email does not exist!")
      return;
    }
    toast.success("Reset link sent to your email")
  } catch {
    toast.error("Email not found")
  }

}
</script>

<style scoped>
input::placeholder {
  color: #a0a0a0;
}
</style>
