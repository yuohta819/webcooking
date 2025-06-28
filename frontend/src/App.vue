<script setup>
import { onMounted, ref } from 'vue';
import SimpleBar from 'simplebar';
import EffectMouted from './layout/model/EffectMouted.vue';
import Footer from './layout/main/Footer.vue';
import Header from './layout/main/Header.vue';
import 'animate.css';
const scrollContainer = ref(null);
onMounted(() => {
  new SimpleBar(scrollContainer.value, { autoHide: false });
});
</script>

<template>

  <div ref="scrollContainer" style="height: 100vh; ">
    <Header />

    <EffectMouted />
    <router-view />
    <Footer />
  </div>


</template>
<style>
/* Custom màu thanh cuộn */
.simplebar-scrollbar::before {
  background-color: #FC791A !important;
  border-radius: 8px;
}

/* Track (nền thanh cuộn) */
.simplebar-track.simplebar-vertical {
  background-color: #ffe6cc !important;
  width: 10px;
  border-radius: 10px;
  right: 0;
  /* Đảm bảo nó nằm đúng */
}

/* Tránh đè lên nội dung (cách 1: thêm padding) */
[data-simplebar] {
  padding-right: 12px;
  /* hoặc phù hợp với chiều rộng scrollbar */
}
</style>
