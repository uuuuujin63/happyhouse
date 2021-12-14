import { apiInstance } from "@/api/index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  // 1. header의 정보로 access token을 담아감
  console.log("userid " + userid);
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
  // 2. path 변수로 아이디도 담아감
}

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(sido, success, fail) {
  api.get(`/map/gugun/${sido}`).then(success).catch(fail);
}

function dongList(gugun, success, fail) {
  api.get(`/map/dong/${gugun}`).then(success).catch(fail);
}

// 로그아웃 기능

export { login, findById, sidoList, gugunList, dongList };
