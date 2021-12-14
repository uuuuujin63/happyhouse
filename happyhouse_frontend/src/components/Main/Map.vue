<template>
  <div class="py-5 p-st bg-light">
    <b-container fluid>
      <div
        class="text-center"
        data-aos="fade"
        data-aos-once="true"
        data-aos-duration="1000"
      >
        <span class="title text-center">아파트 정보 조회</span>
      </div>
      <hr width="100%" />
      <!-- 카카오 맵 -->
      <b-row style="height: 80vh" align-h="center">
        <b-col class="map_wrap kakao-map" cols="11">
          <kakao-map
            data-aos="fade-up"
            data-aos-once="true"
            data-aos-duration="1000"
          ></kakao-map>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import KakaoMap from "@/components/Main/MapComponents/KakaoMap.vue";

export default {
  name: "Map",
  components: {
    KakaoMap,
  },
};
</script>

<style scoped>
.title {
  font-size: 30px;
  font-weight: 500;
}
.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  max-width: 75vw;
  max-height: 40vw;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap a,
.map_wrap a:hover,
.map_wrap a:active {
  color: #000;
  text-decoration: none;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 100%;
}
</style>
