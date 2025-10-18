<script setup>
import { ref } from 'vue';
import { onMounted, onUnmounted } from 'vue';

const position = ref({ x: 0, y: 0 })
let mouseX = 0
let mouseY = 0
let animationFrameId = null

const updatePositonMouse = (e) => {
    mouseX = e.clientX
    mouseY = e.clientY
}
const animate = (e) => {
    position.value.x += (mouseX - position.value.x) * 0.1;
    position.value.y += (mouseY - position.value.y) * 0.1;
    animationFrameId = requestAnimationFrame(animate)
}
onMounted(() => {
    window.addEventListener('mousemove', updatePositonMouse)
    animate()
})

onUnmounted(() => {
    window.removeEventListener('mousemove', updatePositonMouse)
})
</script>
<template>
    <div class="mouse-follower" :style="{ transform: `translate3d(${position.x}px,${position.y}px,0)` }" :key="1">
        <span class="absolute top-[10px] left-[9px] w-[8px]" key="2"
            style="background-color: red; height: 8px; border-radius: 50%;"></span>
    </div>
</template>
<style scoped>
.mouse-follower {
    width: 30px;
    height: 30px;
    left: -10px;
    top: -10px;
    border: 1px solid #BD5915;
    border-radius: 50%;
    position: fixed;
    pointer-events: none;
    z-index: 100;
    transition: transform 0.01s ease;
}
</style>