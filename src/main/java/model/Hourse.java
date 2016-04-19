package model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by zhangll on 2016/4/14.
 */
public class Hourse {
    private String title;           //出租标题i
    private String modifyDate;      //最后修改时间
    private List<String> imgList;   //图片列表
    private BigDecimal price;       //价格
    private String HourseType;      //房屋类型
    private List<String> area;        //房屋小区
    private String address;         //具体地址
    private List<String> config;    //房屋配置
    private String url;             //url地址
    private String description;     //房屋描述

    public Hourse() {
    }

    @Override
    public String toString() {
        return "Hourse{" +
                "title='" + title + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", imgList=" + imgList.toString() +
                ", price=" + price +
                ", HourseType='" + HourseType + '\'' +
                ", area=" + area.toString() +
                ", address='" + address + '\'' +
                ", config=" + config.toString() +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Hourse(String title, String modifyDate, List<String> imgList, BigDecimal price, String hourseType, List<String> area, String address, List<String> config, String url, String description) {
        this.title = title;
        this.modifyDate = modifyDate;
        this.imgList = imgList;
        this.price = price;
        HourseType = hourseType;
        this.area = area;
        this.address = address;
        this.config = config;
        this.url = url;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getHourseType() {
        return HourseType;
    }

    public List<String> getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getConfig() {
        return config;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setHourseType(String hourseType) {
        HourseType = hourseType;
    }

    public void setArea(List<String> area) {
        this.area = area;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setConfig(List<String> config) {
        this.config = config;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
