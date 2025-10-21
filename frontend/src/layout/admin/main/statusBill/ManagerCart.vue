<script setup lang="ts">
import { onMounted, ref, computed } from "vue";
import axios from "axios";

interface StatusItem {
  account: string;
  billid: number;
  idproduct: number;
  name: string;
  time: string;
  menu: {
    describe: string;
    id: number;
    img: string;
    name: string;
    price: string;
  };
}

const list = ref<StatusItem[]>([]);
const currentPage = ref(1);
const itemsPerPage = 6; // ✅ mỗi trang 6 sản phẩm

onMounted(async () => {
  const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/bill/call`);
  list.value = res.data;
  console.log(res.data)
});

// ✅ Tính tổng số trang
const totalPages = computed(() => Math.ceil(list.value.length / itemsPerPage));

// ✅ Cắt dữ liệu theo trang
const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  const end = start + itemsPerPage;
  return list.value.slice(start, end);
});

// ✅ Hàm chuyển trang
const nextPage = () => {
  if (currentPage.value < totalPages.value) currentPage.value++;
};
const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--;
};
</script>

<template>
  <div class="min-h-screen bg-[#F4F1EA] py-10 px-6">
    <!-- Tiêu đề -->
    <div class="text-center mb-8">
      <h2 class="text-3xl font-semibold text-gray-700 mb-2">📦 Danh sách đơn hàng</h2>
      <p class="text-gray-500">Theo dõi thông tin chi tiết các đơn hàng của bạn</p>
    </div>

    <!-- Bảng -->
    <div class="overflow-x-auto bg-white shadow-md rounded-2xl border border-gray-200">
      <table class="min-w-full text-sm text-left text-gray-600">
        <thead class="bg-[#EDE9E0] text-gray-700 uppercase text-[13px]">
          <tr>
            <th class="px-6 py-3">#</th>
            <th class="px-6 py-3">Tài khoản</th>
            <th class="px-6 py-3">Tên người dùng</th>
            <th class="px-6 py-3">Tên món</th>
            <th class="px-6 py-3">Giá</th>
            <th class="px-6 py-3">Thời gian</th>
          </tr>
        </thead>

        <tbody>
          <tr
            v-for="(item, index) in paginatedList"
            :key="item.billid"
            class="border-b border-gray-100 hover:bg-[#F9F7F3] transition duration-200"
          >
            <td class="px-6 py-4 font-medium text-gray-500">
              {{ (currentPage - 1) * itemsPerPage + index + 1 }}
            </td>
            <td class="px-6 py-4">{{ item.account.username }}</td>
            <td class="px-6 py-4">{{ item.name }}</td>
            <td class="px-6 py-4 flex items-center gap-3">
              <img
                v-if="item.menu?.img"
                :src="item.menu.img"
                alt="menu"
                class="w-10 h-10 rounded-lg object-cover border border-gray-200"
              />
              <span>{{ item.menu?.name }}</span>
            </td>
            <td class="px-6 py-4 font-semibold text-amber-700">
              {{ item.menu?.price?.toLocaleString('vi-VN') }}₫
            </td>
            <td class="px-6 py-4 text-gray-500">
              {{ new Date(item.time).toLocaleString() }}
            </td>
          </tr>

          <tr v-if="paginatedList.length === 0">
            <td colspan="6" class="px-6 py-8 text-center text-gray-400 italic">
              Không có đơn hàng nào để hiển thị
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- ✅ Phân trang -->
    <div class="flex justify-center items-center mt-6 gap-4">
      <button
        @click="prevPage"
        :disabled="currentPage === 1"
        class="px-4 py-2 rounded-lg text-sm bg-white border border-gray-300 text-gray-600 hover:bg-gray-100 disabled:opacity-50 disabled:cursor-not-allowed"
      >
        ◀ Trang trước
      </button>

      <span class="text-gray-600 font-medium">
        Trang {{ currentPage }} / {{ totalPages || 1 }}
      </span>

      <button
        @click="nextPage"
        :disabled="currentPage === totalPages || totalPages === 0"
        class="px-4 py-2 rounded-lg text-sm bg-white border border-gray-300 text-gray-600 hover:bg-gray-100 disabled:opacity-50 disabled:cursor-not-allowed"
      >
        Trang sau ▶
      </button>
    </div>
  </div>
</template>
