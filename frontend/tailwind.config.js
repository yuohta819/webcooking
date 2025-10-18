/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  // ⚙️ Bật chế độ experimental để tắt oklab/oklch
  experimental: {
    disableColorFunction: true,
  },
}
