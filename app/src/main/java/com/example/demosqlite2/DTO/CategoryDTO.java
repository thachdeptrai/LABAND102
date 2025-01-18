package com.example.demosqlite2.DTO;

public class CategoryDTO {
    int id ;
    String name ;
    double price;
    public  String toString () {
        return  "ID cat: " + id + "Name: " + name ;

    }

    public CategoryDTO() {
    }

    public CategoryDTO(String name) {
        this.name = name;
    }

    public CategoryDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
