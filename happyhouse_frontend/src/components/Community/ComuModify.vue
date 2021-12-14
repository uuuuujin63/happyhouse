<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <form ref="form">
      <b-form-group
        label="제목"
        label-for="title-input"
        invalid-feedback="제목을 입력해주세요."
        :state="titleState"
      >
        <b-form-input
          id="title-input"
          v-model="getter.title"
          :state="titleState"
          ref="title"
          required
          >{{ getter.title }}</b-form-input
        >
      </b-form-group>
      <b-form-group
        label="내용"
        label-for="content-input"
        invalid-feedback="내용을 입력해주세요."
        :state="contentState"
      >
        <b-form-textarea
          id="content-input"
          v-model="getter.content"
          :state="contentState"
          ref="content"
          rows="3"
          required
          >{{ getter.content }}</b-form-textarea
        >
      </b-form-group>
      <div style="text-align: center">
        <b-button variant="secondary"
          ><router-link :to="{ name: 'ComuList' }">취소</router-link></b-button
        >
        <b-button variant="secondary" @click="confirm" style="margin: 10px"
          >수정</b-button
        >
      </div>
    </form>
  </b-container>
</template>

<script>
const comuStore = "comuStore";
import { mapActions, mapGetters } from "vuex";
export default {
  name: "ArticleModify",
  data() {
    return {
      titleState: true,
      contentState: true,
    };
  },
  computed: {
    ...mapGetters(comuStore, ["getArticle"]),
    getter() {
      console.log(this.getArticle(this.$route.params.idx));
      return this.getArticle(this.$route.params.idx);
    },
  },
  methods: {
    ...mapActions(comuStore, ["modifyArticle"]),

    confirm() {
      if (!this.titleVail() || !this.contentVali()) {
        alert("내용을 확인해주세요!!!");
        return;
      }
      this.modifyArticle(this.getter);
      this.$router.push({
        name: "ComuDetail",
        params: { idx: this.$route.params.idx },
      });
    },

    titleVail() {
      const valid = this.$refs.title.checkValidity();
      this.titleState = valid;
      return valid;
    },

    contentVali() {
      const valid = this.$refs.content.checkValidity();
      this.contentState = valid;
      return valid;
    },
  },
};
</script>

<style></style>
