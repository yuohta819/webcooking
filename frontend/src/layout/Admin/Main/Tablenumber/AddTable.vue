<template>
  <div class="min-h-screen bg-gray-100 flex items-center justify-center p-6">
    <div class="bg-white shadow-xl rounded-2xl w-full max-w-md p-8 border border-gray-200">
      <h1 class="text-2xl font-semibold text-gray-800 mb-6 text-center">
        ➕ Thêm Bàn Mới
      </h1>

      <!-- Biểu mẫu -->
      <form @submit.prevent="submitForm" class="space-y-5">
        <!-- Tên bàn -->
        <div>
          <label class="block text-gray-700 font-medium mb-1">Tên bàn</label>
          <input
            v-model="tableName"
            type="text"
            placeholder="Nhập tên bàn"
            required
            class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-indigo-500 outline-none"
          />
        </div>

        <!-- Số ghế -->
        <div>
          <label class="block text-gray-700 font-medium mb-1">Số ghế</label>
          <input
            v-model.number="seats"
            type="number"
            min="1"
            placeholder="Nhập số lượng ghế"
            required
            class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-indigo-500 outline-none"
          />
        </div>

        <!-- Trạng thái -->
        <div>
          <label class="block text-gray-700 font-medium mb-1">Trạng thái</label>
          <select
            v-model="status"
            class="w-full border border-gray-300 rounded-lg px-4 py-2 focus:ring-2 focus:ring-indigo-500 outline-none"
          >
            <option :value="true">Đang hoạt động</option>
            <option :value="false">Ngừng hoạt động</option>
          </select>
        </div>

        <!-- Nút hành động -->
        <div class="flex justify-between items-center pt-4">
          <router-link
            :to="`/${appName}/tablenumber`"
            class="text-gray-600 hover:text-indigo-600 transition font-medium"
          >
            ← Quay lại danh sách
          </router-link>
          <button
            type="submit"
            class="bg-indigo-600 text-white px-5 py-2 rounded-lg hover:bg-indigo-700 transition"
          >
            Lưu bàn
          </button>
        </div>
      </form>

      <!-- Thông báo thành công -->
      <p v-if="message" class="text-green-600 font-semibold mt-4 text-center">
        {{ message }}
      </p>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useToast } from "vue-toastification";
const appName = import.meta.env.VITE_APP_NAME
const router = useRouter();
const message = ref("");
const toast = useToast();

// Khai báo các biến dữ liệu
const tableName = ref("");
const seats = ref(2);
const status = ref(false);

const submitForm = async () => {
  try {
    // Lấy thời gian hiện tại và định dạng lại
    const now = new Date();
    const formattedDate = now.toISOString().split('.')[0]; // Bỏ mili giây + ký tự Z

    // Gửi dữ liệu lên backend
    await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/number/add`, {
      table_name: tableName.value,
      seats: seats.value,
      status: status.value,
      accountid: null,
      created_at: formattedDate
    });

    toast.success("✅ Thêm bàn mới thành công!");
    setTimeout(() => router.push(`/${import.meta.env.VITE_APP_NAME}/tablenumber`), 1500);
  } catch (error) {
    console.error(error);
    message.value = "❌ Thêm bàn thất bại!";
  }
};
</script>
