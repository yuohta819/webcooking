<script setup>
import axios from "axios";
import { ref } from "vue";
import { useToast } from "vue-toastification";
import { useRouter } from "vue-router";

const account = ref("");
const password = ref("");
const confirmPassword = ref("");
const name = ref("");
const phone = ref("");
const emailError = ref("");
const toast = useToast();
const router = useRouter();

function validateEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

async function handleSubmit() {
    // Kiểm tra input trước khi gửi
    if (!name.value || !account.value || !password.value || !confirmPassword.value || !phone.value) {
        toast.error("Please fill in all fields!");
        return;
    }

    if (!validateEmail(account.value)) {
        toast.error("Invalid email format!");
        return;
    }

    if (password.value !== confirmPassword.value) {
        toast.error("Passwords do not match!");
        return;
    }

    // Kiểm tra định dạng số điện thoại (tuỳ chọn)
    const phoneRegex = /^[0-9]{9,11}$/;
    if (!phoneRegex.test(phone.value)) {
        toast.error("Invalid phone number format!");
        return;
    }

    try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/account/save`, {
            params: {
                account: account.value,
                password: password.value,
                name: name.value,
                phone: phone.value
            }
        });

        if (response.data !== false) {
            toast.success("Account created successfully!");
            setTimeout(() => {
                router.push("/account/login").then(() => location.reload());
            }, 2000);
        } else {
            toast.error("Account already exists");
        }
    } catch (error) {
        toast.error("Server error. Please try again later!");
    }
}

// Theo dõi lỗi email khi nhập
function handleAccount(value) {
    account.value = value;
    emailError.value = validateEmail(value) ? "" : "Invalid email format";
}
</script>

<template>
    <div class="img-2">
        <div class="text-center py-40">
            <div style="color: white; text-transform: uppercase;" class="text-[80px] font-black mb-3">
                MY ACCOUNT
            </div>
            <div style="color: white;" class="font-bold">
                Home <span class="ml-3">/</span>
                <span class="ml-3 font-bold" style="color: red;">My Account</span>
            </div>
        </div>
    </div>

    <div style="background-color: #F4F1EA;" class="py-30 lg:px-30 rounded-[20px]">
        <div style="background-color: white;" class="py-7 rounded-[15px]">
            <div class="flex justify-between lg:mx-10">
                <div class="px-10 w-145 rounded-[20px]" style="background-color: #F4F1EA;">
                    <div class="place-items-center py-10">
                        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/accountLogo.png" alt="">
                    </div>
                    <div class="text-center text-[40px] font-black">Welcome Back</div>
                    <div class="text-center py-1 font-normal">Please Enter Your Details</div>

                    <!-- Name -->
                    <div class="mt-10">
                        <input class="py-5 pl-3" type="text" v-model="name" placeholder="Name"
                            style="width: 100%; background: white;" />
                    </div>

                    <!-- Email -->
                    <div class="mt-5">
                        <input class="py-5 pl-3" type="email" :class="emailError ? 'border-red-500' : ''"
                            @input="handleAccount($event.target.value)" placeholder="Email"
                            style="width: 100%; background: white;" />
                        <div v-if="emailError" class="text-red-500 text-sm mt-1">{{ emailError }}</div>
                    </div>

                    <!-- Phone -->
                    <div class="mt-5">
                        <input class="py-5 pl-3" type="text" v-model="phone" placeholder="Phone number"
                            style="width: 100%; background: white;" />
                    </div>

                    <!-- Password -->
                    <div class="mt-5">
                        <input class="py-5 pl-3" type="password" v-model="password" placeholder="Password"
                            style="width: 100%; background: white;" />
                    </div>

                    <!-- Confirm Password -->
                    <div class="mt-5">
                        <input class="py-5 pl-3" type="password" v-model="confirmPassword"
                            placeholder="Confirm Password" style="width: 100%; background: white;" />
                    </div>

                    <div class="flex items-center justify-between mt-5">
                        <div class="flex items-center justify-between">
                            <div class="ml-2">
                                <input style="transform: scale(1.6);" type="checkbox">
                            </div>
                            <div class="ml-4" style="color: #615D5A;">Remember For 30 days</div>
                        </div>
                        <a href="/forgot">
                            <div class="text-end font-medium" style="color: #EB0029;">
                                Forgot Password?
                            </div>
                        </a>
                    </div>

                    <!-- Submit Button -->
                    <div class="my-5 relative" @click="handleSubmit">
                        <div class="py-3 text-center button rounded-[50px] flex items-center justify-around px-39"
                            style="background: #EB0029; color: white !important;">
                            <div class="font-bold text-[14px] relative z-3">Register</div>
                            <div class="box-1"></div>
                            <div class="box-2"></div>
                        </div>
                    </div>

                    <div class="my-10 text-center">
                        <span>Already have an account? </span>
                        <span style="color: #EB0029;">
                            <a href="/account">Login</a>
                        </span>
                    </div>
                </div>

                <div class="w-140 lg:block hidden " style="align-self: center;">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/profile/profile.png" alt="">
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
input {
    border: 1px solid #D4DCFF;
    outline: none;
    border-radius: 8px;
}

input.border-red-500 {
    border-color: #f87171;
}

.button {
    color: white;
    height: 40.8px;
    position: relative;
    overflow: hidden;
    z-index: 5;
    cursor: pointer;
}

.box-1,
.box-2 {
    position: absolute;
    width: 100%;
    height: 50%;
    left: 0;
    overflow: hidden;
}

.box-1 {
    top: 0;
}

.box-2 {
    bottom: 0;
}

.box-1::before,
.box-2::before {
    content: '';
    position: absolute;
    width: 100%;
    height: 100%;
    background: #FC781A;
    transition: all 0.5s ease;
}

.box-1::before {
    top: 0;
    right: -100%;
}

.box-2::before {
    bottom: 0;
    left: -100%;
}

.button:hover .box-1::before {
    right: 0;
}

.button:hover .box-2::before {
    left: 0;
}

.img-2 {
    background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/breadcumb.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    background-position: center;
}
</style>
