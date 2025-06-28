<script setup>

import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { computed } from 'vue'
import { useToast } from "vue-toastification";
const router = useRoute()
const name = localStorage.getItem("account")
const data = ref([])
const count = ref(1)
const toast = useToast();
onMounted(async () => {
    const res = await axios.get(`http://localhost:8080/api/infor/${name}`)
    data.value = res.data

})
const grandTotal = computed(() => {
    return data.value.reduce((sum, item) => {
        return sum + Math.floor(item.menu.price) * item.total
    }, 0)
})
function handleCount(item, name) {
    if (name === "asc") {
        item.total++
    }
    if (item.total > 1) {
        if (name === "desc") {
            item.total--
        }
    }
}
async function removeItem(id) {
    const res = await axios.get(`http://localhost:8080/api/delete/${id}`)
    if (res) {
        toast.success("Deleted successfully!")
    }
    setTimeout(() => {
        location.reload()
    },1000)
}
</script>
<template>
    <div class="img-2">
        <div class="text-center py-40">
            <div style="color: white; text-transform: uppercase;" class="text-[80px] font-black mb-3">Shop details</div>
            <div style="color: white; " class="font-bold">Home <span class="ml-3">/</span> <span class="ml-3 font-bold"
                    style="color: red;">Shop details</span></div>
        </div>
    </div>
    <div class="overflow-x-auto  pt-26 px-26">
        <table class="min-w-full text-sm text-center" style="border: 1px solid #D2D2D1;">
            <thead class="bg-gray-100 font-semibold text-gray-800">
                <tr>
                    <th class=" py-7  font-black text-[16px]">Menu Image</th>
                    <th class=" font-black text-[16px]">Menu Name</th>
                    <th class=" font-black text-[16px]">Price</th>
                    <th class=" font-black text-[16px]">Quantity</th>
                    <th class=" font-black text-[16px]">Total</th>
                    <th class=" font-black text-[16px]">Remove</th>
                </tr>
            </thead>
            <tbody class="bg-white">
                <tr v-for="(item, index) in data" :key="index">
                    <td class="xl:p-4 py-5 xl:block hidden">
                        <img :src="item.menu.img" alt="menu-img" style="width: 85px !important; height: 80px !important;" class="h-20 rounded-lg object-cover mx-auto" />
                    </td>
                    <td class="p-4 text-gray-700 text-[18px]" style="color: #5C6574;">{{ item.menu.name }}</td>
                    <td class="p-4 font-semibold text-gray-900 text-[20px]">${{ Math.floor(item.menu.price) }}</td>
                    <td class="p-4">
                        <div class="flex items-center justify-center space-x-2">
                            <button @click="handleCount(item, 'desc')"
                                class="border px-2 py-1 rounded hover:bg-gray-200">−</button>
                            <div class="min-w-[30px] px-7 py-1 font-bold" style="border: 1px solid #D2D2D1; color: #EB0029;">{{ item.total }}</div>
                            <button @click="handleCount(item, 'asc')"
                                class="border px-2 py-1 rounded hover:bg-gray-200">+</button>
                        </div>
                    </td>
                    <td class="p-4 font-semibold text-gray-900">
                        ${{ Math.floor(item.menu.price) * item.total }}
                    </td>
                    <td class="p-4" style="cursor: pointer;" @click="removeItem(item.menu.id)" >
                        <button  style="cursor: pointer;" class="text-red-500 hover:scale-110 transition">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="max-w-2xl mx-auto py-30">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Cart Totals</h2>
        <div class="border grid grid-cols-2 ">
            <!-- Left -->
            <div class="p-6 space-y-6 text-left font-semibold text-gray-700">
                <div>Cart Subtotal</div>
                <div>Shipping and Handling</div>
                <div>Order Total</div>
            </div>

            <!-- Right -->
            <div class="p-6 space-y-6">
                <!-- Subtotal -->
                <div class="text-gray-900 font-semibold">$54</div>

                <!-- Shipping options -->
                <div class="space-y-2">
                    <label class="flex items-center space-x-2">
                        <input type="radio" value="0" v-model="shipping" class="accent-red-600 w-4 h-4" />
                        <span class="font-semibold text-sm text-gray-800">Free shipping</span>
                    </label>

                    <label class="flex items-center space-x-2">
                        <input type="radio" value="5" v-model="shipping" class="accent-red-600 w-4 h-4" />
                        <div class="font-semibold text-sm text-gray-800">Flat rate</div>
                    </label>

                    <p class="text-sm text-gray-500">Shipping options will be updated during checkout.</p>
                    <a href="#" class="text-sm text-black font-semibold underline">Change address</a>
                </div>

                <!-- Order total -->
                <div class="text-red-600 font-bold text-lg">${{ grandTotal }}</div>
            </div>
        </div>

        <!-- Checkout button -->
        <div class="mt-6">
            <button class="bg-red-600 hover:bg-red-700 text-white font-semibold py-3 px-6 w-full">
                Proceed to checkout
            </button>
        </div>
    </div>
</template>
<style scoped>
.img-2 {
    background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/breadcumb.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
}
</style>
