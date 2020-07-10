package com.example.jishelpdesk;

public class EmpModel {

    String name, number, pan, address, empid;
//    long cActive,ctotal;

    public EmpModel(String name, String number, String pan, String address, String empid, long cActive, long ctotal) {
        this.name = name;
        this.number = number;
        this.pan = pan;
        this.address = address;
        this.empid = empid;
//        this.cActive=cActive;
//        this.ctotal=ctotal;
    }

    public EmpModel() {
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

//    public long getcActive() {
//        return cActive;
//    }
//
//    public long getCtotal() {
//        return ctotal;
//    }
}
