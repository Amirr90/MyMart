package com.e.vibhacart.Modals;

public class Address {
    private String name;
    private String address;
    private String mobile;
    private boolean isPrimaryAddress;
    String pinCode;
    String landMark;
    String city;
    String addId;

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setPrimaryAddress(boolean primaryAddress) {
        isPrimaryAddress = primaryAddress;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Address(String name, String address, String mobile, boolean isPrimaryAddress) {
        this.name = name;
        this.address = address;
        this.mobile = mobile;
        this.isPrimaryAddress = isPrimaryAddress;
    }

    public Address() {
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getMobile() {
        return mobile;
    }

    public boolean isPrimaryAddress() {
        return isPrimaryAddress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", mobile='" + mobile + '\'' +
                ", isPrimaryAddress=" + isPrimaryAddress +
                ", pinCode='" + pinCode + '\'' +
                ", landMark='" + landMark + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
