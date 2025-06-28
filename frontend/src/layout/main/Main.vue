<script setup>
import { onMounted, ref } from 'vue';
import SimpleBar from 'simplebar';
import 'simplebar-core/dist/simplebar.css';
import Header from './Header.vue';
import Introduce from './Introduce.vue';
import EffectMouted from '../model/EffectMouted.vue';
import Box from './Box.vue';
import AboutUs from './AboutUs.vue';
import PopularDishes from './PopularDishes.vue';
import FreshEat from './FreshEat.vue';
import FoodMenu from './FoodMenu.vue';
import SpecialOffer from './SpecialOffer.vue';
import OurChefe from './OurChefe.vue';
import Testimonials from './Testimonials.vue';
import LastestNew from './LastestNew.vue';
import AuthorSucess from '../Page/AuthorSucess.vue';
import ContactSection from './ContactSection.vue';

</script>

<template>
    <Introduce />
    <EffectMouted />
    <AuthorSucess />
    <div>
        <Box />
    </div>
    <AboutUs />
    <PopularDishes />
    <FreshEat />
    <FoodMenu />
    <SpecialOffer />
    <div>
        <OurChefe />
    </div>
    <Testimonials />
    <LastestNew />
</template>