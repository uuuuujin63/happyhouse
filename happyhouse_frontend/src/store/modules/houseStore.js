import {
  sidoList,
  gugunList,
  dongList,
  houseList,
  dealList,
  countVisited,
  getMostVisited,
} from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ text: "시/도", value: null }],
    guguns: [{ text: "시/구/군", value: null }],
    dongs: [{ text: "읍/면/동", value: null }],
    houses: [],
    house: null,
    deals: [],
    dongcode: null,
    envInfos: [],
    mostVisited: [],
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_DEAL_LIST: (state, deals) => {
      state.deals = deals;
    },
    SET_HOUSE: (state, house) => {
      state.house = house;
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ text: "시/도", value: null }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ text: "시/구/군", value: null }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ text: "읍/면/동", value: null }];
    },
    CLEAR_HOUSE_LIST: (state) => {
      state.houses = [];
    },
    CLEAR_DEAL_LIST: (state) => {
      state.deals = [];
    },
    CLEAR_HOUSE: (state) => {
      state.house = null;
    },
    PUSH_ENV_INFOS: (state, data) => {
      state.envInfos.push(data);
    },
    CLEAR_ENV_INFOS: (state) => {
      state.envInfos = [];
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_LOCATION: (state, dongcode) => {
      state.dongcode = dongcode;
    },
    SET_MOST_VISITED: (state, data) => {
      state.mostVisited = data;
    },
    DO_NOTHING: () => {},
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          // console.log(data);
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      gugunList(
        sidoCode,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      dongList(
        gugunCode,
        ({ data }) => {
          // console.log(commit, response);
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouse: ({ commit }, bounds) => {
      return houseList(
        bounds,
        ({ data }) => {
          // console.log(data);
          commit("SET_HOUSE_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDeal: ({ commit }, houseInfo) => {
      return dealList(
        houseInfo,
        ({ data }) => {
          console.log(data);
          commit("SET_DEAL_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    countVisited: ({ commit }, houseInfo) => {
      countVisited(
        houseInfo,
        ({ data }) => {
          commit("DO_NOTHING", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getMostVisited: ({ commit }) => {
      getMostVisited(
        ({ data }) => {
          commit("SET_MOST_VISITED", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default houseStore;
