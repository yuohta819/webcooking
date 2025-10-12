<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { useRoute } from 'vue-router';
const data = ref([])
const data1 = ref([])
const router = useRoute()
onMounted(async () => {
    const response = await axios.get("http://localhost:8080/api/menu")
    data.value = response.data.slice(0, 12)
    data1.value = [...data.value]
})
function handleChange(type) {
    if (type === 'high') {
        data1.value = [...data.value].sort((a, b) => a.price - b.price)
    } else if (type === 'low') {
        data1.value = [...data.value].sort((a, b) => b.price - a.price)
    } else {
        data1.value = [...data.value]
    }
}
</script>

<template>
    <div>
        <div v-if="!router.params.id">
            <div class="flex px-10  py-5 lg:justify-between justify-center  flex-wrap items-center">
                <div style="color: #615D5A;">Showing 1 - 12 of 30 Results</div>
                <div class="flex w-70 justify-between py-5 items-center">
                    <select name="" id="" class="px-5 py-3 appearance-none relative"
                        style="background-color: white; color: #615D5A;" @change="handleChange($event.target.value)">
                        <option value="Default Sorting" selected>Default Sorting</option>
                        <option value="high">Sort by price: low to high</option>
                        <option value="low">Sort by price: high to low</option>
                    </select>
                    <div style="color: #F43C22;"><i class="fa-solid fa-grip"></i></div>
                    <div><i class="fa-solid fa-list"></i></div>
                </div>
            </div>
            <div style="background-color: #F4F1EA;" class=" flex pb-10">
                <div class="flex flex-wrap gap-11 justify-around">
                    <div v-for="item in data1" style="background-color: white" class="w-60 mt-30 rounded-[15px]">
                        <div class="place-items-center " style="margin-top: -100px;">
                            <div class="w-40 h-40 img rounded-[50%]  p-2 left-8"></div>
                            <a :href="`/admin/edit/${item.id}`">
                                <img :src="item.img" alt="" width="160" class="p-2" style="margin-top: -158px;"></a>
                        </div>
                        <div class="text-center px-5  text-center py-5">
                            <div class="font-bold text-[21px]">{{ item.name }}</div>
                            <div class="text-[18px] py-2" style="color: #7D8490">{{ item.describe }}</div>
                            <div class="font-bold text-[20px]" style="color: #F43C22">${{ item.price }}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <router-view />
    </div>
</template>