<template>
  <div class="h-100 w-100">
    <div id="map" class="border"></div>
    <map-options v-on:selectCategory="selectCategory"></map-options>
    <house-list
      :houstListOn="houseListOn"
      v-on:getHouseDetails="getHouseDetails"
      v-show="houseListOn"
    ></house-list>
    <detail-modal
      :dealLoading="dealLoading"
      v-on:modalClose="modalClose"
    ></detail-modal>
  </div>
</template>

<script>
import MapOptions from "@/components/Main/MapComponents/MapOptions";
import HouseList from "@/components/Main/MapComponents/HouseList";
import DetailModal from "@/components/Main/MapComponents/DetailModal";

import markerSet from "@/assets/markers/marker_set.png";

import http from "@/util/http-common.js";
import { mapState, mapMutations, mapActions } from "vuex";
const houseStore = "houseStore";

export default {
  name: "KakaoMap",
  data() {
    return {
      // 지도
      map: null,
      // 선택된 카테고리를 담을 변수입니다
      category: "APT",
      houseListOn: true,
      // 마커를 담을 배열입니다
      aptMarkers: [],
      envMarkers: [],
      // 마커 클러스터러 객체입니다
      clusterer: null,
      // 아파트 인포 윈도우
      infoWindow: null,
      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      placeOverlay: null,
      contentNode: null,
      // 거래 정보를 가져올 때 로딩 화면을 띄우기 위한 변수입니다
      dealLoading: true,
      // 주소-좌표 변환 객체입니다
      geocoder: null,
      // 장소 검색 객체입니다
      ps: null,
    };
  },
  components: {
    MapOptions,
    HouseList,
    DetailModal,
  },
  created() {
    this.CLEAR_HOUSE_LIST();
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d58749d6012310787af79b0508d3dd3d&libraries=services,clusterer,drawing";
      document.head.appendChild(script);
    }
  },
  computed: {
    ...mapState(houseStore, ["houses", "deals", "dongcode"]),
  },
  watch: {
    dongcode(newVal) {
      this.setCenterToDong(newVal);
    },
  },
  methods: {
    ...mapMutations(houseStore, [
      "CLEAR_HOUSE_LIST",
      "SET_HOUSE",
      "PUSH_ENV_INFOS",
      "CLEAR_ENV_INFOS",
    ]),
    ...mapActions(houseStore, ["getHouse", "getDeal", "countVisited"]),
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
        maxLevel: 6,
      };
      this.map = new kakao.maps.Map(container, options);
      this.geocoder = new kakao.maps.services.Geocoder();
      this.ps = new kakao.maps.services.Places(this.map);
      this.infoWindow = new kakao.maps.InfoWindow();
      this.placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });

      // 커스텀 오버레이 설정
      this.contentNode = document.createElement("div");
      this.contentNode.className = "placeinfo_wrap";
      this.addEventHandle(
        this.contentNode,
        "mousedown",
        kakao.maps.event.preventMap
      );
      this.addEventHandle(
        this.contentNode,
        "touchstart",
        kakao.maps.event.preventMap
      );

      // 지도 범위가 변경될 때 마다 아파트를 불러오도록 eventListener 설정
      kakao.maps.event.addListener(this.map, "zoom_changed", this.searchPlaces);
      kakao.maps.event.addListener(this.map, "dragend", this.searchPlaces);
      // 마커 클러스터러를 사용하기 위한 설정
      this.clusterer = new kakao.maps.MarkerClusterer({
        map: this.map,
        averageCenter: true,
        minLevel: 6,
      });
    },
    selectCategory(category) {
      if (category === "APT") {
        this.houseListOn = true;
      } else {
        this.houseListOn = false;
      }
      this.category = category;
      this.searchPlaces();
    },
    // 카테고리에 따라 장소를 검색하는 함수입니다
    searchPlaces() {
      // 커스텀 오버레이를 숨깁니다
      // placeOverlay.setMap(null);
      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarkers();

      if (this.category === "APT") {
        this.getAptList();
      } else {
        this.ps.categorySearch(this.category, this.placesSearchCB, {
          useMapBounds: true,
        });
      }
    },
    // DB에서 현재 지도 영역 안의 아파트를 불러옵니다
    getAptList() {
      let mapBounds = this.map.getBounds();
      let dLat =
        mapBounds.getNorthEast().getLat() - mapBounds.getSouthWest().getLat();
      let dLng =
        mapBounds.getNorthEast().getLng() - mapBounds.getSouthWest().getLng();
      let bounds = {
        neLat: mapBounds.getSouthWest().getLat() + 0.8 * dLat,
        neLng: mapBounds.getSouthWest().getLng() + 0.7 * dLng,
        swLat: mapBounds.getSouthWest().getLat() + 0.2 * dLat,
        swLng: mapBounds.getSouthWest().getLng() + 0.15 * dLng,
      };
      // store에 저장된 아파트 목록을 지우고 새로 받아옵니다
      // 불러오는 작업이 끝나면 마커와 인포윈도우를 설정합니다
      this.CLEAR_HOUSE_LIST();
      this.getHouse(bounds).then(() => {
        this.addHouseMarkers();
      });
    },
    // 지도 확대 레벨에 따라 클러스터러 혹은 마커를 표시합니다
    addHouseMarkers() {
      let level = this.map.getLevel();

      for (let idx = 0; idx < this.houses.length; idx++) {
        let position = new kakao.maps.LatLng(
            this.houses[idx].lat,
            this.houses[idx].lng
          ),
          imageSrc = markerSet, // 마커 이미지
          imageSize = new kakao.maps.Size(25, 35), // 마커 이미지의 크기
          imageOptions = {
            spriteOrigin: new kakao.maps.Point(82, 5),
            spriteSize: new kakao.maps.Size(190, 184),
          },
          markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imageOptions
          ),
          marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage,
            clickable: true,
          });

        kakao.maps.event.addListener(
          marker,
          "click",
          this.markerClickListener(
            this.map,
            this.houses[idx],
            marker,
            this.getHouseDetails
          )
        );

        if (level < 6) marker.setMap(this.map); // 지도 레벨이 10 미만인 경우 지도에 마커를 표시합니다
        this.aptMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

        // ----------------- Geocoder -----------------
        let addr = this.houses[idx].dongName + " " + this.houses[idx].jibun;
        this.addressSearch(addr).then((result) => {
          // -------------- 인포 윈도우 생성 --------------
          let iwContent = "";
          if (!result[0].road_address) {
            iwContent = `
              <div class="info-window" style="padding: 5px; padding-left: 20px; border: 1px solid black; border-radius: 4px; color: white; background-color: darkgreen; min-width: 190px;">
                ${this.houses[idx].aptName} <br />
                ${addr}
              </div>`;
          } else {
            iwContent = `
              <div class="info-window" style="padding: 5px; border: 1px solid black; border-radius: 4px; color: white; background-color: darkgreen; min-width: 190px;">
                ${this.houses[idx].aptName} <br />
                ${result[0].road_address.address_name}
              </div>`;
          }

          this.infoWindow = new kakao.maps.InfoWindow({
            content: iwContent,
          });

          kakao.maps.event.addListener(
            marker,
            "mouseover",
            this.markerMouseOverListener(this.map, marker, this.infoWindow)
          );
          kakao.maps.event.addListener(
            marker,
            "mouseout",
            this.markerMouseOutListener(this.infoWindow)
          );
        });
      }

      if (level >= 6) this.clusterer.addMarkers(this.aptMarkers); // 지도 레벨이 10 이상인 경우 지도에 클러스터러를 표시합니다
    },
    // 마커를 클릭했을 때 이벤트
    markerClickListener(map, house, marker, getHouseDetails) {
      return function () {
        // 지도의 중심 좌표를 이동합니다
        map.setLevel(3);
        map.panTo(marker.getPosition());
        // 해당 아파트의 세부 정보를 불러옵니다
        getHouseDetails(house);
      };
    },
    // 아파트에 대한 세부 정보(실거래가, 환경정보)를 불러옵니다
    getHouseDetails(house) {
      this.countVisited(house);
      this.getDealList(house);
      this.getEnvInfos(house);
    },
    // 마커에 마우스가 올라갔을 때 이벤트
    markerMouseOverListener(map, marker, infoWindow) {
      return function () {
        infoWindow.open(map, marker);
      };
    },
    // 마커에서 마우스가 벗어났을 때 이벤트
    markerMouseOutListener(infoWindow) {
      return function () {
        infoWindow.close();
      };
    },
    // 지도 상의 모든 마커를 제거합니다
    removeMarkers() {
      for (let aptMarker of this.aptMarkers) {
        aptMarker.setMap(null);
      }
      for (let envMarker of this.envMarkers) {
        envMarker.setMap(null);
      }
      this.clusterer.removeMarkers(this.aptMarkers);
      this.aptMarkers = [];
      this.envMarkers = [];
      this.placeOverlay.setMap(null);
      this.infoWindow.close();
    },
    // 공공 데이터에서 해당 아파트의 거래 정보를 불러옵니다
    getDealList(house) {
      this.SET_HOUSE(house);
      this.$bvModal.show("detail-modal");
      this.getDeal(house).then(() => {
        this.dealLoading = false;
      });
    },
    // modal 창이 닫힐 때
    modalClose() {
      this.dealLoading = true;
    },
    // 카카오 서버로부터 모든 카테고리에 대한 정보를 얻어옵니다
    async getEnvInfos(house) {
      this.CLEAR_ENV_INFOS();

      let categories = ["SC4", "PS3", "HP8", "AC5"];
      for (let category of categories) {
        await this.categorySearch(category, 800, house).then((data) => {
          this.PUSH_ENV_INFOS(data);
        });
      }
    },
    categorySearch(category, radius, house) {
      return new Promise((resolve, reject) => {
        this.ps.categorySearch(
          category,
          (data, status) => {
            if (status === kakao.maps.services.Status.OK) {
              resolve(data);
            }
          },
          {
            location: new kakao.maps.LatLng(house.lat, house.lng),
            radius: radius, // 반경 800m 이내
            sort: kakao.maps.services.SortBy.DISTANCE,
          }
        );
      });
    },
    setCenterToDong(dongcode) {
      http.get(`/map/latlng/${dongcode}`).then(({ data }) => {
        let position = new kakao.maps.LatLng(data.lat, data.lng);
        this.map.setLevel(4);
        this.map.panTo(position);
        this.selectCategory("APT");
      });
    },
    // 좌표로 법정동 상세 주소 정보를 요청합니다
    searchDetailAddrFromCoords(coords) {
      return new Promise((resolve) => {
        this.geocoder.coord2Address(
          coords.getLng(),
          coords.getLat(),
          function (result, status) {
            if (status === kakao.maps.services.Status.OK) {
              resolve(result);
            }
          }
        );
      });
    },
    // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
    addEventHandle(target, type, callback) {
      if (target.addEventListener) {
        target.addEventListener(type, callback);
      } else {
        target.attachEvent("on" + type, callback);
      }
    },
    // 주소로 장소를 검색합니다
    addressSearch(address) {
      return new Promise((resolve) => {
        this.geocoder.addressSearch(address, function (result, status) {
          if (status === kakao.maps.services.Status.OK) {
            resolve(result);
          }
        });
      });
    },
    placesSearchCB(data, status) {
      if (status === kakao.maps.services.Status.OK) {
        console.log(data);
        this.displayEnvs(data);
      } else if (status == kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 없습니다");
      }
    },
    displayEnvs(places) {
      for (var i = 0; i < places.length; i++) {
        // 마커를 생성하고 지도에 표시합니다
        var marker = this.addEnvMarker(
          new kakao.maps.LatLng(places[i].y, places[i].x)
        );

        // 마커와 검색결과 항목을 클릭 했을 때
        // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
        (function (
          marker,
          place,
          displayPlaceInfo,
          contentNode,
          placeOverlay,
          map
        ) {
          kakao.maps.event.addListener(
            marker,
            "click",
            displayPlaceInfo(place, contentNode, placeOverlay, map)
          );
        })(
          marker,
          places[i],
          this.displayPlaceInfo,
          this.contentNode,
          this.placeOverlay,
          this.map
        );
      }
    },
    addEnvMarker(position) {
      let imageSrc = markerSet,
        imageSize = new kakao.maps.Size(25, 35), // 마커 이미지의 크기
        imageOptions = {
          spriteSize: new kakao.maps.Size(190, 184),
          spriteOrigin: null,
        };

      switch (this.category) {
        case "SC4":
          imageOptions.spriteOrigin = new kakao.maps.Point(160.3, 97);
          break;
        case "AC5":
          imageOptions.spriteOrigin = new kakao.maps.Point(43, 97);
          break;
        case "PS3":
          imageOptions.spriteOrigin = new kakao.maps.Point(4.5, 97);
          break;
        case "HP8":
          imageOptions.spriteOrigin = new kakao.maps.Point(82.5, 141.5);
          break;
      }

      let markerImage = new kakao.maps.MarkerImage(
        imageSrc,
        imageSize,
        imageOptions
      );

      let marker = new kakao.maps.Marker({
        position: position, // 마커의 위치
        image: markerImage,
      });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.envMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
    displayPlaceInfo(place, contentNode, placeOverlay, map) {
      return function () {
        var content = `<div class="placeinfo">
          <a class="title" href="${place.place_url}" target="_blank" title="${place.place_name}">${place.place_name}</a>`;

        if (place.road_address_name) {
          content += `  <span title="${place.road_address_name}">${place.road_address_name}</span>
                        <span class="jibun" title="${place.address_name}">(지번 : ${place.address_name})</span>`;
        } else {
          content += `  <span title="${place.address_name}">${place.address_name}</span>`;
        }

        content += `  <span class="tel">${place.phone}</span>
                  </div>
                  <div class="after"></div>`;

        contentNode.innerHTML = content;
        console.log(contentNode);

        placeOverlay.setContent(contentNode);
        placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
        placeOverlay.setMap(map);
      };
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 100%;
  position: relative;
  overflow: hidden;
}
.overlaybox {
  position: relative;
  width: 360px;
  height: 350px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/box_movie.png")
    no-repeat;
  padding: 15px 10px;
}
.overlaybox div,
ul {
  overflow: hidden;
  margin: 0;
  padding: 0;
}
.overlaybox li {
  list-style: none;
}
.overlaybox .boxtitle {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png")
    no-repeat right 120px center;
  margin-bottom: 8px;
}
.overlaybox .first {
  position: relative;
  width: 247px;
  height: 136px;
  margin-bottom: 8px;
}
.first .text {
  color: #fff;
  font-weight: bold;
}
.first .triangle {
  position: absolute;
  width: 48px;
  height: 48px;
  top: 0;
  left: 0;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/triangle.png")
    no-repeat;
  padding: 6px;
  font-size: 18px;
}
.first .movietitle {
  position: absolute;
  width: 100%;
  bottom: 0;
  background: rgba(0, 0, 0, 0.4);
  padding: 7px 15px;
  font-size: 14px;
}
.overlaybox ul {
  width: 247px;
}
.overlaybox li {
  position: relative;
  margin-bottom: 2px;
  background: #2b2d36;
  padding: 5px 10px;
  color: #aaabaf;
  line-height: 1;
}
.overlaybox li span {
  display: inline-block;
}
.overlaybox li .number {
  font-size: 16px;
  font-weight: bold;
}
.overlaybox li .title {
  font-size: 13px;
}
.overlaybox li .last {
  font-size: 12px;
  margin-right: 15px;
}
.overlaybox ul .arrow {
  position: absolute;
  margin-top: 8px;
  right: 25px;
  width: 5px;
  height: 3px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/updown.png")
    no-repeat;
}
.overlaybox li .up {
  background-position: 0 -40px;
}
.overlaybox li .down {
  background-position: 0 -60px;
}
.overlaybox li .count {
  position: absolute;
  margin-top: 5px;
  right: 15px;
  font-size: 10px;
}
.overlaybox li:hover {
  color: #fff;
  background: #d24545;
}
.overlaybox li:hover .up {
  background-position: 0 0px;
}
.overlaybox li:hover .down {
  background-position: 0 -20px;
}
.map_category_wrap,
.map_category_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_category_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png")
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
::v-deep .placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
::v-deep .placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
::v-deep .placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
::v-deep .placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
::v-deep .placeinfo a,
::v-deep .placeinfo a:hover,
::v-deep .placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
::v-deep .placeinfo a,
::v-deep .placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
::v-deep .placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
::v-deep .placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
::v-deep .placeinfo .tel {
  color: #0f7833;
}
::v-deep .placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
.showinterestBtn {
  margin: 20px 20px 20px 20px;
}
</style>
