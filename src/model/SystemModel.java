package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class SystemModel {

    private final ArrayList<Object[]> simulationTable;
    private final Queue queue;
    private final Server server;
    private double accumulatedWaitingTime;
    private double highestWaitingTime;
    private double accumulatedSystemTime;
    private double highestSystemTime;

    public SystemModel(){
        simulationTable = new ArrayList<>();
        queue = new Queue();
        server = new Server();
        accumulatedWaitingTime = 0.0;
        highestWaitingTime = 0.0;
        accumulatedSystemTime = 0.0;
        highestSystemTime = 0.0;
    }

    public void startSimulation(LinkedList<Entity> entities){
        // these are the arrivals before the entity at service is finished; initialized as the first entity to arrive
        Entity arrivals = entities.get(entities.indexOf(entities.peek()));

        for(Entity entity: entities){
            if(arrivals == null){
                enterSystem(entity);
                break;
            }

            while (arrivals != null && entity.getDprtTime() > arrivals.getArrivalTime()){
                enterSystem(arrivals);
                simulationTable.add(new Object[]{arrivals.getEntityNo(), arrivals.getArrivalTime(),
                                    "Arr", queue.getEntitiesInQueue().size(),server.checkIfBusy(),
                                    getATOfEntitiesInQueue(),getATOfEntityInService(),server.getServicesFinished(),
                                    queue.getQueuePassers(),round(accumulatedWaitingTime),round(highestWaitingTime),
                                    round(accumulatedSystemTime),round(highestSystemTime)});

                //updates the arrivals (the entities queueing); assigns null if the last data entry has been reached
                arrivals = entities.indexOf(arrivals)==entities.size()-1? null: entities.get(entities.indexOf(arrivals)+1);
            }
            departSystem();
            simulationTable.add(new Object[]{entity.getEntityNo(),round(entity.getDprtTime()),"Dep",
                                queue.getEntitiesInQueue().size(),server.checkIfBusy(),
                                getATOfEntitiesInQueue(),getATOfEntityInService(),server.getServicesFinished(),
                                queue.getQueuePassers(),round(accumulatedWaitingTime),round(highestWaitingTime),
                                round(accumulatedSystemTime),round(highestSystemTime)});
        }
    }

    private void enterSystem(Entity entity){
        if(server.checkIfBusy() == 1){
            queue.enterQueue(entity);
        }else{
            queue.enterQueue(entity);
            server.enterServer(queue.exitQueue());
        }
    }

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
    }

    public ArrayList<Object[]> getSimulationTable(){
        return simulationTable;
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

    private double round(double number){
        return Math.round(number*100.0)/100.0;
    }
}
