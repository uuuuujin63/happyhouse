import http from "@/util/http-common.js";

export default {
  data() {
    return {
      // 지도
      map: null,
      // 장소 검색 객체
      ps: null,
      // 인포 윈도우
      infowindow: null,
      // 주소 좌표 변환 객체
      geocoder: null,
      // 마커를 담을 배열입니다
      aptMarkers: [],
      storeMarkers: [],
      centerMarkers: [],
      // 마커를 표시할 위치와 내용을 가지고 있는 객체 배열입니다
      aptPositions: [],
      storePositions: [],
      centerPositions: [],
      // 오버레이
      aptOverlays: [],
      storeOverlays: [],
      centerOverlays: [],
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "";
      // 개인 서비스키 넣기
      document.head.appendChild(script);
    }
  },
  methods: {
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(container, options);
      this.ps = new kakao.maps.services.Places();
      this.infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
      this.geocoder = new kakao.maps.services.Geocoder();
    },
    // 검색 결과 목록과 마커를 생성하는 함수입니다
    displayPlaces(places) {
      var listEl = document.getElementById("placesList"),
        menuEl = document.getElementById("menu_wrap"),
        fragment = document.createDocumentFragment(),
        bounds = new kakao.maps.LatLngBounds();

      //검색 결과 목록에 추가된 항목들을 제거합니다
      this.removeAllChildNods(listEl);
      // 지도에 표시되고 있는 마커를 제거합니다
      this.removeMarker();
      // 오버레이를 담은 배열을 비웁니다
      this.aptOverlays = [];

      for (var i = 0; i < places.length; i++) {
        var position = new kakao.maps.LatLng(places[i].lat, places[i].lng),
          marker = this.addMarker(position, i),
          itemEl = this.getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해 LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(position);

        // 마커 위에 커스텀 오버레이를 만들어 표시합니다.
        var content = this.makeInfoWindow(marker, places[i]);
        var overlay = new kakao.maps.CustomOverlay({
          content: content,
          map: this.map,
          position: position,
          xAnchor: 0.32,
          yAnchor: 1,
        });
        overlay.setMap(null);
        this.aptOverlays.push(overlay);

        // 마커와 검색결과 항목에 클릭했을때 인포 윈도우를 지도 상에 표시합니다
        // 인포 윈도우는 닫을 수 있습니다
        (function (map, marker, overlay, place) {
          kakao.maps.event.addListener(marker, "click", function () {
            http.post("/map/deal", place).then((dealList) => {
              // 기존 매물 내역 삭제
              // $('#info-append').empty();
              // $('#listInfo').empty();
              // $('#listInfo').append(place.aptName);
              // for (let deal of dealList) {
              //   $("#info-append").append(`
              // 		<div class="info-box overflow-auto border border bg-dark p-2 mt-4">
              // 			<h5 class="text-white font-weight-light mt-1 mb-2 text-center border-bottom p-1">매 물</h5>
              // 			<div class="info-element mt-2 text-center border border-white badge-light">
              // 				매매가   | ${deal.dealAmount} 만원
              // 			</div>
              // 			<div class="info-element mt-2 text-center border border-white badge-light">
              // 				전용면적 | ${deal.area} m^2
              // 			</div>
              // 			<div class="info-element mt-2 text-center border border-white badge-light">
              // 				층수     | ${deal.floor} 층
              // 			</div>
              // 		</div>`);
              // }
            });
          });

          // 마커를 클릭할 시 인포 윈도우를 띄우고, 맵의 중심을 이동시킵니다
          kakao.maps.event.addListener(marker, "click", function () {
            this.closeOverlays();
            overlay.setMap(map);
            map.panTo(new kakao.maps.LatLng(place.lat, place.lng));
          });
          // 지도를 클릭할 시 인포 윈도우가 사라집니다
          kakao.maps.event.addListener(map, "click", function (mouseEvent) {
            overlay.setMap(null);
          });

          itemEl.onclick = function () {
            this.closeOverlays();
            overlay.setMap(map);
            map.panTo(new kakao.maps.LatLng(place.lat, place.lng));
            http.post("/map/deal", place).then((dealList) => {
              // $("#info-append").empty();
              // $("#listInfo").empty();
              // $("#listInfo").append(place.aptName);
              // for (let deal of dealList) {
              //   $("#info-append").append(`
              // 		<div class="info-box overflow-auto border border bg-dark p-2 mt-4">
              // 			<h5 class="text-white font-weight-light mt-1 mb-2 text-center border-bottom p-1">매 물</h5>
              // 			<div class="info-element mt-2 text-center border border-white badge-light">
              // 				매매가   | ${deal.dealAmount} 만원
              // 			</div>
              // 			<div class="info-element mt-2 text-center border border-white badge-light">
              // 				전용면적 | ${deal.area} m^2
              // 			</div>
              // 			<div class="info-element mt-2 text-center border border-white badge-light">
              // 				층수     | ${deal.floor} 층
              // 			</div>
              // 		</div>`);
              // }
            });
          };
          itemEl.onmouseover = function () {
            overlay.setMap(map);
          };
          itemEl.onmouseout = function () {
            overlay.setMap(null);
          };
        })(this.map, marker, overlay, places[i]);

        fragment.appendChild(itemEl);
      }
      // 검색결과 항목들을 검색결과 목록 Elemnet에 추가합니다
      listEl.appendChild(fragment);
      menuEl.scrollTop = 0;

      // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
      this.map.setBounds(bounds);
    },
    closeOverlays() {
      for (let aptOverlay of this.aptOverlays.length) {
        aptOverlay.setMap(null);
      }
      for (let storeOverlay of this.storeOverlays.length) {
        storeOverlay.setMap(null);
      }
      for (let centerOverlay of this.centerOverlays.length) {
        centerOverlay.setMap(null);
      }
    },
    // 주변 상권 마커를 생성하고 표시합니다
    displayStores(places) {
      // 지도에 표시되고 있는 상권 마커를 제거합니다
      this.removeStoreMarker();
      // 오버레이를 담은 배열을 비웁니다
      this.storeOverlays = [];

      for (var i = 0; i < places.length; i++) {
        console.log(places[i]);
        var position = new kakao.maps.LatLng(places[i].lat, places[i].lng),
          marker = this.addStoreMarker(position, i);

        // 마커 위에 인포 윈도우를 만들어 표시합니다.
        var content =
          '<div style="padding:5px;">' +
          places[i].storeName +
          "<br>" +
          places[i].addr +
          "</div>";
        var overlay = new kakao.maps.InfoWindow({
          content: content,
          map: this.map,
          position: position,
        });
        overlay.setMap(null);
        this.storeOverlays.push(overlay);

        (function (map, marker, overlay, place) {
          // 마커를 클릭할 시 인포 윈도우를 띄우고, 맵의 중심을 이동시킵니다
          kakao.maps.event.addListener(marker, "click", function () {
            this.closeOverlays();
            overlay.setMap(map);
            map.panTo(new kakao.maps.LatLng(place.lat, place.lng));
          });
          // 지도를 클릭할 시 인포 윈도우가 사라집니다
          kakao.maps.event.addListener(map, "click", function (mouseEvent) {
            overlay.setMap(null);
          });
        })(this.map, marker, overlay, places[i]);
      }
    },
    // 선별 진료소 오버레이
    displayCenters(places) {
      // 오버레이를 담은 배열을 비웁니다
      this.centerOverlays = [];

      for (var i = 0; i < places.length; i++) {
        this.geocoder.addressSearch(places[i].addr, function (result, status) {
          if (status === kakao.maps.services.Status.OK) {
            var position = new kakao.maps.LatLng(result[0].y, result[0].x);
            var marker = this.addCenterMarker(position);

            console.log(result);
            // 마커 위에 인포 윈도우를 만들어 표시합니다.
            var content =
              '<div style="padding:5px;">' +
              result[0].road_address.building_name +
              "<br>" +
              result[0].road_address.address_name +
              "</div>";
            var overlay = new kakao.maps.InfoWindow({
              content: content,
              map: this.map,
              position: position,
            });
            overlay.setMap(null);
            this.centerOverlays.push(overlay);

            (function (map, marker, overlay, position) {
              // 마커를 클릭할 시 인포 윈도우를 띄우고, 맵의 중심을 이동시킵니다
              kakao.maps.event.addListener(marker, "click", function () {
                this.closeOverlays();
                overlay.setMap(map);
                map.panTo(position);
              });
              // 지도를 클릭할 시 인포 윈도우가 사라집니다
              kakao.maps.event.addListener(map, "click", function (mouseEvent) {
                overlay.setMap(null);
              });
            })(this.map, marker, overlay, position);
          }
        });
      }
    },
    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    addMarker(position, idx, title) {
      var imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map); // 지도 위에 마커를 표출합니다
      this.aptMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    // 상권 마커를 생성하고 리스트에 마커를 추가하는 함수입니다
    addStoreMarker(position, title) {
      var imageSrc = "/assets/marker/storemarker.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map);
      this.storeMarkers.push(marker); // 배열에 생성된 마커를 추가합니다

      return marker;
    },
    // 선별 진료소 마커를 생성하고 리스트에 마커를 추가하는 함수입니다
    addCenterMarker(position, title) {
      var imageSrc = "/assets/marker/carecentermarker.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(this.map);
      this.centerMarkers.push(marker); // 배열에 생성된 마커를 추가합니다
      marker.setVisible(false);

      return marker;
    },
    //검색결과 항목을 Element로 반환하는 함수입니다
    getListItem(index, place) {
      var el = document.createElement("li"),
        itemStr =
          '<span class="markerbg marker_' +
          (index + 1) +
          '"></span>' +
          '<div class="info">' +
          "    <h5>" +
          place.aptName +
          "</h5> <button>관심등록</button>" +
          '<button type="button" id="envInfoBtn" value="' +
          place.lat +
          "," +
          place.lng +
          "," +
          place.sidoName +
          "," +
          place.gugunName +
          '">상권정보</button>' +
          "    <span>" +
          place.sidoName +
          " " +
          place.gugunName +
          " " +
          place.dongName +
          " " +
          place.jibun +
          "</span>" +
          "</div>";
      el.innerHTML = itemStr;
      el.className = "item";

      return el;
    },
    //인포윈도우에 장소명을 표시합니다
    makeInfoWindow(marker, place) {
      var content =
        '<div class="overlaybox">' +
        '    <div class="boxtitle">' +
        place.aptName +
        "</div>" +
        '    <div class="first">' +
        '        <img src="assets/img/apt.png" style="width:247px; height:136px;" alt="">' +
        "    </div>" +
        "    <ul>" +
        '        <li class="up">' +
        '            <span class="title">건축년도</span>' +
        '            <span class="count">' +
        place.buildYear +
        " 년</span>" +
        "        </li>" +
        "        <li>" +
        '            <span class="title">주소</span>' +
        '            <span class="count">' +
        place.sidoName +
        " " +
        place.gugunName +
        " " +
        place.dongName +
        " " +
        place.jibun +
        "</span>" +
        "        </li>" +
        "        <li>" +
        '            <span class="title">최신거래금액</span>' +
        '            <span class="count">' +
        place.recentprice +
        " 만원</span>" +
        "        </li>" +
        "        <li>" +
        '            <span class="last" id="recenthistor" data-toggle="modal" data-target="#myModal">아파트정보 update</span>' +
        "        </li>" +
        "    </ul>" +
        "</div>";

      return content;
    },
    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    removeMarker() {
      for (let aptMarker of this.aptMarkers) {
        aptMarker.setMap(null);
      }
      for (let storeMarker of this.storeMarkers) {
        storeMarker.setMap(null);
      }
      for (let centerMarker of this.centerMarkers) {
        centerMarker.setMap(null);
      }
      this.aptMarkers = [];
      this.storeMarkers = [];
      this.centerMarkers = [];
    },
    // 상권 마커를 모두 제거합니다
    removeStoreMarker() {
      for (let storeMarker of this.storeMarkers) {
        storeMarker.setMap(null);
      }
      this.storeMarkers = [];
    },
    // 상권 마커를 숨깁니다
    hideStoreMarker() {
      for (let storeMarker of this.storeMarkers) {
        storeMarker.setVisible(false);
      }
    },
    // 상권 마커를 보입니다
    showStoreMarker() {
      console.log(this.storeMarkers);
      for (let storeMarker of this.storeMarkers) {
        storeMarker.setVisible(true);
      }
    },
    // 선별 진료소 마커를 숨깁니다
    hideCenterMarker() {
      for (let centerMarker of this.centerMarkers) {
        centerMarker.setVisible(false);
      }
    },
    // 선별 진료소 마커를 보입니다
    showCenterMarker() {
      for (let centerMarker of this.centerMarkers) {
        centerMarker.setVisible(true);
      }
      this.map.setLevel(7);
    },
    // 검색결과 목록의 자식 Element를 제거하는 함수입니다
    removeAllChildNods(el) {
      while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
      }
    },
  },
};
