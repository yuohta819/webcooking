<script setup>
const img = ['https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_1.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_2.png',
    'https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_3.png'
]
const type = ['https://www.ex-coders.com/php-template/fresheat/assets/img/bg/offerBG2_1.jpg','https://www.ex-coders.com/php-template/fresheat/assets/img/bg/offerBG2_2.jpg','https://www.ex-coders.com/php-template/fresheat/assets/img/bg/offerBG2_3.jpg']
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
const price = ['start price $25', 'start price $28','start price $55']
</script>

<template>
    <div class="img">
        <div class="text-center py-40">
            <div style="color: white; text-transform: uppercase;" class="text-[80px] font-black mb-3">Food Menu 01</div>
            <div style="color: white; " class="font-bold">Home <span class="ml-3">/</span> <span class="ml-3 font-bold"
                    style="color: red;">Food Menu 1</span></div>
        </div>
    </div>
    <div class="flex justify-between py-20">
        <div v-for="(item, index) in 3" class=" w-[500px] mx-4 flex justify-around"
         :style="{ backgroundImage: `url(${type[index]})` }">
            <div class="pl-5 py-10 self-center">
                <div class="font-bold text-[12px]" style="color: #EB0029;">{{ content[index] }}</div>
                <div class="text-[13px] font-bold pt-2"
                    style="color: white; text-transform: uppercase;">{{ price[index] }}</div>
                <div class="text-[22px] font-bold py-2"
                    style="color: white;">{{ name[index] }}</div>
                <div style="color: white; "
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
</template>

<style scoped>
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
.img {
    background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/breadcumb.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
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