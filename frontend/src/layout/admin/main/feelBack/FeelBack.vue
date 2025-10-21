<template>
    <div class="min-h-screen bg-gradient-to-br from-indigo-50 via-white to-blue-50 p-6">
        <div class="max-w-7xl mx-auto bg-white rounded-2xl shadow-2xl p-8 border border-indigo-100">
            <h1
                class="text-4xl font-extrabold mb-8 text-center bg-gradient-to-r from-indigo-600 via-blue-500 to-cyan-400 bg-clip-text text-transparent drop-shadow-sm">
                🗂️ Quản Lý Góp Ý Khách Hàng
            </h1>

            <!-- Thanh công cụ -->
            <div class="flex flex-col sm:flex-row justify-between items-center mb-6 gap-4">
                <input v-model="search" type="text" placeholder="🔍 Tìm theo tên hoặc email..."
                    class="w-full sm:w-1/3 p-2 border border-indigo-200 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none shadow-sm" />
                <p class="text-gray-600 text-sm">
                    Tổng số góp ý:
                    <span class="font-semibold text-indigo-600">{{ totalItems }}</span>
                </p>
            </div>

            <!-- Bảng Feedback -->
            <div class="overflow-x-auto rounded-xl border border-gray-200 shadow-lg">
                <table class="min-w-full text-sm text-left border-collapse">
                    <thead class="bg-gradient-to-r from-indigo-600 via-blue-500 to-cyan-400 text-white">
                        <tr>
                            <th class="py-3 px-4 text-center">ID</th>
                            <th class="py-3 px-4">Tên</th>
                            <th class="py-3 px-4">Email</th>
                            <th class="py-3 px-4">Chủ đề</th>
                            <th class="py-3 px-4">Nội dung</th>
                            <th class="py-3 px-4">Phản hồi (Admin)</th>
                            <th class="py-3 px-4">Ngày gửi</th>
                            <th class="py-3 px-4 text-center">Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(fb, index) in paginatedFeedback" :key="fb.idfeedback"
                            class="border-b hover:bg-indigo-50 transition duration-150">
                            <td class="py-3 px-4 text-center font-semibold text-indigo-700">{{ index + 1 }}</td>
                            <td class="py-3 px-4 font-medium text-gray-800">{{ fb.name }}</td>
                            <td class="py-3 px-4 text-gray-700">{{ fb.email }}</td>
                            <td class="py-3 px-4 text-gray-800 font-semibold">{{ fb.subject }}</td>
                            <td class="py-3 px-4 text-gray-600 truncate max-w-xs">{{ fb.message }}</td>

                            <!-- Ô input phản hồi admin -->
                            <td class="py-3 px-4">
                                <input v-model="adminNotes[fb.idfeedback]" type="text"
                                    :placeholder="fb.admin_note || 'Nhập phản hồi...'"
                                    class="w-full p-1 border rounded focus:ring-2 focus:ring-indigo-400 focus:border-indigo-400" />

                            </td>


                            <td class="py-3 px-4 text-gray-500">{{ formatDate(fb.createdAt) }}</td>

                            <!-- Hành động -->
                            <td class="py-3 px-4 text-center space-x-2">
                                <button @click="editFeedback(fb)"
                                    class="bg-yellow-400 hover:bg-yellow-500 text-white py-1 px-3 rounded-lg text-sm shadow-md transition duration-150">
                                    ✏️ Sửa
                                </button>
                                <button @click="deleteFeedback(fb.idfeedback)"
                                    class="bg-red-500 hover:bg-red-600 text-white py-1 px-3 rounded-lg text-sm shadow-md transition duration-150">
                                    🗑️ Xóa
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>

                <p v-if="paginatedFeedback.length === 0" class="text-center py-6 text-gray-500">
                    Không có góp ý nào được tìm thấy.
                </p>
            </div>

            <!-- Phân trang -->
            <div v-if="totalPages > 1" class="flex justify-center mt-6 space-x-2">
                <button @click="prevPage" :disabled="currentPage === 1"
                    class="px-3 py-1 border rounded-lg text-sm bg-white hover:bg-indigo-50 disabled:opacity-50">
                    ◀ Trước
                </button>

                <button v-for="page in totalPages" :key="page" @click="setPage(page)" :class="[
                    'px-3 py-1 rounded-lg text-sm font-semibold transition',
                    page === currentPage
                        ? 'bg-indigo-600 text-white shadow-md'
                        : 'bg-white border hover:bg-indigo-50 text-gray-700'
                ]">
                    {{ page }}
                </button>

                <button @click="nextPage" :disabled="currentPage === totalPages"
                    class="px-3 py-1 border rounded-lg text-sm bg-white hover:bg-indigo-50 disabled:opacity-50">
                    Sau ▶
                </button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";
