<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <div class="blank"></div>
    <b-row class="mb-4 text-center" align-h="center">
      <b-col cols="7">
        <span class="display-3">비밀번호 찾기</span>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="5">
        <b-form class="text-left">
          <b-form-group id="input-group-1" label="ID" label-for="input-1">
            <b-form-input
              id="input-1"
              v-model="user.userid"
              type="id"
              placeholder="ID..."
              required
              ref="userid"
              @keyup.enter="confirm"
            ></b-form-input>
          </b-form-group>

          <b-form-group
            id="input-group-2"
            label="당신이 어릴적 살았던 동네는 ?"
            label-for="input-2"
          >
            <b-form-input
              id="input-2"
              v-model="user.findpwdkey"
              placeholder="답변을 입력해주세요"
              required
              ref="findpwdkey"
              @keyup.enter="confirm"
            ></b-form-input>
          </b-form-group>

          <div style="text-align: center">
            <b-button type="button" variant="success" @click="movePage"
              >뒤로</b-button
            >
            <b-button
              type="button"
              variant="primary"
              @click="confirm"
              style="margin: 10px"
              >찾기</b-button
            >
          </div>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      user: {
        userid: "",
        findpwdkey: "",
      },
      useridState: false,
      findpwdkey: false,
    };
  },
  methods: {
    ...mapActions(memberStore, ["findPwd"]),
    confirm() {
      if (!this.useridvalidation() || !this.findpwdkeyvalidation()) {
        alert("아이디 및 답변을 확인하세요");
        return;
      }
      this.findPwd(this.user);
      console.log(this.$store.state.memberStore.findState);
      if (this.$store.state.memberStore.findState) {
        this.$router.push({ name: "Login" });
      }
    },
    useridvalidation() {
      const valid = this.$refs.userid.checkValidity();
      this.useridState = valid;
      return valid;
    },
    findpwdkeyvalidation() {
      const valid = this.$refs.findpwdkey.checkValidity();
      this.findpwdkeyState = valid;
      return valid;
    },
    movePage() {
      this.$router.push({ name: "Login" });
    },
  },
};
</script>

<style></style>
