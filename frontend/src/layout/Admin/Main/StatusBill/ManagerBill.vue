<script lang="ts" setup>
import { SmileOutlined, DownOutlined } from '@ant-design/icons-vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';
interface StatusItem {
  account: string;
  billid: number;
  idproduct: number;
  name: string;
  time: string;
  menu: {
    describe: string;
    id: number;
    img: string;
    name: string;
    price: string; // hoặc number nếu backend trả về dạng số
  };
}

const list = ref<StatusItem[]>([])
onMounted(async () => {
    const res = await axios.get('http://localhost:8080/bill/account')
    list.value = res.data
    console.log(list.value)
})
const columns = [
  { title: 'Account', dataIndex: 'account', key: 'account' },
  { title: 'Name', dataIndex: 'name', key: 'name' },
  { title: 'Menu ID', dataIndex: 'idproduct', key: 'idproduct' },
  { title: 'Time', dataIndex: 'time', key: 'time' },
  { title: 'Price', key: 'price' }, // 👈 thêm dòng này
];
</script>

<template>
  <div style="background-color: #F4F1EA;">
    <a-table :columns="columns" :data-source="list" class="px-5 py-10">
      <template #bodyCell="{ column, record, index }">

        <!-- Cột Account -->
        <template v-if="column.key === 'account'">
          {{ record.account }}
        </template>

        <!-- Cột Name -->
        <template v-if="column.key === 'name'">
          {{ record.name }}
        </template>

        <!-- Cột Menu ID -->
        <template v-if="column.key === 'idproduct'">
          {{ record.idproduct }}
        </template>

        <!-- Cột Time -->
        <template v-if="column.key === 'time'">
          {{ new Date(record.time).toLocaleString() }}
        </template>

        <!-- ✅ Cột Price từ menu.price -->
        <template v-if="column.key === 'price'">
          {{ record.menu?.price }}₫
        </template>

      </template>
    </a-table>
  </div>
</template>

