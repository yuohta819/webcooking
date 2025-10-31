<template>
  <!-- Floating Chatbot -->
  <div class="fixed bottom-6 right-6 z-50">
    <transition name="fade">
      <div
        v-if="open"
        class="w-80 bg-white shadow-2xl rounded-3xl overflow-hidden border border-gray-200 flex flex-col backdrop-blur-xl"
      >
        <!-- Header -->
        <div class="bg-gradient-to-r from-indigo-600 to-purple-600 text-white p-3 flex items-center justify-between">
          <div class="flex items-center space-x-2">
            <span class="text-2xl">🤖</span>
            <h2 class="font-semibold tracking-wide">Nhà hàng Bot</h2>
          </div>
          <button
            @click="toggleChat"
            class="text-white text-2xl font-bold hover:text-gray-200 transition"
          >
            ×
          </button>
        </div>

        <!-- Chat body -->
        <div ref="chatBox" class="h-96 overflow-y-auto p-4 bg-gradient-to-b from-gray-50 to-gray-100 space-y-3 scroll-smooth">
          <div
            v-for="(msg, index) in messages"
            :key="index"
            class="flex"
            :class="msg.sender === 'user' ? 'justify-end' : 'justify-start'"
          >
            <!-- Bot avatar -->
            <div v-if="msg.sender === 'bot'" class="w-8 h-8 rounded-full bg-indigo-500 text-white flex items-center justify-center mr-2">
              🤖
            </div>

            <!-- Chat bubble -->
            <div
              :class="[
                'px-4 py-2 rounded-2xl shadow-sm max-w-[75%] text-sm leading-relaxed',
                msg.sender === 'user'
                  ? 'bg-indigo-600 text-white rounded-br-none'
                  : 'bg-white border border-gray-200 text-gray-800 rounded-bl-none',
              ]"
            >
              {{ msg.text }}
            </div>

            <!-- User avatar -->
            <div v-if="msg.sender === 'user'" class="w-8 h-8 rounded-full bg-gray-300 flex items-center justify-center ml-2">
              👤
            </div>
          </div>
        </div>

        <!-- Input -->
        <div class="border-t border-gray-200 p-3 bg-white flex items-center">
          <input
            v-model="userMessage"
            type="text"
            placeholder="Nhập tin nhắn..."
            @keyup.enter="sendMessage"
            class="flex-1 px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-400 outline-none text-sm"
          />
          <button
            @click="sendMessage"
            class="ml-2 px-3 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700 transition flex items-center space-x-1"
          >
            <span>Gửi</span>
            <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 12h13M12 5l7 7-7 7" />
            </svg>
          </button>
        </div>
      </div>
    </transition>

    <!-- Floating Button -->
    <button
      v-if="!open"
      @click="toggleChat"
      class="relative bg-gradient-to-r from-indigo-600 to-purple-600 text-white p-4 rounded-full shadow-xl hover:scale-110 hover:shadow-2xl transition transform duration-200"
    >
      💬
      <span
        class="absolute -top-1 -right-1 bg-red-500 text-xs text-white rounded-full w-5 h-5 flex items-center justify-center animate-pulse"
      >
        1
      </span>
    </button>
  </div>
</template>

<script setup>
import { ref, nextTick } from "vue";
import axios from "axios";

const open = ref(false);
const userMessage = ref("");
const messages = ref([
  { sender: "bot", text: "Xin chào 👋! Tôi là trợ lý nhà hàng. Bạn muốn đặt món hay xem gợi ý hôm nay?" },
]);
const chatBox = ref(null);

function toggleChat() {
  open.value = !open.value;
  if (open.value) scrollToBottom();
}

async function sendMessage() {
  const text = userMessage.value.trim();
  if (!text) return;

  messages.value.push({ sender: "user", text });
  userMessage.value = "";

  try {
    console.log(text)
    const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/api/chat`, { message: "hello" });
    console.log(res.data)
    messages.value.push({ sender: "bot", text: res.data.reply });
  } catch (err) {
    console.log(err)
    messages.value.push({
      sender: "bot",
      text: "😥 Xin lỗi, hệ thống đang bận. Vui lòng thử lại sau.",
    });
  }

  await nextTick();
  scrollToBottom();
}

function scrollToBottom() {
  const box = chatBox.value;
  if (box) box.scrollTop = box.scrollHeight;
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: translateY(10px);
}

/* Custom scrollbar cho khung chat */
.h-96::-webkit-scrollbar {
  width: 6px;
}
.h-96::-webkit-scrollbar-thumb {
  background-color: rgba(99, 102, 241, 0.5);
  border-radius: 3px;
}
.h-96::-webkit-scrollbar-thumb:hover {
  background-color: rgba(99, 102, 241, 0.7);
}
</style>
