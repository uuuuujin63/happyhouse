package com.happyhouse.vue.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.happyhouse.vue.model.HouseDealDto;

public class AptDealAPI {

    private static String getTagValue(String tag, Element eElement) {
        if (eElement.getElementsByTagName(tag).item(0) == null)
            return "";
        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);
        if (nValue == null)
            return "";
        return nValue.getNodeValue().trim();
    }

    private static boolean aptCheck(List<HouseDealDto> houseList, String apt) {
        for (int i = 0; i < houseList.size(); i++) {
            if (apt.trim().equals(houseList.get(i).getAptName().trim()))
                return false;
        }
        return true;
    }

    public static List<HouseDealDto> getHouseList(Map<String, String> map) {
        try {
            String url =
                    "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptTradeDev";
            String serviceKey =
                   ""; // 개인서비스키 넣기
            String pageNo = map.get("pageNo");
            String numOfRows = map.get("numOfRows");
            String LAWD_CD = map.get("LAWD_CD");
            String DEAL_YMD = map.get("DEAL_YMD");

            Document documentInfo = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(url + "?serviceKey=" + serviceKey + "&pageNo=" + pageNo + "&numOfRows=" + numOfRows
                            + "&LAWD_CD=" + LAWD_CD + "&DEAL_YMD=" + DEAL_YMD);
            documentInfo.getDocumentElement().normalize();

            NodeList nList = documentInfo.getElementsByTagName("item");
            List<HouseDealDto> houseList = new ArrayList<HouseDealDto>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (!houseList.isEmpty() && !aptCheck(houseList, getTagValue("아파트", eElement)))
                        continue;
                    HouseDealDto house = new HouseDealDto();
                    house.setDealAmount(getTagValue("거래금액", eElement));
                    house.setBuiltYear(Integer.parseInt(getTagValue("건축년도", eElement)));
                    house.setDongName(getTagValue("법정동", eElement));
                    house.setAptName(getTagValue("아파트", eElement));
                    house.setDealYear(Integer.parseInt(getTagValue("년", eElement)));
                    house.setDealMonth(Integer.parseInt(getTagValue("월", eElement)));
                    house.setDealDay(Integer.parseInt(getTagValue("일", eElement)));
                    house.setArea(Double.parseDouble(getTagValue("전용면적", eElement)));
                    house.setFloor(Integer.parseInt(getTagValue("층", eElement)));
                    houseList.add(house);
                }
            }
            return houseList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
