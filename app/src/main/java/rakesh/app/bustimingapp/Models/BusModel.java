package rakesh.app.bustimingapp.Models;

public class BusModel {
    private String busNumber,busType,busName,busSource,busDestination,busSourceTime,busDestinationTime;
    public BusModel(){

    }
    public BusModel(String busNumber, String busType, String busName, String busSource, String busDestination, String busSourceTime, String busDestinationTime) {
        this.busNumber = busNumber;
        this.busType = busType;
        this.busName = busName;
        this.busSource = busSource;
        this.busDestination = busDestination;
        this.busSourceTime = busSourceTime;
        this.busDestinationTime = busDestinationTime;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
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
}
