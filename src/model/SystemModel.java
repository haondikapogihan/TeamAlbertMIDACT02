package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Representation of the entire simulation system
 *
 * It conjoins a Queue class and Server class instances
 * and also computes for the statistical accumulators
 */
public class SystemModel {

    private final ArrayList<Object[]> simulationValues;//contains all the values to be displayed in the table
    private final Queue queue;//the Queue instance
    private final Server server;//the Server instance
    private double accumulatedWaitingTime;//summation of all the entities' waiting time
    private double highestWaitingTime;//holds the highest waiting time from the simulation
    private double accumulatedSystemTime;//summation of all the entities' overall time spent in the system
    private double highestSystemTime;//holds the highest system time from the simulation
    private double qOfT;//keeps track of the number of entities in queue at a certain time
    private double highestQPart;//holds the highest number of entities in queue from the simulation
    private double bOfT;//keeps track of the state of the resource at a certain time

    //Needed values for the computation of Q(t) and B(t)
    //From the plot, X is the time and Y has the values for Q(t) and B(t)
    //Since the plot follows a step chart, the area under the curve is computed like that of a rectangle(L*W)
    //To compute for those values, you have to consider the current and previous X and Y plot values,
    //subtracting the current to the previous values, just as shown in the formula:
    //      (currX-prevX) * (currY-prevY)
    private double prevX;//the previous x value
    private double qPrevY;//the previous y value for Q(t)
    private double bPrevY;//the previous y value for B(t)

    public SystemModel(){
        simulationValues = new ArrayList<>();
        queue = new Queue();
        server = new Server();
        accumulatedWaitingTime = 0.0;
        highestWaitingTime = 0.0;
        accumulatedSystemTime = 0.0;
        highestSystemTime = 0.0;
        qOfT = 0.0;
        highestQPart = 0.0;
        bOfT = 0.0;
        prevX = 0.0;
        qPrevY = 0.0;
        bPrevY = 0.0;
    }

    public void startSimulation(LinkedList<Entity> entities){
        // these are the entities arriving before the entity at service is finished; initialized as the first entity to arrive
        Entity arrivals = entities.get(entities.indexOf(entities.peek()));

        for(Entity entity: entities){
            //checks if no one has arrived
            if(arrivals == null){
                enterSystem(entity);
                break;
            }

            while (arrivals != null && entity.getDprtTime() > arrivals.getArrivalTime()){
                enterSystem(arrivals);
                simulationValues.add(new Object[]{arrivals.getEntityNo(), arrivals.getArrivalTime(),
                                    "Arr", queue.getEntitiesInQueue().size(),server.checkIfBusy(),
                                    getATOfEntitiesInQueue(),getATOfEntityInService(),server.getServicesFinished(),
                                    queue.getQueuePassers(),round(accumulatedWaitingTime),round(highestWaitingTime),
                                    round(accumulatedSystemTime),round(highestSystemTime),
                                    round(qOfT),round(highestQPart),round(bOfT)});

                //updates the arrivals (the entities queueing); assigns null if the last data entry has been reached
                arrivals = entities.indexOf(arrivals)==entities.size()-1? null: entities.get(entities.indexOf(arrivals)+1);
            }
            departSystem();
            simulationValues.add(new Object[]{entity.getEntityNo(),round(entity.getDprtTime()),"Dep",
                                queue.getEntitiesInQueue().size(),server.checkIfBusy(),
                                getATOfEntitiesInQueue(),getATOfEntityInService(),server.getServicesFinished(),
                                queue.getQueuePassers(),round(accumulatedWaitingTime),round(highestWaitingTime),
                                round(accumulatedSystemTime),round(highestSystemTime),
                                round(qOfT),round(highestQPart),round(bOfT)});
        }
    }

    //helper method for accommodating an entity who arrived
    private void enterSystem(Entity entity){
        if(server.checkIfBusy() == 1){
            queue.enterQueue(entity);
        }else{
            queue.enterQueue(entity);
            server.enterServer(queue.exitQueue());
        }

        //solve for the integrals
        double x = entity.getArrivalTime() - prevX;
        double qY = qPrevY;
        double bY = server.checkIfBusy();
        qOfT += x * qY;
        highestQPart = queue.getEntitiesInQueue().size()>highestQPart? queue.getEntitiesInQueue().size():highestQPart;
        bOfT += x * bY;
        prevX = entity.getArrivalTime();
        qPrevY = queue.getEntitiesInQueue().size();
        bPrevY = bY;
    }

    //helper method for departing an entity who got its service done
    private void departSystem(){
        Entity recentlyFinishedEntity = server.exitServer();

        if(queue.getEntitiesInQueue().size() > 0){
            server.enterServer(queue.exitQueue());

            //solve for the accumulated waiting time
            if(server.getInService() != null){
                server.getInService().setWaitingTime(recentlyFinishedEntity.getDprtTime()-server.getInService().getArrivalTime());
                accumulatedWaitingTime += server.getInService().getWaitingTime();
                //sets the highest waiting time
                if(server.getInService().getWaitingTime() > highestWaitingTime){
                    highestWaitingTime = server.getInService().getWaitingTime();
                }
            }
        }
        //solve for the accumulated system time of entities finished
        double systemTime = recentlyFinishedEntity.getDprtTime() - recentlyFinishedEntity.getArrivalTime();
        accumulatedSystemTime += systemTime;
        //sets the highest system time
        if(systemTime > highestSystemTime){
            highestSystemTime = systemTime;
        }

        //solve for the integrals
        double x = recentlyFinishedEntity.getDprtTime() - prevX;
        double qY = qPrevY;
        double bY = server.checkIfBusy();
        qOfT += x * qY;
        highestQPart = queue.getEntitiesInQueue().size()>highestQPart? queue.getEntitiesInQueue().size():highestQPart;
        bOfT += x * bY;
        prevX = recentlyFinishedEntity.getDprtTime();
        qPrevY = queue.getEntitiesInQueue().size();
        bPrevY = bY;
    }

    //getters
    public ArrayList<Object[]> getSimulationTable(){
        return simulationValues;
    }

    private String getATOfEntitiesInQueue(){
        LinkedList<Entity> entitiesInQueue = queue.getEntitiesInQueue();
        LinkedList<Double> arvlTimeOfEntitiesInQ = new LinkedList<>();

        for(int i = entitiesInQueue.size()-1; i >= 0; i--){
            arvlTimeOfEntitiesInQ.add(entitiesInQueue.get(i).getArrivalTime());
        }
        return Arrays.toString(arvlTimeOfEntitiesInQ.toArray());
    }

    private String getATOfEntityInService(){
        return server.getInService()==null? "": String.valueOf(server.getInService().getArrivalTime());
    }

    //helper method for rounding numbers to only two decimal places
    private double round(double number){
        return Math.round(number*100.0)/100.0;
    }
}
