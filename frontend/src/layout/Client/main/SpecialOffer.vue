<script setup>
import { ref, onBeforeUnmount, onMounted, computed } from 'vue'
const seconds = ref(365 * 86400)
let timer = null;
onMounted(() => {
    timer = setInterval(() => {
        if (seconds.value > 0) {
            seconds.value--;
        } else {
            clearInterval(timer);
        }
    }, 1000);
});
onBeforeUnmount(() => clearInterval(timer));

const days = computed(() => Math.floor(seconds.value / 86400));
const hours = computed(() => Math.floor((seconds.value % 86400) / 3600));
const minutes = computed(() => Math.floor((seconds.value % 3600) / 60));
const secs = computed(() => seconds.value % 60);
</script>

<template>
    <div>
        <div class="relative 2xl:mx-4 ">
            <div class="absolute top-9 left-21"><img class="img"
                    src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/timerShape1_1.svg" alt="">
            </div>
            <div class="absolute top-0 right-0">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/timerShape1_2.svg" alt="">
            </div>
            <div class="absolute bottom-0 right-0">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/timerShape1_3.svg" alt="">
            </div>
            <img class="h-150" src="https://www.ex-coders.com/php-template/fresheat/assets/img/bg/timerBg.png"
                alt="" height="900">
            <div class="absolute top-10 left-33 xl:block hidden">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/timer/timerThumb1_1.png" alt=""
                    width="640">
            </div>
            <div class="absolute top-[88px] 
         left-1/2 -translate-x-1/2 
         xl:left-auto xl:translate-x-0 
         xl:right-[20%]
         w-full max-w-[600px] bg-white/15 p-4 rounded-md" style="background-color: rgba(255, 255, 255, 0.15);">
                <div class="flex justify-center pt-10 font-bold ">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
                    <span class="px-1 font-bold text-[14px]" style="color: #FC7D1F;">POPULAR DISHES</span>
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
                </div>
                <div class="text-[30px] font-bold px-5 py-7" style="color: white;">
                    Get 30% Discount Every Item
                </div>
                <div class="flex justify-around">
                    <div class="p-3 w-20 text-center" style="background-color: #FC7D1F;">
                        <div class="font-bold text-[20px]" style="color: white;">{{ days }}
                        </div>
                        <div class="w-full my-3 " style="color: white; background: white; height: 1px;"></div>
                        <div class="text-center font-semibold" style="color: white;">DAYS</div>
                    </div>
                    <div class="p-3 w-20 text-center" style="background-color: #FC7D1F;">
                        <div class="font-bold text-[20px]" style="color: white;">{{ hours }}
                        </div>
                        <div class="w-full my-3 " style="color: white; background: white; height: 1px;"></div>
                        <div class="text-center font-semibold" style="color: white;">HRS</div>
                    </div>
                    <div class="p-3 w-20 text-center" style="background-color: #FC7D1F;">
                        <div class="font-bold text-[20px]" style="color: white;">{{ minutes }}
                        </div>
                        <div class="w-full my-3 " style="color: white; background: white; height: 1px;"></div>
                        <div class="text-center font-semibold" style="color: white;">MINS</div>
                    </div>
                    <div class="p-3 w-20 text-center" style="background-color: #FC7D1F;">
                        <div class="font-bold text-[20px]" style="color: white;">{{ secs }}
                        </div>
                        <div class="w-full my-3 " style="color: white; background: white; height: 1px;"></div>
                        <div class="text-center font-semibold" style="color: white;">SECS</div>
                    </div>
                </div>
                <div class="text-center pt-10 pb-10 relative ">
                    <button class="px-5 py-3">
                        <span class="relative z-10">VIEW ALL ITEM</span>
                        <div class="box-1"></div>
                        <div class="box-2"></div>
                        <i style="background-color: white; color: red;"
                            class="p-1 ml-4 fa-solid fa-arrow-right z-10"></i>
                    </button>
                </div>

            </div>
        </div>
    </div>
</template>
<style>
@keyframes spin {

    100% {
        transform: rotate(360deg);
    }
}
</style>
<style scoped>
.img {
    animation: spin 20s linear infinite;
    transform-origin: center center;
}

.top-10 {
    top: calc(var(--spacing) * 13.6);
}

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
</style>