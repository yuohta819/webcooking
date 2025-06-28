<script setup>

import { onMounted, ref } from 'vue';
import axios from "axios"


const data = ref([])
const isClass = ref(null)
onMounted(async () => {
    const response = await axios.get("http://localhost:8080/api/menu")
    data.value = response.data.slice(0, 5)
})
function handleMouseMove(id) {
    isClass.value = id
}
function handleMouseMoveOut() {
    isClass.value = null
}
</script>

<template>
    <div style="background-color: #F4F1EA;" class="relative">
        <div class="flex justify-center pt-27 font-bold ">
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            <span class="px-1 font-bold text-[14px]" style="color: #FC7D1F;">POPULAR DISHES</span>
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
        </div>
        <h2 class="py-1 text-center text-[43px] items-justify font-black leading-[50px]">
            Best selling Dishes
        </h2>
        <div class="flex justify-around py-10 px-3 flex-wrap">
            <div v-for="item in data" @mouseenter="handleMouseMove(item.id)" @mouseleave="handleMouseMoveOut"
                style="background-color: white;" class="mt-5"
                :class="['rounded-[10px]', 'w-67', 'relative', 'move', isClass == item.id ? 'type-1' : '']">
                <div class="pt-5 pr-3 text-end">
                    <i style="background-color: #F43C22; color: white;"
                        class="fa-regular fa-heart p-2 rounded-[50%]"></i>
                </div>
                <div class="place-items-center ">
                    <img :src=item.img alt="" width="160" class="p-2">
                </div>
                <div class="absolute z-5 icon" style="color:red;">
                    <div class=" rounded-[50%]" style="background-color: white; color: black;"><i
                            class="text-[16] p-2 fa-solid fa-basket-shopping"></i>
                    </div>
                    <div class=" rounded-[50%] mt-2" style="background-color: white; color: black;"><i
                            class="p-2 fa-solid fa-eye"></i></div>
                </div>
                <div class="text-center px-5">
                    <div class="font-bold item-dis text-[20px] pt-3 " :class="isClass == item.id ? 'type-2' : ''">{{
                        item.name }}</div>
                    <div class="my-2 text-[14px]" :class="isClass == item.id ? 'type-2' : ''" style="color: #7D8490;">
                        {{ item.describe }}</div>
                    <div class="font-bold pb-5 text-[22px]" :class="isClass == item.id ? 'type-2' : ''"
                        style="color: #F43C22;">${{ item.price }}</div>
                </div>
            </div>
            <div class="absolute img-1">
                <img class=""
                    src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/popularDishesShape1_2.png"
                    alt="">
            </div>

            <div class="absolute left-0 bottom-0">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/popularDishesShape1_1.png"
                    alt="">
            </div>
        </div>
        <div class="text-center pt-10 pb-30 relative ">
            <button class="px-5 py-3">
                <span class="relative z-10">VIEW ALL ITEM</span>
                <div class="box-1"></div>
                <div class="box-2"></div>
                <i style="background-color: white; color: red;" class="p-1 ml-4 fa-solid fa-arrow-right z-10"></i>
            </button>
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

.img-1 {
    top: 20px;
    right: 0;
    animation: move 5s ease infinite;
}

@keyframes move {

    0%,
    100% {
        transform: translateY(0);
    }

    50% {
        transform: translateY(30px);
    }
}

.icon {
    right: 11px;
    transform: translateY(-74px) scale(0.9);
    opacity: 0;
    transition: transform 0.5s ease, opacity 0.9s ease;
}

.rounded-\[10px\]:hover .icon {
    opacity: 1;
    transform: translateY(-155px) scale(0.9);

}

.item-dis {
    animation: all 3s ease;
}

.type-1 {
    background: url("https://www.ex-coders.com/php-template/fresheat/assets/img/bg/dishesThumbBG.png") !important;
    background-repeat: no-repeat;
    background-size: contain;
    transition: background-position 0.3s ease;
    background-position: -30px center;

}

.font-bold {
    transition: color 0.5s ease;
}

.type-2 {
    color: white !important;
    animation: all 1s ease-in-out infinite;
}
</style>