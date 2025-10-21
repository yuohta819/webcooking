<script setup>
import { onMounted, ref } from 'vue'

import axios from 'axios'
import { useToast } from "vue-toastification";
import { useRoute } from 'vue-router';
const toast = useToast();
const name = ref("")
const img = ref("")
const decribe = ref("")
const price = ref("")
const data = ref([])
const router = useRoute()
const account = localStorage.getItem("account")
onMounted(async () => {
    const res = await axios.get(`http://localhost:8080/api/detail/${router.params.id}`)
    data.value = res.data
})
async function handleSubmit() {
     if (!account) {
        toast.warning("Please login to your account.")
        return;
    } 
    const fields = [
        { ref: name, key: 'name' },
        { ref: img, key: 'img' },
        { ref: decribe, key: 'decribe' },
        { ref: price, key: 'price' }
    ]

    fields.forEach(({ ref, key }) => {
        if (ref.value === "") {
            ref.value = data.value[0][key]
        }
    })
    const res = await axios.post(`http://localhost:8080/api/edit`, {
        id: router.params.id,
        img: img.value,
        name: name.value,
        describe: decribe.value,
        price: price.value
    })
    if (res.data == "yes") {
        toast.success("Edited successful products!")
    }
}
function handleImg(infor) {
    img.value = infor
}
function handleName(infor) {
    name.value = infor
}
function handleDescribe(infor) {
    decribe.value = infor
}
function handlePrice(infor) {
    price.value = infor
}
const shipDifferent = ref(false)
</script>

<template>
    <div class="img-2">
        <div class="text-center py-40">
            <div style="color: white; text-transform: uppercase;" class="text-[80px] font-black mb-3">Eddit Products</div>
            <div style="color: white; " class="font-bold">Home <span class="ml-3">/</span> <span class="ml-3 font-bold"
                    style="color: red;">Eddit Products</span></div>
        </div>
    </div>
    <div class="bg-[#f8f5f0] p-6 max-w-6xl mx-auto my-20">
        <h2 class="text-2xl font-semibold mb-4">Eddit Products</h2>
        <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
            <!-- Billing -->
            <div v-for="item in data">
                <input type="text" class="w-1/2 p-2 border rounded mb-4 w-full" v-model="item.img"
                    @input="handleImg($event.target.value)" placeholder="Link img" />
                <input class="w-full p-2 border rounded mb-4" v-model="item.name"
                    @input="handleName($event.target.value)" placeholder="Name: " />
                <input class="w-full p-2 border rounded mb-4" v-model="item.describe"
                    @input="handleDescribe($event.target.value)" placeholder="Describe: " />
                <input class="w-full p-2 border rounded mb-4" v-model="item.price"
                    @input="handlePrice($event.target.value)" placeholder="Price: " />
                <div class="flex items-center justify-between w-50 px-6 py-3 button" @click="handleSubmit"
                    style="background-color: #E10E21;">
                    <div style="color: white;" class="relative z-5">Add to Product
                    </div>
                    <div class="box-1"></div>
                    <div class="box-2"></div>
                    <div style="color: white;"><i class="fa-solid fa-bowl-food"></i></div>
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
