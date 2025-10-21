
<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useToast } from "vue-toastification";
import { useRouter } from 'vue-router';

const toast = useToast();
const name = ref("")
const img = ref("")        // URL trả về từ Cloudinary
const describe = ref("")
const price = ref("")
const isUploading = ref(false)
const roles = sessionStorage.getItem("canAdd")
const router  = useRouter()
if (roles === 'false') {
  toast.warning("Bạn không có quyền truy cập chức năng này !!!")
 router.push(`/${import.meta.env.VITE_APP_NAME}`)
}
// ✅ Upload ảnh khi người dùng chọn file
async function handleImageUpload(event) {
  const file = event.target.files[0]
  if (!file) return

  const formData = new FormData()
  formData.append("file", file)

  isUploading.value = true
  try {
    const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/api/upload-image`, formData, {
      headers: { "Content-Type": "multipart/form-data" },
    })
    img.value = res.data.url // URL trả về từ backend
    toast.success("Tải ảnh lên thành công!")
  } catch (err) {
    toast.error("Lỗi khi tải ảnh lên!")
  } finally {
    isUploading.value = false
  }
}

// ✅ Submit sản phẩm
async function handleSubmit() {
  try {
    const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/api/create`, {
      img: img.value,
      name: name.value,
      describe: describe.value,
      price: price.value
    })
    if (res.data == '') {
      toast.success("Tạo sản phẩm thành công!")
    }
  } catch (err) {
    toast.error("Tạo sản phẩm thất bại!")
  }
}
</script>

<template>
  <div class="bg-gradient-to-br flex justify-center pt-20 pb-20" style="background-color: #F4F1EA;">
    <div class="bg-white shadow-xl rounded-2xl p-10 w-full max-w-lg border border-gray-200">
      <h2 class="text-3xl font-semibold text-gray-700 text-center mb-8 flex items-center justify-center gap-2">
        <i class="fa-solid fa-box-open text-gray-700"></i> Add Product
      </h2>

      <div class="flex flex-col gap-6">

        <!-- Upload ảnh -->
        <div>
          <input type="file" accept="image/*" @change="handleImageUpload"
            class="block w-full text-gray-700 border border-gray-300 rounded-lg p-2 cursor-pointer" />
          <div v-if="isUploading" class="text-blue-500 text-sm mt-2 animate-pulse">Đang tải ảnh lên...</div>

          <div v-if="img" class="mt-4 flex justify-center">
            <img :src="img" alt="Preview" class="rounded-xl shadow-md max-h-48 object-cover" />
          </div>
        </div>

        <input
          class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none transition"
          placeholder="Product Name" v-model="name" />

        <textarea rows="3"
          class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none transition resize-none"
          placeholder="Product Description" v-model="describe"></textarea>

        <input
          class="w-full p-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-400 focus:outline-none transition"
          placeholder="Price" v-model="price" />

        <button @click="handleSubmit"
          class="w-full py-3 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg shadow-md transition duration-300 flex items-center justify-center gap-2">
          <i class="fa-solid fa-check-circle text-white text-lg"></i>
          Save Product
        </button>
      </div>
    </div>
  </div>
</template>