import { useToast } from "vue-toastification";

const toast = useToast();

const feedbackList = ref([]);
const search = ref("");
const currentPage = ref(1);
const itemsPerPage = 6;

// ✅ Dùng object để lưu phản hồi riêng từng feedback
const adminNotes = ref({});

// Gọi API lấy dữ liệu
const fetchFeedback = async () => {
    try {
        const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/feedback/call`);
        console.log(res.data)
        feedbackList.value = res.data.filter((f) => f.isDeleted === false);
    } catch (err) {
        toast.error("❌ Lỗi khi tải dữ liệu feedback!");
        console.error(err);
    }
};

// Xóa (xóa mềm)
const deleteFeedback = async (id) => {
    const roles3 = sessionStorage.getItem("canDelete")
    if (roles3 === 'false') {
        toast.warning("Bạn không có quyền truy cập chức năng này !!!")
        return
    }
    try {
        await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/feedback/delete/${id}`);
        feedbackList.value = feedbackList.value.filter((f) => f.idfeedback !== id);
        toast.success("🗑️ Xóa góp ý thành công!");
    } catch (err) {
        toast.error("❌ Lỗi khi xóa góp ý!");
        console.error(err);
    }
};

// ✅ Cập nhật phản hồi admin theo từng hàng
const editFeedback = async (fb) => {
    const roles2 = sessionStorage.getItem("canEdit")
    if (roles2 === 'false') {
        toast.warning("Bạn không có quyền truy cập chức năng này !!!")
        return
    }
    try {
        const note = adminNotes.value[fb.idfeedback] || "";
        await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/feedback/update`, {
            adminNote: note,
            id: fb.idfeedback
        });
        toast.success("✏️ Cập nhật phản hồi thành công!");
    } catch (err) {
        toast.error("❌ Lỗi khi cập nhật phản hồi!");
        console.error(err);
    }
};

// Lọc theo tên hoặc email
const filteredFeedback = computed(() => {
    return feedbackList.value.filter(
        (fb) =>
            fb.name.toLowerCase().includes(search.value.toLowerCase()) ||
            fb.email.toLowerCase().includes(search.value.toLowerCase())
    );
});

const totalItems = computed(() => filteredFeedback.value.length);
const totalPages = computed(() => Math.ceil(totalItems.value / itemsPerPage));

const paginatedFeedback = computed(() => {
    const start = (currentPage.value - 1) * itemsPerPage;
    return filteredFeedback.value.slice(start, start + itemsPerPage);
});

const setPage = (page) => {
    if (page >= 1 && page <= totalPages.value) currentPage.value = page;
};
const nextPage = () => setPage(currentPage.value + 1);
const prevPage = () => setPage(currentPage.value - 1);

const formatDate = (date) => new Date(date).toLocaleString("vi-VN");

onMounted(fetchFeedback);
</script>

<style scoped>
table {
    border-radius: 12px;
    overflow: hidden;
}

tr:nth-child(even) {
    background-color: #f9fafb;
}
</style>
