<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <div class="blank"></div>
    <b-row class="mb-3 text-center" align-h="center">
      <b-col cols="7">
        <span class="display-3">Join</span>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="5">
        <b-form class="text-left">
          <label for="feedback-user">ID</label>
          <b-form-input
            v-model.lazy="user.userid"
            :state="validation"
            id="feedback-user"
          ></b-form-input>
          <b-form-invalid-feedback :state="validation">
            Your user ID must be 5-12 characters long.
          </b-form-invalid-feedback>
          <b-form-valid-feedback :state="validation"> </b-form-valid-feedback>

          <b-form-group id="input-group-2" label="Password" label-for="input-2">
            <b-form-input
              type="password"
              id="input-2"
              v-model="user.password"
              placeholder="Password..."
              required
              ref="password"
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
              placeholder="비밀번호를 찾기 위한 질문입니다."
              required
              ref="findpwdkey"
              @keyup.enter="confirm"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-3" label="Name" label-for="input-3">
            <b-form-input
              id="input-2"
              v-model="user.name"
              placeholder="name..."
              required
              ref="name"
              @keyup.enter="confirm"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-4" label="Address" label-for="input-4">
            <b-form-select
              style="width: 30%; margin: 5px"
              id="sidoSelect"
              v-model="sido"
              :options="sidos"
              @change="getGugunList"
              required
            ></b-form-select>
            <b-form-select
              style="width: 30%; margin: 5px"
              id="gugunSelect"
              v-model="gugun"
              :options="guguns"
              @change="getDongList"
              required
            ></b-form-select>
            <b-form-select
              style="width: 30%; margin: 5px"
              id="dongSelect"
              v-model="dong"
              :options="dongs"
              required
            ></b-form-select>
            <!-- <b-form-input
              id="input-2"
              v-model="user.addr"
              placeholder="Address..."
              required
              ref="addr"
              @keyup.enter="confirm"
            ></b-form-input> -->
          </b-form-group>

          <div style="text-align: center">
            <b-button type="button" variant="success" @click="confirm"
              >회원가입</b-button
            >
            <b-form-text id="password-help-block">
              이미 가입한 적이 있다면
              <a><router-link :to="{ name: 'Login' }">로그인</router-link></a>
            </b-form-text>
          </div>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
const memberStore = "memberStore";
import { mapActions, mapMutations, mapState } from "vuex";
export default {
  data() {
    return {
      user: {
        userid: "",
        password: null,
        name: null,
        addr: null,
        findpwdkey: null,
        addrcert: null,
      },
      passwordState: false,
      nameState: false,
      addrState: false,
      findpwdkeyState: false,
      // 선택된 dropdown
      sido: null,
      gugun: null,
      dong: null,
    };
  },
  computed: {
    ...mapState(memberStore, ["sidos", "guguns", "dongs"]),
    validation() {
      return this.user.userid.length > 4 && this.user.userid.length < 13;
    },
  },
  created() {
    this.getSidoList();
  },
  methods: {
    ...mapActions(memberStore, [
      "registerUser",
      "getSido",
      "getGugun",
      "getDong",
    ]),
    ...mapMutations(memberStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
    getSidoList() {
      this.CLEAR_SIDO_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.sido = null;
      this.gugun = null;
      this.dong = null;
      this.getSido();
    },
    // 구군 목록을 불러옵니다
    getGugunList() {
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.gugun = null;
      this.dong = null;
      if (this.sido) this.getGugun(this.sido);
    },
    // 동 목록을 불러옵니다
    getDongList() {
      this.CLEAR_DONG_LIST();
      this.dong = null;
      if (this.gugun) this.getDong(this.gugun);
    },
    pwdvalidation() {
      const valid = this.$refs.password.checkValidity();
      this.passwordState = valid;
      return valid;
    },
    addrvalidation() {
      let err = true;
      let msg = "";
      !this.sido && ((msg = "시/도를 선택해주세요"), (err = false));
      err && !this.gugun && ((msg = "시/구/군을 선택해주세요"), (err = false));
      err && !this.dong && ((msg = "읍/면/동을 입력해주세요"), (err = false));
      if (!err) return false;
      return true;
    },
    namevalidation() {
      const valid = this.$refs.name.checkValidity();
      this.nameState = valid;
      return valid;
    },
    findpwdkeyvalidation() {
      const valid = this.$refs.findpwdkey.checkValidity();
      this.findpwdkeyState = valid;
      return valid;
    },

    confirm() {
      if (
        !this.pwdvalidation() ||
        !this.addrvalidation() ||
        !this.namevalidation() ||
        !this.validation ||
        !this.findpwdkeyvalidation()
      ) {
        alert("내용을 입력해주세요.");
        return;
      }
      console.log(this.dong);
      this.user.sidoAddr = this.sido;
      this.user.gugunAddr = this.gugun;
      this.user.addr = this.dong;
      this.registerUser(this.user);
      this.$router.push({ name: "Main" });
    },
  },
};
</script>

<style></style>
