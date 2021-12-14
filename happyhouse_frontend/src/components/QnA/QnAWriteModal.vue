<template>
  <b-modal
    id="modal-prevent-closing"
    ref="modal"
    size="lg"
    title="이용 문의"
    @show="resetModal"
    @hidden="resetModal"
    @ok="handleOk"
  >
    <form ref="form" @submit.stop.prevent="handleSubmit">
      <b-form-group label="아이디" label-for="iduser-input">
        <b-form-input
          id="iduser-input"
          v-model="iduser"
          disabled
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="제목"
        label-for="title-input"
        invalid-feedback="문의 제목을 입력해주세요."
        :state="titleState"
      >
        <b-form-input
          id="title-input"
          v-model="q_title"
          :state="titleState"
          ref="title"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group
        label="내용"
        label-for="content-input"
        invalid-feedback="문의 내용을 입력해주세요."
        :state="contentState"
      >
        <b-form-textarea
          id="content-input"
          v-model="q_content"
          :state="contentState"
          ref="content"
          rows="3"
          required
        ></b-form-textarea>
      </b-form-group>
    </form>
  </b-modal>
</template>

<script>
import { mapActions } from "vuex";
const qnaStore = "qnaStore";

export default {
  data() {
    return {
      iduser: "",
      q_title: "",
      titleState: null,
      q_content: "",
      contentState: null,
    };
  },
  computed: {},
  created() {
    this.iduser = this.$store.state.memberStore.userInfo.userid;
  },
  methods: {
    ...mapActions(qnaStore, ["getQnaList", "registerQna"]),
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
    resetModal() {
      this.q_title = "";
      this.titleState = null;
      this.q_content = "";
      this.contentState = null;
    },
    handleOk(bvModalEvt) {
      // Prevent modal from closing
      bvModalEvt.preventDefault();
      // Trigger submit handler
      this.handleSubmit();
    },
    handleSubmit() {
      // Exit when the form isn't valid
      if (!this.checkTitleValidity() || !this.checkContentValidity()) {
        return;
      }
      // 등록 여부 alert로 확인 후 닫아주기
      this.registerQna({
        iduser: this.iduser,
        q_title: this.q_title,
        q_content: this.q_content,
      }).then(() => {
        this.$emit("getList");
      });
      // Hide the modal manually
      this.$nextTick(() => {
        this.$bvModal.hide("modal-prevent-closing");
      });
    },
  },
};
</script>

<style></style>
