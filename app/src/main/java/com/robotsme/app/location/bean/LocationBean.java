package com.robotsme.app.location.bean;

import com.zsd.android.dblib.annotation.DbField;
import com.zsd.android.dblib.annotation.DbTable;

@DbTable("tb_location")
public class LocationBean {

    @DbField(value = "_id", primaryKey = true)
    private int id;
    private double lat;
    private double lng;
    @DbField("create_time")
    private long time;
    private String province;
    private String city;
    private String area;
    private String street;
    private String strAd;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStrAd() {
        return strAd;
    }

    public void setStrAd(String strAd) {
        this.strAd = strAd;
    }
}
