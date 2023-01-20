package com.example.myapplicationprojectpart1;

import java.io.Serializable;

public class RequestedService implements Serializable {
    private String serviceName;
    private String customerName;
    private String address;
    private String email;
    private String dateOfBirth;
    private RequestStatus status;
    private ScheduledServices scheduledServices;

    public RequestedService(){

    }

    public RequestedService(String serviceName, String customerName, String address, String email, String dateOfBirth, RequestStatus status, ScheduledServices scheduledServices) {
        this.serviceName = serviceName;
        this.customerName = customerName;
        this.address = address;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
        this.scheduledServices = scheduledServices;
    }

    public ScheduledServices getScheduledServices() {
        return scheduledServices;
    }

    public void setScheduledServices(ScheduledServices scheduledServices) {
        this.scheduledServices = scheduledServices;
    }

    public String getServiceName() {
        return serviceName;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "RequestedService{" +
                "serviceName='" + serviceName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
