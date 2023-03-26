package model;

public class Entity {

    private int entityNo;
    private double arvlTime;
    private double iarvlTime;
    private double serviceTime;
    private double dprtTime;
    private double waitingTime;

    /* This entity constructor assumes that the entity is the FIRST entity to seize the resource,
     * unless the Waiting Time is set, resulting to a different computed Departure Time
     * which is Departure Time = Arrival Time + Service Time + Waiting Time
    */
    public Entity(int entityNo, double arvlTime, double iarvlTime, double serviceTime){
        this.entityNo = entityNo;
        this.arvlTime = arvlTime;
        this.iarvlTime = iarvlTime;
        this.serviceTime = serviceTime;
        this.dprtTime = arvlTime + serviceTime;// Initial departure time if Entity has no waiting time
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

    public void setWaitingTime(double waitingTime){
        this.waitingTime = waitingTime;
        this.dprtTime = dprtTime + waitingTime;
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

    public double getWaitingTime(){
        return waitingTime;
    }
}
