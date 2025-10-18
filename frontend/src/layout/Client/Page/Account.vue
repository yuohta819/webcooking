<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import { useToast } from "vue-toastification";
import { useRouter } from 'vue-router';

const account = ref('');
const password = ref('');
const remember = ref(false); // ✅ trạng thái checkbox
const toast = useToast();
const router = useRouter();

// ✅ Tự động đăng nhập nếu có dữ liệu trong localStorage
onMounted(() => {
    const savedAccount = localStorage.getItem("account");
    const savedName = localStorage.getItem("name");
    const savedExpire = localStorage.getItem("expireTime");
    // ✅ Nếu từng đăng nhập trước đó, tự điền email vào ô input
    if (savedAccount) {
        account.value = savedAccount;
        remember.value = true; // tick lại ô Remember cho người dùng
    }
    if (savedAccount && savedName && savedExpire) {
        const now = new Date().getTime();
        if (now < parseInt(savedExpire)) {
            // Còn hạn 30 ngày → tự vào trang chủ
            router.push("/");
        } else {
            // Hết hạn → xóa
            localStorage.removeItem("account");
            localStorage.removeItem("name");
            localStorage.removeItem("expireTime");
        }
    }
});

async function handleSubmit() {
    // ✅ Kiểm tra định dạng email
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(account.value)) {
        toast.error("Please enter a valid email address!");
        return;
    }

    if (password.value.trim() === '') {
        toast.error("Password cannot be empty!");
        return;
    }

    try {
        const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/account/check`, {
            params: {
                account: account.value,
                password: password.value
            }
        });
        console.log(response.data)
        if (response.data !== '') {
            loginSuccess();

            // ✅ Nếu người dùng chọn “Remember for 30 days”
            if (remember.value) {
                const expireTime = new Date().getTime() + 30 * 24 * 60 * 60 * 1000; // 30 ngày
                localStorage.setItem("name", response.data[0].name);
                localStorage.setItem("account", response.data[0].username);
                localStorage.setItem("expireTime", expireTime.toString());
                localStorage.setItem("accountid", response.data[0].accountid)
            } else {
                // ✅ Lưu tạm trong sessionStorage (mất khi đóng trình duyệt)
                sessionStorage.setItem("name", response.data[0].name);
                sessionStorage.setItem("account", response.data[0].username);
                sessionStorage.setItem("accountid", response.data[0].accountid);

            }

            setTimeout(() => {
                router.push("/").then(() => {
                    location.reload();
                });
            }, 2000);
        } else {
            loginFail();
        }
    } catch (error) {
        toast.error("An error occurred while logging in!");
        console.error(error);
    }
}

function loginSuccess() {
    toast.success("Login successful!");
}

function loginFail() {
    toast.error("Login failed. Wrong account or password");
}

function handleAccount(name) {
    account.value = name;
}

function handlePassword(pass) {
    password.value = pass;
}

function handleToken() {
    localStorage.setItem("token", "google");
    window.location.href = `${import.meta.env.VITE_API_URL_BACKEND}/authorization/google`;
}
</script>

<template>
    <div class="img-2">
        <div class="text-center py-40">
            <div style="color: white; text-transform: uppercase;" class="text-[80px] font-black mb-3">MY ACCOUNT</div>
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
                        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/accountLogo.png"
                            alt="">
                    </div>

                    <div class="text-center text-[40px] font-black">Welcome Back</div>
                    <div class="text-center py-1 font-normal">Please Enter Your Details</div>

                    <div class="mt-10">
                        <input class="py-5 pl-3" v-model="account" type="text"
                            @input="handleAccount($event.target.value)" placeholder="Email"
                            style="width: 100%; background: white;">
                    </div>

                    <div class="mt-5">
                        <input class="py-5 pl-3" type="password" @input="handlePassword($event.target.value)"
                            placeholder="Password" style="width: 100%; background: white;">
                    </div>

                    <a href="/forgot">
                        <div class="text-end mt-5 font-medium" style="color: #EB0029;">Forgot Password?</div>
                    </a>

                    <!-- ✅ Checkbox remember -->
                    <div class="flex items-center">
                        <div class="ml-2">
                            <input style="transform: scale(1.6);" type="checkbox" v-model="remember">
                        </div>
                        <div class="ml-4" style="color: #615D5A;">Remember For 30 days</div>
                    </div>

                    <div class="my-5 relative" @click="handleSubmit">
                        <div class="py-3 text-center button rounded-[50px] flex items-center justify-around px-39"
                            style="background: #EB0029; color: white !important;">
                            <div class="font-bold text-[14px] relative z-3">Login</div>
                            <div class="box-1"></div>
                            <div class="box-2"></div>
                        </div>
                    </div>

                    <div class="my-5 relative" @click="handleToken">
                        <div class="py-3 text-center button rounded-[50px] flex items-center justify-around px-39"
                            style="background: white; color: white;">
                            <img class="relative z-3"
                                src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/googleLogo.png"
                                alt="">
                            <div class="font-bold text-[14px] relative z-3 content 2xl:block hidden">
                                Log In With Google
                            </div>
                            <div class="box-1"></div>
                            <div class="box-2"></div>
                        </div>
                    </div>

                    <div class="my-10 text-center">
                        <span>Don’t have an account? </span>
                        <span style="color: #EB0029;"><a href="/register">Sign Up</a></span>
                    </div>
                </div>

                <div class="w-140 lg:block hidden">
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/profile/profile.png" alt="">
                </div>
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
    transition: 0.5s ease;
    background: #FC781A;
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

input {
    border: 1px solid #D4DCFF;
    outline: none;
    border-radius: 8px;
}
</style>
