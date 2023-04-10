package model;

import java.util.LinkedList;

/**
 * Representation of the resource being seized by the entities
 */
public class Server {

    int servicesFinished;//keeps track of the numbers of entities having their services done
    private boolean isBusy;//the current condition of the resource
    LinkedList<Entity> inService;//the entity in service

    public Server(){
        servicesFinished = 0;
        isBusy = false;
        inService = new LinkedList<>();
    }

    //checks the current condition/state of the resource
    public int checkIfBusy(){
        return isBusy ? 1:0;
    }

    //enters and entity to seize this resource
    public void enterServer(Entity entity){
        inService.add(entity);
        isBusy = true;
    }

    //exits the entity in service
    public Entity exitServer(){
        Entity recentlyFinishedEntity = inService.remove();
        isBusy = false;
        servicesFinished += 1;
        return recentlyFinishedEntity;
    }

    //returns the current entity in service
    public Entity getInService(){
        return inService.peek();
    }

    //returns how many services were finished from this resource
    public int getServicesFinished(){
        return servicesFinished;
    }
}
