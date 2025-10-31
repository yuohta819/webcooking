<script setup>
import axios from "axios";
import { ref, computed, onMounted } from "vue";
import { useToast } from "vue-toastification";
import { useRoute, useRouter } from "vue-router";

const toast = useToast();
const router = useRouter();
const route = useRoute()
let token = localStorage.getItem("token") || sessionStorage.getItem("token")


const cart = ref([]);
const paymentMethod = ref("zalopay");
const fullname = ref("");
const phone = ref("");
const people = ref(1);
const accountid = ref(0)
const account = ref('')
const mail = ref('')
onMounted(async () => {
  try {
    const res = await axios.get(
      `${import.meta.env.VITE_API_URL_BACKEND}/api/infor`,
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
    if (res.data === '') {
      toast.error("Token expired or invalid!!!")

      localStorage.clear()
      sessionStorage.clear()
      router.push("/")

    }
    cart.value = res.data.cart
    account.value = res.data.name
    accountid.value = res.data.accountid
    mail.value = res.data.account
  } catch (err) {
    console.error("Lỗi khi tải giỏ hàng:", err)
  }
});

const grandTotal = computed(() => {
  return cart.value.reduce((sum, item) => sum + item.menu.price * item.total, 0);
});

const totalWithTax = computed(() => grandTotal.value + grandTotal.value * 0.3);

const productIds = computed(() => cart.value.map(item => item.menu.id));
const productMoney = computed(() => cart.value.map(item => item.menu.price * item.total));

async function handlePayment() {

  if (!phone.value || !people.value) {
    toast.warning("Please fill in all receiver information!");
    return;
  }

  try {
    // ✅ Hiển thị toast ngay khi bấm nút
    const id = toast.info("Processing your order...", { timeout: false });

    let apiUrl = "";
    if (paymentMethod.value === "zalopay") apiUrl = "/number/payment/zalopay";
    else if (paymentMethod.value === "bidv") apiUrl = "/number/payment/bidv";
    else apiUrl = "/number/payment/cash";

    // ✅ Gọi API thanh toán
    const res = await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}${apiUrl}`, {
      account: mail.value,
      accountid: accountid.value,
      fullname: account.value,
      phone: phone.value,
      people: people.value,
      payment: paymentMethod.value,
      total: totalWithTax.value,
      products: cart.value.map(item => ({
        id: item.menu.id,
        name: item.menu.name,
        price: item.menu.price,
        quantity: item.total,
        total: item.menu.price * item.total
      }))
    });

    // ✅ Cập nhật lại toast thành “Thành công”
    setTimeout(() => {
      toast.update(id, {
        content: "Order successful!", // 🔹 ở bản mới là content, không phải render
        type: "success",
        timeout: 2000,
      });
    }, 2000);

    if (res.data === 'OK') {
      await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/status/save`, {
        account: mail.value,
        accountid: accountid.value,
        status: productIds.value,
        money: productMoney.value,
        people: people.value,
        name: account.value,
      });

      setTimeout(() => {
        router.push("/").then(() => window.location.reload());
      }, 2000);
    } else {
      toast.update("Payment failed! Please try again.");
    }

  } catch (err) {
    toast.error("Payment failed!");
  }

}
</script>


<template>
  <div class="bg-gray-50 min-h-screen py-16 px-6">
    <h1 class="text-3xl font-bold text-center text-gray-800 mb-10">Checkout</h1>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-10 max-w-6xl mx-auto">
      <!-- Left: Order Summary -->
      <div class="bg-white p-6 rounded-2xl shadow-lg border">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Your Order</h2>
        <table class="min-w-full text-left text-gray-700 border-t">
          <thead class="font-semibold border-b">
            <tr>
              <th class="py-3">Product</th>
              <th>Qty</th>
              <th>Total</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in cart" :key="item.menu.id" class="border-b">
              <td class="py-3">{{ item.menu.name }}</td>
              <td>{{ item.total }}</td>
              <td>${{ (item.menu.price * item.total).toLocaleString("vi-VN") }}</td>
            </tr>
          </tbody>
        </table>

        <div class="mt-6 space-y-2 text-right font-semibold">
          <div>Subtotal: ${{ grandTotal.toLocaleString("vi-VN") }}</div>
          <div>Tax: 30%</div>
          <div class="text-red-600 text-lg">Total: ${{ totalWithTax.toLocaleString("vi-VN") }}</div>
        </div>
      </div>

      <!-- Right: Payment Information -->
      <div class="bg-white p-6 rounded-2xl shadow-lg border">
        <h2 class="text-2xl font-semibold text-gray-700 mb-4">Payment Information</h2>

        <!-- Receiver Information -->
        <div class="space-y-4 mb-6">
          <div>
            <label class="block text-gray-600 mb-1 font-medium">Full Name</label>
            <input  :value="`${account}`" readonly type="text" placeholder="Receiver name"
              class="w-full border px-3 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-red-500" />
          </div>
          <div>
            <label class="block text-gray-600 mb-1 font-medium">Phone Number</label>
            <input v-model="phone" type="text" placeholder="Phone number"
              class="w-full border px-3 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-red-500" />
          </div>
          <div>
            <label class="block text-gray-600 mb-1 font-medium">Number of People</label>
            <input v-model="people" type="number" min="1" placeholder="Enter number of people"
              class="w-full border px-3 py-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-red-500" />
          </div>
        </div>

        <!-- Payment Method -->
        <div class="mb-6">
          <label class="block text-gray-700 font-semibold mb-2">Payment Method</label>
          <div class="space-y-2">
            <label class="flex items-center space-x-3">
              <input type="radio" v-model="paymentMethod" value="zalopay" />
              <span>ZaloPay</span>
            </label>
            <label class="flex items-center space-x-3">
              <input type="radio" v-model="paymentMethod" value="bidv" />
              <span>BIDV Bank Transfer</span>
            </label>
            <label class="flex items-center space-x-3">
              <input type="radio" v-model="paymentMethod" value="cash" />
              <span>Cash on Delivery (COD)</span>
            </label>
          </div>
        </div>

        <!-- Confirm Button -->
        <button @click="handlePayment"
          class="w-full py-3 bg-red-600 hover:bg-red-700 text-white font-bold rounded-lg transition">
          Confirm Payment
        </button>
      </div>
    </div>
  </div>
</template>
