<template>
  <div class="w-screen h-screen bg-black bg-opacity-60 flex items-center justify-center font-pixel">
    <div class="bg-[#6B3F25] border-[6px] border-[#F08A3C] rounded-xl shadow-lg px-6 py-8 w-80 text-center">
      <h2 class="text-white text-2xl mb-6">PAUSED</h2>

      <div class="space-y-4">
        <button
          class="w-full py-2 bg-[#F08A3C] text-white font-bold text-lg border-4 border-black shadow-sm hover:translate-y-1 transition"
        >
          Resume
        </button>

        <button
          class="w-full py-2 bg-[#F08A3C] text-white font-bold text-lg border-4 border-black shadow-sm hover:translate-y-1 transition"
        >
          Options
        </button>

        <button
          disabled
          class="w-full py-2 bg-[#6B4E35] text-gray-400 font-bold text-lg border-4 border-black opacity-60 cursor-not-allowed"
        >
          Quit
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
// Không cần JS logic cho mockup đơn giản này
</script>

<style scoped>
@font-face {
  font-family: 'Pixel';
  src: url('/fonts/PressStart2P-Regular.ttf'); /* Hoặc font pixel bạn thích */
}
.font-pixel {
  font-family: 'Pixel', monospace;
}
</style>
