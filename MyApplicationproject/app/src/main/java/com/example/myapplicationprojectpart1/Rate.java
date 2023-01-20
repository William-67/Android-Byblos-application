package com.example.myapplicationprojectpart1;

import java.io.Serializable;

public class Rate implements Serializable {
    private ScheduledServices scheduledServices;
    private float rate;
    private String feedback;
    private String key;
    private UserAccount customer;


    public Rate(ScheduledServices scheduledServices, float rate, String feedback, String key, UserAccount customer) {
        this.scheduledServices = scheduledServices;
        this.rate = rate;
        this.feedback = feedback;
        this.key = key;
        this.customer = customer;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Rate() {
    }

    public UserAccount getCustomer() {
        return customer;
    }

    public void setCustomer(UserAccount customer) {
        this.customer = customer;
    }

    public ScheduledServices getScheduledServices() {
        return scheduledServices;
    }

    public void setScheduledServices(ScheduledServices scheduledServices) {
        this.scheduledServices = scheduledServices;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "scheduledServices=" + scheduledServices +
                ", rate=" + rate +
                ", feedback='" + feedback + '\'' +
                ", key='" + key + '\'' +
                ", customer=" + customer +
                '}';
    }
}
