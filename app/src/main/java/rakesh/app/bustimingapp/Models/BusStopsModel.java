package rakesh.app.bustimingapp.Models;

public class BusStopsModel {
    String stopsIndex;
    String busStopName,busReachTime,busExitTime,busWaitingTime;

    public BusStopsModel(String stopsIndex) {
        this.stopsIndex = stopsIndex;
    }

    public BusStopsModel(String stopsIndex, String busStopName, String busReachTime, String busExitTime, String busWaitingTime) {
        this.stopsIndex = stopsIndex;
        this.busStopName = busStopName;
        this.busReachTime = busReachTime;
        this.busExitTime = busExitTime;
        this.busWaitingTime = busWaitingTime;
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
