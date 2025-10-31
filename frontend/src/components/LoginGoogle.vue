<script setup>
import { useRoute, useRouter } from 'vue-router'
import { onMounted } from 'vue'

const route = useRoute()
const router = useRouter()

onMounted(() => {
    const token = route.query.token
    const email = route.query.email
    const name = route.query.name

    if (token) {
        localStorage.setItem("name", name);
        localStorage.setItem("account", email);
        localStorage.setItem("token", token);
        setTimeout(() => {
            router.push("/").then(() => {
                location.reload();
            });
        }, 1000);
    } else {
        alert('❌ Không nhận được token!')
    }
})

</script>