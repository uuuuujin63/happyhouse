<template>
  <b-container class="bv-example-row mt-3" align-v="center">
    <div class="blank"></div>
    <b-row class="mb-4 text-center" align-h="center">
      <b-col cols="7">
        <span class="display-3">My Page</span>
      </b-col>
    </b-row>
    <b-row align-h="center">
      <b-col cols="5">
        <b-form class="text-left">
          <b-form-group id="input-group-1" label="ID" label-for="input-1">
            <b-form-input
              id="input-1"
              v-model="userInfo.userid"
              ref="id"
              required
              :disabled="true"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-2" label="Password" label-for="input-2">
            <b-form-input
              id="input-2"
              ref="password"
              type="password"
              required
              v-model="userInfo.password"
              :disabled="disabled"
            ></b-form-input>
          </b-form-group>

          <b-form-group id="input-group-3" label="Name" label-for="input-3">
            <b-form-input
              id="input-3"
              ref="name"
              required
              v-model="userInfo.name"
              :disabled="disabled"
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
              :disabled="disabled"
            ></b-form-select>
            <b-form-select
              style="width: 30%; margin: 5px"
              id="gugunSelect"
              v-model="gugun"
              :options="guguns"
              @change="getDongList"
              required
              :disabled="disabled"
            ></b-form-select>
            <b-form-select
              style="width: 30%; margin: 5px"
              id="dongSelect"
              v-model="dong"
              :options="dongs"
              required
              :disabled="disabled"
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
          <!-- <b-form-group id="input-group-4" label="Address" label-for="input-4">
            <b-form-input
              id="input-4"
              ref="addr"
              required
              v-model="userInfo.addr"
              :disabled="disabled"
            ></b-form-input> -->
          <!-- </b-form-group> -->
          <div style="text-align: center">
            <div style="text-align: right">
              <a v-b-modal.remove-modal style="color: red" @click="removeUser"
                >회원탈퇴</a
              >
            </div>
            <div style="text-align: center">
              <b-button
                type="button"
                variant="success"
                @click="modify"
                v-if="this.disabled"
                >수정</b-button
              >
              <b-button
                type="button"
                variant="success"
                @click="endModify"
                v-else
                >수정완료</b-button
              >
            </div>
          </div>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MyPage",
  components: {},
  computed: {
    ...mapState(memberStore, ["sidos", "guguns", "dongs"]),
    // ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    this.userInfo = this.$store.state.memberStore.userInfo;
    //console.log(this.userInfo);
    this.getSidoList();
    this.sido = this.$store.state.memberStore.userInfo.sidoAddr;
    console.log(this.userInfo);
    this.getGugunList();
    this.gugun = this.$store.state.memberStore.userInfo.gugunAddr;
    this.getDongList();
    this.dong = this.$store.state.memberStore.userInfo.addr;
  },
  methods: {
    ...mapActions(memberStore, [
      "setUserInfo",
      "deleteUser",
      "getSido",
      "getGugun",
      "getDong",
    ]),
    ...mapMutations(memberStore, [
      "SET_IS_LOGIN",
      "SET_USER_INFO",
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

    modify() {
      this.disabled = false;
    },
    removeUser() {
      this.$bvModal
        .msgBoxConfirm("탈퇴하시겠습니까?", {
          okTitle: "네",
          cancelTitle: "아니오",
        })
        .then((value) => {
          if (value == true) {
            this.deleteUser(this.userInfo.userid);
            this.SET_IS_LOGIN(false);
            this.SET_USER_INFO(null);
            sessionStorage.removeItem("access-token");
            if (this.$route.path != "/") this.$router.push({ name: "Main" });
          }
        });
    },
    endModify() {
      if (
        !this.checkIdValidity() ||
        !this.checkPwdValidity() ||
        !this.checkNameValidity() ||
        !this.checkAddrValidity()
      ) {
        alert("입력하신 내용을 확인해주세요.");
        return;
      }
      console.log("endModify");
      this.userInfo.sidoAddr = this.sido;
      this.userInfo.gugunAddr = this.gugun;
      this.userInfo.addr = this.dong;
      console.log(this.userInfo);
      console.log(this.dong);
      this.setUserInfo(this.userInfo);
      this.disabled = true;
      // 내용 수정으로 보내기
      this.$router.push({ name: "Mypage" });
    },
    checkIdValidity() {
      const valid = this.$refs.id.checkValidity();
      this.useridState = valid;
      return valid;
    },
    checkPwdValidity() {
      const valid = this.$refs.password.checkValidity();
      this.passwordState = valid;
      return valid;
    },
    checkNameValidity() {
      const valid = this.$refs.name.checkValidity();
      this.nameState = valid;
      return valid;
    },
    checkAddrValidity() {
      let err = true;
      let msg = "";
      !this.sido && ((msg = "시/도를 선택해주세요"), (err = false));
      err && !this.gugun && ((msg = "시/구/군을 선택해주세요"), (err = false));
      err && !this.dong && ((msg = "읍/면/동을 입력해주세요"), (err = false));
      console.log(this.sido);
      console.log(this.gugun);
      console.log(this.dong);
      if (!err) return false;
      return true;
    },
  },
  data() {
    return {
      disabled: true,
      userInfo: {
        userid: "",
        password: "",
        name: "",
        addr: "",
        sidoAddr: "",
        gugunAddr: "",
      },
      useridState: null,
      passwordState: null,
      nameState: null,
      addrState: null,
      sido: null,
      gugun: null,
      dong: null,
    };
  },
};
</script>

<style></style>
