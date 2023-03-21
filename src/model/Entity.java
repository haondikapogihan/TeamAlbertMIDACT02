package model;

public class Entity {

    private int entityNo;
    private double arvlTime;
    private double iarvlTime;
    private double serviceTime;
    private final double dprtTime;

    public Entity(int entityNo, double arvlTime, double iarvlTime, double serviceTime){
        this.entityNo = entityNo;
        this.arvlTime = arvlTime;
        this.iarvlTime = iarvlTime;
        this.serviceTime = serviceTime;
        this.dprtTime = arvlTime + serviceTime;
    }

    //setters
    public void setCustNo(int entityNo){
        this.entityNo = entityNo;
    }

    public void setArrivalTime(double arvlTime){
        this.arvlTime = arvlTime;
    }

    public void setInterarvlTime(double iarvlTime){
        this.iarvlTime = iarvlTime;
    }

    public void setServiceTime(double serviceTime){
        this.serviceTime = serviceTime;
    }

    //getters
    public int getEntityNo(){
        return entityNo;
    }

    public double getArrivalTime(){
        return arvlTime;
    }

    public double getInterarvlTime(){
        return iarvlTime;
    }

    public double getServiceTime(){
        return serviceTime;
    }

    public double getDprtTime(){
        return dprtTime;
    }
}
