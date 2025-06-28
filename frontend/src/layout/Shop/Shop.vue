<script setup>
import axios from "axios"
import { onMounted, ref, nextTick } from "vue";
import { useRouter } from "vue-router";
const data = ref([])
const data1 = ref([])
const data3 = ref([])
const router = useRouter()
const price1 = ['23', '30', '45', '70']
const price = ['54', '34', '50', '76']
onMounted(async () => {
    const response = await axios.get("http://localhost:8080/api/menu")
    data.value = response.data.slice(0, 12)
    data1.value = [...data.value]
})
onMounted(async () => {
    const response = await axios.get('http://localhost:8080/api/menu')
    data3.value = response.data.slice(0, 4)

})

function handleChange(type) {
    if (type === 'high') {
        data1.value = [...data.value].sort((a, b) => a.price - b.price)
    } else if (type === 'low') {
        data1.value = [...data.value].sort((a, b) => b.price - a.price)
    } else {
        data1.value = [...data.value]
    }
}
function handleSearch(name) {
    data1.value = data.value.filter(item => item.name.trim().toLowerCase().includes(name.toLowerCase().trim()))
}
function handleClick(id) {
    router.push(`/detail/${id}`).then(() => {
        location.reload()
    })
}
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
    <div class="img-2">
        <div class="text-center py-40">
            <div style="color: white; text-transform: uppercase;" class="text-[80px] font-black mb-3">Shop</div>
            <div style="color: white; " class="font-bold">Home <span class="ml-3">/</span> <span class="ml-3 font-bold"
                    style="color: red;">Shop</span></div>
        </div>
    </div>
    <div class="flex py-30 gap-5 justify-between px-5 flex-wrap" style="background: #F4F0E8;">
        <div style="width: 24%;" class="xl:block hidden" >
            <div class="px-6 py-9 rounded-[15px]" style="background: white;">
                <div class="text-[20px] font-bold mb-5">Search</div>
                <div style="background: #EB0029;" class="h-[2px] w-20">
                </div>
                <div class="flex mt-5 px-5 py-3 rounded-[10px] justify-between" style="background: #F4F0E8;">
                    <input type="text" placeholder="Search here" @input="handleSearch($event.target.value)">
                    <div>
                        <i class="fa-solid fa-magnifying-glass"></i>
                    </div>
                </div>
            </div>
            <div class="px-6 py-9 rounded-[15px] mt-10" style="background: white;">
                <div class="text-[20px] font-bold mb-5">Search</div>
                <div style="background: #EB0029;" class="h-[2px] w-20">
                </div>
                <div class="mt-5  pt-3 rounded-[10px] w-30" style="font-family: Epilogue, sans-serif;">
                    <span style="background: #F4F0E8;" class="rounded-[5px] px-4 py-2">Cheese</span>
                    <span style="background: #F4F0E8;" class="rounded-[5px] px-4 py-2 ml-1">Cocktail</span>
                    <span style="background: #F4F0E8;" class="rounded-[5px] px-4 py-2 ml-1">Drink</span>
                </div>
                <div class="mt-5  pb-1 rounded-[10px] w-30" style="font-family: Epilogue, sans-serif;">
                    <span style="background: #F4F0E8;" class="rounded-[5px] px-4 py-2 ">Uncategorized</span>
                    <span style="background: #F4F0E8;" class="rounded-[5px] px-4 py-2 ml-1">Pizza</span>
                </div>
                <div class="mt-5  pb-1 rounded-[10px] w-30" style="font-family: Epilogue, sans-serif;">
                    <span style="background: #F4F0E8;" class="rounded-[5px] px-4 py-2 ml-1">Non Veg</span>
                </div>
            </div>
            <div class="px-6 py-9 rounded-[15px] mt-10" style="background: white;">
                <div class="text-[20px] font-bold mb-5">Filter By Price</div>
                <div style="background: #EB0029;" class="h-[2px] w-20">
                </div>
                <div v-for="(item, index) in data3">
                    <div class="flex px-7 my-5 mb-5 py-4 justify-between items-center rounded-[10px] item">
                        <div>
                            <img :src=item.img alt="">
                        </div>
                        <div class="w-37">
                            <div class="item font-medium" style="border: none;">{{ item.name }}</div>
                            <div class="my-1">
                                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/star3.svg"
                                    alt="">
                            </div>
                            <div>
                                <span class="font-bold" style="color: #EB0029;"> {{ price1[index] }}$</span>
                                <span class="ml-3 font-bold" style="color: #6B7381;">{{ price[index] }}$</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="width: 74%;" >
            <div>
                <div class="flex  py-5 lg:justify-between justify-center  flex-wrap items-center">
                    <div style="color: #615D5A;">Showing 1 - 12 of 30 Results</div>
                    <div class="flex w-70 justify-between py-5">
                        <select name="" id="" class="px-5 py-3 appearance-none relative"
                            style="background-color: white; color: #615D5A;"
                            @change="handleChange($event.target.value)">
                            <option value="Default Sorting" selected>Default Sorting</option>
                            <option value="high">Sort by price: low to high</option>
                            <option value="low">Sort by price: high to low</option>
                        </select>
                        <div style="color: #F43C22;"><i class="fa-solid fa-grip"></i></div>
                        <div><i class="fa-solid fa-list"></i></div>
                    </div>
                </div>
                <div style="background-color: #F4F1EA;" class=" flex">
                    <div class="flex flex-wrap gap-11 justify-around">
                        <div v-for="item in data1" style="background-color: white" class="w-60 mt-30 rounded-[15px]">
                            <div class="place-items-center " style="margin-top: -100px;">
                                <div class="w-40 h-40 img rounded-[50%]  p-2 left-8"></div>
                                <img :src="item.img" alt="" width="160" class="p-2" style="margin-top: -158px;">
                            </div>
                            <div class="text-center px-5  text-center py-5">
                                <div class="font-bold text-[21px]">{{ item.name }}</div>
                                <div class="text-[18px] py-2" style="color: #7D8490">{{ item.describe }}</div>
                                <div class="font-bold text-[20px]" style="color: #F43C22">${{ item.price }}</div>
                            </div>
                            <div class="flex justify-center mb-4  " @click="handleClick(item.id)">
                                <div class="px-7 py-2 button rounded-[16px] relative"
                                    style="background-color: rgba(235, 0, 41, 0.1);">
                                    <div class="flex items-center">
                                        <div class=" z-10 font-bold text-[15px] content">ORDER NOW</div>
                                        <div class="box-1 "></div>
                                        <div class="box-2"></div>
                                        <div>
                                            <i style=" transform: scale(0.8);"
                                                class="content p-1 ml-2 fa-solid fa-basket-shopping z-10"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style scoped>
.img-2 {
    background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/breadcumb.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
}

select {
    outline: none;
    border-radius: 5px;
}

.content {
    color: red;
}

.button:hover .content {
    color: white;
}

.button {
    color: white;
    height: 40.8px;
    position: relative;
    overflow: hidden;
    z-index: 5;
    cursor: pointer;
}

.button .button,
.button i {
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
    background: #EB0029;
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
    background: #EB0029;
    transition: left 0.5s ease;
}


.button:hover .box-1::before {
    right: 0;
    color: white;
}

.button:hover .box-2::before {
    left: 0;
    color: white;
}

.button:hover .img {
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

input {
    outline: none;
}

.item {
    border: 1px solid #CECFC9;
}

.item:hover {
    border: 1px solid #EB0029;
    color: #EB0029;
}
</style>
