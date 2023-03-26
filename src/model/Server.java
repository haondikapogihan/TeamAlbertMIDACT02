package model;

import java.util.LinkedList;

public class Server {

    int servicesFinished;
    private boolean isBusy;
    LinkedList<Entity> inService;

    public Server(){
        servicesFinished = 0;
        isBusy = false;
        inService = new LinkedList<>();
    }

    public int checkIfBusy(){
        return isBusy ? 1:0;
    }

    public void enterServer(Entity entity){
        inService.add(entity);
        isBusy = true;
    }

    public Entity exitServer(){
        Entity recentlyFinishedEntity = inService.remove();
        isBusy = false;
        servicesFinished += 1;
        return recentlyFinishedEntity;
    }

    public Entity getInService(){
        return inService.peek();
    }

    public int getServicesFinished(){
        return servicesFinished;
    }
}
