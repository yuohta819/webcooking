<script setup>
import axios from "axios"
import { onMounted, ref, nextTick } from "vue";
import { Swiper, SwiperSlide } from 'swiper/vue'
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
const data = ref([])
onMounted(async () => {
    const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/menu`)
    data.value = response.data.slice(0, 4)
})
const img = ['https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_1.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_2.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_3.png'
]
const content = [
    'ON THIS WEEK',
    'WELCOME FRESHEAT',
    'ON THIS WEEK'
]
const name = [
    'SPICY FRIED CHICKEN',
    'TODAY SPACIAL FOOD',
    'SPECIAL CHICKEN ROLL'
]

</script>

<template>
    <div style="background-color: #F4F1EA;" class="relative pt-10">
        <div class="flex justify-center pt-20 font-bold ">
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            <span class="px-2" style="color: #FC7D1F;">BEST FOOD</span>
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
        </div>
        <div>
            <div class="text-center font-bold text-[40px] pb-10">Popular Food Items</div>
        </div>
        <div class="w-full max-w-[1300px] mx-auto px-40 py-12">
            <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
                <div v-for="item in data" :key="item._id"
                    class="bg-white rounded-[10px] shadow-md flex flex-col items-center p-4 mt-20 lg:mt-0">
                    <div class="relative -mt-20 w-32 h-32" >
                        <div class="w-full h-full img rounded-[50%] absolute p-2 ">
                        </div>
                        <img :src="item.img" alt="" class="w-full h-full object-cover rounded-full p-2 z-10" />
                    </div>
                    <div class="text-center mt-6">
                        <div class="font-bold text-[21px]">{{ item.name }}</div>
                        <div class="text-[18px] py-2 text-gray-500">{{ item.describe }}</div>
                        <div class="font-bold text-[20px] text-[#F43C22]">${{ item.price }}</div>
                    </div>
                </div>
            </div>
        </div>
        <!-- w-40 h-40 img rounded-[50%] absolute p-2 left-8 -->
        <div class="absolute top-[140px] right-[20px]">
            <img class=" img_1"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bestFoodItemsShape1_2.png" alt="">
        </div>
        <div class="absolute top-[366px]">
            <img class="img_2"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bestFoodItemsShape1_1.png" alt="">
        </div>
        <div class="flex justify-between justify-center py-20 flex-wrap">
            <div v-for="(item, index) in 3" class=" w-[450px] mx-4 flex justify-around mt-5"
                style="background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/offerBG1_1.jpg');">
                <div class="pl-5 py-10 self-center">
                    <div class="font-bold text-[12px]" style="color: #EB0029;">{{ content[index] }}</div>
                    <div class="text-[22px] font-bold py-2"
                        style="color: white; box-shadow: rgba(0, 0, 0, 0.1) 0px 4px 12px;">{{ name[index] }}</div>
                    <div style="color: #FC791A; box-shadow: rgba(50, 50, 93, 0.25) 0px 50px 100px -20px, rgba(0, 0, 0, 0.3) 0px 30px 60px -30px, rgba(10, 37, 64, 0.35) 0px -2px 6px 0px inset;"
                        class="font-extrabold text-[12px]">Limits Time Offer</div>
                    <div class="mt-10 px-4 py-4 w-40 flex items-center justify-around text-[15px] button">
                        <div class="box-1"></div>
                        <div class="box-2"></div>
                        <div class="relative z-10">
                            <button>ORDER NOW</button>
                        </div>
                        <i class="fa-solid fa-arrow-right z-10"></i>
                    </div>
                </div>
                <div class="relative w-40">
                    <img class="img_1"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/offerShape1_4.png" alt="">
                    <img class="absolute top-15" :src=img[index] alt="" width="200">
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
.swiper-box {
    margin-left: auto;
    margin-right: auto;
    position: relative;
    overflow: hidden;
    list-style: none;
    padding: 0;
    z-index: 1;
    display: block;
}

.swiper-box .swiper-slide {
    width: 20% !important;
    /* hoặc bao nhiêu px bạn muốn */
}

button {
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


button:hover .box-1::before {
    right: 0;
}

button:hover .box-2::before {
    left: 0;
}

.img {
    animation: spin 20s linear infinite;
    border: 2px dashed #F28092;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
}

.img_1 {
    animation: move 5s ease-in-out infinite;
}

@keyframes move {

    0%,
    100% {
        transform: translateX(0);

    }

    50% {
        transform: translateX(20px);

    }
}

.img_2 {
    animation: move2 2s ease-in-out infinite;
}

@keyframes move2 {

    0%,
    100% {
        transform: translateY(0);
    }

    50% {
        transform: translateY(-30px);
    }
}

.button button,
.button i {
    position: relative;
    z-index: 10;
    cursor: pointer;
}

.button {
    background: #EB0029;
    color: white;
    height: 50px;
    position: relative;
    overflow: hidden;
    z-index: 5;
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
    background: black;
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
    background: black;
    transition: left 0.5s ease;
}

.button:hover {
    border: 1px solid red;
}

.button:hover .box-1::before {
    right: 0;
}

.button:hover .box-2::before {
    left: 0;
}
</style>