<script setup>

import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRouter } from 'vue-router';
import { computed } from 'vue'
import { useToast } from "vue-toastification";
const name = localStorage.getItem("account")
const data = ref([])
const toast = useToast();
const route = useRouter()
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
const productIds = computed(() => {
    return data.value.map(item => item.menu.id);
});
const productMoney = computed(() => {

    return data.value.map(item => item.menu.price * item.total);
});
const account = localStorage.getItem("account")
async function handleBill() {
    if (!account) {
        toast.warning("Please login to your account.")
        return;
    }
    const res = await axios.post("http://localhost:8080/status/save", {
        account: name,
        status: productIds.value,   // mảng ID
        money: productMoney.value
    })
    toast.success("Order successful!")
    setTimeout(() => {
        route.push('/').then(() => {
            window.location.reload()
        })
    }, 2000)


}

async function removeItem(id) {
    const res = await axios.get(`http://localhost:8080/api/delete/${id}`)
    if (res) {
        toast.success("Deleted successfully!")
    }
    setTimeout(() => {
        location.reload()
    }, 1000)
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
                        <img :src="item.menu.img" alt="menu-img"
                            style="width: 85px !important; height: 80px !important;"
                            class="h-20 rounded-lg object-cover mx-auto" />
                    </td>
                    <td class="p-4 text-gray-700 text-[18px]" style="color: #5C6574;">{{ item.menu.name }}</td>
                    <td class="p-4 font-semibold text-gray-900 text-[20px]">${{ Math.floor(item.menu.price) }}</td>
                    <td class="p-4">
                        <div class="flex items-center justify-center space-x-2">
                            <button @click="handleCount(item, 'desc')"
                                class="border px-2 py-1 rounded hover:bg-gray-200">−</button>
                            <div class="min-w-[30px] px-7 py-1 font-bold"
                                style="border: 1px solid #D2D2D1; color: #EB0029; margin-right: 0;">{{ item.total }}
                            </div>
                            <button @click="handleCount(item, 'asc')"
                                class="border px-2 py-1 rounded hover:bg-gray-200">+</button>
                        </div>
                    </td>
                    <td class="p-4 font-semibold text-gray-900">
                        ${{ Math.floor(item.menu.price) * item.total }}
                    </td>
                    <td class="p-4" style="cursor: pointer;" @click="removeItem(item.menu.id)">
                        <button style="cursor: pointer;" class="text-red-500 hover:scale-110 transition">
                            <i class="fa-solid fa-trash"></i>
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="max-w-2xl mx-auto py-30">
        <h2 class="text-2xl font-semibold text-gray-700 amb-4">Cart Totals</h2>
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
                <div class="text-gray-900 font-semibold">${{ grandTotal }}</div>

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
                <div class="text-red-600 font-bold text-lg">${{ grandTotal + 20 }}</div>
            </div>
        </div>

        <!-- Checkout button -->
        <div class="box-5">
            <div class="text-center relative pb-5 mt-5">
                <button class="px-5 py-3 w-full" @click="handleBill">
                    <span class="relative z-10 text-[14px] font-bold">Order</span>
                    <div class="box-1"></div>
                    <div class="box-2"></div>
                </button>
            </div>
        </div>
    </div>
</template>
<style scoped>
.box-5 button {
    background: #EB0029;
    color: white;
    height: 50px;
    position: relative;
    overflow: hidden;
    z-index: 5;
    cursor: pointer;
}

.box-5 button button,
.box-5 button i {
    position: relative;
    z-index: 10;
    cursor: pointer;
}

.box-5 .box-1 {
    position: absolute;
    width: 100%;
    height: 50%;
    top: 0;
    left: 0;
    overflow: hidden;
}

.box-5 .box-1::before {
    content: '';
    position: absolute;
    top: 0;
    right: -100%;
    width: 100%;
    height: 100%;
    background: #FC781A;
    transition: right 0.5s ease;
}

.box-5 .box-2 {
    position: absolute;
    width: 100%;
    height: 50%;
    bottom: 0;
    left: 0;
    overflow: hidden;
}

.box-5 .box-2::before {
    content: '';
    position: absolute;
    bottom: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: #FC781A;
    transition: left 0.5s ease;
}

.box-5 button:hover {
    border: 1px solid red;
}

.box-5 button:hover .box-1::before {
    right: 0;
}

.box-5 button:hover .box-2::before {
    left: 0;
}

.img-2 {
    background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/breadcumb.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
}
</style>
