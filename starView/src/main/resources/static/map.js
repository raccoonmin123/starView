// stargazing_map.js 파일 내용 시작
// 1. 별 관측 명소들의 좌표와 정보 정의
var starGazingSpots = [
    {
        name: "안반데기마을",
        lat: 37.5670,
        lng: 128.8770,
        description: "강원도 강릉의 고원 마을. 탁 트인 밤하늘과 운해가 유명합니다."
    },
    {
        name: "제천 덕주산성",
        lat: 36.8778,
        lng: 128.0934,
        description: "충북 제천의 산성. 높은 지대에서 별을 감상하기 좋습니다."
    },
    {
        name: "연천 당포성",
        lat: 38.0125,
        lng: 127.0264,
        description: "경기도 연천의 고성터. 주변 빛공해가 적어 별 보기 좋습니다."
    },
    {
        name: "영월 별마로천문대",
        lat: 37.1472,
        lng: 128.4687,
        description: "강원도 영월의 유명 천문대. 전문 장비로 관측 가능하며, 교육 프로그램도 있습니다."
    },
    {
        name: "화천 조경철천문대",
        lat: 38.2575,
        lng: 127.5028,
        description: "강원도 화천의 천문대. 광덕산 정상 부근에 위치해 별이 매우 잘 보입니다."
    },
    {
        name: "태안 운여해변",
        lat: 36.6575,
        lng: 126.3142,
        description: "충남 태안의 해변. 물때가 맞으면 반영 사진으로 유명하며, 서해의 별을 볼 수 있습니다."
    },
    {
        name: "평창 육백마지기",
        lat: 37.3871,
        lng: 128.5366,
        description: "강원도 평창의 드넓은 고원 지대. 야생화와 함께 별을 감상할 수 있습니다."
    },
    {
        name: "부여 가림성 (성흥산성)",
        lat: 36.2162,
        lng: 126.8530,
        description: "충남 부여의 백제 유적지. 고즈넉한 분위기에서 별을 볼 수 있습니다."
        }
    ];

// 2. 지도 초기화: 모든 마커를 포함할 수 있는 초기 중심과 줌 레벨 설정
var mapOptions = {
    center: new naver.maps.LatLng(36.5, 127.8), // 대한민국 대략적 중심
    zoom: 7 // 대한민국 전체가 어느 정도 보이도록 설정
};

var map = new naver.maps.Map('map', mapOptions);

// 모든 마커를 포함할 LatLngBounds 객체 생성
var bounds = new naver.maps.LatLngBounds();

// 3. 각 장소에 마커와 정보창 추가
starGazingSpots.forEach(function(spot) {
    var position = new naver.maps.LatLng(spot.lat, spot.lng);

    // 마커 생성
    var marker = new naver.maps.Marker({
        position: position,
        map: map,
        title: spot.name // 마우스 오버 시 이름 표시
    });

    // 정보창 생성
    var infowindow = new naver.maps.InfoWindow({
        content: '<div class="info-window-content">' +
                 '<b>' + spot.name + '</b>' +
                 '<span>' + spot.description + '</span>' +
                 '</div>'
    });

    // 마커 클릭 시 정보창 열기/닫기 이벤트 리스너 추가
    naver.maps.Event.addListener(marker, "click", function(e) {
        if (infowindow.getMap()) {
            infowindow.close();
        } else {
            infowindow.open(map, marker);
        }
    });

    // bounds에 현재 마커의 위치를 포함시키기
    bounds.extend(position);
});

// 4. 모든 마커가 보이도록 지도 뷰포트 조정
map.fitBounds(bounds);
// stargazing_map.js 파일 내용 끝