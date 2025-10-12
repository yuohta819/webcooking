<script lang="ts" setup>
import { SmileOutlined, DownOutlined } from '@ant-design/icons-vue';
import axios from 'axios';
import { onMounted, ref } from 'vue';
interface StatusItem {
  accountid: number;
  menuid: number;
  money: number;
  index: number;
  status1: boolean;
  status2: boolean;
  status3: boolean;
  statusid: number;
}

const list = ref<StatusItem[]>([])
onMounted(async () => {
    const res = await axios.get('http://localhost:8080/status/find')
    list.value = res.data
})
const columns = [
    { title: 'Account ID', dataIndex: 'accountid', key: 'accountid' },
    { title: 'Menu ID', dataIndex: 'menuid', key: 'menuid' },
    { title: 'Money', dataIndex: 'money', key: 'money' },
    { title: 'Status 1', dataIndex: 'status1', key: 'status1' },
    { title: 'Status 2', dataIndex: 'status2', key: 'status2' },
    { title: 'Status 3', dataIndex: 'status3', key: 'status3' },
];
async function handleStatus(id: number, status: boolean, index: number, type: String) {
    const res = await axios.post(`http://localhost:8080/status/update`, null, {
        params: {
            id: id,
            check: !status,
            type: type
        }
    })
    switch (type) {
        case 'status1':
            list.value[index].status1 = !status
            break;
        case 'status2':
            list.value[index].status2 = !status
            break;
        case 'status3':
            list.value[index].status3 = !status
            break;
    }
}
</script>

<template>
    <div style="background-color: #F4F1EA;">
        <a-table :columns="columns" :data-source="list" class="px-5 py-10">
            <template #bodyCell="{ column, record, index }">
                <template v-if="column.key === 'status1'">
                    <a-button type="primary" :danger="record.status1" :ghost="!record.status1"
                        @click="handleStatus(record.statusid, record.status1, index, 'status1')">
                        {{ record.status1 ? 'Đã xác nhận ' : 'Chờ xác nhận' }}
                    </a-button>
                </template>

                <template v-if="column.key === 'status2'">
                    <a-button type="primary" :danger="record.status2" :ghost="!record.status2"
                        @click="handleStatus(record.statusid, record.status2, index, 'status2')">
                        {{ record.status2 ? 'Đã thực hiện ' : 'Chờ thực hiện' }}
                    </a-button>
                </template>

                <template v-if="column.key === 'status3'">
                    <a-button type="primary" :danger="record.status3" :ghost="!record.status3"
                        @click="handleStatus(record.statusid, record.status3, index, 'status3')">
                        {{ record.status3 ? 'Đã giao' : 'Đang giao' }}
                    </a-button>
                </template>
            </template>
        </a-table>
    </div>
</template>
