<template>
  <b-container>
    <b-row class="mt-4">
      <b-nav-form class="ml-auto">
        <b-form-input
          v-model="searchWord"
          size="sm"
          class="mr-sm-2"
          placeholder="검색어를 입력하세요."
          @keyup.enter="search"
        ></b-form-input>
        <b-button size="sm" class="my-2 my-sm-0" type="button" @click="search"
          >Search</b-button
        >
      </b-nav-form>

      <b-table-simple
        :busy="isBusy"
        class="mt-3"
        outlined:per-page="perPage"
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
            <b-th width="45%">제목</b-th>
            <b-th width="15%">등록일자</b-th>
          </b-tr>
        </b-thead>
        <b-tbody>
          <b-tr v-for="(notice, index) in lists" :key="index">
            <b-td v-if="currentPage == 1">{{ noticeList.length - index }}</b-td>
            <b-td v-else>{{
              noticeList.length - 10 * (currentPage - 1) - index
            }}</b-td>
            <b-td
              ><router-link
                :to="{ name: 'NoticeDetail', params: { idx: notice.idx } }"
                >{{ notice.title }}</router-link
              ></b-td
            >
            <b-td>{{ changeRegtime(notice.regitime) }}</b-td>
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
          v-if="valid()"
          ><router-link :to="{ name: 'NoticeWrite' }"> 작성하기</router-link>
        </b-button>
      </div>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions, mapState } from "vuex";
import moment from "moment";
const noticeStore = "noticeStore";
const memberStore = "memberStore";
export default {
  name: "NoticeList",
  data() {
    return {
      searchKey: "",
      perPage: 10,
      currentPage: 1,
    };
  },
  computed: {
    ...mapState(noticeStore, ["noticeList"]),
    ...mapState(memberStore, ["userInfo"]),
    rows() {
      return this.noticeList.length;
    },
    lists() {
      const items = this.noticeList;
      return items.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
  },
  created() {
    this.getNoticeList();
  },
  methods: {
    ...mapActions(noticeStore, ["getNoticeList", "searchNoticList"]),
    search() {
      if (!this.searchWord) {
        alert("검색어를 입력해주세요.");
        return;
      }
      this.searchNoticList(this.searchKey);
    },
    changeRegtime(regitime) {
      return moment(new Date(regitime)).format("YYYY-MM-DD");
    },
    valid() {
      if (this.userInfo) {
        if (this.userInfo.admin) {
          return true;
        }
        return false;
      }
      return false;
    },
  },
};
</script>

<style></style>
