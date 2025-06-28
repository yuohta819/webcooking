<script setup>

import { ref, onMounted } from 'vue'
import Swiper from 'swiper'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import 'swiper/css';
const check = ref(null)

function handleCheckMove(id) {
    check.value = id
}
function handleCheckOut(id) {
    check.value = null
}
onMounted(() => {
    new Swiper('.swiper', {
        modules: [Autoplay, Navigation, Pagination],
        loop: true,
        slidesPerView: 4,
        spaceBetween: 5,
        speed: 1000, // 1s để hoàn tất chuyển slide
        autoplay: {
            delay: 2500, // thời gian chuyển slide (đơn vị millisecond), 2500 = 2.5s
            disableOnInteraction: false, // nếu true, khi bạn tương tác (như kéo) sẽ dừng autoplay
        },
        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
    })
})
const isXL = ref(false)

onMounted(() => {
    const checkWidth = () => {
        isXL.value = window.innerWidth > 1280
    }

    checkWidth()
    window.addEventListener('resize', checkWidth)
})
const img = ['https://www.ex-coders.com/php-template/fresheat/assets/img/chefe/chefeThumb1_1.png', 'https://www.ex-coders.com/php-template/fresheat/assets/img/chefe/chefeThumb1_2.png', 'https://www.ex-coders.com/php-template/fresheat/assets/img/chefe/chefeThumb1_3.png']
const author = ['Ralph Edwards', 'Leslie Alexander', 'Ronald Richards']
const content = ['Chef Lead', 'Chef Assistant', 'Chef Assistant']
const logo = ref(['https://www.ex-coders.com/php-template/fresheat/assets/img/logo/clientLogo1_6.png', 'https://www.ex-coders.com/php-template/fresheat/assets/img/logo/clientLogo1_2.png'
    , 'https://www.ex-coders.com/php-template/fresheat/assets/img/logo/clientLogo1_4.png', 'https://www.ex-coders.com/php-template/fresheat/assets/img/logo/clientLogo1_3.png'
    , 'https://www.ex-coders.com/php-template/fresheat/assets/img/logo/clientLogo1_5.png', 'https://www.ex-coders.com/php-template/fresheat/assets/img/logo/clientLogo1_1.png'
])
</script>

<template>
    <div style="background-color: #F4F1EA;" class="relative">
        <div class="flex justify-center pt-30 font-bold ">
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            <span class="px-1 font-bold text-[14px]" style="color: #FC7D1F;">OUR CHEFE</span>
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
        </div>
        <h2 class="py-1 text-center text-[43px] items-justify font-black leading-[50px] relative z-5">
            Meet Our Expert Chefe
        </h2>
        <div class="flex justify-around xl:px-29 relative pb-70 flex-wrap">
            <div v-for="(item, index) in 3">
                <div class="relative mt-50 xl:mt-10">
                    <div class="place-items-center relative z-2"> <img :src=img[index] alt="" class="transform-x-1/2">
                    </div>
                    <div style="background-color: white;" class="w-100 absolute py-10 right-31 h-70 cir top-47">
                        <h3 class="text-center text-[24px] font-bold absolute top-37 left-28">{{ author[index] }}</h3>
                        <div class="text-center pt-4 absolute top-45 left-39" style="color: #938B90;">{{ content[index]
                            }}</div>
                    </div>
                    <div @mouseenter="handleCheckMove(index)" @mouseleave="handleCheckOut"
                        class="absolute z-4 flex gap-2 top-70 right-25">
                        <div class=" top-70 left-24 z-3">
                            <i :class="index == check ? 'icon-2' : ''"
                                class="p-3 rounded-[50%] icon-1 fa-brands fa-facebook-f"></i>
                        </div>
                        <div class=" top-70 left-34 z-3">
                            <i :class="index == check ? 'icon-color' : ''"
                                class="p-3 icon rounded-[50%] fa-solid fa-share-nodes"></i>
                        </div>
                        <div class=" top-70 left-45 z-3">
                            <i :class="index == check ? 'icon-2' : ''"
                                class="p-3 icon-1 rounded-[50%] fa-brands fa-linkedin-in"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="absolute top-20">
            <img class="img" src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/chefeShape1_1.png"
                alt="">
        </div>
        <div class="absolute right-0">
            <img class="img-1" src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/chefeShape1_2.png"
                alt="">
        </div>
        <div class="swiper px-20 absolute  justify-center  " style="width: 83%;  ">
            <div class="swiper-wrapper pb-30 ">
                <div v-for="(item, index) in logo" :key="index + '1'" class="swiper-slide"
                    style="place-items: center !important;">
                    <img :src="item" alt="Logo" />
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>
@media (max-width: 1280px) {
    .swiper {
        display: none;
    }
}
.right-31 {
    right: calc(var(--spacing) * -11);
}

.cir {
    border-radius: 50% 50% 0 0;
}

.icon {
    background-color: #FC791A;
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
</style>
