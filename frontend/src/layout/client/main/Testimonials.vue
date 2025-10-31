<script setup>


</script>

<template>
  <section class="relative bg-[#181818] overflow-hidden">
    <!-- Background image with overlay -->
    <div class="relative">
      <img
        src="https://www.ex-coders.com/php-template/fresheat/assets/img/testimonial/testimonialThumb1_1.png"
        alt="background"
        class="w-full h-[500px] sm:h-[600px] lg:h-[700px] object-cover opacity-70"
      />
      <div
        class="absolute inset-0 bg-gradient-to-b from-black/60 via-black/30 to-[#181818]/90"
      ></div>

      <!-- Play button -->
      <img
        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/player.svg"
        alt="play"
        class="absolute top-1/2 left-1/2 w-16 sm:w-20 lg:w-24 -translate-x-1/2 -translate-y-1/2 hover:scale-110 transition-transform duration-300"
      />

      <!-- Decorative shape -->
      <img
        src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/testimonialShape1_1.png"
        alt="decor"
        class="absolute right-0 top-16 w-28 sm:w-44 lg:w-60 opacity-40 animate-pulse"
      />

      <!-- Section Title -->
      <div
        class="absolute top-6 sm:top-12 left-1/2 -translate-x-1/2 xl:translate-x-0 xl:left-auto xl:right-[18rem] text-center xl:text-left"
      >
        <div class="flex justify-center xl:justify-start items-center gap-2">
          <img
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg"
            alt=""
            class="w-4 sm:w-5"
          />
          <span class="text-[#FC7D1F] font-semibold text-sm uppercase tracking-wider"
            >Testimonials</span
          >
          <img
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg"
            alt=""
            class="w-4 sm:w-5"
          />
        </div>
        <h2
          class="text-white font-black text-2xl sm:text-4xl lg:text-5xl leading-tight mt-2 drop-shadow-lg"
        >
          What Our Clients Say
        </h2>
      </div>

      <!-- Testimonial Card -->
      <div
        class="absolute bottom-[-6rem] sm:bottom-[-8rem] left-1/2 transform -translate-x-1/2 bg-white/95 backdrop-blur-md rounded-3xl shadow-2xl p-6 sm:p-10 w-[90%] sm:w-[70%] lg:w-[480px] transition-all hover:shadow-[#FC7D1F]/40 hover:scale-[1.02]"
      >
        <div class="flex items-center justify-between">
          <div class="flex items-center">
            <img
              src="https://www.ex-coders.com/php-template/fresheat/assets/img/testimonial/testimonialProfile1_1.png"
              alt="profile"
              class="w-16 h-16 rounded-full object-cover"
            />
            <div class="pl-4">
              <div class="font-bold text-lg sm:text-xl text-gray-800">
                Albert Flores
              </div>
              <div class="text-gray-500 text-sm">Web Designer</div>
              <img
                src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/star.svg"
                alt="stars"
                class="mt-1 w-20"
              />
            </div>
          </div>
          <img
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/quote.svg"
            alt="quote"
            class="w-8 sm:w-10 opacity-70"
          />
        </div>
        <p class="mt-5 text-gray-600 text-base sm:text-lg leading-relaxed">
          “Penatibus magnis dis parturient montes nascetur ridiculus mus. Ut id
          lorem ac enim vestibulum blandit nec sit amet felis.”
        </p>
      </div>
    </div>

    <!-- Moving marquee -->
    <div class="mt-[300px] sm:mt-[350px] lg:mt-[400px] bg-[#181818] py-16 overflow-hidden relative">
      <div
        class="marquee-content flex whitespace-nowrap animate-marquee gap-16"
      >
        <span
          v-for="text in ['chicken pizza','grilled chicken','burger','fresh pasta']"
          :key="text"
          class="text-[40px] sm:text-[70px] font-extrabold uppercase text-white opacity-15 tracking-wider"
        >
          {{ text }}
        </span>
      </div>
    </div>
  </section>
</template>

<style scoped>
@keyframes marquee {
  0% {
    transform: translateX(0%);
  }
  100% {
    transform: translateX(-50%);
  }
}
.animate-marquee {
  animation: marquee 20s linear infinite;
}
</style>

