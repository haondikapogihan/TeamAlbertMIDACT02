package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EntitiesList {
    private final LinkedList<Entity> entities = new LinkedList<>();
    private final HashMap<Double,Entity> arrivalTimeAttr = new HashMap<>();

    public EntitiesList(){
        readCSV();
        loadArrivalTimeAttr();
    }

    private void readCSV(){
        try{
            File source = new File("./res/entity_details.csv");
            Scanner scanner = new Scanner(source);
            while(scanner.hasNextLine()){
                String[] values = scanner.next().split(",");
                Entity newEntity;
                if(values[2].equalsIgnoreCase("-")){
                    newEntity = new Entity(Integer.parseInt(values[0]), Double.parseDouble(values[1]),
                            0, 0);
                }else{
                    newEntity = new Entity(Integer.parseInt(values[0]), Double.parseDouble(values[1]),
                            Double.parseDouble(values[2]), Double.parseDouble(values[3]));
                }
                entities.add(newEntity);
            }
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }
    }

    private void loadArrivalTimeAttr(){
        for(Entity entity: entities){
            arrivalTimeAttr.put(entity.getArrivalTime(),entity);
        }
    }

    public LinkedList<Entity> getEntities(){
        return entities;
    }

    public HashMap<Double, Entity> getArrivalTimeAttr(){
        return arrivalTimeAttr;
    }
}
