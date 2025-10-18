<script setup>

</script>

<template>
    <div style="background-color: #FFFFFF;" class="flex relative">
        <div class="hidden 2xl:block">
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/aboutShape1_1.png" alt=""
                width="700">
            <img class="absolute top-22 left-27 img  w-[23%]"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/aboutShape1_3.png" alt="" >
            <img class="absolute top-0 left-83"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/aboutShape1_2.png" alt="">
        </div>
        <div>
            <div class="flex justify-center pt-27 font-bold ">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
                <span class="px-1 font-bold text-[20px]" style="color: #FC7D1F;">ABOUT US</span>
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            </div>
            <h2 class="py-1 text-center text-[43px] items-justify font-black leading-[50px]">Variety Of Flavours From
                <br> American Cuisine
            </h2>
            <div class="pt-2 text-center mx-10" style="color: #615D5A;">It is a long established fact that
                a reader will be distracted the readable content of a page when looking at layout
                the point established fact that</div>
            <div class="text-center mt-10 relative pb-10">
                <button class="px-5 py-3">
                    <span class="relative z-10 ">ORDER NOW</span>
                    <div class="box-1"></div>
                    <div class="box-2"></div>
                    <i style="background-color: white; color: red;" class="p-1 ml-2 fa-solid fa-arrow-right z-10"></i>
                </button>
            </div>
        </div>
        <div class="hidden 2xl:block">
            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/aboutShape1_4.png" alt=""
                width="700">
            <img class="absolute top-22 right-22 img w-[23%]"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/aboutShape1_6.png" alt="" >
            <img class="absolute bottom-0 right-85"
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/aboutShape1_5.png" alt="">
        </div>
    </div>
</template>
<style scoped>
.img {
    animation: spin 20s linear infinite;
    z-index: 5;
}

@keyframes spin {
    0% {
        transform: rotate(0deg);
    }

    100% {
        transform: rotate(360deg);
    }
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