<script setup>
import { onMounted } from 'vue'
import axios from 'axios'


const token = localStorage.getItem("token")
if (token != null) {
    onMounted(async () => {
        // Khi component được render, code này chạy!
        const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/login/user`, {
            withCredentials: true
        })
        localStorage.setItem("name", res.data.name)
        localStorage.setItem("account", res.data.email)
        localStorage.setItem("type", "google")
        localStorage.removeItem("token")
        location.reload()
    })
}
</script>
