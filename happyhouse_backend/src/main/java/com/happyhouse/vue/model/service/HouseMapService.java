package com.happyhouse.vue.model.service;

import java.util.List;
import java.util.Map;
import com.happyhouse.vue.model.CareCenterDto;
import com.happyhouse.vue.model.HouseDealDto;
import com.happyhouse.vue.model.HouseInfoDto;
import com.happyhouse.vue.model.SidoGugunCodeDto;
import com.happyhouse.vue.model.StoreDto;

public interface HouseMapService {

    List<SidoGugunCodeDto> getSido() throws Exception;

    List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;

    List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;

    List<HouseInfoDto> getAptInDong(Map<String, String> bounds) throws Exception;

    List<HouseDealDto> getHouseDeal(HouseInfoDto houseInfo) throws Exception;

    boolean addInterest(Map<String, String> map) throws Exception;

    List<CareCenterDto> getCareCenter(String sidoName, String gugunName) throws Exception;

    List<StoreDto> getStores(Double lat, Double lng) throws Exception;

    Map<String, String> getBaseLatLng(String dongCode) throws Exception;
    
    boolean countVisited(int aptCode) throws Exception;
    
    List<HouseInfoDto> getMostVisited() throws Exception;
    
}
