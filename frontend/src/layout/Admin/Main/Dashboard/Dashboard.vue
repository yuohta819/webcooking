<template>
    <DashboardStats />
    <div class="flex gap-10 mt-20">
        <PieProduct class="mt-10" />
        <SalesOverview />
    </div>
</template>
<script setup lang="ts">
import PieProduct from './PieProduct.vue';
import DashboardStats from './DashboardStats.vue';
import SalesOverview from './SalesOverview.vue';
</script>