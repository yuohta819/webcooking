<script setup>

import axios from 'axios';
import { onMounted, ref } from 'vue';
import ContactSection from './ContactSection.vue';
import 'animate.css';
const name = ref('')
const check = ref(false)
const showPanel = ref(false)
const activeDropdown = ref('')
const type1 = localStorage.getItem("name")
const account = localStorage.getItem("account")
const type = localStorage.getItem("type")
if (type1) {
    name.value = type1;
    if (account && type === "google") {
        onMounted(async () => {
            axios.get('http://localhost:8080/account/save', {
                params: {
                    name: type1,
                    account: account,
                    password: 'google'
                }
            })
        })
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
function handleLogout() {
    localStorage.clear();
    location.reload();
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
            <button class="text-white bg-red-500 p-2 rounded-full" @click="handleCheck">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24"
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
            <div class="flex px-11 img xl:justify-between py-3 justify-end"
                style="color: white; background-color: #EB0029; ">
                <span>
                    <i class="fa-solid fa-clock"></i>
                    09:00 am - 06:00 pm
                </span>
                <div class="pr-10 xl:block hidden" style="width: 22%;">
                    <ul class="flex justify-around">
                        <li>Follow Us:</li>
                        <li><i class="fa-brands fa-facebook-f"></i></li>
                        <li><i class="fa-brands fa-twitter"></i></li>
                        <li><i class="fa-brands fa-youtube"></i></li>
                        <li><i class="fa-brands fa-linkedin-in"></i></li>
                    </ul>
                </div>
            </div>
            <div style="color: white;" class="flex md:px-11 px-5 mt-7 justify-end items-center">
                <span class="2xl:flex items-center hidden" style="width: 70.8%; height: 36px;">
                    <ul class="flex gap-8 font-bold  relative">
                        <li class="relative" @mouseover=" handleDropdown('home', true)"
                            @mouseleave=" handleDropdown('home', false)">
                            <div class="flex items-center h-[70px]">
                                Home
                                <i class="ml-3 fa-solid fa-plus"></i>
                            </div>
                            <div v-if="activeDropdown === 'home'"
                                class="absolute w-100  top-15 left-10 animate__animated z-2 animate__slideInUp "
                                style="background-color: white;">
                                <a href="/aboutus" @mouseleave=" handleDropdown('home', false)">
                                    <img width="800" class="px-6 py-7"
                                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/header/home1.jpg"
                                        alt="">
                                </a>
                                <div class="text-center py-2" style="color: black;">Home</div>
                            </div>
                        </li>
                        <li @mouseover=" handleDropdown('about', true)">
                            <div class="flex items-center h-[70px]">
                                About Us
                                <i class="ml-3 fa-solid fa-plus"></i>
                            </div>
                            <div class="absolute  top-16 left-30 animate__animated z-2 animate__slideInUp  "
                                @mouseleave=" handleDropdown('about', false)" v-if="activeDropdown === 'about'"
                                style="background-color: white;">
                                <a href="/aboutus">
                                    <div class="content w-50 px-4 py-3">
                                        About Us
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li @mouseover=" handleDropdown('shop', true)" class="flex items-center">
                            <div>
                                Shop
                            </div><i class="ml-3 fa-solid fa-plus"></i>
                            <div class="absolute  top-16 left-60 animate__animated z-2 animate__slideInUp "
                                @mouseleave=" handleDropdown('shop', false)" v-if="activeDropdown === 'shop'"
                                style="background-color: white;">
                                <a href="/shop">
                                    <div class="content w-50 px-4 py-3">
                                        Shop
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li @mouseover=" handleDropdown('add', true)" class="flex items-center">
                            <div>
                                Page
                            </div><i class="ml-3 fa-solid fa-plus"></i>
                            <div class="absolute  top-16 left-90 animate__animated z-2 animate__slideInUp "
                                @mouseleave=" handleDropdown('add', false)" v-if="activeDropdown === 'add'"
                                style="background-color: white;">
                                <a href="/account">
                                    <div class="content w-50 px-4 py-3">
                                        Account
                                    </div>
                                </a>
                                <a href="/register">
                                    <div class="content w-50 px-4 py-3">
                                        Register
                                    </div>
                                </a>
                                <a href="/cart">
                                    <div class="content w-50 px-4 py-3">
                                        Cart
                                    </div>
                                </a>
                                <a href="/register">
                                    <div class="content w-50 px-4 py-3">
                                        Add Account
                                    </div>
                                </a>
                                <a href="/edit">
                                    <div class="content w-50 px-4 py-3">
                                        Edit Product
                                    </div>
                                </a>
                            </div>
                        </li>
                        <li>
                            <div class="flex items-center h-[70px]">
                                Blog
                                <i class=" ml-3 fa-solid fa-plus"></i>
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
                <div style="color: #5C6574;" class="sm:w-[340px] flex w-70 xl:pr-8 items-center">
                    <div :class="name != '' ? 'yes' : 'login'"
                        class="flex md:w-90 xl:justify-around justify-end mr-3 flex">
                        <div class="text-[18px] mr-1">{{ name }}</div>
                        <div class="text-[18px]" @click="handleLogout()" style="cursor: pointer;">Logout</div>
                    </div>
                    <ul class="flex lg:justify-around justify-end w-40">
                        <li class="pr-5 text-[20px]"><i class="fa-solid fa-magnifying-glass"></i></li>
                        <li class="pr-5 text-[20px]"><i class="fa-solid fa-cart-shopping"></i></li>
                        <li @click="handleCheck" class="xl:pr-5 text-[20px] cursor-pointer"><i
                                class="fa-solid fa-bars"></i></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
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