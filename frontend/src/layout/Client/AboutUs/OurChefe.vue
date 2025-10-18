<script setup>

import { ref, onMounted } from 'vue'
import Swiper from 'swiper'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import 'swiper/css';
const check = ref(null)
const isClass = ref(null)
function handleMouseMove(id) {
    isClass.value = id
}
function handleMouseMoveOut() {
    isClass.value = null
}
const img = ['https://www.ex-coders.com/php-template/fresheat/assets/img/chefe/chefeThumb2_1.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/chefe/chefeThumb2_2.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/chefe/chefeThumb2_3.jpg']
const author = ['Ralph Edwards', 'Leslie Alexander', 'Ronald Richards']
const content = ['Chef Lead', 'Chef Assistant', 'Chef Assistant']
const icon = ['fab fa-linkedin-in','fab fa-youtube','fab fa-facebook-f']
</script>

<template>
    <div style="background-color: #F4F1EA;" class="relative">
        <div class="flex justify-center pt-30 font-bold ">
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            <span class="px-1 font-bold text-[14px]" style="color: #FC7D1F;">OUR CHEFE</span>
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
        </div>
        <h2 class="py-1 text-center pb-10 text-[43px] items-justify font-black leading-[50px]">
            Meet Our Expert Chefe
        </h2>
        <div class="flex justify-around px-29 relative pb-30 flex-wrap" @mouseenter="handleMouseMove(item.id)" @mouseleave="handleMouseMoveOut">
            <div v-for="(item, index) in 3" class=" containerr mt-5">
                <div class="relative ">
                    <div class="place-items-center relative z-2"> <img :src=img[index] alt="" class="transform-x-1/2"
                            width="400">
                    </div>
                    <div style="background-color: white; margin-top: -79px; border-radius: 30px 0 30px 0; align-content: center;"
                        class="relative z-3 h-30 mx-10">
                        <h3 class="text-center text-[24px] font-bold  top-37 left-28">{{ author[index] }}</h3>
                        <div class="text-center pt-4  top-45 left-39" style="color: #938B90;">{{ content[index]
                            }}</div>
                    </div>
                    <div class="absolute top-50 z-3 icon" style="right: -18px;" :key="index">
                        <div class="bg-red-600 text-white flex flex-col items-center py-6 w-19 h-60" style="border-radius: 0 20px 0 20px;">
                            <div v-for="(item,index) in 3">
                                <a href="#" class="mb-4">
                                    <div
                                        class="bg-white text-red-600 rounded-full w-10 h-10 flex items-center justify-center mb-3">
                                        <i :class="icon[index]"></i>
                                    </div>
                                </a>
                            </div>
                            <div>
                                <div class="text-[17x] rotate-270 py-5 pl-5 font-bold">
                                    Share
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="absolute top-20">
            <img class="img" src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/chefeShape1_1.png"
                alt="">
        </div>
        <div class="absolute right-0 top-0">
            <img class="img-1" src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/chefeShape1_2.png"
                alt="">
        </div>
    </div>

</template>

<style scoped>
.right-31 {
    right: calc(var(--spacing) * -11);
}

.cir {
    border-radius: 50% 50% 0 0;
}

.icon {
    color: white;
    transition: background 0.3s ease;
    cursor: pointer;
    opacity: 1;
}

.test:hover .icon {
    background: #F12425;
}

.icon-color {
    background-color: #F12425;
}

.icon-1 {
    background: white;
    box-shadow: rgba(0, 0, 0, 0.16) 0px 10px 36px 0px, rgba(0, 0, 0, 0.06) 0px 0px 0px 1px;
    cursor: pointer;
    opacity: 0;
    transition: opacity 1s ease;
    transition: color 0.6s ease;
    transition: background 0.6s ease;
}

.icon-1:hover {
    background: #F12425;
    color: white;
}

.icon-2 {
    opacity: 1;
}

.img {
    animation: transform 5s ease infinite;
}

@keyframes transform {

    0%,
    100% {
        transform: translateY(0);
    }

    50% {
        transform: translateY(30px);
    }
}

.img-1 {
    animation: transform1 6s ease-in-out infinite;
}

@keyframes transform1 {

    0%,
    100% {
        transform: translateX(0);
    }

    50% {
        transform: translateX(-30px);
    }
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
.containerr:hover .icon {
    opacity: 1;
    transform: translateY(-155px) scale(0.9);

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

</style>
