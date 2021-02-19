package com.e.vibhacart.Modals;

public class Address {
    private String name;
    private String address;
    private String mobile;
    private boolean isPrimaryAddress;

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
}
