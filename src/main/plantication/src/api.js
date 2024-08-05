// src/api.js
import axios from "axios";

const api = axios.create({
  baseURL: "http://your-api-base-url.com", // API의 기본 URL 설정
});

export const getPlantGuides = () => api.get("/guide");
export const createPlant = (plantData) => api.post("/guide", plantData);
// 추가적인 API 엔드포인트 함수들...
