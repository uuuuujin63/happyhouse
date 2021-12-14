<template>
  <b-container>
    <b-row class="mt-3">
      <b-col
        cols="6"
        class="my-5"
        data-aos="fade-right"
        data-aos-once="true"
        data-aos-duration="1000"
      >
        <div>
          <span class="title">매물 검색 추이</span>
          <span class="subtitle pl-2">가장 많이 조회된 매물입니다</span>
        </div>
        <hr width="100%" />
        <table id="search-list">
          <tr
            v-for="(house, index) in mostVisited"
            :key="index"
            style="height: 37px"
            @click="searchHouse(house)"
          >
            <td style="width: 25px">
              <b>{{ index + 1 }} </b>
            </td>
            <td style="width: 100px">{{ house.dongName }}</td>
            <td>{{ house.aptName }}</td>
          </tr>
        </table>
        <BarChart
          :chartData="mostVisited"
          v-on:searchHouse="searchHouse"
          class="pr-5"
        />
      </b-col>
      <b-col
        cols="6"
        class="my-5"
        data-aos="fade-left"
        data-aos-once="true"
        data-aos-duration="1000"
      >
        <div class="d-inline-block">
          <span class="title">News</span>
          <span class="subtitle pl-2">아파트 매물 관련 뉴스입니다</span>
        </div>
        <div class="d-inline-block">
          <span class="subtitle pl-2">
            <a
              href="https://search.naver.com/search.naver?where=news&amp;sm=tab_jum&amp;query=%EC%95%84%ED%8C%8C%ED%8A%B8+%EB%A7%A4%EB%AC%BC"
            >
              더보기
            </a>
          </span>
        </div>
        <hr width="100%" class="mb-0" />
        <b-table-simple
          class="mt-0"
          outlined:per-page="perPage"
          :current-page="currentPage"
        >
          <b-thead>
            <b-tr>
              <b-th width="10%">번호</b-th>
              <b-th width="45%">제목</b-th>
              <b-th width="15%">등록일자</b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <b-tr v-for="(news, index) in lists" :key="index">
              <b-td v-if="currentPage == 1">{{ newslist.length - index }}</b-td>
              <b-td v-else>{{
                newslist.length - perPage * (currentPage - 1) - index
              }}</b-td>
              <b-td
                ><a :href="news.originallink" v-html="news.title">{{
                  news.title
                }}</a></b-td
              >
              <b-td>{{ changeRegtime(news.pubDate) }}</b-td>
            </b-tr>
          </b-tbody>
        </b-table-simple>

        <b-pagination
          class="mx-auto"
          v-model="currentPage"
          :total-rows="rows"
          :per-page="perPage"
          first-text="First"
          prev-text="Prev"
          next-text="Next"
          last-text="Last"
          align="center"
        ></b-pagination>
      </b-col>
    </b-row>
    <div style="height: 50px"></div>
  </b-container>
</template>

<script>
import { mapGetters } from "vuex";
import BarChart from "@/components/Main/Charts/BarChart";
import http from "@/util/http-common.js";
import moment from "moment";

const houseStore = "houseStore";

export default {
  name: "Chart",
  components: {
    BarChart,
  },
  data() {
    return {
      newslist: [],
      searchKey: "",
      perPage: 10,
      currentPage: 1,
    };
  },
  created() {
    this.$store.dispatch("houseStore/getMostVisited");

    http.get("/news").then((response) => {
      console.log(response.data.items);
      this.newslist = response.data.items;
    });
  },
  computed: {
    mostVisited() {
      return this.$store.state.houseStore.mostVisited;
    },

    rows() {
      return this.newslist.length;
    },
    lists() {
      const items = this.newslist;
      return items.slice(
        (this.currentPage - 1) * this.perPage,
        this.currentPage * this.perPage
      );
    },
  },
  methods: {
    searchHouse(house) {
      this.$emit("scrollToMap");
      this.$store.commit("houseStore/SET_LOCATION", house.dongCode);
    },

    changeRegtime(regitime) {
      return moment(new Date(regitime)).format("YYYY-MM-DD");
    },
  },
};
</script>

<style scoped>
.title {
  font-size: 30px;
  font-weight: 500;
}
.subtitle {
  font-size: 16px;
  font-weight: 400;
}
table {
  width: 100%;
}
#search-list tr:hover {
  background-color: #a8c9f0;
}
</style>
