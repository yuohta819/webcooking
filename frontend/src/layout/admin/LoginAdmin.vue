<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useToast } from "vue-toastification";
import { useRouter } from 'vue-router';

const name = ref('');
const password = ref('');
const toast = useToast();
const router = useRouter();

async function handleSubmit(e) {
  e.preventDefault(); // Ngăn reload trang mặc định
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/${import.meta.env.VITE_APP_NAME}/check`, {
      params: {
        name: name.value,
        password: password.value
      }
    });
    console.log(response.data)
    if (response.data !== "") {
      loginSuccess();
      sessionStorage.setItem("accountid", response.data.accountid)
      sessionStorage.setItem("admin", JSON.stringify(response.data.account))
      sessionStorage.setItem('canAdd',response.data.canAdd)
      sessionStorage.setItem('canDelete',response.data.canDelete)
      sessionStorage.setItem('canEdit',response.data.canEdit)
      router.push(`/${import.meta.env.VITE_APP_NAME}/dashboard`)
    } else {
      loginFail();
    }
  } catch (err) {
    toast.error("Lỗi kết nối đến server.");
    console.error(err);
  }
}

function loginSuccess() {
  toast.success("Account created successfully!");
}
function loginFail() {
  toast.error("Wrong account and password");
}
</script>

<template>
  <div class="bg-[#f0f2f5] h-screen flex items-center justify-center">
    <div class="bg-white rounded-lg shadow-md p-6 w-[350px] text-center">
      <h2 class="text-2xl font-semibold text-[#1677ff] mb-5">Đăng Nhập</h2>

      <!-- ✅ Gắn sự kiện @submit vào form -->
      <form id="login-form" @submit="handleSubmit">
        <div class="mb-4 text-left">
          <label for="username" class="block mb-1 text-sm font-medium">Tài khoản</label>
          <div class="flex items-center border rounded px-3 py-2">
            <span class="mr-2 text-gray-400">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M5.121 17.804A10.953 10.953 0 0112 15c2.39 0 4.604.745 6.379 2.004M15 10a3 3 0 11-6 0 3 3 0 016 0z" />
              </svg>
            </span>
            <!-- ✅ Gắn v-model để cập nhật giá trị -->
            <input type="text" id="username" name="username" required placeholder="Tài khoản"
              class="outline-none w-full" v-model="name" />
          </div>
        </div>

        <div class="mb-6 text-left">
          <label for="password" class="block mb-1 text-sm font-medium">Mật khẩu</label>
          <div class="flex items-center border rounded px-3 py-2">
            <span class="mr-2 text-gray-400">
              <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                  d="M12 11c0-1.105-.895-2-2-2s-2 .895-2 2m10 0v4a2 2 0 01-2 2H8a2 2 0 01-2-2v-4m6-4V5a2 2 0 10-4 0v2h4z" />
              </svg>
            </span>
            <!-- ✅ Gắn v-model để cập nhật giá trị -->
            <input type="password" id="password" name="password" required placeholder="Mật khẩu"
              class="outline-none w-full" v-model="password" />
          </div>
        </div>

        <button type="submit"
          class="w-full bg-blue-600 hover:bg-blue-700 text-white py-2 rounded transition duration-200">
          Đăng Nhập
        </button>
      </form>
    </div>
  </div>
</template>
<style scoped>
input {
  outline: none !important;
  border: none !important;
}
</style>