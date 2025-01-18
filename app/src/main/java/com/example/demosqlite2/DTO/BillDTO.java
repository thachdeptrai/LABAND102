package com.example.demosqlite2.DTO;

public class BillDTO {

    private int id;
    private String fullname;
    private String address;
    private String createdDate;

    public BillDTO(int id, String fullname, String address, String createdDate) {
        this.id = id;
        this.fullname = fullname;
        this.address = address;
        this.createdDate = createdDate;
    }
    public BillDTO(  String fullname, String address, String createdDate) {

        this.fullname = fullname;
        this.address = address;
        this.createdDate = createdDate;
    }
    public int getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getCreatedDate() {
        return createdDate;
    }
}
