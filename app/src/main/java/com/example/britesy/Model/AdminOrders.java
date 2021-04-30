package com.example.britesy.Model;

public class AdminOrders {
    private  String Fullname,Mobilenumber,address,city,country,date,time,totalAmount,shippingState,state,pincode;

    public AdminOrders() {
    }

    public AdminOrders(String fullname, String mobilenumber, String address, String city, String country, String date, String time, String totalAmount, String shippingState, String state, String pincode) {
        Fullname = fullname;
        Mobilenumber = mobilenumber;
        this.address = address;
        this.city = city;
        this.country = country;
        this.date = date;
        this.time = time;
        this.totalAmount = totalAmount;
        this.shippingState = shippingState;
        this.state = state;
        this.pincode = pincode;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getMobilenumber() {
        return Mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        Mobilenumber = mobilenumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }
}

