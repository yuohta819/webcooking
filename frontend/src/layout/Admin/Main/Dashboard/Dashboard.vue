<template>
    <DashboardStats />
    <div class="flex gap-5 mt-20">
        <PieProduct  />
        <SalesOverview />
    </div>
</template>
<script setup lang="ts">
import PieProduct from './PieProduct.vue';
import DashboardStats from './DashboardStats.vue';
import SalesOverview from './SalesOverview.vue';
</script>