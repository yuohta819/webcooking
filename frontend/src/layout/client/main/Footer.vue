<script setup>

const icon = ['fa-solid fa-location-dot', 'fa-solid fa-envelope', 'fa-solid fa-phone-volume']
const content = ['4648 Rocky Road Philadelphia', 'info@exmple.com', '+88 0123 654 99']
const name = ['Address', 'Send Email', 'call emergency']
const type = ['About Us', 'Our Gallery', 'Our Blogs', 'FAQ’S', 'Contact Us']
const title = ['Burger King', 'Pizza king', ' Fresh Food', 'Vegetable', 'Desserts']
</script>

<template>
    <div style="background-color: #020F1C;" class="relative py-23">
        <img class="right-0 top-0 absolute"
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/footerShape1_4.png" alt="">
        <div class="xl:flex  justify-between mx-30 rounded-[20px] relative z-2" style="background-color: #FC791A;">
            <div v-for="(item, index) in 3" class="xl:px-15 px-5 py-13 flex items-center ">
                <div class="px-3 py-2 rounded-[50%]" style="background-color: white;"><i style="color: #FC791A;"
                        :class="icon[index]"></i></div>
                <div class="ml-3">
                    <div class="mb-1 font-bold" style="color: white;">{{ name[index] }}</div>
                    <div class="xl:text-[20px] text-[14px] font-bold" style="color: white;">{{ content[index] }}</div>
                </div>
            </div>
        </div>
        <div class="flex pt-20 px-32 gap-9 flex-wrap">
            <div class="w-78">
                <div>
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/logo/logoWhite.svg" alt="">
                </div>
                <div style="color: #A4A7AA;" class="py-10 leading-[30px]">
                    Phasellus ultricies aliquam volutpat ullamcorper laoreet neque, a lacinia
                    curabitur
                    lacinia mollis
                </div>
                <div class="flex gap-3">
                    <div class="px-3 py-1" style="border: 1px solid white;"><i style="color: white;"
                            class="fa-brands fa-facebook-f"></i></div>
                    <div class="px-3 py-1" style="border: 1px solid white;"><i style="color: white;"
                            class="fa-brands fa-twitter"></i></div>
                    <div class="px-3 py-1" style="border: 1px solid white;"> <i style="color: white;"
                            class="fa-brands fa-linkedin-in"></i></div>
                    <div class="px-3 py-1" style="border: 1px solid white;"><i style="color: white;"
                            class="fa-brands fa-youtube"></i></div>
                </div>
            </div>
            <div class="w-70">
                <div class="text-[30px] font-bold pb-2" style="color: white;">Quick Links</div>
                <div class="w-40 h-1 mb-10" style="background-color: #FC791A;"></div>
                <div v-for="(item, index) in 5" class="">
                    <ul class="flex" style="color: white;">
                        <li>
                            <i class="fa-solid fa-chevron-right"></i>
                        </li>
                        <li class="ml-3 mb-5 text-[17px]">{{ type[index] }}</li>
                    </ul>
                </div>
            </div>
            <div class="w-70">
                <div class="text-[30px] font-bold pb-2" style="color: white;">Our Menu</div>
                <div class="w-40 h-1 mb-10" style="background-color: #FC791A;"></div>
                <div v-for="(item, index) in 5" class="">
                    <ul class="flex" style="color: white;">
                        <li>
                            <i class="fa-solid fa-chevron-right"></i>
                        </li>
                        <li class="ml-3 mb-5 text-[17px]">{{ title[index] }}</li>
                    </ul>
                </div>
            </div>
            <div>
                <div class="text-[30px] font-bold pb-2" style="color: white;">Contact Us</div>
                <div class="w-40 h-1 mb-10" style="background-color: #FC791A;"></div>
                <div>
                    <span style="color: #615D5A;" class="text-[17px]">Monday -Friday: </span>
                    <span style="color: #FC791A;">8am - 4pm</span>
                </div>
                <div class="py-5">
                    <span style="color: #615D5A;" class="text-[17px]">Saturday: </span>
                    <span style="color: #FC791A;">8am - 12am</span>
                </div>
                <div style="background-color: white;" class="px-4 rounded-[10px]">
                    <input class="pl-3 py-5" type="text" placeholder="Your email address">
                    <i style="background-color: #FC791A; color: white;"
                        class="px-3 py-3 rounded-[11px] fa-solid fa-arrow-right"></i>
                </div>
                <div class="pt-5">
                    <input style="transform: scale(1.6);" type="checkbox">
                    <span style="color: white; " class="ml-4">I agree to the Privacy Policy. </span>
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>
input {
    outline: none;
}
</style>