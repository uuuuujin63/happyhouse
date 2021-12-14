<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <form ref="form" @submit.stop.prevent="handleSubmit">
      <b-form-group
        label="제목"
        label-for="title-input"
        invalid-feedback="제목을 입력해주세요."
        :state="titleState"
      >
        <b-form-input
          id="title-input"
          v-model="notice.title"
          :state="titleState"
          ref="title"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="내용"
        label-for="content-input"
        invalid-feedback="내용을 입력해주세요."
        :state="contentState"
      >
        <b-form-textarea
          id="content-input"
          v-model="notice.content"
          :state="contentState"
          ref="content"
          rows="3"
          required
        ></b-form-textarea>
      </b-form-group>
      <div style="text-align: center">
        <b-button variant="secondary" @click="mvlist"> 취소</b-button>
        <b-button variant="secondary" @click="confirm" style="margin: 10px"
          >등록</b-button
        >
      </div>
    </form>
  </b-container>
</template>

<script>
import { mapActions } from "vuex";
const noticeStore = "noticeStore";
const memberStore = "memberStore";
export default {
  data() {
    return {
      iduser: "admin",
      titleState: null,
      contentState: null,
      notice: {
        title: "",
        content: "",
        userid: "",
      },
    };
  },
  created() {
    this.notice.userid = this.$store.state.memberStore.userInfo.userid;
  },
  methods: {
    ...mapActions(noticeStore, ["registerNotice", "getNoticeList"]),
    checkTitleValidity() {
      const valid = this.$refs.title.checkValidity();
      this.titleState = valid;
      return valid;
    },
    checkContentValidity() {
      const valid = this.$refs.content.checkValidity();
      this.contentState = valid;
      return valid;
    },
    confirm() {
      console.log(this.notice);
      console.log(this.notice.title);
      if (!this.checkContentValidity() || !this.checkTitleValidity()) {
        alert("내용을 입력해주세요 !");
        return;
      }
      this.registerNotice(this.notice).then(() => {
        this.getNoticeList();
        this.$router.push({ name: "NoticeList" });
      });
    },
    mvlist() {
      this.$router.push({ name: "NoticeList" });
    },
  },
};
</script>

<style></style>
