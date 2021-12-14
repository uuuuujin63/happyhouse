<template>
  <div id="list-wrapper" class="mt-0">
    <div id="apt-list" class="content-list overflow-auto">
      <!-- 아파트 정보 (이름, 동, 건축년도, 지번) -->
      <table class="table">
        <tr>
          <th
            colspan="2"
            class="table text-center"
            style="height: 70px; font-size: 15px"
            v-if="showAptText"
          >
            지도를 움직여<br />아파트 목록을 확인하세요
          </th>
        </tr>
      </table>
      <table
        class="table shadow"
        v-for="(house, index) in houses"
        :key="index"
        @click="selectHouse(house)"
      >
        <tr>
          <th
            colspan="2"
            class="text-left"
            style="padding-left: 20px; font-size: 15px"
          >
            {{ house.dongName }} {{ house.aptName }}
          </th>
        </tr>
        <!-- 도로명 주소를 보여주면 더 좋을것 같음 -->
        <tr>
          <th style="width: 30%; padding-left: 20px">지번</th>
          <td style="width: 70%">{{ house.jibun }}</td>
        </tr>
        <tr>
          <th style="padding-left: 20px">건축년도</th>
          <td>{{ house.buildYear }}</td>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  name: "HouseList",
  data() {
    return {
      showAptText: true,
      tabIndex: 0,
    };
  },
  props: {
    houseListOn: Boolean,
  },
  computed: {
    ...mapState(houseStore, ["houses", "deals", "house"]),
  },
  watch: {
    houses(newVal) {
      this.showAptText = newVal.length === 0 ? true : false;
    },
    houseListOn(newVal) {
      if (newVal) this.slideList(true);
      else this.slideList(false);
    },
  },
  methods: {
    ...mapMutations(houseStore, ["SET_HOUSE", "CLEAR_HOUSE"]),
    tabClass(idx) {
      if (this.tabIndex == idx) {
        return ["bg-light"];
      } else {
        return ["text-light"];
      }
    },
    selectHouse(house) {
      this.$emit("getHouseDetails", house);
    },
    slideList(direction) {
      if (direction) {
        // 왼쪽으로 밀기
      } else {
        // 오른쪽으로 밀기
      }
    },
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style scoped>
#list-wrapper {
  top: 10px;
  right: 10px;
  position: absolute;
  background-color: transparent;
  /* background-color: lightgrey; */
  height: 95%;
  z-index: 1;
}
#list-wrapper table {
  margin: 5px 10px;
  min-width: 300px;
  width: 94%;
  border: 2px solid transparent;
  border-radius: 15px;
  background-color: white;
  font-size: 14px;
}
#list-wrapper table:hover {
  background-color: #9ec0e8;
}
#list-wrapper .content-list {
  height: 100%;
}
</style>
