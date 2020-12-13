package com.gjc.domain;

public class Address {
    private int addressid;//地址编号
    private String addressname;//详细地址
    private String province;//省
    private String city;//市
    private String area;//区
    private String postcode;//邮编
    private String receiver;//收件人
    private String phone;//电话
    private int vipid;//关联的用户id

    public int getAddressid() {
        return addressid;
    }

    public void setAddressid(int addressid) {
        this.addressid = addressid;
    }

    public String getAddressname() {
        return addressname;
    }

    public void setAddressname(String addressname) {
        this.addressname = addressname;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getVipid() {
        return vipid;
    }

    public void setVipid(int vip) {
        this.vipid = vip;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressid=" + addressid +
                ", addressname='" + addressname + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", postcode='" + postcode + '\'' +
                ", receiver='" + receiver + '\'' +
                ", phone='" + phone + '\'' +
                ", vipid=" + vipid +
                '}';
    }
}
