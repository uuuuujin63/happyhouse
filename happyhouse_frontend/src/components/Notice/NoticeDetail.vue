<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <b-table-simple :busy="isBusy" class="mt-3" outlined>
      <template #table-busy>
        <div class="text-center text-danger my-2">
          <b-spinner class="align-middle"></b-spinner>
          <strong>Loading...</strong>
        </div>
      </template>
      <b-thead>
        <b-tr>
          <b-th width="10%" class="text-center">{{ getter.title }}</b-th>
        </b-tr>
      </b-thead>
      <b-tbody>
        <b-tr>
          <b-td class="text-right">{{ changeRegtime(getter.regitime) }}</b-td>
        </b-tr>
        <b-tr>
          <b-td rowspan="8">{{ getter.content }}</b-td>
        </b-tr>
      </b-tbody>
    </b-table-simple>
    <div style="text-align: center">
      <b-button variant="secondary" @click="mvlist" style="margin: 10px">
        목록</b-button
      >
      <b-button
        variant="secondary"
        @click="noticedelete"
        style="margin: 10px"
        v-if="valid()"
        >삭제</b-button
      ><b-button
        variant="secondary"
        @click="mvmodify"
        style="margin: 10px"
        v-if="valid()"
        >수정</b-button
      >
    </div>
  </b-container>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import moment from "moment";
const noticeStore = "noticeStore";
const memberStore = "memberStore";
export default {
  data() {
    return {
      notice: {},
    };
  },
  computed: {
    ...mapGetters(noticeStore, ["getNotice"]),
    ...mapState(memberStore, ["userInfo"]),
    getter() {
      console.log(this.getNotice(this.$route.params.idx));
      return this.getNotice(this.$route.params.idx);
    },
  },
  created() {},
  methods: {
    ...mapActions(noticeStore, ["deleteNotice", "getNoticeList"]),
    changeRegtime(regitime) {
      return moment(new Date(regitime)).format("YYYY-MM-DD hh:mm:ss");
    },
    noticedelete() {
      // 삭제 한번 더 확인하는 확인창

      this.deleteNotice(this.$route.params.idx).then(() => {
        this.getNoticeList();
        this.$router.push({ name: "NoticeList" });
      });
    },
    mvmodify() {
      this.$router.push({
        name: "NoticeModify",
        params: { idx: this.$route.params.idx },
      });
    },
    mvlist() {
      this.$router.push({ name: "NoticeList" });
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
