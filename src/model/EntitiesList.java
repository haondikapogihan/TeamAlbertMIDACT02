package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EntitiesList {
    private final List<Entity> entities = new ArrayList<>();

    public EntitiesList(){
        readCSV();
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

    public List<Entity> getEntities(){
        return entities;
    }
}
