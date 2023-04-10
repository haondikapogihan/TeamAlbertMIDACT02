package model;

import java.util.LinkedList;

/**
 * Representation of the queue itself
 */
public class Queue{

    private int queuePassers;//tracks the number of entities which passed through the queue
    LinkedList<Entity> entitiesInQueue;//list of entities in queue

    public Queue(){
        queuePassers = 0;
        entitiesInQueue = new LinkedList<>();
    }

    //enters an entity in the queue
    public void enterQueue(Entity entity){
        entitiesInQueue.add(entity);
    }

    //removes an entity in the queue
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
