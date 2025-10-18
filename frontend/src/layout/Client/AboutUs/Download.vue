<script setup>
import { ref } from 'vue';
const logo = ref(['https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_5.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_1.jpg'
    , 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_2.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_3.jpg'
    , 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_5.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_2.jpg'
])
</script>

<template>
    <div class="relative" style="background-color: #F4F1EA;">
            <div class="mx-10 relative ">
                <img height="80" style="height: 420px !important;"
                    src="https://www.ex-coders.com/php-template/fresheat/assets/img/bg/ctaBG3_1.jpg" alt="">
                
                <img class="absolute left-146 bottom-0 hidden xl:block"
                    src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape3_5.png" alt="">
                <div class="absolute img-7 left-13 top-0">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape3_1.png" alt="">
                </div>
                <div class="absolute img-6 bottom-0 left-13">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape3_2.png" alt="">
                </div>
                <img class="absolute right-[50.3px] top-0"
                    src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape3_4.png" alt="">
                <div class="img-3 absolute right-12 bottom-0  xl:block hidden">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape3_3.png" alt="">
                </div>
                <div class="img-4 absolute right-80 bottom-0 xl:block hidden">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/cta/ctaThumb3_1.png" alt="">
                </div>
                <div class="img-5 absolute right-180 top-30  xl:block hidden">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape3_6.png" alt="">
                </div>

                <div class="absolute top-[92px] left-1/2 transform -translate-x-1/2 xl:left-[168px] xl:transform-none xl:-translate-x-0">
                    <div class="flex items-center font-bold ">
                        <img style="color: white;"
                            src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIconWhite.svg"
                            alt="">
                        <span class="px-1 font-bold text-[14px]" style="color: white;">DOWNLOAD APP</span>
                        <img style="color: white;"
                            src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIconWhite.svg"
                            alt="">
                    </div>
                    <div class="py-3 text-[35px] font-black " style="color: white; font-family: Roboto, sans-serif;">
                        Download Food App Order Today!</div>
                    <div class="flex  gap-2 mt-10">
                        <div class="flex items-center px-8 py-1 h-16 rounded-[40px] icon-1 w-50">
                            <div>
                                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/appleStore.svg"
                                    alt="">
                            </div>
                            <div class="ml-3">
                                <div class="text-[16px] font-bold" style="color: white; line-height: 20px;">Get it on
                                    <br><span class="font-black">App store</span>
                                </div>
                            </div>
                        </div>
                        <div class="flex items-center px-8 py-1 h-16 rounded-[40px] icon-2">
                            <div>
                                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/playStore.svg"
                                    alt="">
                            </div>
                            <div class="ml-3">
                                <div class="text-[16px] font-bold" style="color: white; line-height: 20px;">Get it on
                                    <br><span class="font-black">Google play</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
    </div>
    <div class="justify-center gap-6 mt-30" style="width: 100%;  ">
        <div class="flex justify-around">
            <div v-for="(item, index) in logo" :key="index + '1'" style="place-items: center !important;">
                <div class="img flex justify-center">
                    <img :src="item" alt="Logo" style="width: 250px !important;" />
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.icon-1 {
    background-color: #E10E21;
    transition: background 0.4s ease;
    border: 1px solid #E10E21;
    cursor: pointer;
}

.icon-1:hover {
    background: #010F1C;
    border: 1px solid white;
}

.icon-2 {
    background-color: #010F1C;
    transition: background 0.4s ease;
    border: 1px solid white;
    cursor: pointer;
}

.icon-2:hover {
    background: #E10E21;
}

.img-3 {
    animation: transform 3s linear infinite;
}

@keyframes transform {

    0%,
    100% {
        transform: translateY(0);
    }

    50% {
        transform: translateY(-30px);
    }
}

.img-4 {
    animation: transform1 3s linear infinite;
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

.img-5 {
    animation: spin 10s linear infinite;
}

@keyframes spin {

    0%,
    100% {
        transform: rotate(0);
    }

    100% {
        transform: rotate(360deg);
    }
}

.img-6 {
    animation: transform3 3s linear infinite;
}

@keyframes transform3 {

    0%,
    100% {
        transform: translateY(0);
    }

    50% {
        transform: translateY(-30px);
    }
}

.img-7 {
    animation: transform4 3s linear infinite;
}

@keyframes transform4 {

    0%,
    100% {
        transform: translateX(0);
    }

    50% {
        transform: translateX(30px);
    }
}
</style>