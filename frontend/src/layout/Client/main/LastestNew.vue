<script setup>
import { ref, onMounted } from 'vue'
import Swiper from 'swiper'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
import 'swiper/css';
const img = ['https://www.ex-coders.com/php-template/fresheat/assets/img/blog/blogThumb1_2.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/blog/blogThumb1_1.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/blog/blogThumb1_3.jpg']
const day = ['25', '17', '20']
const logo = ref(['https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_5.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_1.jpg'
    , 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_2.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_3.jpg'
    , 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_5.jpg', 'https://www.ex-coders.com/php-template/fresheat/assets/img/gallery/galleryThumb1_2.jpg'
])
const food = ['chicken', 'noodles', 'chicken']
const content = ['Quick Cravings Unraveling Fast Food Delights', 'Benefits of health and safety measures', 'Fast Food Frenzy a Taste of Convenience']
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
</script>

<template>
    <div style="background-color: #F4E6D7;" class="relative">
        <img class="absolute top-32"
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/blog/burger-shape.png" alt="">
        <img class="absolute right-0 top-32"
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/blog/pizza-shape.png" alt="">
        <div class="flex justify-center pt-30 font-bold ">
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            <span class="px-1 font-bold text-[14px]" style="color: #FC7D1F;">LATEST NEWS</span>
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
        </div>
        <h2 class="py-1 text-center pb-10 text-[43px] items-justify font-black leading-[50px]">
            Our Latest Foods News
        </h2>
        <div class="flex justify-around sm:px-20 pb-20 flex-wrap">
            <div v-for="(item, index) in 3" style="background-color: white;" class="w-100 mt-5">
                <div class="img">
                    <img :src="img[index]" alt="">
                </div>
                <div class="flex items-center justify-between px-5 pt-10">
                    <div class="px-5 pt-1" style="background-color: #EB0029;">
                        <div class="text-center text-[27px] font-bold" style="color: white;">
                            {{ day[index] }}
                        </div>
                        <div class="text-center text-[14px] pb-3" style="color: white;">Dec</div>
                    </div>
                    <div class="flex w-25 tems-center">
                        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/user.svg" alt="">
                        <div class="ml-3 font-bold text-[14px]" style="color: #6A676C;">By Admin</div>
                    </div>
                    <div class="flex items-center w-25 mr-5">
                        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/tag.svg" alt="">
                        <div class="ml-3 font-bold text-[14px]" style="color: #6A676C;">{{ food[index] }}</div>
                    </div>

                </div>
                <div class="font-bold px-5 py-5 text-[25px] content" style="line-height: 30px;">{{ content[index] }}
                </div>
                <div class="pl-5 pb-5  relative ">
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
        <div class="justify-center gap-6" style="width: 100%;  ">
            <div class="flex justify-around">
                <div v-for="(item, index) in logo" :key="index + '1'" 
                    style="place-items: center !important;">
                    <div class="img flex justify-center">
                        <img :src="item" alt="Logo" style="width: 250px !important;" />
                    </div>
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
    transition: color 1s ease;
}

.content:hover {
    color: red;
}

.img {
    overflow: hidden;
    width: 100%;
}

.img img {
    width: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
}

.img:hover img {
    transform: scale(1.1);
    position: relative;
}
</style>