package com.example.aman.pgapp.Model;

/**
 * Created by Aman on 09-10-2017.
 */

class Address {
    String Adress;
    String City;
    String State;
    String Pincode;

    public Address(String address, String city, String state, String pincode) {
        Adress = address;
        City = city;
        State = state;
        Pincode = pincode;
    }

    public String getAddress() {
        return Adress;
    }

    public void setAddress(String address) {
        Adress = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "Address='" + Adress + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", Pincode='" + Pincode + '\'' +
                '}';
    }
}
