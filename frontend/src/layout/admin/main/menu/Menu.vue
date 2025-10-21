
<template>
  <div class="menu-page">
    <h1 class="menu-header">☕ THỰC ĐƠN NHÀ HÀNG</h1>

    <!-- Toolbar -->
    <div class="menu-toolbar">
      <a-button type="primary" shape="round" size="large" @click="exportPDF" class="export-btn">
        📄 Xuất PDF
      </a-button>

      <!-- Bộ chọn màu -->
      <div class="color-selector">
        <span>🎨 Màu nền:</span>
        <select v-model="selectedTheme" @change="applyTheme" class="color-dropdown">
          <option value="brown">Nâu (Mặc định)</option>
          <option value="green">Xanh Lá</option>
          <option value="blue">Xanh Dương</option>
          <option value="purple">Tím</option>
          <option value="red">Đỏ</option>
        </select>
      </div>
    </div>

    <!-- Nội dung menu -->
    <div ref="menuRef" class="menu-wrapper">
      <a-card bordered class="menu-card">
        <h2 class="menu-title">MENU</h2>
        <a-divider />

        <!-- Ảnh minh họa -->
        <div class="menu-images">
          <img src="https://res.cloudinary.com/demo/image/upload/coffee.jpg" alt="Cafe" />
          <img src="https://res.cloudinary.com/dzlfgmtbc/image/upload/v1760675908/blog-5-1000x565_nvkucb.jpg" alt="Nước ép" />
          <img src="https://res.cloudinary.com/dzlfgmtbc/image/upload/v1760675957/20220211142347-margherita-9920_600x600_or9vl1.jpg" alt="Sinh tố" />
        </div>

        <a-divider />

        <!-- Danh sách món -->
        <div class="menu-list">
          <div v-for="item in items" :key="item.id" class="menu-item">
            <div class="item-image">
              <img :src="item.img || 'https://via.placeholder.com/80'" alt="Ảnh món" />
            </div>

            <div class="item-info">
              <span class="item-name">{{ item.name }}</span>
              <span class="item-dots"></span>
              <span class="item-price">{{ formatPrice(item.price) }}</span>
            </div>

            <div class="item-qr">
              <img :src="item.qrData" alt="QR" />
            </div>
          </div>
        </div>

        <a-divider />
        <div class="menu-footer">🍶 Các loại món ăn!!</div>
      </a-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import QRCode from 'qrcode'
import html2canvas from 'html2canvas'
import jsPDF from 'jspdf'
import { useToast } from 'vue-toastification'
const items = ref([])
const menuRef = ref(null)
const selectedTheme = ref(localStorage.getItem('theme') || 'brown')
const toast = useToast()
// Định dạng giá tiền
function formatPrice(v) {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(v)
}

// Tạo link QR theo id món
function itemUrl(item) {
  const base = window.location.origin
  return `${base}/menu/${encodeURIComponent(item.id)}`
}

// Sinh mã QR cho từng món
async function generateQRFor(item) {
  try {
    const url = itemUrl(item)
    const qr = await QRCode.toDataURL(url, {
      errorCorrectionLevel: 'H',
      margin: 1,
      width: 80,
      color: { dark: '#000000', light: '#ffffff' },
    })
    item.qrData = qr
  } catch (err) {
    console.error('QR gen err', err)
  }
}

