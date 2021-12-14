<template>
  <b-container>
    <b-row class="mt-4">
      <b-nav-form class="ml-auto">
        <b-form-select
          v-model="searchKey"
          :options="options"
          size="sm"
          class="mr-sm-1"
        ></b-form-select>
        <b-form-input
          v-model="searchWord"
          size="sm"
          class="mr-sm-2"
          placeholder="검색어를 입력하세요."
        ></b-form-input>
        <b-button size="sm" class="my-2 my-sm-0" type="button" @click="search"
          >Search</b-button
        >
      </b-nav-form>

      <b-table-simple
        :busy="isBusy"
        class="mt-3"
        outlined
        :per-page="perPage"
        :current-page="currentPage"
      >
        <template #table-busy>
          <div class="text-center text-danger my-2">
            <b-spinner class="align-middle"></b-spinner>
            <strong>Loading...</strong>
          </div>
        </template>
        <b-thead>
          <b-tr>
            <b-th width="10%">번호</b-th>
            <b-th width="10%">답변여부</b-th>
            <b-th width="45%">제목</b-th>
            <b-th width="10%">작성자</b-th>
            <b-th width="15%">등록일자</b-th>
          </b-tr>
        </b-thead>
        <b-tbody v-for="(qna, index) in lists" :key="index">
          <b-tr>
            <b-td>{{ qnaList.length - index }}</b-td>
            <b-td
              ><b>{{ isReplied(qna.answer) }}</b></b-td
            >
            <b-td @click="toggleView(index)">{{ qna.q_title }}</b-td>
            <b-td>{{ qna.iduser }}</b-td>
            <b-td>{{ changeRegtime(qna.regtime) }}</b-td>
          </b-tr>
          <b-tr v-show="toggle[index]">
            <b-th colspan="1">내용</b-th>
            <b-td colspan="3">{{ qna.q_content }}</b-td>
            <b-th colspan="1"
              ><b-button
                v-show="toggle[index]"
                size="sm"
                variant="outline-danger"
                @click="remove(qna)"
                v-if="canDelete(qna.iduser)"
              >
                삭제
              </b-button></b-th
            >
          </b-tr>
          <b-tr v-show="toggle[index] && qna.answer" variant="secondary">
            <b-th colspan="1">답변</b-th>
            <b-td colspan="4">{{ qna.answer }}</b-td>
          </b-tr>
          <b-tr
            v-show="toggle[index] && !qna.answer"
            variant="secondary"
            v-if="adminvalid()"
          >
            <b-th colspan="1" v-if="adminvalid()">답변입력</b-th>
            <b-td colspan="3">
              <b-form-textarea
                v-model="answer"
                placeholder="답변 내용을 입력해주세요."
              />
            </b-td>
            <b-td class="align-middle" colspan="1">
              <b-button
                size="sm"
                variant="outline-secondary"
                @click="addReply(qna)"
              >
                등록
              </b-button>
            </b-td>
          </b-tr>
        </b-tbody>
      </b-table-simple>

      <b-pagination
        class="mx-auto"
        v-model="currentPage"
        :total-rows="rows"
        :per-page="perPage"
        first-text="First"
        prev-text="Prev"
        next-text="Next"
        last-text="Last"
      ></b-pagination>
      <div>
        <b-button
          v-b-modal.modal-lg.modal-prevent-closing
          size="sm"
          class="ml-auto"
          variant="outline-secondary"
          v-if="writevalid()"
        >
          작성하기
        </b-button>
      </div>
    </b-row>
    <qna-write-modal v-on:getList="getList" />
  </b-container>
</template>

<script>
import moment from "moment";
import QnaWriteModal from "@/components/QnA/QnAWriteModal.vue";

import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";
const qnaStore = "qnaStore";

export default {
  data() {
    return {
      isBusy: false,
      toggle: [],
      answer: "",
      searchKey: "iduser",
      searchWord: "",
      options: [
        { value: "iduser", text: "작성자" },
        { value: "q_no", text: "번호" },
      ],
      perPage: 10,
      currentPage: 1,
    };
  },
  created() {
    this.getQnaList();
  },
  mounted() {
    for (let i = 0; i < this.qnaList.length; i++) {
      this.toggle.push(false);
    }
  },
  components: {
    QnaWriteModal,
  },
  computed: {
    ...mapState(qnaStore, ["qnaList"]),
    ...mapState(memberStore, ["userInfo"]),
    rows() {
      return this.qnaList.length;
    },
    lists() {
      const items = this.qnaList;
      return items.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
  },
  methods: {
    ...mapActions(qnaStore, [
      "getQnaList",
      "toggleQna",
      "modifyQna",
      "removeQna",
      "searchByQnaId",
      "searchByQnaNo",
    ]),
    getList() {
      this.getQnaList();
    },
    toggleView(index) {
      for (let i = 0; i < this.qnaList.length; i++) {
        if (i !== index) this.toggle[i] = false;
      }
      this.$set(this.toggle, index, !this.toggle[index]);
    },
    toggleOff() {
      for (let i = 0; i < this.qnaList.length; i++) {
        this.toggle[i] = false;
      }
    },
    addReply(qna) {
      qna.answer = this.answer;
      this.modifyQna(qna);
    },
    remove(qna) {
      this.removeQna(qna).then(() => {
        this.getQnaList();
        this.toggleOff();
      });
    },
    search() {
      if (!this.searchWord) {
        alert("검색어를 입력해주세요.");
        return;
      }
      if (this.searchKey === "iduser") {
        alert("작성자 아이디로 검색합니다.");
        this.searchByQnaId(this.searchWord);
      } else if (this.searchKey === "q_no") {
        alert("글 번호로 검색합니다.");
        this.searchByQnaNo(this.searchWord);
      }
    },
    changeRegtime(regtime) {
      return moment(new Date(regtime)).format("YYYY-MM-DD");
    },
    isReplied(answer) {
      if (answer) return "답변완료";
      else return "답변예정";
    },
    canDelete(id) {
      if (this.userInfo) {
        return this.userInfo.userid == id || this.userInfo.admin;
      }
      return false;
    },
    adminvalid() {
      if (this.userInfo) {
        if (this.userInfo.admin) {
          return true;
        }
        return false;
      }
      return false;
    },
    // 비로그인자는 qna 작성하지 못하도록 막음
    writevalid() {
      if (this.userInfo) {
        return true;
      }
      return false;
    },
  },
};
</script>

<style scoped>
#box {
  border: 1px solid black;
  width: 100%;
}
</style>
