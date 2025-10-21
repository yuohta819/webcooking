<template>
    <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-100 to-gray-200">
        <div class="bg-white shadow-2xl rounded-3xl p-10 w-full max-w-md border border-gray-100">
            <!-- Tiêu đề -->
            <h2 class="text-3xl font-extrabold text-center text-gray-800 mb-8">
                Đăng ký tài khoản
            </h2>

            <!-- Form -->
            <div class="space-y-5">
                <div>
                    <label class="block text-gray-700 mb-2 font-semibold">Họ và tên</label>
                    <input v-model="name" type="text" placeholder="Nhập họ và tên"
                        class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all" />
                </div>

                <div>
                    <label class="block text-gray-700 mb-2 font-semibold">Email</label>
                    <input v-model="email" type="email" placeholder="Nhập email"
                        class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all" />
                </div>

                <div>
                    <label class="block text-gray-700 mb-2 font-semibold">Số điện thoại</label>
                    <input v-model="phone" type="text" placeholder="Nhập số điện thoại"
                        class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all" />
                </div>

                <div>
                    <label class="block text-gray-700 mb-2 font-semibold">Mật khẩu</label>
                    <input v-model="password" type="password" placeholder="Nhập mật khẩu"
                        class="w-full border border-gray-300 rounded-xl px-4 py-3 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition-all" />
                </div>
            </div>

            <div class="mt-5">
                <!-- Nút đăng ký -->
                <button @click="handleSubmit"
                    class="mt-10  w-full bg-gradient-to-r from-blue-500 to-indigo-600 hover:from-blue-600 hover:to-indigo-700 text-white font-semibold py-3 rounded-xl shadow-lg transition-all duration-300 transform hover:scale-[1.03]">
                    Đăng ký
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import axios from 'axios'
import { ref } from 'vue'
import { useToast } from "vue-toastification"
import { useRouter } from 'vue-router'

const name = ref('')
const email = ref('')
const phone = ref('')
const password = ref('')
const toast = useToast()
const router = useRouter()
const roles = sessionStorage.getItem("canAdd")
if (roles === 'false') {
  toast.warning("Bạn không có quyền truy cập chức năng này !!!")
 router.push(`/${import.meta.env.VITE_APP_NAME}`)
}
async function handleSubmit() {
    if (!name.value || !email.value || !phone.value || !password.value) {
        toast.warning("Vui lòng điền đầy đủ thông tin.")
        return
    }

    try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/admin/save`, {
            params: {
                name: name.value,
                email: email.value,
                phone: phone.value,
                password: password.value
            }
        })

        if (response.data === '') {
            toast.success("Tạo tài khoản thành công!")
        } else {
            toast.error("Tài khoản đã tồn tại.")
        }
    } catch (error) {
        toast.error("Đã xảy ra lỗi, vui lòng thử lại.")
    }
}
</script>
