package com.example.coolweather.util;

import android.text.TextUtils;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {
    //解析和处理服务器返回的省级数据
    public static boolean handleProvinceResponse(String reponse){
        if (!TextUtils.isEmpty(reponse)){
            JSONArray allProvinces= null;
            try {
                allProvinces = new JSONArray(reponse);
                for (int i=0;i<allProvinces.length();i++){
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    //解析服务器返回的市级数据
    public static boolean handleCityResponse(String reponse,int provinceId){
        if (!TextUtils.isEmpty(reponse)){
            JSONArray allCities=new JSONArray();
            for (int i=0;i<allCities.length();i++){
                try {
                    JSONObject cityObject=allCities.getJSONObject(i);
                    City city=new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }
    //解析和处理服务器返回的县级数据
    public static boolean handleCountyResponse(String response,int cityId){
        if (!TextUtils.isEmpty(response)){
            JSONArray allCounties=new JSONArray();
            for (int i=0;i<allCounties.length();i++){
                try {
                    JSONObject countyObject=allCounties.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }
}
