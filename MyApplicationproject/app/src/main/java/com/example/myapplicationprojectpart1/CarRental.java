package com.example.myapplicationprojectpart1;

public class CarRental extends RequestedService{
    private LicenseType licenseType;
    private CarType carType;
    private String pickDate;
    private String returnDate;
    private RequestStatus status;
    private String key;

    public CarRental(){

    }

    public CarRental(String serviceName, String customerName, String address, String email, String dateOfBirth,
                     RequestStatus status, ScheduledServices scheduledServices, LicenseType licenseType, CarType carType, String pickDate, String returnDate, RequestStatus status1, String key) {
        super(serviceName, customerName, address, email, dateOfBirth, status, scheduledServices);
        this.licenseType = licenseType;
        this.carType = carType;
        this.pickDate = pickDate;
        this.returnDate = returnDate;
        this.status = status1;
        this.key = key;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    public String getPickDate() {
        return pickDate;
    }

    public void setPickDate(String pickDate) {
        this.pickDate = pickDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "CarRental{" +
                "licenseType='" + licenseType + '\'' +
                ", carType=" + carType +
                ", pickDate='" + pickDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", status=" + status +
                ", key='" + key + '\'' +
                '}';
    }
}
