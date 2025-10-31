<template>
  <div class="min-h-screen bg-gradient-to-br from-gray-50 to-indigo-50 p-6">
    <div class="max-w-5xl mx-auto">
      <!-- Header -->
      <div class="mb-8 text-center">
        <h1 class="text-3xl font-bold text-indigo-700">📝 Comment History</h1>
        <p class="text-gray-500 mt-2">
          View all your feedbacks and admin responses.
        </p>
      </div>

      <!-- Search + Actions -->
      <div class="bg-white p-4 rounded-xl shadow mb-4 flex flex-col md:flex-row justify-between gap-3">
        <input v-model="q" type="search" placeholder="🔍 Search by subject or message..."
          class="w-full md:w-1/2 border rounded-lg px-3 py-2 focus:ring-2 focus:ring-indigo-300 outline-none" />

        <div class="flex items-center gap-2">
          <select v-model="sort" class="border rounded-lg px-3 py-2">
            <option value="newest">Newest</option>
            <option value="oldest">Oldest</option>
          </select>

          <select v-model.number="perPage" class="border rounded-lg px-3 py-2">
            <option :value="5">5</option>
            <option :value="10">10</option>
            <option :value="20">20</option>
          </select>

          <button @click="refresh" class="bg-indigo-600 hover:bg-indigo-700 text-white px-4 py-2 rounded-lg transition">
            🔄 Refresh
          </button>
        </div>
      </div>

      <!-- Loading -->
      <div v-if="loading" class="text-center py-8 text-gray-500">
        Loading comments...
      </div>

      <!-- List -->
      <div v-else class="bg-white rounded-xl shadow divide-y">
        <div v-for="c in paginatedComments" :key="c.idfeedback"
          class="p-4 flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div>
            <p class="font-semibold text-indigo-700">{{ c.subject }}</p>
            <p class="text-gray-700 mt-1">{{ c.message }}</p>

            <p v-if="c.admin_note" class="mt-2 text-sm text-gray-600 border-l-4 border-green-400 pl-3">
              💬 <strong>Admin:</strong> {{ c.admin_note }}
            </p>

            <p class="text-sm text-gray-500 mt-1">📅 {{ formatDate(c.createdAt) }}</p>
          </div>

          <div class="text-right">
            <p class="mt-1">
              <strong>Status:</strong>
              <span class="px-2 py-1 rounded-full text-sm"
                :class="c.admin_note ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'">
                {{ c.admin_note ? 'Replied' : 'Pending' }}
              </span>
            </p>

            <button @click="viewDetails(c)" class="block mt-2 text-sm text-indigo-600 hover:underline">
              View details
            </button>
          </div>
        </div>

        <div v-if="paginatedComments.length === 0" class="py-6 text-center text-gray-500">
          No comments found.
        </div>
      </div>

      <!-- Pagination -->
      <div v-if="filteredComments.length > 0" class="flex justify-between items-center mt-6 text-sm text-gray-600">
        <p>
          Showing
          {{ (page - 1) * perPage + 1 }}–{{
            Math.min(page * perPage, filteredComments.length)
          }}
          of {{ filteredComments.length }}
        </p>
        <div class="flex items-center gap-2">
          <button @click="goToPage(page - 1)" :disabled="page === 1"
            class="px-3 py-1 border rounded disabled:opacity-40">
            Prev
          </button>
          <span class="px-3 py-1 bg-gray-100 rounded">
            Page {{ page }} / {{ totalPages }}
          </span>
          <button @click="goToPage(page + 1)" :disabled="page === totalPages"
            class="px-3 py-1 border rounded disabled:opacity-40">
            Next
          </button>
        </div>
      </div>
    </div>

    <!-- Modal -->
    <transition name="fade">
      <div v-if="selected" class="fixed inset-0 bg-black/40 flex items-center justify-center z-50 p-4">
        <div class="bg-white rounded-xl shadow-xl w-full max-w-md p-6">
          <h2 class="text-lg font-semibold mb-4 text-indigo-700">
            Comment Details
          </h2>
          <p><strong>Subject:</strong> {{ selected.subject }}</p>
          <p><strong>Message:</strong> {{ selected.message }}</p>
          <p class="mt-2 text-sm text-gray-500">
            📅 {{ formatDate(selected.createdAt) }}
          </p>
          <p class="mt-1">
            <strong>Status:</strong>
            <span class="px-2 py-1 rounded-full text-sm"
              :class="selected.admin_note ? 'bg-green-100 text-green-700' : 'bg-yellow-100 text-yellow-700'">
              {{ selected.admin_note ? 'Replied' : 'Pending' }}
            </span>
          </p>
          <p v-if="selected.admin_note" class="mt-3">
            💬 <strong>Admin reply:</strong> {{ selected.admin_note }}
          </p>

          <div class="text-right mt-6">
            <button @click="selected = null" class="px-4 py-2 bg-indigo-600 hover:bg-indigo-700 text-white rounded-lg">
              Close
            </button>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, computed, watch } from "vue";
import { useRouter } from "vue-router";

const q = ref("");
const sort = ref("newest");
const perPage = ref(5);
const page = ref(1);
const loading = ref(false);
const comments = ref([]);
const selected = ref(null);
const router = useRouter()
const account = localStorage.getItem("accountid") || sessionStorage.getItem("accountid");
const token = localStorage.getItem("token") || sessionStorage.getItem("token")

// ✅ Lấy tất cả comment 1 lần duy nhất
async function fetchComments() {
  loading.value = true;
  if (!token) {
    router.push("/account/login")
  }
  try {
    const res = await axios.get(
      `${import.meta.env.VITE_API_URL_BACKEND}/feedback/client`,
      {
        headers: {
          Authorization: `Bearer ${token}` // hoặc sessionStorage nếu bạn dùng
        }
      }
    );

    // ✅ Đảm bảo dữ liệu luôn là mảng
    comments.value = Array.isArray(res.data) ? res.data : res.data.data || [];
  } catch (err) {
    console.error("Error fetching comments:", err);
    comments.value = [];
  } finally {
    loading.value = false;
  }
}


function refresh() {
  fetchComments();
}

function goToPage(n) {
  if (n < 1 || n > totalPages.value) return;
  page.value = n;
}

function viewDetails(c) {
  selected.value = c;
}

function formatDate(iso) {
  if (!iso) return "Unknown date";
  const d = new Date(iso);
  if (isNaN(d)) return "Invalid date";
  return new Intl.DateTimeFormat("en-US", {
    dateStyle: "medium",
    timeStyle: "short",
  }).format(d);
}

// ✅ Lọc + sắp xếp client-side
const filteredComments = computed(() => {
  let list = comments.value;
  if (q.value) {
    list = list.filter(
      (c) =>
        c.subject?.toLowerCase().includes(q.value.toLowerCase()) ||
        c.message?.toLowerCase().includes(q.value.toLowerCase())
    );
  }

  list = [...list].sort((a, b) => {
    const da = new Date(a.createdAt);
    const db = new Date(b.createdAt);
    return sort.value === "newest" ? db - da : da - db;
  });

  return list;
});

// ✅ Chỉ cắt danh sách hiển thị hiện tại
const paginatedComments = computed(() => {
  const start = (page.value - 1) * perPage.value;
  return filteredComments.value.slice(start, start + perPage.value);
});

const totalPages = computed(() => Math.max(1, Math.ceil(filteredComments.value.length / perPage.value)));

// Nếu tìm kiếm hoặc thay đổi perPage → reset page
watch([q, perPage], () => (page.value = 1));

fetchComments();
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
