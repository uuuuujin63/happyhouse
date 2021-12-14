<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <div class="blank"></div>
    <b-row class="mb-4 text-center" align-h="center">
      <b-col cols="7">
        <span class="display-3">Login</span>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="5">
        <b-form class="text-left">
          <b-alert show variant="danger" v-if="isLoginError"
            >아이디 또는 비밀번호를 확인하세요.</b-alert
          >
          <b-form-group id="input-group-1" label="ID" label-for="input-1">
            <b-form-input
              id="input-1"
              v-model="user.userid"
              type="id"
              placeholder="ID..."
              required
              @keyup.enter="confirm"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-2" label="Password" label-for="input-2">
            <b-form-input
              type="password"
              id="input-2"
              v-model="user.password"
              placeholder="Password..."
              required
              @keyup.enter="confirm"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-3">
            <b-form-checkbox
              v-model="status"
              value="remember"
              unchecked-value="not_remember"
              >아이디 저장</b-form-checkbox
            >
          </b-form-group>
          <div style="text-align: center">
            <b-button
              type="button"
              variant="primary"
              @click="confirm"
              style="margin: 10px"
              >로그인</b-button
            >
            <b-button type="button" variant="success" @click="movePage"
              >회원가입</b-button
            >
          </div>
          <div style="text-align: center">
            <b-form-text id="password-help-block">
              비밀번호를 잊었다면
              <a
                ><router-link :to="{ name: 'FindPwd' }"
                  >비밀번호찾기</router-link
                ></a
              >
            </b-form-text>
          </div>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  name: "Login",
  data() {
    return {
      user: {
        userid: null,
        password: null,
      },
      status: "not_remember",
    };
  },
  created() {
    if (this.$cookie.get("rememberid")) {
      this.status = "remember";
      this.user.userid = this.$cookie.get("rememberid");
    } else {
      this.status = "not_remember";
    }
    console.log(this.status);
    console.log(this.user.userid);
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm() {
      await this.userConfirm(this.user); //토큰저장
      let token = sessionStorage.getItem("access-token"); //토큰 얻어오기
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.checkRememberId();
        this.$router.push({ name: "Main" });
      } else {
        alert("아이디 혹은 비밀번호를 확인해주세요.");
      }
    },
    movePage() {
      this.$router.push({ name: "Join" });
    },
    checkRememberId() {
      if (this.status == "not_remember") {
        this.$cookie.delete("rememberid");
        console.log("rememberid 삭제");
      } else {
        this.$cookie.set("rememberid", this.user.userid);
      }
    },
  },
};
</script>

<style></style>
