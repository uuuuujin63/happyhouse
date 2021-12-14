<template>
  <div>
    <b-container>
      <div class="blank"></div>
      <b-row class="mt-3">
        <b-col cols="9">
          <span class="display-3">Community </span>
          <!-- 동네에 역삼동을 ~ 이런식으로 동 이름을 넣으면 좋을 것 같음 -->
          <span class="h5">
            <strong>{{ this.dongName }}</strong
            >을 대표하는 공간입니다.</span
          >
        </b-col>
      </b-row>
    </b-container>
    <router-view />
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const memberStore = "memberStore";
const comuStore = "comuStore";
export default {
  data() {
    return {
      sido: null,
      gugun: null,
      dong: null,
    };
  },
  created() {
    this.dong = this.$store.state.memberStore.userInfo.addr;
    this.getDongName(this.dong);
    console.log(this.dongName);
  },
  computed: {
    ...mapState(memberStore, ["dongs"]),
    ...mapState(comuStore, ["dongName"]),
  },
  methods: {
    ...mapActions(comuStore, ["getDongName"]),
  },
};
</script>

<style></style>
