<script setup>

import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';
import { useToast } from "vue-toastification";
import PopularDishes from '../main/PopularDishes.vue';

const router = useRoute()
const id = router.params.id
const data = ref([])
const count = ref(1)
const toast = useToast();
const name = localStorage.getItem("name")
const account = localStorage.getItem("account")
onMounted(async () => {
    const res = await axios.get(`http://localhost:8080/api/detail/${id}`)
    data.value = res.data
})
function handlleCount(name) {
    if (name === "asc") {
        count.value++
    }
    if (count.value > 1) {
        if (name === "desc") {
            count.value--
        }
    }
}
async function handleSaveInfor(type) {
    if (name) {
        const res = await axios.get(`http://localhost:8080/api/cart`, {
            params: {
                id: type,
                name: name,
                count: count.value,
                account: account
            }
        })
        toast.success("Product added successfully!");
    } else {
        toast.warning("Must be logged in to account!");
        setTimeout(() => {
            router.push('/account')
        }, 2000)
    }
}
</script>

<template>
    <div class="img-1">
        <div class="text-center py-40">
            <div style="color: white; text-transform: uppercase;" class="text-[80px] font-black mb-3">Shop details</div>
            <div style="color: white; " class="font-bold">Home <span class="ml-3">/</span> <span class="ml-3 font-bold"
                    style="color: red;">Shop details</span></div>
        </div>
    </div>
    <div style="background: #F4F1EA;" class="px-4 pt-29">
        <div v-for="item in data" class="flex xl:px-45 justify-center py-15 gap-7 flex-wrap" style="background-color: white;">
            <div style="background-color: #F4F1EA; width: 50%;" class="place-items-center py-14 rounded-[10px]">
                <div class="place-items-center relative">
                    <div class="img absolute inset-0 rounded-full p-2 w-full h-full overflow-hidden z-0">
                        <!-- vòng quay -->
                    </div>
                    <img  src="https://www.ex-coders.com/php-template/fresheat/assets/img/dishes/dishes3_1.png" alt=""
                        class="p-2">
                </div>
            </div>
            <div style="width: 45%;" class="py-3">
                <div class="flex justify-between items-center">
                    <div class="font-bold text-[25px]">{{ item.name }}</div>
                    <div class="font-extrabold text-[36px]">${{ Math.floor(item.price) }}</div>
                </div>
                <div class="flex gap-1">
                    <div v-for="n in 5" :key="n" class="rate pt-1">
                        <div :class="n <= item.rate ? 'text-black-500' : 'text-yellow-500'">
                            <i class="text-[12px] fa-solid fa-star"></i>
                        </div>
                    </div>
                </div>

                <div class="content">(2 customer reviews)</div>
                <div style="width: 100%; height: 1px; background-color: #615D5A;" class="my-5"></div>
                <div style="color: #615D5A;">Aliquam hendrerit a augue insuscipit. Etiam aliquam massa quis des
                    mauris commodo venenatis ligula commodo leez sed blandit convallis dignissim
                    onec vel pellentesque neque.</div>
                <div class="flex gap-5 my-5 items-center">
                    <div style="color: #615D5A;">Quantity </div>
                    <div class="flex gap-1">
                        <div style="border: 1px solid #D2D2D1;" class="w-14 rounded-[5px]">
                            <div class=" flex items-center h-full justify-center">{{ count }}</div>
                        </div>
                        <div class="px-2 py-1 rounded-[4px]" @click="handlleCount('asc')"
                            style="background-color: white; border: 1px solid #D2D2D1;"><i style="color: #D2D2D1;"
                                class="fa-solid fa-plus"></i>
                        </div>
                        <div class="px-2 py-1 rounded-[4px]" @click="handlleCount('desc')"
                            style="background-color: white; border: 1px solid #D2D2D1;"><i class="fa-solid fa-minus"
                                style="color: #D2D2D1;"></i>
                        </div>
                    </div>
                </div>
                <div class="flex gap-5 flex-wraps">
                    <div class="flex items-center xl:justify-between justify-center w-[170px] px-6 py-3 button"
                        @click="handleSaveInfor(item.id)" style="background-color: #E10E21;">
                        <div style="color: white;" class="relative z-5 xl:block hidden">Add to Cart
                        </div>
                        <div class="box-1"></div>
                        <div class="box-2"></div>
                        <div style="color: white;"><i class="fa-solid fa-cart-shopping"></i></div>
                    </div>
                    <div class="flex items-center  xl:justify-between justify-center w-47 px-6 py-3 button"
                        style="background-color: #FC791A;">
                        <div style="color: white;" class="relative z-4 xl:block hidden">ADD TO wishlist</div>
                        <div style="color: white;"><i class="fa-solid fa-heart"></i></div>
                        <div class="box-1"></div>
                        <div class="box-2"></div>
                    </div>
                </div>
            </div>
        </div>
        <div style="background-color: white;" class="xl:px-45">
            <div class="font-bold text-[33px]">Product Description</div>
            <div class="my-3 text-[17px]" style="color: #5C6574;">
                Neque porro est qui dolorem ipsum quia quaed inventor veritatis et quasi
                architecto beatae vitae dicta sunt explicabo. Aelltes port lacus quis enim
                var sed efficitur turpis gilla sed sit amet finibus eros. Lorem Ipsum is
                simply dummy text of the printing and typesetting industry.
            </div>
            <div class="mt-7 text-[17px]" style="color: #5C6574;">
                Neque porro est qui dolorem ipsum quia quaed inventor veritatis et quasi
                architecto beatae vitae dicta sunt explicabo. Aelltes port lacus quis enim
                var sed efficitur turpis gilla sed sit amet finibus eros. Lorem Ipsum is
                simply dummy text of the printing and typesetting industry.
            </div>
            <div class="my-10" style="width: 100%; height: 1px; background-color: #D2D2D1;"></div>
        </div>
    </div>
    <PopularDishes />

</template>
<style scoped>
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

.img-1 {
    background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/breadcumb.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
}

.content {
    color: #615D5A;
    cursor: pointer;
}

.content:hover {
    color: #D02735;
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
</style>