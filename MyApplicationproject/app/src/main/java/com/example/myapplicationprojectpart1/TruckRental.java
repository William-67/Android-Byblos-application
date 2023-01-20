package com.example.myapplicationprojectpart1;

public class TruckRental extends RequestedService{

    private String kilometers;
    private String area;
    private RequestStatus status;
    private LicenseType licenseType;
    private String pickDate;
    private String returnDate;
    private String key;

    public TruckRental(){

    }

    public TruckRental(String serviceName, String customerName, String address, String email, String dateOfBirth, RequestStatus status, ScheduledServices scheduledServices, String kilometers, String area, RequestStatus status1, LicenseType licenseType, String pickDate, String returnDate, String key) {
        super(serviceName, customerName, address, email, dateOfBirth, status, scheduledServices);
        this.kilometers = kilometers;
        this.area = area;
        this.status = status1;
        this.licenseType = licenseType;
        this.pickDate = pickDate;
        this.returnDate = returnDate;
        this.key = key;
    }

    @Override
    public String toString() {
        return "TruckRental{" +
                "kilometers='" + kilometers + '\'' +
                ", area='" + area + '\'' +
                ", status=" + status +
                ", licenseType=" + licenseType +
                ", pickDate='" + pickDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                ", key='" + key + '\'' +
                '}';
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

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKilometers() {
        return kilometers;
    }

    public void setKilometers(String kilometers) {
        this.kilometers = kilometers;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

}
