<script setup>

import { ref } from 'vue';


const transfer = ref(0)
const position = ref({ x: 0, y: 0 })
let mouseX = 0
let mouseY = 0
const updatePositonMouse = (e) => {
    mouseX = e.clientX;
    mouseY = e.clientY;
}
setInterval(() => {
    if (transfer.value == 2) {
        transfer.value = 0;
    } else {
        transfer.value = transfer.value + 1;
    }
}, 7000)

const content = [' SPICY FRIED CHICKEN', 'Chicago Deep Pizza King', ' Chicago Deep Burger King']
const img = ['https://www.ex-coders.com/php-template/fresheat/assets/img/banner/bannerThumb1_1.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/banner/bannerThumb1_2.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/banner/bannerThumb1_3.png'
]
</script>
<!-- , 'Chicago Deep Pizza King', ' Chicago Deep Burger King' -->
<template>
    <div>
        <div class="img">
            <img style="height: 740px;" class="relative"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/bg/bannerBG1_1.jpg" alt=""
                width="1900px">
            <div class="">
                <div class="hidden xl:block">
                    <img class="absolute z-2 top-[28px] animate__animated customRight animate__delay-1s"
                        :key="transfer + '-img'"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bannerShape1_1.svg "
                        alt="">
                    <img class="absolute top-[517px] animate__animated customRight animate__delay-1s"
                        :key="transfer + '-img'"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bannerShape1_2.svg"
                        alt="">
                    <img class="absolute right-[40px] animate__animated slice  top-[0]" :key="transfer + '-img'"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bannerShape1_4.svg"
                        alt="">
                    <img class="absolute right-[745px] top-[588px] animate__animated animate__lightSpeedInLeft animate__slower animate__delay-3s"
                        :key="transfer + '-img'"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bannerShape1_3.svg"
                        alt="">
                    <img class=" absolute right-[0px] top-[600px] animate__animated slice " :key="transfer + '-img'"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bannerShape1_5.svg"
                        alt="">
                    <img class="absolute spin right-[860px] top-[520px] animate__repeat-2" :key="transfer + '-img'"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bannerShape1_6.svg"
                        alt="">
                    <div class="img-main  ">
                        <img class="absolute animate__animated customRight right-[114px] top-[100px] animate__delay-0.8s img-main"
                            :key="transfer + '-img'" :src=img[transfer] alt="" width="690">
                    </div>
                </div>
                <div class="absolute top-[172px] left-[30px] text-[25px]">
                    <h2 style="color: #FC791A;" class="z-2 font-bold animate__animated customRight animate__delay-0.8s"
                        :key="transfer + '-img'">
                        WELCOME
                        FRESHEAT</h2>
                    <div class="xl:text-[86px] font-black absolute animate__animated customRight animate__delay-0.8s xl:w-[800px] md:w-[700px] text-[60px]"
                        :key="transfer + '-img'"
                        style="color: white; text-transform: uppercase; font-family: Roboto, sans-serif;">
                        <h1 class="break-words whitespace-normal w-full ">{{ content[transfer] }}</h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
.img {
    position: relative;
}

.slower {
    --animate-duration: 5s;
}

.customRight {
    animation-name: newCustomRight;
    --animate-duration: 2s;
}

.slice {
    --animate-duration: 2s;
    animation-name: slice;
}

@keyframes slice {
    0% {
        opacity: 0;
        -webkit-transform: translate3d(-1000%, 0, 0);
    }

    100% {
        opacity: 1;
        transform: translate3d(0, 0, 0);
    }
}

@keyframes newCustomRight {
    0% {
        opacity: 0;
        -webkit-transform: translate3d(16%, 0, 0);
    }

    100% {
        opacity: 1;
        -webkit-transform: translate3d(0, 0, 0);
        transform: translate3d(0, 0, 0);
    }
}

.spin {
    animation: spin 10s linear infinite;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}
</style>