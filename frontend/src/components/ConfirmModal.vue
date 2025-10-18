<template>
    <a-modal v-model:open="visible" :title="title" :ok-text="okText" :cancel-text="cancelText" :ok-type="okType"
        centered :confirm-loading="loading" @ok="handleOk" @cancel="handleCancel">
        <div class="flex items-start gap-3">
            <i class="fa-solid fa-triangle-exclamation text-red-500 text-2xl mt-1"></i>
            <div>
                <p class="text-gray-700 mb-1">{{ message }}</p>
                <p v-if="detail" class="text-gray-500 text-sm">{{ detail }}</p>
            </div>
        </div>
    </a-modal>
    <ToastService ref="toast" />
</template>

<script setup>
import { ref, defineExpose } from 'vue'
import ToastService from "./ToastService.vue"
const visible = ref(false)
const loading = ref(false)
const title = ref('Xác nhận xóa')
const message = ref('Bạn có chắc chắn muốn xóa mục này?')
const detail = ref('')
const okText = ref('Xóa')
const cancelText = ref('Hủy')
const okType = ref('danger')
const toast = ref(null)
let resolveFn = null

// Mở modal => trả về Promise
function open(options = {}) {
    title.value = options.title || 'Xác nhận xóa'
    message.value = options.message || 'Bạn có chắc chắn muốn xóa mục này?'
    detail.value = options.detail || ''
    okText.value = options.okText || 'Xóa'
    cancelText.value = options.cancelText || 'Hủy'
    okType.value = options.okType || 'danger'

    visible.value = true
    return new Promise((resolve) => {
        resolveFn = resolve
    })
}

async function handleOk() {
    loading.value = true
    toast.value.success(`Đã xóa thành công!`) // ✅
    await new Promise((r) => setTimeout(r, 300))
    loading.value = false
    visible.value = false
    resolveFn?.(true) // Trả về true nếu người dùng xác nhận
    
}

function handleCancel() {
    visible.value = false
    resolveFn?.(false)
}

defineExpose({ open })
</script>
<style>
:where(.css-dev-only-do-not-override-1p3hq3p).ant-modal-root .ant-modal-centered .ant-modal
 {
    top: -25%;
    display: inline-block;
    padding-bottom: 0;
    text-align: start;
    vertical-align: middle;
}
</style>
