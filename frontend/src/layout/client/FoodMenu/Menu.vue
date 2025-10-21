<script setup>

import { onMounted, ref } from 'vue'
import axios from "axios"
const data = ref([])
const data1 = ref([])
const check = ref(1)
onMounted(async () => {
    const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/menu`)
    data.value = response.data.slice(0, 5)
    data1.value = response.data.slice(6, 11)

})
function handleClick(id) {
    check.value = id
}

const img = ['https://www.ex-coders.com/php-template/fresheat/assets/img/menu/menuIcon1_1.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/menu/menuIcon1_2.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/menu/menuIcon1_3.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/menu/menuIcon1_4.png'
]
const name = ['Fast Food', 'Drink & Juice', 'Chicken Pizza', 'Fresh Pasta']

const content = ['', '', '', 'CHICKEN PIZZA', '', 'CHICKEN FRY']
</script>
<template>
    <div style="background-color: #F4F1EA;" class=" pt-20 pb-10 px-5">
        <div style="background-color: white;" class="rounded-[20px]  ">
            <div class="flex justify-center pt-20 font-bold ">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
                <span class="px-1 font-bold text-[14px]" style="color: #FC7D1F;">FOOD MENU</span>
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            </div>
            <h2 class="py-1 text-center pb-10 text-[43px] items-justify font-black leading-[50px]">
                 Fresheat Foods Menu
            </h2>
            <div class="flex justify-around px-42 pb-10 xl:flex hidden">
                <div v-for="(item, index) in 4" class="cursor-pointer">
                    <div class="flex items-center">
                        <img :src=img[index] alt="" >
                        <div class="pl-3 text-[20px] font-semibold" :class="index + 1 == check ? 'box-1' : ''"
                            @click="handleClick(index + 1)">{{ name[index] }}</div>
                        <div class="pl-5 type w-1 h-10"></div>
                    </div>
                </div>
            </div>
            <div class="box mx-27"></div>
            <transition>
                <div class="flex justify-around py-15 " :key="check">
                    <div class="w-110">
                        <div v-for="item in data" :key="item.id">
                            <div class="flex items-center justify-between mb-7">
                                <div class="flex items-center">
                                    <img :src=item.img alt="" width="80" >
                                    <div class="pl-10">
                                        <div class="text-[27px] font-bold cursor-pointer"
                                            :class="item.id == 1 && check == 1 ? 'box-1' : ''">{{ item.name }}
                                        </div>
                                        <div class="text-[17px]" style="color: #938B90;">{{ item.describe }}</div>
                                    </div>
                                </div>
                                <div class="font-semibold text-[17px]">${{ item.price }}</div>
                            </div>
                        </div>
                    </div>
                    <img class="xl:flex hidden" src="https://www.ex-coders.com/php-template/fresheat/assets/img/menu/menuThumb3_1.png" alt="">
                    <div class="w-110 lg:block hidden">
                        <div v-for="item in data1" :key="item.id">
                            <div class="flex items-center justify-between mb-7">
                                <div class="flex items-center">
                                    <img :src=item.img alt="" width="80">
                                    <div class="pl-10">
                                        <div class="text-[27px] font-bold cursor-pointer">{{ item.name }}</div>
                                        <div class="text-[17px]" style="color: #938B90;">{{ item.describe }}</div>
                                    </div>
                                </div>
                                <div class="font-semibold text-[17px]">${{ (item.price).toLocaleString("vi-VN") }}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </transition>
        </div>
    </div>
    <div style="background-color: #F4F1EA;">
        <div class="marquee pb-20">
            <div class="marquee-content">
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    chicken pizza
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    GRILLED CHICKEN
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    BURGER
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    FRESH PASTA
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    chicken pizza
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    GRILLED CHICKEN
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    BURGER
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    FRESH PASTA
                </span>
            </div>
        </div>
    </div>
</template>
<style scoped>
.type {
    border-right: 1px dashed #BCB8B9;
}

.box {
    border-bottom: 1px solid #BCB8B9;
}

.box-1 {
    color: #F12425;
}

.v-enter-active,
.v-leave-active {
    transition: all 0.5s ease;
}

.v-enter-from,
.v-leave-to {
    transform: translateY(20px);
    opacity: 0;
}


.box-3 {
    color: #BCB8B9;
    transition: color 0.9s ease;
}

.box-3:hover {
    color: #EB0029;
}

.marquee {
    overflow: hidden;
    background: #F4F1EA;
}

.marquee-content {
    display: inline-flex;
    align-items: center;
    animation: marquee 25s linear infinite;
    flex-shrink: 0;
}

.marquee-content .item {
    font-size: 80px;
    display: inline-block;
    font-weight: 800;
    text-transform: uppercase;
    position: relative;
    flex-shrink: 0;
    color: #BCB8B9;
    transition: color 0.3s ease;
    cursor: pointer;
}

.marquee-content .item:hover {
    color: #EB0029;
}

.marquee-content .item::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 3px;
    background: #EB0029;
    transition: width 0.5s ease;
}

.marquee-content .item:hover::after {
    width: 100%;
}

@keyframes marquee {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(-50%);
    }
}
</style>