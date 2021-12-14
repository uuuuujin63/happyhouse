import http from "@/util/http-common.js";
const noticeStore = {
  namespaced: true,
  state: {
    noticeList: [],
    notice: { idx: 0 },
  },
  getters: {
    getNotice: (state) => (idx) => {
      console.log(idx);
      console.log(state.noticeList);
      let temp = state.noticeList.find((notice) => notice.idx == idx);
      if (temp) return temp;
      return state.notice;
    },
  },
  mutations: {
    SET_NOTICELIST: (state, noticeList) => {
      state.noticeList = noticeList;
    },
    SEARCH_BY_TITLE: (state, noticeList) => {
      state.noticeList = noticeList;
    },
    // SET_NOTICE: (state, notice) => {
    //     console.log(notice);
    //     state.notice = notice;
    // },
    DO_NOTHING() {},
  },
  actions: {
    getNoticeList({ commit }) {
      http.get("/notice").then((response) => {
        //console.log(response);
        commit("SET_NOTICELIST", response.data);
      });
    },
    searchNoticList({ commit }, keyword) {
      http.get(`/notice/search/${keyword}`).then((response) => {
        //console.log(response);
        commit("SEARCH_BY_TITLE", response.data);
      });
    },
    // getNotice({ commit }, idx) {
    //     http.get(`/notice/${idx}`).then((response) => {
    //         commit("SET_NOTICE", response.data);
    //         console.log("왜안돼");
    //         console.log(this.state.notice);
    //     });
    // },
    registerNotice({ commit }, notice) {
      return http.post("/notice", notice).then((response) => {
        if (response.data === "success") {
          commit("DO_NOTHING");
          alert("공지사항이 등록되었습니다.");
        } else {
          alert("공지사항이 등록 실패");
        }
      });
    },
    modifyNotice({ commit }, notice) {
      http.put("/notice", notice).then((response) => {
        if (response.data === "success") {
          commit("DO_NOTHING");
          alert("공지사항이 수정되었습니다.");
        }
      });
    },
    deleteNotice({ commit }, idx) {
      return http.delete(`/notice/${idx}`).then((response) => {
        if (response.data === "success") {
          commit("DO_NOTHING");
          alert("공지사항이 삭제되었습니다.");
        }
      });
    },
  },
};

export default noticeStore;
