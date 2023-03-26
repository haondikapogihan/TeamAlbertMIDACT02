package model;

import java.util.LinkedList;

public class Queue{

    private int queuePassers;
    LinkedList<Entity> entitiesInQueue;

    public Queue(){
        queuePassers = 0;
        entitiesInQueue = new LinkedList<>();
    }

    public void enterQueue(Entity entity){
        entitiesInQueue.add(entity);
    }

    public Entity exitQueue(){
        queuePassers += 1;
        return entitiesInQueue.pop();
    }

    public LinkedList<Entity> getEntitiesInQueue() {
        return entitiesInQueue;
    }

    public int getQueuePassers() {
        return queuePassers;
    }
}
