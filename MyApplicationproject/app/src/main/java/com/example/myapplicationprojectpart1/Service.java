package com.example.myapplicationprojectpart1;

import java.io.Serializable;
import java.util.List;

public class Service implements Serializable {

    //instance variable
    private String name;
    private double price;
    private String key;

    private List<String> detail;

    //constructor
    public Service(){

    }

    public Service(String name, double price, String key, List<String> detail) {
        this.name = name;
        this.price = price;
        this.key = key;
        this.detail = detail;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getKey() {
        return key;
    }

    public List<String> getDetail() {
        return detail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDetail(List<String> detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Service{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", key='" + key + '\'' +
                ", detail=" + detail +
                '}';
    }
//check if services exists
    public static boolean isDuplicate(List<Service> services, String name){
        for (Service service : services){
            if (service.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
