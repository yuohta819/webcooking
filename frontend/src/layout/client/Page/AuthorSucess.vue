<script setup>
import { onMounted } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router'

const route = useRoute()
const token = localStorage.getItem("token")

if (token == 'google') {
    const userRes = await axios.get(
        `${import.meta.env.VITE_API_URL_BACKEND}/login/user`,
        { withCredentials: true }
    )

    localStorage.setItem("name", userRes.data.name)
    localStorage.setItem("account", userRes.data.email)
    localStorage.setItem("type", "google")

    // Xoá token tạm để tránh chạy lại vòng lặp
    localStorage.removeItem("token")

    // Reload để cập nhật UI
    location.reload()
}
if (token && token != 'google') {
    console.log(token)
    onMounted(async () => {
        try {
            // 1️⃣ Gửi API để kiểm tra token hợp lệ hay không
            const checkRes = await axios.post(
                `${import.meta.env.VITE_API_URL_BACKEND}/auth/check-token`,
                { token },
                { withCredentials: true }
            )

            if (checkRes.data !== null) {
                // 2️⃣ Nếu token hợp lệ, gọi tiếp API lấy thông tin user
                const userRes = await axios.get(
                    `${import.meta.env.VITE_API_URL_BACKEND}/login/user`,
                    { withCredentials: true }
                )

                localStorage.setItem("name", userRes.data.name)
                localStorage.setItem("account", userRes.data.email)
                localStorage.setItem("type", "google")

                // Xoá token tạm để tránh chạy lại vòng lặp
                localStorage.removeItem("token")

                // Reload để cập nhật UI
                location.reload()
            } else {
                console.warn("Token không hợp lệ")
                localStorage.removeItem("token")
            }
        } catch (err) {
            console.error("Lỗi khi xác thực token:", err)
            localStorage.removeItem("token")
        }
    })
}
</script>
