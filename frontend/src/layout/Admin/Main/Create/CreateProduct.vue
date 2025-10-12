<script setup>
import { ref } from 'vue'

import axios from 'axios'
import { useToast } from "vue-toastification";
const toast = useToast();
const name = ref("")
const img = ref("")
const decribe = ref("")
const price = ref("")
async function handleSubmit() {
  const res = await axios.post(`http://localhost:8080/api/create`, {
    img: img.value,
    name: name.value,
    describe: decribe.value,
    price: price.value
  })
  if (res.data == "") {
    toast.success("Create successful products!")
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
  <div class="bg-[#f8f5f0] p-6 max-w-6xl mx-auto">
    <h2 class="text-2xl font-semibold mb-4">Add Products</h2>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
      <!-- Billing -->
      <div>
        <input type="text" class="w-1/2 p-2 border rounded mb-4 w-full" @input="handleImg($event.target.value)"
          placeholder="Link img" />
        <input class="w-full p-2 border rounded mb-4 " style="margin: 15px 0;" @input="handleName($event.target.value)" placeholder="Name: " />
        <input class="w-full p-2 border rounded mb-4"  @input="handleDescribe($event.target.value)"
          placeholder="Describe: " />
        <input class="w-full p-2 border rounded mb-4" style="margin: 15px 0;" @input="handlePrice($event.target.value)" placeholder="Price: " />
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
