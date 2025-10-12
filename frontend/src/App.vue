<script setup>
import { useRoute } from 'vue-router'
import { computed,onMounted, ref } from 'vue'
import SimpleBar from 'simplebar';
import Header from './layout/main/Header.vue'
import Footer from './layout/main/Footer.vue'
import EffectMouted from './layout/model/EffectMouted.vue'
import 'animate.css';
const scrollContainer = ref(null);
const route = useRoute()

// Kiểm tra xem có hiển thị layout client không
const isClientRoute = computed(() => route.meta.layout === 'client')
onMounted(() => {
  new SimpleBar(scrollContainer.value, { autoHide: false });
});
</script>

<template>
  <div ref="scrollContainer" data-simplebar style="height: 100vh; ">
    <div class="min-h-screen overflow-y-auto">
      <!-- Custom scroll được áp dụng cho toàn bộ trang -->
      <Header v-if="isClientRoute" />
      <EffectMouted v-if="isClientRoute" />
      <router-view />
      <Footer v-if="isClientRoute" />
    </div>

  </div>

</template>

<style>
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
