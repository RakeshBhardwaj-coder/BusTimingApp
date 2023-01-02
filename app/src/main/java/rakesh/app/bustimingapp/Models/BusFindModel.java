package rakesh.app.bustimingapp.Models;

public class BusFindModel {
    private String busName,busType,busExitTime, busReachTime, busStopName, busWaitingTime;


    public BusFindModel() {

    }

    public BusFindModel(String busName, String busType, String busExitTime, String busReachTime, String busStopName, String busWaitingTime) {
        this.busName = busName;
        this.busType = busType;
        this.busExitTime = busExitTime;
        this.busReachTime = busReachTime;
        this.busStopName = busStopName;
        this.busWaitingTime = busWaitingTime;
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

    public String getBusExitTime() {
        return busExitTime;
    }

    public void setBusExitTime(String busExitTime) {
        this.busExitTime = busExitTime;
    }

    public String getBusReachTime() {
        return busReachTime;
    }

    public void setBusReachTime(String busReachTime) {
        this.busReachTime = busReachTime;
    }

    public String getBusStopName() {
        return busStopName;
    }

    public void setBusStopName(String busStopName) {
        this.busStopName = busStopName;
    }

    public String getBusWaitingTime() {
        return busWaitingTime;
    }

    public void setBusWaitingTime(String busWaitingTime) {
        this.busWaitingTime = busWaitingTime;
    }
}
