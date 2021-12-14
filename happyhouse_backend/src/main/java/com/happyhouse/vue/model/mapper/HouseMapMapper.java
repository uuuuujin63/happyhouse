 package com.happyhouse.vue.model.mapper;

import java.util.List;
import java.util.Map;
import com.happyhouse.vue.model.CareCenterDto;
import com.happyhouse.vue.model.HouseInfoDto;
import com.happyhouse.vue.model.SidoGugunCodeDto;
import com.happyhouse.vue.model.StoreDto;

public interface HouseMapMapper {

    List<SidoGugunCodeDto> getSido() throws Exception;

    List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;

    List<HouseInfoDto> getDongInGugun(String gugun) throws Exception;

    List<HouseInfoDto> getAptInDong(Map<String, String> bounds) throws Exception;

    int addInterest(Map<String, String> map) throws Exception;

    String getRecentPrice(int aptcode) throws Exception;

    List<CareCenterDto> getCareCenter(Map<String, String> map) throws Exception;

    List<StoreDto> getStores(Map<String, Double> map) throws Exception;
    
    Map<String, String> getBaseLatLng(String dongCode) throws Exception;
    
    int countVisited(int aptCode) throws Exception;
    
    List<HouseInfoDto> getMostVisited() throws Exception;
    
}
