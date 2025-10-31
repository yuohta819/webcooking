<script setup>
import axios from "axios"
import { onMounted, ref, nextTick } from "vue";
import { Swiper, SwiperSlide } from 'swiper/vue'
import { useRouter } from "vue-router"
import 'swiper/css'
import 'swiper/css/navigation'
import 'swiper/css/pagination'
import { Autoplay, Navigation, Pagination } from 'swiper/modules'
const data = ref([])
const router = useRouter()
onMounted(async () => {
  const response = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/menu`)
  data.value = response.data.slice(0, 4)
})
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
function detail(id) {
  router.push(`/detail/${id}`)
}
</script>
<template>
  <div style="background-color: #F4F1EA;" class="relative pt-10 overflow-hidden">
    <!-- Tiêu đề -->
    <div class="flex justify-center items-center flex-wrap pt-10 sm:pt-20 font-bold gap-2 text-center">
      <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt=""
        class="w-6 sm:w-8" />
      <span class="text-[#FC7D1F] text-sm sm:text-base md:text-lg">BEST FOOD</span>
      <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt=""
        class="w-6 sm:w-8" />
    </div>

    <h2 class="text-center font-bold text-2xl sm:text-3xl md:text-4xl lg:text-[40px] py-8">
      Popular Food Items
    </h2>

    <!-- Danh sách món ăn -->
    <div class="w-full max-w-[1300px] mx-auto px-4 sm:px-10 md:px-20 lg:px-40 py-6 sm:py-12">
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8 sm:gap-10">
        <div v-for="item in data" :key="item._id"
          class="bg-white rounded-[10px] shadow-md flex flex-col items-center p-4 sm:p-6 cursor-pointer hover:scale-[1.02] transition-transform duration-300"
          @click="detail(item.id)">
          <div class="relative -mt-16 sm:-mt-20 w-28 sm:w-32 h-28 sm:h-32">
            <div class="w-full h-full img rounded-full absolute p-2"></div>
            <img :src="item.img" alt="" class="w-full h-full object-cover rounded-full p-2 z-10" />
          </div>
          <div class="text-center mt-6">
            <div class="font-bold text-lg sm:text-[21px]">{{ item.name }}</div>
            <div class="text-gray-500 text-sm sm:text-[17px] py-2">{{ item.describe }}</div>
            <div class="font-bold text-[18px] sm:text-[20px] text-[#F43C22]">
              ${{ item.price }}
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Hình nền trang trí -->
    <img class="absolute top-[120px] right-[10px] sm:right-[20px] w-20 sm:w-32 lg:w-40 img_1"
      src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bestFoodItemsShape1_2.png" alt="" />
    <img class="absolute top-[360px] left-0 w-16 sm:w-24 lg:w-32 img_2"
      src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/bestFoodItemsShape1_1.png" alt="" />

    <!-- Ưu đãi -->
    <div class="flex flex-wrap justify-center gap-6 sm:gap-8 md:gap-10 py-10 sm:py-16 md:py-20">
      <div v-for="(item, index) in 3" :key="index"
        class="relative w-full sm:w-[340px] md:w-[400px] lg:w-[450px] flex justify-between items-center rounded-lg overflow-hidden"
        style="background: url('https://www.ex-coders.com/php-template/fresheat/assets/img/bg/offerBG1_1.jpg'); background-size: cover;">
        <div class="pl-4 sm:pl-5 py-8 sm:py-10 flex-1">
          <div class="font-bold text-xs sm:text-sm text-[#EB0029]">{{ content[index] }}</div>
          <div class="text-white text-lg sm:text-xl md:text-2xl font-bold py-2 drop-shadow-lg">
            {{ name[index] }}
          </div>
          <div class="font-extrabold text-[11px] sm:text-[12px] text-[#FC791A] drop-shadow-md">
            Limits Time Offer
          </div>

          <a href="/menu">
            <div
              class="mt-6 sm:mt-8 px-4 py-3 sm:py-4 w-32 sm:w-40 flex items-center justify-around text-[13px] sm:text-[15px] button mx-auto sm:mx-0">
              <div class="box-1"></div>
              <div class="box-2"></div>
              <div class="relative z-10 flex items-center gap-2">
                <button>
                  ORDER NOW
                </button>
                <i class="fa-solid fa-arrow-right z-10"></i>
              </div>
            </div>
          </a>
        </div>

        <div class="relative w-24 sm:w-32 md:w-36 flex-shrink-0">
          <img class="img_1 w-full"
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/offerShape1_4.png" alt="" />
          <img class="absolute top-8 left-0 sm:top-10 w-full" :src="img[index]" alt="" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.swiper-box {
  margin-left: auto;
  margin-right: auto;
  position: relative;
  overflow: hidden;
  list-style: none;
  padding: 0;
  z-index: 1;
  display: block;
}

.swiper-box .swiper-slide {
  width: 20% !important;
  /* hoặc bao nhiêu px bạn muốn */
}

button {
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


button:hover .box-1::before {
  right: 0;
}

button:hover .box-2::before {
  left: 0;
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