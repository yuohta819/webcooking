<script setup>

import axios from 'axios';
import { onMounted, ref, onUnmounted } from 'vue';
import ContactSection from './ContactSection.vue';
import { useToast } from "vue-toastification";
import 'animate.css';
import { computed } from 'vue'
import { emitter } from "../../../components/eventBus"; // 👈 import trực tiếp

let token = localStorage.getItem("token") || sessionStorage.getItem("token")

const toast = useToast();
const data = ref([])

const API_URL = import.meta.env.VITE_API_URL_BACKEND;
const checkcart = ref(null)
const name = ref('')
const check = ref(false)
const showPanel = ref(false)
const showCart = ref(false)
const activeDropdown = ref('')
let type1 = localStorage.getItem("name") || sessionStorage.getItem("name")
let account = localStorage.getItem("account") || sessionStorage.getItem("account")
let type = localStorage.getItem("type") || sessionStorage.getItem("type")
// ✅ Hàm kiểm tra token
onMounted(async () => {
    if (token) {
        try {
            let account = localStorage.getItem("account") || sessionStorage.getItem("account")
            // Gọi API check token
            const res = await axios.get(`${API_URL}/check/token`, {
                headers: {
                    Authorization: `Bearer ${token}`, // ✅ Gửi token lên backend
                },
                params: { account }, // ✅ Gửi dưới dạng query param
            });

            // ✅ Nếu backend trả về 200 => token hợp lệ
            return { valid: true, data: res.data };

        } catch (err) {
            toast.warning("Token expired or invalid!!!")
            localStorage.clear()
            sessionStorage.clear()
        }
    }
})
async function loadCart() {
    try {
        const res = await axios.get(
            `${import.meta.env.VITE_API_URL_BACKEND}/api/infor`,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        )
        if (res.data === '') {
            // toast.error("Token expired or invalid!!!")
            localStorage.clear()
            sessionStorage.clear()

        }
        data.value = res.data.cart
    } catch (err) {
        console.error("Lỗi khi tải giỏ hàng:", err)
    }
}
// Lắng nghe sự kiện khi có sản phẩm mới thêm
onMounted(() => {
    loadCart();
    emitter.on("cart-updated", loadCart);
});

onUnmounted(() => {
    emitter.off("cart-updated", loadCart);
});
onMounted(loadCart)

const grandTotal = computed(() => {
    loadCart()
    return data.value.reduce((sum, item) => {
        return sum + Math.floor(item.menu.price) * item.total
    }, 0)
})

