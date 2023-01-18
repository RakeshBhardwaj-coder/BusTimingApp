package rakesh.app.bustimingapp.Models;

public class BusStopsModel {
    String stopsIndex;
    String busName,busType,busStopName,busReachTime,busExitTime,busWaitingTime,busFinalDestination;

    public BusStopsModel(){

    }

    public BusStopsModel(String stopsIndex, String busName, String busType, String busStopName, String busReachTime, String busExitTime, String busWaitingTime, String busFinalDestination) {
        this.stopsIndex = stopsIndex;
        this.busName = busName;
        this.busType = busType;
        this.busStopName = busStopName;
        this.busReachTime = busReachTime;
        this.busExitTime = busExitTime;
        this.busWaitingTime = busWaitingTime;
        this.busFinalDestination = busFinalDestination;
    }

    public String getBusFinalDestination() {
        return busFinalDestination;
    }

    public void setBusFinalDestination(String busFinalDestination) {
        this.busFinalDestination = busFinalDestination;
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

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    public String getBusReachTime() {
        return busReachTime;
    }

    public void setBusReachTime(String busReachTime) {
        this.busReachTime = busReachTime;
    }

    public String getBusExitTime() {
        return busExitTime;
    }

    public void setBusExitTime(String busExitTime) {
        this.busExitTime = busExitTime;
    }

    public String getBusWaitingTime() {
        return busWaitingTime;
    }

    public void setBusWaitingTime(String busWaitingTime) {
        this.busWaitingTime = busWaitingTime;
    }

    public String getStopsIndex() {
        return stopsIndex;
    }

    public void setStopsIndex(String stopsIndex) {
        this.stopsIndex = stopsIndex;
    }


}
