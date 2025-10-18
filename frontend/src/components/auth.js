import axios from "axios";

export async function verifyToken() {
  const token = localStorage.getItem("token") || sessionStorage.getItem("token");
  if (!token) return false;

  try {
    const res = await axios.get(`${import.meta.env.VITE_API_URL_BACKEND}/account/verify`, {
      headers: { Authorization: `Bearer ${token}` }
    });
    return res.data === true;
  } catch {
    return false;
  }
}
