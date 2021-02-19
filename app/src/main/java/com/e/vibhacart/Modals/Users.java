package com.e.vibhacart.Modals;

public class Users {
    private String name,address,phoneNumberl,image;

    public Users() {
    }

    public Users(String name, String address, String phoneNumberl, String image) {
        this.name = name;
        this.address = address;
        this.phoneNumberl = phoneNumberl;
        this.image = image;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumberl() {
        return phoneNumberl;
    }

    public void setPhoneNumberl(String phoneNumberl) {
        this.phoneNumberl = phoneNumberl;
    }
}
