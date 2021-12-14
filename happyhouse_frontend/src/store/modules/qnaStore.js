import http from "@/util/http-common.js";

const qnaStore = {
  namespaced: true,
  state: {
    qnaList: [],
  },
  mutations: {
    GET_QNA_LIST(state, data) {
      state.qnaList = data;
    },
    REGISTER_QNA(state, data) {
      state.qnaList.push(data);
    },
    REMOVE_QNA(state, data) {
      console.log(state.qnaList.indexOf(data));
      state.qnaList.splice(state.qnaList.indexOf(data), 1);
    },
    SEARCH_BY_QNA_ID(state, data) {
      state.qnaList = data;
    },
    SEARCH_BY_QNA_NO(state, data) {
      let dataList = [data];
      state.qnaList = dataList;
    },
    DO_NOTHING() {},
  },
  actions: {
    getQnaList({ commit }) {
      return http.get("/qna").then((response) => {
        console.log(response);
        commit("GET_QNA_LIST", response.data);
      });
    },
    registerQna({ commit }, qna) {
      return http.post("/qna", qna).then((response) => {
        console.log(response);
        if (response.data === "success") {
          commit("REGISTER_QNA", qna);
          alert("문의 내용을 등록했습니다.");
        }
      });
    },
    modifyQna({ commit }, qna) {
      return http.put(`/qna/${qna.q_no}`, qna).then((response) => {
        if (response.data === "success") {
          commit("DO_NOTHING");
          alert("답변이 등록되었습니다.");
        }
      });
    },
    removeQna({ commit }, qna) {
      return http.delete(`/qna/${qna.q_no}`).then((response) => {
        if (response.data === "success") {
          commit("DO_NOTHING");
          alert("문의 내역이 삭제되었습니다.");
        }
      });
    },
    searchByQnaId({ commit }, iduser) {
      return http.get(`/qna/search/id/${iduser}`).then((response) => {
        commit("SEARCH_BY_QNA_ID", response.data);
      });
    },
    searchByQnaNo({ commit }, q_no) {
      return http.get(`/qna/search/no/${q_no}`).then((response) => {
        commit("SEARCH_BY_QNA_NO", response.data);
      });
    },
  },
};

export default qnaStore;
