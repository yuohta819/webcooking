
<template>
    <div class="min-h-screen bg-gray-100 p-8">
        <div class="max-w-6xl mx-auto bg-white shadow-lg rounded-2xl p-6">
            <h1 class="text-2xl font-semibold text-gray-800 mb-6">Quản lý bàn</h1>

            <!-- Thanh tìm kiếm và nút thêm -->
            <div class="flex justify-between mb-4">
                <input v-model="search" type="text" placeholder="Tìm kiếm theo tên bàn..."
                    class="border rounded-lg px-4 py-2 w-1/3 focus:ring-2 focus:ring-indigo-500 outline-none" />
                <a :href="`/${appName}/addtable`" style="color: white;">
                    <button class="bg-indigo-600 text-white px-4 py-2 rounded-lg hover:bg-indigo-700 transition">
                        + Thêm bàn
                    </button>
                </a>
            </div>

            <!-- Bảng -->
            <div class="overflow-x-auto rounded-lg border">
                <table class="w-full text-left border-collapse">
                    <thead class="bg-indigo-50 text-indigo-800">
                        <tr>
                            <th class="p-3 border">#</th>
                            <th class="p-3 border">Tên bàn</th>
                            <th class="p-3 border">Số ghế</th>
                            <th class="p-3 border">Trạng thái</th>
                            <th class="p-3 border">Mã tài khoản</th>
                            <th class="p-3 border">Ngày tạo</th>
                            <th class="p-3 border text-center">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="table in filteredTables" :key="table.table_id" class="hover:bg-gray-50 transition">
                            <td class="p-3 border">{{ table.table_id }}</td>
                            <td class="p-3 border">{{ table.table_name }}</td>
                            <td class="p-3 border">{{ table.seats }}</td>
                            <td class="p-3 border">
                                <span
                                    :class="table.status ? 'text-green-600 font-semibold' : 'text-red-600 font-semibold'">
                                    {{ table.status ? 'Hoạt động' : 'Không hoạt động' }}
                                </span>
                            </td>
                            <td class="p-3 border">
                                <input v-model="table.accountid" type="text" placeholder="Nhập mã tài khoản"
                                    class="border rounded-md px-2 py-1 w-full focus:ring-2 focus:ring-indigo-400 outline-none" />
                            </td>
                            <td class="p-3 border">{{ formatDate(table.created_at) }}</td>
                            <td class="p-3 border text-center">
                                <button @click="updateAccountId(table)"
                                    class="bg-yellow-400 text-white px-3 py-1 rounded-md mr-2 hover:bg-yellow-500">
                                    Sửa
                                </button>
                                <button @click="deleteTable(table.table_id)"
                                    class="bg-red-500 text-white px-3 py-1 rounded-md hover:bg-red-600">
                                    Xóa
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Confirm modal component -->
    <ConfirmModal ref="confirmModal" />
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";
import ConfirmModal from "../../../../components/ConfirmModal.vue";
import { useToast } from "vue-toastification";
const tables = ref([]);
const search = ref("");
const confirmModal = ref(null);
const appName = import.meta.env.VITE_APP_NAME

const toast = useToast()
// Tách hàm load lại dữ liệu từ server
const loadTables = async () => {
    try {
        const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/number/call`);
        // Giả sử res.data là mảng bàn
        tables.value = res.data || [];
    } catch (error) {
        console.error("Lỗi khi tải danh sách bàn:", error);
        alert("Lỗi khi tải danh sách bàn!");
    }
};

// Gọi loadTables khi component mount
onMounted(() => {
    loadTables();
});

// Lọc bàn theo tên (vẫn giữ search)
const filteredTables = computed(() =>
    tables.value.filter((t) =>
        t.table_name?.toLowerCase().includes(search.value.toLowerCase())
    )
);

// Format ngày
const formatDate = (date) => new Date(date).toLocaleDateString("vi-VN");

// Cập nhật accountid (sau cập nhật sẽ gọi lại loadTables để đồng bộ từ server)
const updateAccountId = async (table) => {
    const roles = sessionStorage.getItem("canAdd")
    if (roles === 'false') {
        toast.warning("Bạn không có quyền truy cập chức năng này !!!")
        return
    }
    if (!table.accountid) {
        alert("Vui lòng nhập mã tài khoản!");
        return;
    }

    try {
        await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/number/update`, {
            accountid: table.accountid,
            id: table.table_id
        });
        // Sau khi update thành công -> lấy lại danh sách từ server
        await loadTables();
        alert("Cập nhật thành công!");
    } catch (error) {
        console.error("Lỗi khi cập nhật:", error);
        alert("Lỗi khi cập nhật bàn!");
    }
};

// Xóa bàn: mở modal, nếu confirm -> gọi API xóa rồi gọi loadTables()
const deleteTable = async (id) => {
    const roles3 = sessionStorage.getItem("canDelete")
    if (roles3 === 'false') {
        toast.warning("Bạn không có quyền truy cập chức năng này !!!")
        route.push(`/${import.meta.env.VITE_APP_NAME}/tablenumber`)
    }
    const table = tables.value.find((t) => t.table_id === id);
    const name = table ? table.table_name : `ID ${id}`;

    // Mở modal confirm (giả sử open trả về true/false)
    const confirmed = await confirmModal.value.open({
        title: "Xóa bàn",
        message: `Bạn có chắc chắn muốn xóa bàn "${name}" không?`,
        detail: "Hành động này không thể hoàn tác."
    });

    if (!confirmed) return;

    try {
        // Thực hiện xóa bằng DELETE (RESTful)
        await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/number/delete/${id}`);

        // Sau khi xóa thành công, gọi lại API lấy danh sách mới từ server
        await loadTables();

        alert("Đã xóa bàn thành công!");
    } catch (error) {
        console.error("Lỗi khi xóa bàn:", error);

        alert("Lỗi khi xóa bàn!");
    }
};
</script>

<style scoped>
table {
    border-collapse: collapse;
    width: 100%;
}

th,
td {
    text-align: left;
}
</style>