if (type1) {
    name.value = type1;
    if (account && type === "google") {
        onMounted(async () => {
            const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/account/save`, {
                params: {
                    name: type1,
                    account: account,
                    phone: "",
                    password: 'google'
                }
            })
            if (account) {
                localStorage.setItem("accountid", res.data)
            } else {
                sessionStorage.setItem("accountid", res.data)
            }
        })
    }
}
const goToCart = () => {

    if (token) {
        // ✅ Đã đăng nhập
        router.push("/cart");
    } else {
        // ❌ Chưa đăng nhập
        toast.error("⚠️ Please login to view cart!");
    }
};
function handleViewCart() {
    if (data.value.length === 0) {
        toast.warning("🛒 Your cart is empty!");
    } else {
        window.location.href = "/cart";
    }
}
function handleCart() {
    if (checkcart.value) {
        checkcart.value = false // chuyển sang fadeOut
        setTimeout(() => {
            showCart.value = false // ẩn hẳn sau khi chạy xong animation
        }, 500) // 500ms là thời gian animation animate.css
    } else {
        showCart.value = true // hiện lại
        checkcart.value = true // áp dụng fadeIn
    }
}
function handleCheck() {
    if (check.value) {
        check.value = false // chuyển sang fadeOut
        setTimeout(() => {
            showPanel.value = false // ẩn hẳn sau khi chạy xong animation
        }, 500) // 500ms là thời gian animation animate.css
    } else {
        showPanel.value = true // hiện lại
        check.value = true // áp dụng fadeIn
    }
}

const menuItems = [
    { label: 'Home' },
    { label: 'About Us' },
    { label: 'Shop' }, // hiển thị chấm
    { label: 'Pages' },
    { label: 'Blog' },
    { label: 'Contact Us' }
];
const images = [
    'https://www.ex-coders.com/php-template/fresheat/assets/img/header/01.jpg',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/header/02.jpg',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/header/02.jpg',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/header/04.jpg',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/header/05.jpg',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/header/06.jpg'
]
async function handleLogout() {
    try {
        await axios.get(
            `${import.meta.env.VITE_API_URL_BACKEND}/account/delete/token`,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            }
        );
    } catch (err) {
        console.warn("Lỗi khi logout:", err);
    } finally {
        // Xóa dữ liệu cục bộ
        localStorage.clear();
        sessionStorage.clear();
        location.reload();
    }
}

function handleDropdown(type, isEnter) {
    activeDropdown.value = isEnter ? type : ''
}



</script>

<template>
    <div v-if="showPanel" :class="[
        'animate__animated',
        check ? 'animate__fadeInRight' : 'animate__fadeOutRight'
    ]"
        class="bg-white p-6 rounded-lg shadow-md max-w-md mx-auto space-y-6 fixed top-0 right-0 z-4 overflow-y-auto max-h-screen">
        <!-- Header -->
        <div class="flex justify-between items-center">
            <div class="flex items-center space-x-2">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/logo.svg" alt="Logo"
                    class="h-8" />
                <div>
                    <h1 class="text-xl font-bold text-gray-800">FRESHEAT</h1>
                    <span class="text-xs text-gray-500 uppercase">Respondent</span>
                </div>
            </div>
            <button class="text-white bg-red-500 p-2 rounded-full " @click="handleCheck">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-8" fill="none" viewBox="0 0 24 24"
                    stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                </svg>
            </button>
        </div>

        <!-- Description -->
        <p class="text-gray-700 text-sm leading-relaxed">
            This involves interactions between a business and its customers. It's about meeting
            customers' needs and resolving their problems. Effective customer service is crucial.
        </p>

        <!-- Gallery -->
        <div class="grid grid-cols-3 gap-2">
            <img v-for="(img, i) in images" :key="i" :src="img" class="w-full h-24 object-cover rounded-lg" />
        </div>
        <div class="space-y-2">
            <div v-for="(item, index) in menuItems" :key="index"
                class="flex justify-between items-center border-b pb-2 relative group cursor-pointer">
                <span class="font-semibold text-gray-800 text-[15px]">
                    {{ item.label }}
                </span>

                <!-- Plus Icon -->
                <svg class="w-4 h-4 text-black" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M12 5v14m7-7H5" />
                </svg>

                <!-- Dot (only for Shop) -->
                <span v-if="item.label === 'Shop'"
                    class="absolute right-[32px] top-1/2 -translate-y-1/2 w-2 h-2 bg-orange-500 rounded-full"></span>
            </div>
        </div>


        <!-- Contact Info -->
        <div class="space-y-3">
            <h2 class="text-lg font-bold text-gray-800">Contact Info</h2>

            <div class="flex items-start space-x-2 text-sm text-gray-600">
                <svg class="h-5 w-5 text-red-500 mt-1" fill="currentColor" viewBox="0 0 24 24">
                    <path
                        d="M12 2C8.13 2 5 5.13 5 9c0 5.25 7 13 7 13s7-7.75 7-13c0-3.87-3.13-7-7-7zm0 9.5c-1.38 0-2.5-1.12-2.5-2.5S10.62 6.5 12 6.5 14.5 7.62 14.5 9 13.38 11.5 12 11.5z" />
                </svg>
                <span>Main Street, Melbourne, Australia</span>
            </div>

            <div class="flex items-start space-x-2 text-sm text-gray-600">
                <svg class="h-5 w-5 text-red-500 mt-1" fill="currentColor" viewBox="0 0 24 24">
                    <path
                        d="M20 4H4c-1.1 0-2 .9-2 2v12c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zM4 8l8 5 8-5v2l-8 5-8-5V8z" />
                </svg>
                <span>info@fresheat.com</span>
            </div>

            <div class="flex items-start space-x-2 text-sm text-gray-600">
                <svg class="h-5 w-5 text-red-500 mt-1" fill="currentColor" viewBox="0 0 24 24">
                    <path
                        d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-7 14H8v-2h4v2zm0-4H8v-2h4v2zm4 4h-2v-6h2v6z" />
                </svg>
                <span>Mod-Friday, 09am -05pm</span>
            </div>
        </div>
    </div>
    <div class="flex w-[100%] h-38">
        <div style="background: white;" class="px-10 py-10">
            <img class="sm:w-[200px] h-20"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/logo.svg" width="" alt="">
        </div>
        <div style="width: 99%; background-color: #010F1C;">
            <div class="flex px-11 img xl:justify-between py-3 justify-end items-center"
                style="color: white; background-color: #EB0029; ">
                <span>
                    <i class="fa-solid fa-clock"></i>
                    09:00 am - 06:00 pm
                </span>
                <div class="pr-10 xl:block hidden" style="width: 22%;">
                    <ul class="flex justify-around" style="margin-bottom: 0  !important;">
                        <li>Follow Us:</li>
                        <li><i class="fa-brands fa-facebook-f"></i></li>
                        <li><i class="fa-brands fa-twitter"></i></li>
                        <li><i class="fa-brands fa-youtube"></i></li>
                        <li><i class="fa-brands fa-linkedin-in"></i></li>
                    </ul>
                </div>
            </div>
            <div style="color: white;" class="flex md:px-11 px-5 mt-7 justify-between items-center">
                <span class="2xl:flex items-center hidden" style="width: 70.8%; height: 36px;">
                    <ul class="flex gap-8 font-bold  relative" style="margin-bottom: 0;">
                        <li class="relative" @mouseover=" handleDropdown('home', true)"
                            @mouseleave=" handleDropdown('home', false)">
                            <div class="flex items-center h-[70px]">
                                Home
                                <i class="ml-3 fa-solid fa-plus"></i>
                            </div>
                            <div v-if="activeDropdown === 'home'"
                                class="absolute w-100  top-15 left-10 animate__animated z-2 animate__slideInUp animate__faster"
                                style="background-color: white;">
                                <a href="/aboutus" @mouseleave=" handleDropdown('home', false)">
                                    <img width="800" class="px-6 py-7"
                                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/header/home1.jpg"
                                        alt="">
                                </a>
                                <div class="text-center py-2" style="color: black;">Home</div>
                            </div>
                        </li>
                        <li @mouseover=" handleDropdown('about', true)" @mouseleave=" handleDropdown('about', false)">
                            <div class="flex items-center h-[70px]">
                                About Us
                                <i class="ml-3 fa-solid fa-plus"></i>
                            </div>
                            <div class="absolute  top-16 left-30 animate__animated z-2 animate__slideInUp  animate__faster"
                                v-if="activeDropdown === 'about'" style="background-color: white;">
                                <a href="/aboutus">
                                    <div class="content w-50 px-4 py-3">
                                        About Us
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li @mouseover=" handleDropdown('shop', true)" class="flex items-center"
                            @mouseleave=" handleDropdown('shop', false)">
                            <div>
                                Shop
                            </div><i class="ml-3 fa-solid fa-plus"></i>
                            <div class="absolute  top-16 left-60 animate__animated z-2 animate__slideInUp animate__faster"
                                v-if="activeDropdown === 'shop'" style="background-color: white;">
                                <a href="/shop">
                                    <div class="content w-50 px-4 py-3">
                                        Shop
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li @mouseover=" handleDropdown('add', true)" class="flex items-center"
                            @mouseleave=" handleDropdown('add', false)">
                            <div>
                                Page
                            </div><i class="ml-3 fa-solid fa-plus"></i>
                            <div class="absolute  top-16 left-90 animate__animated z-2 animate__slideInUp animate__faster"
                                v-if="activeDropdown === 'add'" style="background-color: white;">
                                <a href="/account/login">
                                    <div class="content w-50 px-4 py-3">
                                        Account
                                    </div>
                                </a>
                                <a href="/register">
                                    <div class="content w-50 px-4 py-3">
                                        Register
                                    </div>
                                </a>
                                <a href="#" @click.prevent="goToCart">
                                    <div class="content w-50 px-4 py-3">
                                        Cart
                                    </div>
                                </a>
                                <a href="/menu">
                                    <div class="content w-50 px-4 py-3">
                                        Menu
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li>
                            <div class="flex items-center h-[70px]">
                                Contact Us <i class=" ml-3 fa-solid fa-plus">
                                </i>
                            </div>
                        </li>
                    </ul>
                </span>
                <!-- Nếu chưa đăng nhập -->
                <div v-if="!account" class="flex items-center">
                    <a href="/account/login">
                        <button class="px-4 py-2 bg-blue-600  text-white rounded-md font-medium transition">
                            Login
                        </button>
                    </a>
                </div>

                <!-- Nếu đã đăng nhập -->
                <div v-else class="sm:w-[400px] flex w-70 xl:pr-8 items-center text-[#5C6574]">
                    <!-- Tên và Logout -->
                    <div class="flex md:w-120 xl:justify-around justify-end mr-3">
                        <a href="/infor/step">
                            <div class="text-[18px] mr-3 font-semibold hover:text-blue-600 transition">
                                Xin chào, {{ type1 }}
                            </div>
                        </a>
                        <div class="text-[18px] cursor-pointer hover:text-red-600 transition" @click="handleLogout">
                            Logout
                        </div>
                    </div>

                    <!-- Icon -->
                    <ul class="flex lg:justify-around justify-end w-40 mb-0 relative "
                        style="margin-bottom: 0 !important;">
                        <!-- Tìm kiếm -->
                        <li class="pr-5 text-[20px] cursor-pointer">
                            <i class="fa-solid fa-magnifying-glass"></i>
                        </li>

                        <!-- Giỏ hàng -->
                        <li class="pr-5 text-[20px] relative cursor-pointer" @click="handleCart">
                            <i class="fa-solid fa-cart-shopping"></i>

                            <!-- Menu giỏ hàng -->
                            <div v-if="checkcart"
                                class="w-80 mx-auto p-4 bg-white rounded shadow absolute z-20 right-0 top-10 animate__animated animate__fadeIn">
                                <!-- Danh sách sản phẩm -->
                                <div v-for="(item, index) in data" :key="index"
                                    class="flex items-center gap-4 py-3 border-b last:border-none">
                                    <img :src="item.menu.img" alt="item image"
                                        class="w-16 h-16 rounded-md object-cover" />
                                    <div class="flex-1">
                                        <h3 class="text-black font-semibold">{{ item.menu.name }}</h3>
                                        <p class="text-sm text-gray-500">
                                            {{ item.total }} x
                                            <span class="text-orange-500 font-semibold">
                                                ${{ Math.floor(item.menu.price) }}
                                            </span>
                                        </p>
                                    </div>
                                </div>

                                <!-- Tổng tiền -->
                                <div class="flex justify-between items-center py-4 mt-4">
                                    <span class="text-lg font-semibold text-gray-700">Total:</span>
                                    <span class="text-xl font-bold text-orange-500">
                                        ${{ grandTotal.toLocaleString(undefined, { minimumFractionDigits: 2 }) }}
                                    </span>
                                </div>
                                <!-- Nút View Cart -->
                                <div class="text-center relative pb-5">
                                    <button @click="handleViewCart"
                                        class="relative px-5 py-3 w-full overflow-hidden rounded-md font-bold transition bg-orange-500 hover:bg-orange-600 text-white">
                                        View Cart
                                    </button>
                                </div>
                            </div>
                        </li>

                        <!-- Menu -->
                        <li @click="handleCheck" class="xl:pr-5 text-[20px] cursor-pointer">
                            <i class="fa-solid fa-bars"></i>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>
button {
    background: #EB0029;
    color: white;
    height: 50px;
    position: relative;
    overflow: hidden;
    z-index: 5;
    cursor: pointer;
}

button button,
button i {
    position: relative;
    z-index: 10;
    cursor: pointer;
}

.box-1 {
    position: absolute;
    width: 100%;
    height: 50%;
    top: 0;
    left: 0;
    overflow: hidden;
}

.box-1::before {
    content: '';
    position: absolute;
    top: 0;
    right: -100%;
    width: 100%;
    height: 100%;
    background: #FC781A;
    transition: right 0.5s ease;
}

.box-2 {
    position: absolute;
    width: 100%;
    height: 50%;
    bottom: 0;
    left: 0;
    overflow: hidden;
}

.box-2::before {
    content: '';
    position: absolute;
    bottom: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: #FC781A;
    transition: left 0.5s ease;
}

button:hover {
    border: 1px solid red;
}

button:hover .box-1::before {
    right: 0;
}

button:hover .box-2::before {
    left: 0;
}

.content {
    background: white;
    color: black;
    transition: color 0.5s ease;
    transition: background 0.5s ease;
}

.content:hover {
    background: #E10E21;
    color: white;
}

li {
    cursor: pointer;
}

.logo-img {
    max-width: none !important;
    /* Đảm bảo tỉ lệ ảnh không bị méo */
    object-fit: contain;
    /* Ảnh nằm gọn trong khung nếu có */
    display: block;
}

.login {
    opacity: 0;
}

.yes {
    opacity: 1;
}
</style>