<script setup>


</script>

<template>
    <div style="background-color: #181818;" class="relative">
        <img class=""
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/testimonial/testimonialThumb1_1.png" alt="">
        <img class="absolute top-75 left-90 img"
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/player.svg" alt="">
        <img class="absolute right-0 top-55"
            src="https://www.ex-coders.com/php-template/fresheat/assets/img/shape/testimonialShape1_1.png" alt="">
        <div class="absolute top-[-40px] left-1/2 -translate-x-1/2 
         xl:left-auto xl:translate-x-0 xl:right-[17.5rem]">
            <div class="flex justify-center pt-15 sm:pt-30 font-bold">
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
                <span class="px-1 font-bold text-[14px] text-[#FC7D1F] uppercase">
                    Testimonials
                </span>
                <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/titleIcon.svg" alt="">
            </div>

            <h2 class="py-1 text-center pb-10 text-[43px] font-black leading-[50px] text-white mb-5">
                What our Clients Say
            </h2>
        </div>
        <div style="background-color: white; border-radius: 30px;" class="absolute top-60  p-11 
         sm:left-1/2 sm:-translate-x-1/2 
         xl:left-auto xl:right-40 xl:translate-x-0 z-6 ">
            <div class="flex items-center justify-between">
                <div class="flex items-center ">
                    <img style="border-radius: 50%;"
                        src="https://www.ex-coders.com/php-template/fresheat/assets/img/testimonial/testimonialProfile1_1.png"
                        alt="">
                    <div class="pl-5">
                        <div class="font-bold text-[24px]">Albert Flores</div>
                        <div class="pb-1">Web Designer</div>
                        <div>
                            <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/star.svg" alt="">
                        </div>
                    </div>
                </div>
                <div>
                    <img src="https://www.ex-coders.com/php-template/fresheat/assets/img/icon/quote.svg" alt="">
                </div>
            </div>
            <div class="pt-5 text-[23px] items-justify" style="color: #6A676C;">Penatibus magnis dis point parturient
                montes nascetur
                ridiculus mus Ut id <br>
                lorem ac enim the vestibulum blandit nec sit amet felis. <br>Fusce quis diam
                odio Cras mattis mi quis tincidunt</div>
        </div>
        <div class="marquee py-20 relative" style="background-color: #181818;">
            <div class="marquee-content  mt-60 xl:mt-0">
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    chicken pizza
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    GRILLED CHICKEN
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    BURGER
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    FRESH PASTA
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    chicken pizza
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    GRILLED CHICKEN
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    BURGER
                </span>
                <span class="text-[80px] box-3 font-extrabold mx-10 item" style="text-transform: uppercase; ">
                    FRESH PASTA
                </span>
            </div>
        </div>
    </div>

</template>
<style scoped>
.img {
    animation: spin 4s linear infinite;
}

@keyframes spin {
    0% {
        transform: rotate(0);
    }

    100% {
        transform: rotate(360deg);
    }
}

.marquee {
    overflow: hidden;
    background: #F4F1EA;
    opacity: 1;
}

.marquee-content {
    display: inline-flex;
    align-items: center;
    animation: marquee 25s linear infinite;
    flex-shrink: 0;
}

.marquee-content .item {
    font-size: 80px;
    display: inline-block;
    font-weight: 800;
    text-transform: uppercase;
    position: relative;
    flex-shrink: 0;
    color: #BCB8B9;
    transition: color 0.3s ease;
    cursor: pointer;
}

.marquee-content .item:hover {
    color: #EB0029;
    opacity: 1;
}

.marquee-content .item::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    width: 0;
    height: 3px;
    background: #EB0029;
    transition: width 0.5s ease;
}

.marquee-content .item:hover::after {
    width: 100%;
}

@keyframes marquee {
    0% {
        transform: translateX(0);
    }

    100% {
        transform: translateX(-50%);
    }
}
</style>
