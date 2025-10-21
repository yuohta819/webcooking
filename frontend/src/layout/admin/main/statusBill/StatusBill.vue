
<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import axios from "axios";

interface StatusItem {
  statusid: string;
  billid: string;
  money: string;
  name: string;
  status1: boolean;
  status2: boolean;
  status3: boolean;
  is_deleted_status: boolean; // 🔹 thêm trường mới
}

const list = ref<StatusItem[]>([]);
const currentPage = ref(1);
const itemsPerPage = 5;
let account = localStorage.getItem("account")
if (account) {
  account = sessionStorage.getItem("account")
}
// Gọi API
onMounted(async () => {
  const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/status/find`);
  list.value = res.data;
});

// Cập nhật trạng thái
async function handleStatus(item: string, status: boolean, index: number, type: string) {
  try {
    console.log(item)
    await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/status/update`, {
      id: item.statusid,
      check: !status,
      type,
      account: item.accountid,
      idproduct: item.idproduct,
      name: item.name,
    });



    const globalIndex = (currentPage.value - 1) * itemsPerPage + index;
    switch (type) {
      case "status1":
        list.value[globalIndex].status1 = !status;
        break;
      case "status2":
        list.value[globalIndex].status2 = !status;
        break;
      case "status3":
        list.value[globalIndex].status3 = !status;
        break;
      case "delete": // 🔹 xử lý toggle xóa
        list.value[globalIndex].is_deleted_status = !status;
        break;
    }
  } catch (err) {
    console.error(err);
  }
}

// Phân trang
const paginatedList = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage;
  return list.value.slice(start, start + itemsPerPage);
});
const totalPages = computed(() => Math.ceil(list.value.length / itemsPerPage));

function prevPage() {
  if (currentPage.value > 1) currentPage.value--;
}
function nextPage() {
  if (currentPage.value < totalPages.value) currentPage.value++;
}
</script>

<template>
  <div class="min-h-screen bg-[#F4F1EA] py-10 px-6">
    <div class="max-w-6xl mx-auto bg-white shadow-lg rounded-2xl p-8 border border-gray-200">
      <h2 class="text-3xl font-bold text-gray-700 mb-20 text-center flex items-center justify-center gap-2">
        <i class="fa-solid fa-box text-blue-500"></i> Quản lý trạng thái đơn hàng
      </h2>

      <div class="overflow-x-auto mt-10">
        <table class="min-w-full border-collapse">
          <thead>
            <tr class="bg-gray-100 text-gray-700 uppercase text-sm font-semibold">
              <th class="py-3 px-4 text-left">Mã đơn</th>
              <th class="py-3 px-4 text-left">Khách hàng</th>
              <th class="py-3 px-4 text-center">Số tiền</th>
              <th class="py-3 px-4 text-center">Xác nhận</th>
              <th class="py-3 px-4 text-center">Thực hiện</th>
              <th class="py-3 px-4 text-center">Giao hàng</th>
              <th class="py-3 px-4 text-center">Trạng thái xóa</th> <!-- 🔹 thêm cột -->
            </tr>
          </thead>

          <tbody>
            <tr v-for="(item, index) in paginatedList" :key="index"
              class="border-t border-gray-200 hover:bg-gray-50 transition">
              <td class="py-3 px-4 font-medium text-gray-800">{{ item.billid }}</td>
              <td class="py-3 px-4 text-gray-700">{{ item.name }}</td>
              <td class="py-3 px-4 text-center text-gray-800 font-semibold">
                {{ Number(item.money).toLocaleString() }} đ
              </td>

              <!-- Trạng thái 1 -->
              <td class="py-3 px-4 text-center">
                <button @click="handleStatus(item, item.status1, index, 'status1')" :class="[
                  'px-4 py-2 rounded-lg font-medium shadow-sm transition',
                  item.status1
                    ? 'bg-green-100 text-green-700 hover:bg-green-200'
                    : 'bg-gray-200 text-gray-600 hover:bg-gray-300'
                ]">
                  {{ item.status1 ? 'Đã xác nhận' : 'Chờ xác nhận' }}
                </button>
              </td>

              <!-- Trạng thái 2 -->
              <td class="py-3 px-4 text-center">
                <button @click="handleStatus(item, item.status2, index, 'status2')" :class="[
                  'px-4 py-2 rounded-lg font-medium shadow-sm transition',
                  item.status2
                    ? 'bg-blue-100 text-blue-700 hover:bg-blue-200'
                    : 'bg-gray-200 text-gray-600 hover:bg-gray-300'
                ]">
                  {{ item.status2 ? 'Đã thực hiện' : 'Chờ thực hiện' }}
                </button>
              </td>

              <!-- Trạng thái 3 -->
              <td class="py-3 px-4 text-center">
                <button @click="handleStatus(item, item.status3, index, 'status3')" :class="[
                  'px-4 py-2 rounded-lg font-medium shadow-sm transition',
                  item.status3
                    ? 'bg-orange-100 text-orange-700 hover:bg-orange-200'
                    : 'bg-gray-200 text-gray-600 hover:bg-gray-300'
                ]">
                  {{ item.status3 ? 'Đã giao' : 'Đang giao' }}
                </button>
              </td>

              <!-- 🔹 Trạng thái xóa -->
              <td class="py-3 px-4 text-center">
                <button @click="handleStatus(item, item.is_deleted_status, index, 'delete')" :class="[
                  'px-4 py-2 rounded-lg font-medium shadow-sm transition',
                  item.is_deleted_status
                    ? 'bg-red-100 text-red-700 hover:bg-red-200'
                    : 'bg-gray-200 text-gray-600 hover:bg-gray-300'
                ]">
                  {{ item.is_deleted_status ? 'Đã xóa' : 'Còn hoạt động' }}
                </button>
              </td>
            </tr>

            <tr v-if="paginatedList.length === 0">
              <td colspan="7" class="text-center py-6 text-gray-500">Không có đơn hàng nào</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- Phân trang -->
      <div class="flex justify-center items-center mt-6 gap-3">
        <button @click="prevPage" :disabled="currentPage === 1"
          class="px-4 py-2 rounded-lg bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium disabled:opacity-50">
          Trước
        </button>

        <span class="text-gray-700 font-medium">
          Trang {{ currentPage }} / {{ totalPages }}
        </span>

        <button @click="nextPage" :disabled="currentPage === totalPages"
          class="px-4 py-2 rounded-lg bg-gray-100 hover:bg-gray-200 text-gray-700 font-medium disabled:opacity-50">
          Sau
        </button>
      </div>
    </div>
  </div>
</template>

<style scoped>
table th,
table td {
  white-space: nowrap;
}
</style>