package rakesh.app.bustimingapp.Models;

public class BusFindModel {
    private String busName,busType,busSource,busDestination,busSourceTime,busDestinationTime,busArrivalTime;

    public BusFindModel(){

    }

    public BusFindModel(String busName, String busType, String busSource, String busDestination, String busSourceTime, String busDestinationTime, String busArrivalTime) {
        this.busName = busName;
        this.busType = busType;
        this.busSource = busSource;
        this.busDestination = busDestination;
        this.busSourceTime = busSourceTime;
        this.busDestinationTime = busDestinationTime;
        this.busArrivalTime = busArrivalTime;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusSource() {
        return busSource;
    }

    public void setBusSource(String busSource) {
        this.busSource = busSource;
    }

    public String getBusDestination() {
        return busDestination;
    }

    public void setBusDestination(String busDestination) {
        this.busDestination = busDestination;
    }

    public String getBusSourceTime() {
        return busSourceTime;
    }

    public void setBusSourceTime(String busSourceTime) {
        this.busSourceTime = busSourceTime;
    }

    public String getBusDestinationTime() {
        return busDestinationTime;
    }

    public void setBusDestinationTime(String busDestinationTime) {
        this.busDestinationTime = busDestinationTime;
    }

    public String getBusArrivalTime() {
        return busArrivalTime;
    }

    public void setBusArrivalTime(String busArrivalTime) {
        this.busArrivalTime = busArrivalTime;
    }


}
