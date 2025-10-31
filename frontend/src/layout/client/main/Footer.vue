<script setup>

const icon = ['fa-solid fa-location-dot', 'fa-solid fa-envelope', 'fa-solid fa-phone-volume']
const content = ['4648 Rocky Road Philadelphia', 'info@exmple.com', '+88 0123 654 99']
const name = ['Address', 'Send Email', 'call emergency']
const type = ['About Us', 'Our Gallery', 'Our Blogs', 'FAQ’S', 'Contact Us']
const title = ['Burger King', 'Pizza king', ' Fresh Food', 'Vegetable', 'Desserts']
const quickLinks = [
  { name: "Home", path: "/" },
  { name: "About Us", path: "/aboutus" },
  { name: "Shop", path: "/shop" },
  { name: "Contact", path: "/contact" },
  { name: "Login", path: "/account/login" },
];

const menuLinks = [
  { name: "Food Menu", path: "/foodmenu" },
  { name: "Cart", path: "/cart" },
  { name: "Checkout", path: "/checkout" },
  { name: "Register", path: "/register" },
  { name: "Feedback", path: "/infor/feedback" },
];
</script>

<template>
  <footer class="bg-[#020F1C] relative overflow-hidden text-white">
    <!-- Decorative Shape -->
    <img
      src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/footerShape1_4.png"
      alt=""
      class="absolute top-0 right-0 w-48 sm:w-64 lg:w-80 opacity-40"
    />

   <!-- 🔸 Info Bar -->
<div
  class="relative z-10 mx-4 sm:mx-8 lg:mx-24 bg-[#FC791A] rounded-2xl shadow-2xl px-6 sm:px-10 py-10 sm:py-12 flex flex-col sm:flex-row justify-center sm:justify-between items-center gap-8 sm:gap-10 text-white text-center sm:text-left"
>
  <!-- Info Item -->
  <div
    v-for="(item, index) in 3"
    :key="index"
    class="flex flex-col sm:flex-row items-center gap-4 sm:gap-5"
  >
    <div
      class="bg-white p-4 sm:p-5 rounded-full flex items-center justify-center shadow-lg hover:scale-110 transition-transform duration-300"
    >
      <i :class="icon[index]" class="text-[#FC791A] text-2xl sm:text-3xl"></i>
    </div>

    <div>
      <p class="uppercase font-semibold text-xs sm:text-sm tracking-wide opacity-90">
        {{ name[index] }}
      </p>
      <p class="font-bold text-lg sm:text-xl leading-snug">
        {{ content[index] }}
      </p>
    </div>
  </div>
</div>


    <!-- 🔸 Footer Content -->
   <footer class="bg-[#1B1B1B] text-white mt-20">
    <div
      class="container mx-auto px-6 sm:px-10 lg:px-20 py-20 grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-12"
    >
      <!-- Column 1 -->
      <div>
        <img
          src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/logoWhite.svg"
          alt="Logo"
          class="w-40"
        />
        <p class="text-gray-400 mt-6 leading-relaxed">
          Phasellus ultricies aliquam volutpat. Ulamcorper laoreet neque, a
          lacinia curabitur lacinia mollis.
        </p>
        <div class="flex gap-3 mt-6">
          <a
            v-for="icon in ['fa-facebook-f','fa-twitter','fa-linkedin-in','fa-youtube']"
            :key="icon"
            href="#"
            class="w-10 h-10 border border-white flex items-center justify-center rounded-full hover:bg-white hover:text-[#FC791A] transition"
          >
            <i :class="`fa-brands ${icon}`"></i>
          </a>
        </div>
      </div>

      <!-- Column 2 -->
      <div>
        <h3 class="text-2xl font-bold mb-2">Quick Links</h3>
        <div class="w-12 h-1 bg-[#FC791A] mb-8"></div>
        <ul class="space-y-4 text-gray-300">
          <li
            v-for="(item, index) in quickLinks"
            :key="index"
            class="flex items-center hover:text-[#FC791A] transition"
          >
            <i class="fa-solid fa-chevron-right text-sm"></i>
            <router-link
              :to="item.path"
              class="ml-3 hover:text-[#FC791A] transition"
            >
              {{ item.name }}
            </router-link>
          </li>
        </ul>
      </div>

      <!-- Column 3 -->
      <div>
        <h3 class="text-2xl font-bold mb-2">Our Menu</h3>
        <div class="w-12 h-1 bg-[#FC791A] mb-8"></div>
        <ul class="space-y-4 text-gray-300">
          <li
            v-for="(item, index) in menuLinks"
            :key="index"
            class="flex items-center hover:text-[#FC791A] transition"
          >
            <i class="fa-solid fa-chevron-right text-sm"></i>
            <router-link
              :to="item.path"
              class="ml-3 hover:text-[#FC791A] transition"
            >
              {{ item.name }}
            </router-link>
          </li>
        </ul>
      </div>

      <!-- Column 4 -->
      <div>
        <h3 class="text-2xl font-bold mb-2">Contact Us</h3>
        <div class="w-12 h-1 bg-[#FC791A] mb-8"></div>
        <p class="text-gray-400 mb-2">
          <span class="text-white">Monday - Friday:</span> 8am - 4pm
        </p>
        <p class="text-gray-400 mb-8">
          <span class="text-white">Saturday:</span> 8am - 12am
        </p>

        <div class="flex items-center bg-white rounded-lg overflow-hidden">
          <input
            type="text"
            placeholder="Your email address"
            class="flex-grow px-4 py-3 text-gray-700 outline-none"
          />
          <button
            class="bg-[#FC791A] text-white px-4 py-3 hover:bg-[#e66f15] transition"
          >
            <i class="fa-solid fa-arrow-right"></i>
          </button>
        </div>

        <div class="mt-5 flex items-center">
          <input
            type="checkbox"
            class="w-5 h-5 accent-[#FC791A] cursor-pointer"
          />
          <label class="ml-3 text-gray-300 text-sm cursor-pointer">
            I agree to the Privacy Policy.
          </label>
        </div>
      </div>
    </div>

    <!-- Copyright -->
    <div class="bg-[#141414] text-gray-400 py-6 text-center text-sm">
      © 2025 Fresheat. All rights reserved.
    </div>
  </footer>

    <!-- Bottom Bar -->
    <div class="border-t border-white/10 py-6 text-center text-gray-500 text-sm">
      © 2025 <span class="text-[#FC791A] font-semibold">Fresheat</span>. All
      rights reserved.
    </div>
  </footer>
</template>

