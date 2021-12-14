<template>
  <div>
    <!-- 시도 시구군 읍면동 선택 -->
    <div id="menu_wrap">
      <div class="option">
        <b-form variant="transparent">
          <b-form-group id="regionSelectForm">
            <b-form-select
              id="sidoSelect"
              v-model="sido"
              :options="sidos"
              @change="getGugunList"
              required
            ></b-form-select>
            <b-form-select
              id="gugunSelect"
              v-model="gugun"
              :options="guguns"
              @change="getDongList"
              required
            ></b-form-select>
            <b-form-select
              id="dongSelect"
              v-model="dong"
              :options="dongs"
              required
            ></b-form-select>
            <b-button
              variant="outline-secondary"
              size="sm"
              style="width: 50px; font-size: 13px"
              @click="moveToSelectedRegion"
            >
              이동
            </b-button>
          </b-form-group>
        </b-form>
      </div>
    </div>
    <!-- 체크박스 -->
    <div id="checkbox_wrap">
      <table class="h-100 w-100 text-center">
        <tr>
          <td>
            <button type="button" @click="selectCategory('APT')">
              아파트🏠
            </button>
          </td>
          <td>
            <button type="button" @click="selectCategory('SC4')">학교🎓</button>
          </td>
          <td>
            <button type="button" @click="selectCategory('PS3')">
              어린이집/유치원🐤
            </button>
          </td>
          <td>
            <button type="button" @click="selectCategory('HP8')">병원💊</button>
          </td>
          <td>
            <button type="button" @click="selectCategory('AC5')">학원📗</button>
          </td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapState } from "vuex";
const houseStore = "houseStore";

export default {
  name: "MapOptions",
  data() {
    return {
      // 선택된 dropdown
      sido: null,
      gugun: null,
      dong: null,
    };
  },
  computed: {
    ...mapState(houseStore, ["sidos", "guguns", "dongs"]),
  },
  created() {
    this.getSidoList();
  },
  methods: {
    ...mapActions(houseStore, ["getSido", "getGugun", "getDong"]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "SET_LOCATION",
    ]),
    // 시도 목록을 불러옵니다
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
    // 유효성 검사를 하고 선택된 지역으로 지도를 이동합니다
    moveToSelectedRegion() {
      let err = true;
      let msg = "";
      !this.sido && ((msg = "시/도를 선택해주세요"), (err = false));
      err && !this.gugun && ((msg = "시/구/군을 선택해주세요"), (err = false));
      err && !this.dong && ((msg = "읍/면/동을 입력해주세요"), (err = false));

      if (!err) alert(msg);
      else {
        this.SET_LOCATION(this.dong);
      }
    },
    selectCategory(category) {
      this.$emit("selectCategory", category);
    },
  },
};
</script>

<style scoped>
#menu_wrap {
  position: absolute;
  top: 0px;
  left: 20px;
  width: 330px;
  height: 45px;
  margin: 10px 20px 30px 10px;
  padding: 5px;
  background: rgba(226, 226, 226, 0.9);
  z-index: 1;
  border-radius: 5px;
}
#menu_wrap .option {
  text-align: center;
}
#menu_wrap .option p {
  margin: 10px 0;
}
#menu_wrap .option button {
  margin-left: 5px;
  font-size: 15px;
}
#menu_wrap select {
  font-size: 12px;
  width: 85px;
  background-color: #11ffee00;
  border: 0px solid;
  padding: 5px;
}
#checkbox_wrap {
  position: absolute;
  top: 50px;
  left: 20px;
  width: 420px;
  margin: 10px 20px 30px 10px;
  padding: 8px;
  background: rgba(226, 226, 226, 0.9);
  z-index: 1;
  border-radius: 5px;
}
#checkbox_wrap button {
  background-color: transparent;
  border: 1px solid transparent;
  border-radius: 5px;
  width: 100%;
  height: 100%;
  font-size: 14px;
}
#checkbox_wrap button:hover {
  background-color: lightgrey;
}
#checkbox_wrap button:active {
  background-color: grey;
}
</style>
