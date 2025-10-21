<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { useToast } from "vue-toastification"
import { useRoute, useRouter } from 'vue-router'

const toast = useToast()
const router = useRoute()
const route = useRouter()
const name = ref("")
const img = ref("")
const decribe = ref("")
const price = ref("")
const data = ref([])
const roles1 = sessionStorage.getItem("canAdd")
const roles2 = sessionStorage.getItem("canEdit")
if (roles2 === 'false') {
    toast.warning("Bạn không có quyền truy cập chức năng này !!!")
    route.push(`/${import.meta.env.VITE_APP_NAME}n/edit`)
}
onMounted(async () => {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/detail/${router.params.id}`)
    data.value = res.data
})

async function handleSubmit() {
    const fields = [
        { ref: name, key: 'name' },
        { ref: img, key: 'img' },
        { ref: decribe, key: 'decribe' },
        { ref: price, key: 'price' }
    ]

    fields.forEach(({ ref, key }) => {
        if (ref.value === "") ref.value = data.value[0][key]
    })

    const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/api/edit`, {
        id: router.params.id,
        img: img.value,
        name: name.value,
        describe: decribe.value,
        price: price.value
    })

    if (res.data === "yes") {
        toast.success("Product updated successfully!")
    }
}

function handleImg(v) { img.value = v }
function handleName(v) { name.value = v }
function handleDescribe(v) { decribe.value = v }
function handlePrice(v) { price.value = v }
</script>

<template>
    <div v-if="router.params.id" class="py-16 bg-gray-50 min-h-screen" style="background-color: #F4F1EA;">
        <div class="max-w-4xl mx-auto bg-white rounded-2xl shadow-lg p-8 border border-gray-100">
            <h2 class="text-3xl font-semibold text-gray-700 mb-8 text-center">
                ✏️ Edit Product
            </h2>

            <div v-for="item in data" :key="item.id" class="space-y-5">
                <div>
                    <label class="block text-gray-600 mb-1 font-medium">Image URL</label>
                    <input type="text"
                        class="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                        v-model="item.img" @input="handleImg($event.target.value)" placeholder="Enter image link..." />
                </div>

                <div>
                    <label class="block text-gray-600 mb-1 font-medium">Product Name</label>
                    <input
                        class="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                        v-model="item.name" @input="handleName($event.target.value)"
                        placeholder="Enter product name..." />
                </div>

                <div>
                    <label class="block text-gray-600 mb-1 font-medium">Description</label>
                    <textarea rows="3"
                        class="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400 resize-none"
                        v-model="item.describe" @input="handleDescribe($event.target.value)"
                        placeholder="Enter product description..."></textarea>
                </div>

                <div>
                    <label class="block text-gray-600 mb-1 font-medium">Price</label>
                    <input type="number"
                        class="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                        v-model="item.price" @input="handlePrice($event.target.value)" placeholder="Enter price..." />
                </div>
                <button @click="handleSubmit"
                    class="w-full cursor-pointer py-3 mt-4 bg-red-400 hover:bg-sky-300 text-sky-800 font-semibold rounded-xl shadow-sm transition-all duration-200 flex items-center justify-center gap-2 border border-sky-300">
                    <i class="fa-solid fa-check-circle text-sky-700"></i>
                    Save Changes
                </button>

            </div>
        </div>
    </div>
</template>

<style scoped>
/* Nhẹ nhàng hơn với background gradient */
body {
    background: linear-gradient(to bottom right, #e0f2fe, #f8fafc);
}
</style>
