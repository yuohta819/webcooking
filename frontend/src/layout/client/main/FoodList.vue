<template>
    <div v-if="foods.length > 0"
        class="min-h-screen flex flex-col items-center justify-center bg-gradient-to-br from-yellow-50 via-orange-50 to-amber-100 py-14 px-6">
        <!-- Tiêu đề -->
        <h1 class="text-4xl md:text-5xl font-extrabold text-orange-700 mb-12 drop-shadow-sm">
            🍜 Recommended Dishes
        </h1>

        <!-- Khung chứa món ăn -->
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-10 max-w-6xl w-full">
            <div v-for="food in foods.slice(0, 3)" :key="food.id"
                class="bg-white rounded-3xl border border-orange-200 shadow-lg hover:shadow-2xl transform hover:-translate-y-2 transition duration-300 overflow-hidden">
                <!-- Ảnh -->
                <a :href="`/detail/${food.id}`">
                    <div class="w-full h-64 bg-gray-100 flex items-center justify-center overflow-hidden">
                        <img :src="food.img" alt="Food"
                            class="max-h-64 w-auto object-contain hover:scale-105 transition-transform duration-500" />
                    </div>

                    <!-- Nội dung -->
                    <div class="p-6 text-center">
                        <h2 class="text-xl font-semibold text-gray-800 mb-2">
                            {{ food.name }}
                        </h2>
                        <p class="text-gray-500 text-sm mb-4 line-clamp-2">
                            {{ food.describe }}
                        </p>

                        <div class="flex justify-center items-center space-x-2">
                            <span class="text-orange-600 font-bold text-lg">
                                {{ food.price.toLocaleString() }}₫
                            </span>
                        </div>

                        <p class="text-xs text-gray-400 mt-3">
                            Added: {{ formatDate(food.isTime) }}
                        </p>
                    </div>
                </a>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const foods = ref([]);

onMounted(async () => {
    let token = sessionStorage.getItem("token") || localStorage.getItem("token");

    try {
        const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/suggest`, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        foods.value = res.data;

        console.log("📦 Data loaded:", foods.value);
    } catch (error) {
        console.error("❌ Failed to load data:", error);
    }
});


const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString("en-US", {
        day: "2-digit",
        month: "short",
        year: "numeric",
    });
};
</script>

<style>
.line-clamp-2 {
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
}
</style>
