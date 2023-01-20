package com.example.myapplicationprojectpart1;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

public class ScheduledServices implements Serializable {
    private String name;
    private String key;
    private String phone;
    private String address;
    private int startHour,startMin,endHour,endMin;
    private DayOfWeek dayOfWeek;
    private String employeeName;


    public ScheduledServices() {
    }

    public ScheduledServices(String name, String key, String phone, String address, int startHour, int startMin, int endHour, int endMin, DayOfWeek dayOfWeek, String employeeName) {
        this.name = name;
        this.key = key;
        this.phone = phone;
        this.address = address;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
        this.dayOfWeek = dayOfWeek;
        this.employeeName = employeeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMin() {
        return startMin;
    }

    public void setStartMin(int startMin) {
        this.startMin = startMin;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMin() {
        return endMin;
    }

    public void setEndMin(int endMin) {
        this.endMin = endMin;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    @Override
    public String toString() {
        return "ScheduledServices{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", startHour=" + startHour +
                ", startMin=" + startMin +
                ", endHour=" + endHour +
                ", endMin=" + endMin +
                ", dayOfWeek=" + dayOfWeek +
                ", employeeName='" + employeeName + '\'' +
                '}';
    }

    public static boolean scheduledOnSameDate(List<ScheduledServices> scheduledServicesList, String key,String name, DayOfWeek dayOfWeek){
        for (ScheduledServices scheduledServices: scheduledServicesList){
            if(scheduledServices.getName().equals(name)&&scheduledServices.getDayOfWeek()==dayOfWeek && !scheduledServices.getKey().equals(key)){
                return true;
            }

        }
        return false;
    }
    public static boolean before(int hour,int min,int otherHour,int otherMin){
        if(hour==otherHour){
            return min<=otherMin;
        }else if(hour<otherHour){
            return true;
        }else{
            return false;
        }
    }
    public static boolean after(int hour,int min,int otherHour,int otherMin){
        if(hour==otherHour){
            return min>=otherMin;
        }else if(hour<otherHour){
            return false;
        }else{
            return true;
        }
    }
    public static boolean timeConflict(List<ScheduledServices> scheduledServicesList,String key,String employeeName,DayOfWeek dayOfWeek,int startHour,int startMin,int endHour,int endMin){
        for (ScheduledServices scheduledServices: scheduledServicesList){
            if(!scheduledServices.getKey().equals(key) && scheduledServices.getEmployeeName().equals(employeeName) && scheduledServices.getDayOfWeek()==dayOfWeek){
                if(!(before(endHour,endMin,scheduledServices.getStartHour(),scheduledServices.getStartMin()) || after(startHour,startMin,scheduledServices.getEndHour(),scheduledServices.getEndMin()))){
                    return true;

                }
            }

        }
        return false;
    }
    public static int getIndex(List<ScheduledServices> serviceList,String key){
        for (int i = 0;i<serviceList.size();i++){
            if(serviceList.get(i).getKey().equals(key)){
                return i;
            }
        }
        return -1;
    }


}

