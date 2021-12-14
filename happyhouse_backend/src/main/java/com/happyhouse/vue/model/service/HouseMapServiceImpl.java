package com.happyhouse.vue.model.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.happyhouse.vue.model.CareCenterDto;
import com.happyhouse.vue.model.HouseDealDto;
import com.happyhouse.vue.model.HouseInfoDto;
import com.happyhouse.vue.model.SidoGugunCodeDto;
import com.happyhouse.vue.model.StoreDto;
import com.happyhouse.vue.model.mapper.HouseMapMapper;
import com.happyhouse.vue.util.AptDealAPI;

@Service
public class HouseMapServiceImpl implements HouseMapService {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<SidoGugunCodeDto> getSido() throws Exception {
        return sqlSession.getMapper(HouseMapMapper.class).getSido();
    }

    @Override
    public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
        return sqlSession.getMapper(HouseMapMapper.class).getGugunInSido(sido);
    }

    @Override
    public List<HouseInfoDto> getDongInGugun(String gugun) throws Exception {
        return sqlSession.getMapper(HouseMapMapper.class).getDongInGugun(gugun);
    }

    @Override
    public List<HouseInfoDto> getAptInDong(Map<String, String> bounds) throws Exception {
        List<HouseInfoDto> list = sqlSession.getMapper(HouseMapMapper.class).getAptInDong(bounds);
        return list;
    }

    @Override
    public List<HouseDealDto> getHouseDeal(HouseInfoDto houseInfo) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        map.put("pageNo", "1");
        map.put("numOfRows", "100");
        map.put("LAWD_CD", houseInfo.getDongCode().substring(0, 5));
        
        List<HouseDealDto> houseList = new ArrayList<HouseDealDto>();
        
        LocalDate now = LocalDate.now();
        for (int months = 1; months <= 6; months++) {
            LocalDate past = now.minusMonths(months);
            String yyyymm = (past.getYear() + "") + String.format("%02d", past.getMonthValue());
            map.put("DEAL_YMD", yyyymm);
            
            for (HouseDealDto house : AptDealAPI.getHouseList(map)) {
                if (house.getDongName().equals(houseInfo.getDongName())
                        && house.getAptName().equals(houseInfo.getAptName())) {
                    houseList.add(house);
                }
            }
        }
        
        return houseList;
    }

    @Override
    public boolean addInterest(Map<String, String> map) throws Exception {
        return sqlSession.getMapper(HouseMapMapper.class).addInterest(map) == 1;
    }

    @Override
    public List<CareCenterDto> getCareCenter(String sidoName, String gugunName) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sidoName", sidoName);
        map.put("gugunName", gugunName);
        return sqlSession.getMapper(HouseMapMapper.class).getCareCenter(map);
    }

    @Override
    public List<StoreDto> getStores(Double lat, Double lng) throws Exception {
        Map<String, Double> map = new HashMap<String, Double>();
        map.put("lat", lat);
        map.put("lng", lng);

        return sqlSession.getMapper(HouseMapMapper.class).getStores(map);
    }

    @Override
    public Map<String, String> getBaseLatLng(String dongCode) throws Exception {
        return sqlSession.getMapper(HouseMapMapper.class).getBaseLatLng(dongCode);
    }

    @Override
    public boolean countVisited(int aptCode) throws Exception {
        return sqlSession.getMapper(HouseMapMapper.class).countVisited(aptCode) == 1;
    }

    @Override
    public List<HouseInfoDto> getMostVisited() throws Exception {
        return sqlSession.getMapper(HouseMapMapper.class).getMostVisited();
    }

}
