import { apiInstance } from "./index.js";

const api = apiInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(sido, success, fail) {
  api.get(`/map/gugun/${sido}`).then(success).catch(fail);
}

function dongList(gugun, success, fail) {
  api.get(`/map/dong/${gugun}`).then(success).catch(fail);
}

function houseList(bounds, success, fail) {
  return api.post(`/map/apt/`, bounds).then(success).catch(fail);
}

function dealList(houseInfo, success, fail) {
  return api.post(`/map/deal/`, houseInfo).then(success).catch(fail);
}

function countVisited(houseInfo, success, fail) {
  return api.get(`/map/visited/${houseInfo.aptCode}`).then(success).catch(fail);
}

function getMostVisited(success, fail) {
  return api.get("/map/visited").then(success).catch(fail);
}

export {
  sidoList,
  gugunList,
  dongList,
  houseList,
  dealList,
  countVisited,
  getMostVisited,
};
