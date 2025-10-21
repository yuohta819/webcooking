<template>
  <div class="min-h-screen bg-gradient-to-br from-[#FFF7ED] via-[#FEF3C7] to-[#FDE68A] flex items-center justify-center py-12 px-4">
    <div class="w-full max-w-5xl bg-white/90 backdrop-blur-md rounded-3xl shadow-xl overflow-hidden grid grid-cols-1 md:grid-cols-2 border border-[#FCD34D]/40">
      <!-- Left: Contact info + map -->
      <div class="p-10 bg-gradient-to-b from-[#FFFBEB] to-[#FEF3C7]">
        <h3 class="text-2xl font-semibold text-[#92400E] mb-4">Contact Information</h3>
        <p class="text-sm text-gray-600 mb-8">
          We’d love to hear from you! Whether you have a question about our dishes or want to book a table, just reach out below.
        </p>

        <div class="space-y-5">
          <div class="flex items-center gap-3">
            <div class="p-3 bg-[#FDE68A]/70 rounded-full">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="#92400E" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 6.75c0-.621.504-1.125 1.125-1.125h17.25c.621 0 1.125.504 1.125 1.125v10.5c0 .621-.504 1.125-1.125 1.125H3.375a1.125 1.125 0 01-1.125-1.125V6.75z" />
                <path stroke-linecap="round" stroke-linejoin="round" d="M3 7l9 6 9-6" />
              </svg>
            </div>
            <div>
              <div class="text-sm text-gray-500">Email</div>
              <div class="font-medium text-gray-800">hello@sunshinecuisine.com</div>
            </div>
          </div>

          <div class="flex items-center gap-3">
            <div class="p-3 bg-[#FCD34D]/60 rounded-full">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="#92400E" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 4.5l1.5 15.75A1.125 1.125 0 004.875 21h14.25a1.125 1.125 0 001.125-1.125L21.75 4.5H2.25z" />
              </svg>
            </div>
            <div>
              <div class="text-sm text-gray-500">Phone</div>
              <div class="font-medium text-gray-800">+84 912 345 678</div>
            </div>
          </div>

          <div class="flex items-center gap-3">
            <div class="p-3 bg-[#FDE68A]/70 rounded-full">
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="#92400E" class="w-5 h-5">
                <path stroke-linecap="round" stroke-linejoin="round" d="M12 2.25c-5.385 0-9.75 4.365-9.75 9.75S6.615 21.75 12 21.75 21.75 17.385 21.75 12 17.385 2.25 12 2.25z" />
                <path stroke-linecap="round" stroke-linejoin="round" d="M12 7.5v4.5l3 1.5" />
              </svg>
            </div>
            <div>
              <div class="text-sm text-gray-500">Hours</div>
              <div class="font-medium text-gray-800">Mon – Sun: 9:00 AM – 10:00 PM</div>
            </div>
          </div>
        </div>

        <div class="mt-8 overflow-hidden rounded-xl shadow-md border border-[#FCD34D]/40">
          <iframe
            class="w-full h-48 md:h-56 border-0"
            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3919.456!2d106.7!3d10.776!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31752!2sHo%20Chi%20Minh%20City!5e0!3m2!1sen!2s!4v0000000000000"
            allowfullscreen loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
        </div>
      </div>

      <!-- Right: Contact form -->
      <div class="p-10 bg-white">
        <h2 class="text-3xl font-bold text-[#92400E] mb-2">Send us a message</h2>
        <p class="text-gray-600 mb-6">Have feedback, questions, or special requests? Let us know below.</p>

        <form @submit.prevent="onSubmit" class="space-y-4">
          <div class="grid grid-cols-1 sm:grid-cols-2 gap-4">
            <div>
              <label class="text-sm text-gray-700">Name</label>
              <input :value="name" type="text" placeholder="Your name" class="mt-1 w-full rounded-lg border border-gray-200 p-2 focus:ring-2 focus:ring-[#FCD34D]" readonly />
            </div>
            <div>
              <label class="text-sm text-gray-700">Email</label>
              <input :value="account" type="email" placeholder="you@example.com" class="mt-1 w-full rounded-lg border border-gray-200 p-2 focus:ring-2 focus:ring-[#FCD34D]" readonly />
            </div>
          </div>

          <div>
            <label class="text-sm text-gray-700">Subject</label>
            <input v-model="subject" type="text" placeholder="Reservation or Inquiry" class="mt-1 w-full rounded-lg border border-gray-200 p-2 focus:ring-2 focus:ring-[#FCD34D]" />
            <p v-if="errors.subject" class="text-xs text-red-500 mt-1">{{ errors.subject }}</p>
          </div>

          <div>
            <label class="text-sm text-gray-700">Message</label>
            <textarea v-model="message" rows="4" placeholder="Write your message..." class="mt-1 w-full rounded-lg border border-gray-200 p-2 focus:ring-2 focus:ring-[#FCD34D]"></textarea>
            <p v-if="errors.message" class="text-xs text-red-500 mt-1">{{ errors.message }}</p>
          </div>

          <button type="submit" class="w-full py-3 rounded-lg bg-gradient-to-r from-[#FBBF24] to-[#F59E0B] text-white font-semibold shadow-md hover:from-[#F59E0B] hover:to-[#D97706] transition-all">
            Send Message
          </button>

          <p v-if="success" class="text-sm text-green-600 mt-2">Message sent successfully! We'll get back to you soon.</p>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue'
import axios from 'axios'
import { useToast } from 'vue-toastification'
const errors = reactive({})
const success = ref(false)
const toast = useToast()
const subject  = ref("")
const message = ref("")
let name = localStorage.getItem("name")
let accountid = localStorage.getItem("accountid")
let account = localStorage.getItem("account")
if (accountid == null) {
    name = sessionStorage.getItem("name")
    account = sessionStorage.getItem("account")
    accountid = sessionStorage.getItem("accountid")
}

function validate() {
  errors.subject = ''
  errors.message = ''
  let ok = true
  if (!subject || subject.value.length < 3) {
    errors.subject = 'Subject is required'
    ok = false
  }
  if (!message || message.value.length < 6) {
    errors.message = 'Message too short'
    ok = false
  }
  return ok
}

async function onSubmit() {
    if (!account) {
        toast.warning("Please login to feelback!!!")
        return;
    }
  success.value = false
  if (!validate()) return
    console.log(subject)
    console.log(message)
  try {
    await axios.post(`${import.meta.env.VITE_API_URL_BACKEND}/feelback/save`, {
      name: name,
      email: account,
      accountid: accountid,
      subject: subject.value,
      message: message.value,
    })
    toast.success("Feelback sent successfully!!!")
  } catch (error) {
    console.error('Error sending message:', error)
  }
}
</script>
