import jwt_decode from "jwt-decode";
import {
  login,
  findById,
  sidoList,
  gugunList,
  dongList,
} from "@/api/member.js";

import http from "@/util/http-common.js";
const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    findState: false,

    sidos: [{ text: "시/도", value: null }],
    guguns: [{ text: "시/구/군", value: null }],
    dongs: [{ text: "읍/면/동", value: null }],
    dongcode: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
      console.log("수정완료");
      console.log(state.userInfo);
    },
    SET_FINDSTATE_T: (state) => {
      state.findState = true;
    },
    SET_FINDSTATE_F: (state) => {
      state.findState = false;
    },
    NOTTHING: (state) => {},
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
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ text: "시/도", value: null }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ text: "시/구/군", value: null }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ text: "읍/면/동", value: null }];
    },
    SET_LOCATION: (state, dongcode) => {
      state.dongcode = dongcode;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          console.log(response.data);
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    // kakao_userConfirm({ commit }) {
    //   axios.get(`/oauth/authorize?client_id=4d70cd9d2c4c9787cb59af8f108c8fc8&redirect_uri=`)
    // },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
          console.log(this.state.userInfo);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    setUserInfo({ commit }, userInfo) {
      http.put("/user", userInfo).then((response) => {
        console.log(response.data);
        if (response.data === "success") {
          console.log(userInfo);
          commit("SET_USER_INFO", userInfo);
          alert("회원정보 수정 완료");
        }
      });
    },
    registerUser({ commit }, userInfo) {
      http.post("/user", userInfo).then((response) => {
        if (response.data === "success") {
          commit("SET_FINDSTATE_T");
          alert("회원가입 완료");
        }
      });
    },
    deleteUser({ commit }, userId) {
      console.log("id " + userId);
      http
        .delete(`/user/delete/${userId}`)
        .then((response) => {
          if (response.data === "success") {
            commit("NOTTHING");
            alert("회원 탈퇴가 완료되었습니다.");
          } else {
            commit("NOTTHING");
            alert("처리 중에 문제가 발생했습니다ㅠㅠ");
          }
        })
        .catch((error) => {
          console.log("response: ", error.response.data);
        });
    },
    findPwd({ commit }, userInfo) {
      http
        .get(
          `user/find?userid=${userInfo.userid}&findpwdkey=${userInfo.findpwdkey}`
        )
        .then((response) => {
          if (response.data.message === "success") {
            commit("SET_FINDSTATE_T");
            alert(
              "당신의 비밀번호는 " + response.data.userInfo.password + "입니다."
            );
          } else {
            commit("SET_FINDSTATE_F");
            alert("비밀번호 찾기 실패");
          }
        });
    },
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          console.log(data);
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
  },
};

export default memberStore;
