<template>
  <div>
    <b-navbar
      class="navbar navbar-expand-lg navbar-light bg-light fixed-top p-st"
      :class="{
        'bg-light': !nightMode,
        'navbar-blur': navbarConfig.blur,
        'bg-dark2': nightMode,
      }"
    >
      <b-container class="container">
        <router-link
          class="navbar-brand"
          :to="{ name: 'Main' }"
          @click.prevent="$emit('scrollTo', 'home')"
        >
          <Logo :nightMode="nightMode" />
        </router-link>
        <button
          class="navbar-toggler"
          type="button"
          data-toggle="collapse"
          data-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span style="color: gray; font-size: 23px"
            ><i class="fas fa-bars"></i>
          </span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto">
            <li class="nav-item mx-2">
              <router-link
                class="nav-link"
                :to="{ name: 'Main' }"
                :class="{ 'text-light': nightMode }"
                >아파트 정보</router-link
              >
            </li>
            <li class="nav-item mx-2">
              <router-link
                class="nav-link"
                :to="{ name: 'Notice' }"
                :class="{ 'text-light': nightMode }"
                >공지사항</router-link
              >
            </li>
            <li class="nav-item mx-2">
              <router-link
                class="nav-link"
                :to="{ name: 'Main' }"
                :class="{ 'text-light': nightMode }"
                @click.native="checkUserAddr"
                >커뮤니티</router-link
              >
            </li>
            <li class="nav-item mx-2">
              <router-link
                class="nav-link"
                :to="{ name: 'QnA' }"
                :class="{ 'text-light': nightMode }"
                >QnA</router-link
              >
            </li>
            <li class="nav-item mx-2" v-if="userInfo">
              <b-nav-item-dropdown>
                <template #button-content>
                  <strong>{{ userInfo.userid }}</strong
                  >님 환영합니다.
                </template>
                <b-dropdown-item
                  style="text-align: center"
                  :to="{ name: 'Mypage' }"
                  >마이페이지</b-dropdown-item
                >
                <b-dropdown-item
                  style="text-align: center"
                  @click="onClickLogout"
                  >로그아웃</b-dropdown-item
                >
              </b-nav-item-dropdown>
            </li>
            <li class="nav-item mx-2" v-else>
              <router-link
                class="nav-link"
                :to="{ name: 'Login' }"
                :class="{ 'text-light': nightMode }"
                >로그인</router-link
              >
            </li>
            <li class="nav-item mx-2" v-if="!userInfo">
              <router-link
                class="nav-link"
                :to="{ name: 'Join' }"
                :class="{ 'text-light': nightMode }"
                >회원가입</router-link
              >
            </li>
            <li class="nav-item ml-2">
              <a
                class="nav-link"
                href="#"
                @click.prevent="switchMode"
                :class="{ 'text-light': nightMode }"
                ><i
                  :class="{
                    'fas fa-moon': nightMode,
                    'far fa-moon': !nightMode,
                  }"
                  v-tooltip.bottom="nightMode ? 'Light Mode' : 'Night Mode'"
                ></i
              ></a>
            </li>
          </ul>
        </div>
      </b-container>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions, mapGetters } from "vuex";
import Logo from "./helpers/Logo";
import info from "../../info";

const memberStore = "memberStore";

export default {
  name: "Navbar",
  props: {
    nightMode: {
      type: Boolean,
    },
  },
  data() {
    return {
      navbarConfig: info.config.navbar,
      localNightMode: this.nightMode,
    };
  },
  components: {
    Logo,
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  created() {
    console.log(this.userInfo);
  },
  methods: {
    ...mapMutations(memberStore, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    ...mapActions(memberStore, ["getUserInfo"]),
    ...mapGetters(memberStore, ["checkUserInfo"]),
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      sessionStorage.removeItem("access-token");
      console.log(this.userInfo);
      if (this.$route.path != "/") this.$router.push({ name: "Main" });
    },
    switchMode() {
      this.localNightMode = !this.localNightMode;
      this.$emit("nightMode", this.localNightMode);
    },
    checkUserAddr(e) {
      if (!this.userInfo) {
        e.preventDefault();

        alert("로그인 후 이용가능합니다.");
        this.$router.push({ name: "Main" });
      } else if (!this.userInfo.addrcert || this.userInfo.addrcert == "N") {
        e.preventDefault();
        this.$bvModal
          .msgBoxConfirm(
            "인증 후 사용할 수 있습니다.\n 인증화면으로 이동하시겠습니까?",
            {
              okTitle: "네",
              cancelTitle: "아니오",
            }
          )
          .then((value) => {
            if (value == true) {
              this.$router.push({ name: "UserCertify" });
            } else {
              this.$router.push({ name: "Main" });
            }
          });
      } else {
        this.$router.push({ name: "ComuList" });
      }
    },
  },
};
</script>

<style scoped>
.nav-link {
  font-weight: 500;
}
.nav-link:hover {
  background-color: rgba(160, 159, 159, 0.336);
}

button {
  border: none;
  outline: none;
}

button:hover {
  border: none;
  outline: none;
}

nav {
  border-bottom: 1px solid rgba(160, 159, 159, 0.336);
  position: fixed !important;
}

.navbar-blur {
  background-color: #ffffff7e;
  backdrop-filter: blur(12px);
}
</style>
