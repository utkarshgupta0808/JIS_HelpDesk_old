package com.example.jishelpdesk;

public class EmpModel {

    String name, number, pan, address, empid;

    public EmpModel(String name, String number, String pan, String address, String empid) {
        this.name = name;
        this.number = number;
        this.pan = pan;
        this.address = address;
        this.empid = empid;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPan() {
        return pan;
    }

    public String getAddress() {
        return address;
    }

    public String getEmpid() {
        return empid;
    }
}
