<script setup>
import axios from 'axios';
import { onMounted } from 'vue';
import { ref } from 'vue';
import { useToast } from "vue-toastification";
import { useRouter } from 'vue-router';
const account = ref('')
const password = ref('')
const name = ref('')
const toast = useToast();
const router = useRouter()
async function handleSubmit() {
    const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/admin/save`, {
        params: {
            password: password.value,
            name: name.value
        }

    })
    if (response.data !== false) {
        loginSuccess()

    } else {
        loginFail();
    }

}

function loginSuccess() {
    toast.success("Account created successfully!");
}
function loginFail() {
    toast.error("Account already exists");
}
function handlePassword(pass) {
    password.value = pass
}
function handleName(infor) {
    name.value = infor
}
</script>

<template>
    <div style="background-color: white;" class=" py-7 rounded-[15px]">
        <div class="flex justify-between lg:mx-10">
            <div class="px-10 w-145 rounded-[20px]" style="background-color: #F4F1EA;">
                <div class="place-items-center py-10">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/accountLogo.png" alt="">
                </div>
                <div class="text-center text-[40px] font-black">Welcome Back</div>
                <div class="text-center py-1 font-normal">Please Enter Your Details</div>
                <div class="mt-10 ">
                    <input class="py-5 pl-3" type="text" @input="handleName($event.target.value)" placeholder="Name"
                        style="width: 100%; background: white;">
                </div>
                <div class="mt-5 ">
                    <input class="py-5 pl-3" type="text" @input="handlePassword($event.target.value)"
                        placeholder="Password" style="width: 100%; background: white;">
                </div>
                <div class="my-5  relative" @click="handleSubmit">
                    <div class="py-3 text-center button  rounded-[50px] flex items-center justify-around px-39"
                        style="background: #EB0029; color: white !important;">
                        <div class="font-bold text-[14px] relative z-3 "> Register
                        </div>
                        <div class="box-1 "></div>
                        <div class="box-2"></div>
                    </div>
                </div>
            </div>
            <div class="w-140 lg:block hidden">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/profile/profile.png" alt="">
            </div>
        </div>
    </div>
</template>

<style scoped>
.content {
    color: black;
    transition: color 1s ease;
}

.content:hover {
    color: white;
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


.button:hover .box-1::before {
    right: 0;
    color: white;
}

.button:hover .box-2::before {
    left: 0;
    color: white;
}

.img-2 {
    background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/breadcumb.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
}

input {
    border: 1px solid #D4DCFF;
    outline: none;
    border-radius: 8px;
}
</style>