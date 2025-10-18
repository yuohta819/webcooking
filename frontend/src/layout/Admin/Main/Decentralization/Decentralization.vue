<template>
  <div class="max-w-4xl mx-auto bg-white rounded-xl shadow-lg p-6 mt-20">
    <h2 class="text-2xl font-semibold mb-4 text-gray-800">Bảng phân quyền</h2>

    <table class="min-w-full border border-gray-200 rounded-lg overflow-hidden">
      <thead class="bg-gray-100">
        <tr>
          <th class="py-3 px-4 text-left text-gray-700 font-semibold">Vai trò</th>
          <th class="py-3 px-4 text-center text-gray-700 font-semibold">Thêm</th>
          <th class="py-3 px-4 text-center text-gray-700 font-semibold">Sửa</th>
          <th class="py-3 px-4 text-center text-gray-700 font-semibold">Xóa</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(role, index) in data" :key="index" class="border-t border-gray-200 hover:bg-gray-50 transition">
          <!-- Cột tài khoản -->
          <td class="py-3 px-4 font-medium text-gray-800">{{ role.account }}</td>

          <!-- Cột quyền thêm -->
          <td class="text-center py-3">
            <label class="relative inline-flex items-center cursor-pointer">
              <input type="checkbox" :checked="role.canAdd" class="sr-only peer"   v-model="role.canAdd"
                @change="handleChange(role.account, role.canAdd, 'canAdd')"  />
              <div
                class="w-11 h-6 rounded-full bg-gray-300 transition-colors duration-300 ease-in-out peer-checked:bg-blue-500">
              </div>
              <div
                class="absolute left-0.5 top-0.5 bg-white w-5 h-5 rounded-full transition-transform duration-300 ease-in-out peer-checked:translate-x-5 shadow-sm">
              </div>
            </label>
          </td>
          <!-- Cột quyền sửa -->
          <td class="text-center py-3">
            <label class="relative inline-flex items-center cursor-pointer">
              <input type="checkbox" class="sr-only peer" :checked="role.canEddit"
                @change="handleChange(role.account, $event.target.checked, 'canEdit')" v-model="role.canEdit" />
              <div class="w-11 h-6 bg-gray-300 rounded-full peer peer-checked:bg-green-500
               transition-colors duration-300 ease-in-out"></div>
              <div class="absolute left-0.5 top-0.5 bg-white w-5 h-5 rounded-full
               transition-transform duration-300 ease-in-out
               peer-checked:translate-x-5 shadow-sm"></div>
            </label>
          </td>

          <!-- Cột quyền xóa -->
          <td class="text-center py-3">
            <label class="relative inline-flex items-center cursor-pointer">
              <input type="checkbox" class="sr-only peer" :checked="role.canDelete"
                @change="handleChange(role.account, $event.target.checked, 'canDelete')" v-model="role.canDelete" />
              <div class="w-11 h-6 bg-gray-300 rounded-full peer peer-checked:bg-red-500
               transition-colors duration-300 ease-in-out"></div>
              <div class="absolute left-0.5 top-0.5 bg-white w-5 h-5 rounded-full
               transition-transform duration-300 ease-in-out
               peer-checked:translate-x-5 shadow-sm"></div>
            </label>
          </td>
        </tr>

      </tbody>
    </table>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import axios from "axios"
const data = ref([])
onMounted(async () => {
  const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/account/decentralization`)
  data.value = res.data.map(role => ({
    ...role,
    canAdd: Boolean(role.canAdd),
    canEdit: Boolean(role.canEdit),
    canDelete: Boolean(role.canDelete)

  }))
  console.log(res.data)

})
async function handleChange(name, per, type) {
  const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/account/per`, {
    type: type,
    statusType: per,
    name: name,
  })
}

</script>

<style scoped>
table {
  border-collapse: collapse;
  width: 100%;
}

th,
td {
  border-bottom: 1px solid #e5e7eb;
}
</style>
