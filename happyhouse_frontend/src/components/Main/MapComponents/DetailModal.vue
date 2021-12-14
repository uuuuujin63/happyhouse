<template>
  <b-modal
    id="detail-modal"
    modal-class="modal"
    title-class="ml-2"
    centered
    :title="houseName"
    @hide="modalClose"
    ok-only
    size="xl"
  >
    <b-row class="wrapper">
      <b-col id="deal-list" cols="6" class="px-4">
        <div style="font-size: 18px">
          <b>실거래 정보</b>
          <span style="font-size: 13px; margin-left: 7px">
            최근 6개월 간의 거래 정보가 표시됩니다.</span
          >
          <hr />
        </div>

        <div v-if="loading" class="text-center" style="margin-top: 30px">
          <b-spinner small label="Loading..."></b-spinner>
          <span> 매물 정보를 불러오고 있습니다...</span>
        </div>
        <div v-else class="overflow-auto h-100">
          <div
            id="table-wrapper"
            v-for="(deal, index) in deals"
            :key="index"
            v-show="!isDealEmpty"
          >
            <table class="table table-borderless m-0">
              <tr>
                <th>거래액</th>
                <td>{{ deal.dealAmount }}</td>
                <th>거래일</th>
                <td>
                  {{ deal.dealYear }}-{{ deal.dealMonth }}-{{ deal.dealDay }}
                </td>
              </tr>
              <tr>
                <th>면적</th>
                <td>{{ deal.area }} ㎡</td>
                <th>층</th>
                <td>{{ deal.floor }}</td>
              </tr>
            </table>
          </div>
          <div v-show="isDealEmpty" class="text-center my-3">
            최근 6개월 간 거래 정보가 없습니다.
          </div>
        </div>
      </b-col>
      <b-col cols="6">
        <div style="font-size: 18px">
          <b>환경 분석</b>
          <span style="font-size: 13px; margin-left: 7px">
            아파트 반경 800m 내의 환경 정보입니다. 클릭하여 자세한 정보를
            확인하세요.
          </span>
          <hr />
        </div>
        <div id="env-info">
          <div class="mb-2">
            <b>학교</b>🎓<br />
            <div class="env-info-wrapper">
              <table>
                <tr
                  v-for="(item, index) in envInfos[0]"
                  :key="index"
                  @click="popupInfo(item)"
                >
                  <td>{{ item.place_name }}, {{ item.distance }}m</td>
                </tr>
              </table>
            </div>
          </div>
          <div class="mb-2">
            <b>어린이집/유치원</b>🐤<br />
            <div class="env-info-wrapper">
              <table>
                <tr
                  v-for="(item, index) in envInfos[1]"
                  :key="index"
                  @click="popupInfo(item)"
                >
                  <td>{{ item.place_name }}, {{ item.distance }}m</td>
                </tr>
              </table>
            </div>
          </div>
          <div class="mb-2">
            <b>병원</b>💊<br />
            <div class="env-info-wrapper">
              <table>
                <tr
                  v-for="(item, index) in envInfos[2]"
                  :key="index"
                  @click="popupInfo(item)"
                >
                  <td>{{ item.place_name }}, {{ item.distance }}m</td>
                </tr>
              </table>
            </div>
          </div>
          <div class="mb-2">
            <b>학원</b>📗<br />
            <div class="env-info-wrapper">
              <table>
                <tr
                  v-for="(item, index) in envInfos[3]"
                  :key="index"
                  @click="popupInfo(item)"
                >
                  <td>{{ item.place_name }}, {{ item.distance }}m</td>
                </tr>
              </table>
            </div>
          </div>
        </div>
      </b-col>
    </b-row>
  </b-modal>
</template>

<script>
import { mapState, mapMutations } from "vuex";
const houseStore = "houseStore";

export default {
  name: "DealListModal",
  data() {
    return {
      loading: true,
      isDealEmpty: null,
    };
  },
  props: {
    dealLoading: Boolean,
  },
  computed: {
    ...mapState(houseStore, ["house", "deals", "envInfos"]),
    houseName() {
      if (this.house)
        return "🏠 " + this.house.dongName + " " + this.house.aptName;
      else return null;
    },
  },
  watch: {
    dealLoading(newVal) {
      this.loading = newVal;
    },
    deals(newVal) {
      if (newVal.length == 0) this.isDealEmpty = true;
      else this.isDealEmpty = false;
    },
  },
  methods: {
    ...mapMutations(houseStore, ["CLAER_HOUSE_LIST"]),
    modalClose() {
      this.$emit("modalClose");
    },
    popupInfo(data) {
      window.open(data.place_url);
    },
  },
};
</script>

<style scoped>
::v-deep .modal {
  background: rgba(255, 255, 255, 0.1);
}
#table-wrapper {
  margin: 5px 10px 10px;
  padding-left: 10px;
  border: 2px solid grey;
  border-radius: 15px;
  min-width: 320px;
  width: 94%;
  background-color: white;
  font-size: 15px;
}
#deal-list {
  height: 450px;
}
#env-info {
  margin: 5px 10px;
  height: 450px;
  overflow: auto;
  padding-top: 10px;
  padding-left: 10px;
}
.env-info-wrapper {
  border: 2px solid grey;
  border-radius: 10px;
  width: 95%;
  height: 115px;
  overflow: auto;
}
.env-info-wrapper table {
  width: 95%;
  margin: 3px 10px;
}
.env-info-wrapper table tr {
  width: 100%;
}
.env-info-wrapper table tr:hover {
  background-color: #9ec0e8;
}
#chart-wrapper {
  width: 60%;
}
</style>