// Lấy danh sách món từ API
async function fetchMenu() {
  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/api/menu`)
    items.value = res.data
    await Promise.all(items.value.map((i) => generateQRFor(i)))
  } catch (err) {
    console.error('Lỗi khi lấy menu:', err)
  }
}

// Áp dụng theme
function applyTheme() {
  localStorage.setItem('theme', selectedTheme.value)
  document.body.setAttribute('data-theme', selectedTheme.value)
}

// Khi mount
onMounted(() => {
  fetchMenu()
  applyTheme()
})

// Xuất ra PDF
async function exportPDF() {
  const roles = sessionStorage.getItem("canAdd")
  if (roles === 'false') {
    toast.warning("Bạn không có quyền truy cập chức năng này !!!")
    return
  }
  if (!menuRef.value) return
  try {
    const canvas = await html2canvas(menuRef.value, { backgroundColor: '#ffffff', scale: 2, useCORS: true })
    const imgData = canvas.toDataURL('image/png')
    const pdf = new jsPDF('p', 'mm', 'a4')
    const pdfWidth = pdf.internal.pageSize.getWidth()
    const pdfHeight = (canvas.height * pdfWidth) / canvas.width
    pdf.addImage(imgData, 'PNG', 0, 0, pdfWidth, pdfHeight)
    pdf.save('menu-nha-hang.pdf')
  } catch (err) {
    console.error('Lỗi khi xuất PDF:', err)
  }
}
</script>

<style scoped>
/* ==== Giao diện cơ bản ==== */
.menu-page {
  min-height: 100vh;
  padding: 40px 20px 80px;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}

.menu-header {
  text-align: center;
  font-size: 2.5rem;
  font-weight: bold;
  color: #ffcc33;
  margin-bottom: 30px;
  text-transform: uppercase;
  letter-spacing: 2px;
  text-shadow: 1px 2px 4px rgba(0, 0, 0, 0.6);
}

.menu-toolbar {
  text-align: center;
  margin-bottom: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 15px;
}

.export-btn {
  background-color: #ffb300 !important;
  border: none !important;
  color: #2b1607 !important;
  font-weight: 600;
  box-shadow: 0 3px 6px rgba(255, 204, 0, 0.3);
}
.export-btn:hover {
  background-color: #ffcc33 !important;
  transform: scale(1.05);
}

.menu-wrapper {
  display: flex;
  justify-content: center;
}

.menu-card {
  width: 720px;
  background: linear-gradient(to bottom right, #fff8f3, #fdfdfd);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
  border-radius: 12px;
  text-align: center;
  padding: 20px 30px;
}

.menu-title {
  font-family: 'Georgia', serif;
  font-size: 26px;
  font-weight: bold;
  color: #b84c00;
  letter-spacing: 2px;
}

.menu-images {
  display: flex;
  justify-content: space-around;
  margin: 10px 0 20px;
}

.menu-images img {
  width: 100px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2);
  border: 1px solid #e3c79b;
}

/* Danh sách món */
.menu-list {
  font-size: 16px;
  color: #333;
  margin-top: 10px;
}

.menu-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 12px 0;
  border-bottom: 1px dotted #e5cda6;
  color: #4e2c10;
}

.item-image img {
  width: 80px;
  height: 60px;
  object-fit: cover;
  border-radius: 6px;
  margin-right: 15px;
}

.item-info {
  display: grid;
  grid-template-columns: 180px 1fr 100px;
  align-items: center;
  flex: 1;
}

.item-name {
  font-weight: 600;
  text-align: left;
}

.item-dots {
  border-bottom: 1px dotted #d1b48c;
  height: 1px;
  margin: 0 8px;
}

.item-price {
  color: #b84c00;
  font-weight: 600;
  text-align: right;
}

/* QR */
.item-qr img {
  width: 65px;
  height: 65px;
  border-radius: 8px;
  background: #fff;
  border: 1px solid #ddd;
  padding: 4px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.15);
  margin-left: 15px;
}

.menu-footer {
  font-style: italic;
  color: #7a4a10;
  font-weight: 500;
  font-size: 15px;
  margin-top: 12px;
}

:deep(.ant-card-body) {
  padding: 0px !important;
  border-radius: 0 0 8px 8px;
}

/* ==== Bộ chọn màu ==== */
.color-selector {
  display: inline-flex;
  align-items: center;
  gap: 10px;
  color: #fff;
}

.color-dropdown {
  padding: 6px 10px;
  border-radius: 8px;
  border: 1px solid white;
  font-weight: 600;
  cursor: pointer;
  color: black;
}

/* ==== Các theme ==== */
/* Nâu (mặc định) */
[data-theme='brown'] .menu-page {
  background: linear-gradient(180deg, #3b1f0f 0%, #1f1008 100%);
}

/* Xanh lá */
[data-theme='green'] .menu-page {
  background: linear-gradient(180deg, #1b5e20 0%, #0d3b12 100%);
}
[data-theme='green'] .menu-header {
  color: #c5e1a5;
}
[data-theme='green'] .export-btn {
  background-color: #81c784 !important;
  color: #1b5e20 !important;
}

/* Xanh dương */
[data-theme='blue'] .menu-page {
  background: linear-gradient(180deg, #0d47a1 0%, #082567 100%);
}
[data-theme='blue'] .menu-header {
  color: #90caf9;
}
[data-theme='blue'] .export-btn {
  background-color: #64b5f6 !important;
  color: #0d47a1 !important;
}

/* Tím */
[data-theme='purple'] .menu-page {
  background: linear-gradient(180deg, #4a148c 0%, #2e0854 100%);
}
[data-theme='purple'] .menu-header {
  color: #ce93d8;
}
[data-theme='purple'] .export-btn {
  background-color: #ba68c8 !important;
  color: #4a148c !important;
}

/* Đỏ */
[data-theme='red'] .menu-page {
  background: linear-gradient(180deg, #7f0000 0%, #4a0000 100%);
}
[data-theme='red'] .menu-header {
  color: #ff8a80;
}
[data-theme='red'] .export-btn {
  background-color: #ef5350 !important;
  color: #4a0000 !important;
}
</style>