<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

// ✅ Import Swiper & Modules
import { Swiper, SwiperSlide } from "swiper/vue";
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import { Autoplay, Navigation, Pagination } from "swiper/modules";

// ✅ Khai báo biến reactivity
const data = ref([]);
const img = [
  "https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_1.png",
  "https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_2.png",
  "https://www.ex-coders.com/php-template/fresheat/assets/img/offer/offerThumb1_3.png",
];
const content = ["ON THIS WEEK", "WELCOME FRESHEAT", "ON THIS WEEK"];
const name = ["SPICY FRIED CHICKEN", "TODAY SPECIAL FOOD", "SPECIAL CHICKEN ROLL"];

const router = useRouter();

// ✅ Gọi API khi component mount
onMounted(async () => {
  try {
    const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/menu`);
    data.value = response.data.slice(0, 4);
  } catch (error) {
    console.error("❌ Lỗi khi tải dữ liệu menu:", error);
  }
});

// ✅ Chuyển trang chi tiết sản phẩm
function detail(id) {
  router.push(`/detail/${id}`);
}
</script>

<template>
  <div class="containerr relative w-[100%]">
    <div>
      <div class="absolute icon1">
        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape1_1.png" alt="">
      </div>
      <div class="absolute icon2">
        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape1_2.png" alt="">
      </div>
    </div>
    <div class="justify-around pt-33 pl-28">
      <div>
        <div class="font-bold text-[22px]" style="color: #F43C22;">WELCOME FRESHEAT</div>
        <div class="py-3 text-[48px] font-bold" style="color: white; font-family: Roboto, sans-serif;">
          TODAY SPACIAL FOOD</div>
        <div class="font-bold text-[29px]" style="color: #FC781A;"> Limits Time Offer</div>
        <div class="pt-10 pb-30 relative ">
          <button class="px-5 py-3">
            <span class="relative z-10">ORDER NOW</span>
            <div class="box-1"></div>
            <div class="box-2"></div>
            <i style="background-color: white; color: red;" class="p-1 ml-4 fa-solid fa-arrow-right z-10"></i>
          </button>
        </div>
      </div>
      <div class="icon3 absolute top-28 right-32 2xl:block hidden">
        <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/cta/ctaThumb1_1.png" alt="" width="650">
      </div>
    </div>
    <div class="absolute icon2 right-0">
      <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/ctaShape1_3.png" alt="">
    </div>

  </div>
</template>

<style scoped>
.containerr {
  background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/ctaBG1_1.jpg');
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
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

.icon1 {
  animation: move1 6s ease infinite;
  left: 10px;
}

@keyframes move1 {

  0%,
  100% {
    transform: translateX(0);
  }

  50% {
    transform: translateX(30px);
  }
}

.icon2 {
  animation: move2 6s ease infinite;
  bottom: 30px;
}

@keyframes move2 {

  0%,
  100% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(30px);
  }
}

.icon3 {
  animation: move3 5s ease infinite;
}

@keyframes move3 {

  0%,
  100% {
    transform: translateX(0);
  }

  50% {
    transform: translateX(30px);
  }
}
</style>