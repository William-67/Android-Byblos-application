package com.example.myapplicationprojectpart1;

public class MovingAssistance extends RequestedService{
    private String startLocation;
    private String endLocation;
    private int numOfBoxes;
    private int numOfMovers;
    private RequestStatus status;
    private String key;

    public MovingAssistance(){

    }

    public MovingAssistance(String serviceName, String customerName, String address, String email, String dateOfBirth, RequestStatus status, ScheduledServices scheduledServices, String startLocation, String endLocation, int numOfBoxes, int numOfMovers, RequestStatus status1, String key) {
        super(serviceName, customerName, address, email, dateOfBirth, status, scheduledServices);
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.numOfBoxes = numOfBoxes;
        this.numOfMovers = numOfMovers;
        this.status = status1;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public int getNumOfBoxes() {
        return numOfBoxes;
    }

    public void setNumOfBoxes(int numOfBoxes) {
        this.numOfBoxes = numOfBoxes;
    }

    public int getNumOfMovers() {
        return numOfMovers;
    }

    public void setNumOfMovers(int numOfMovers) {
        this.numOfMovers = numOfMovers;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MovingAssistance{" +
                "startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", numOfBoxes=" + numOfBoxes +
                ", numOfMovers=" + numOfMovers +
                ", status=" + status +
                ", key='" + key + '\'' +
                '}';
    }
}
