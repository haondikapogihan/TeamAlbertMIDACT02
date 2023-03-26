package model;

import model.EntitiesList;
import model.Entity;
import model.groupheaderapi.ColumnGroup;
import model.groupheaderapi.GroupableTableHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.util.*;

public class TableGenerator2 {

    private final EntitiesList entitiesList = new EntitiesList();
    private final SystemModel systemModel = new SystemModel();
    private final String[] header1 = {"Just Finished Event","Variables","Attributes","Statistical Accumulators"};
    private final String[] header2 = {"Entity No.","Time t","Event type","Q(t)","B(t)","Arrival Time in queue",
            "Arrival Time in Service","P","N","ΣWQ","WQ*","ΣTS","TS*","∫Q","Q*","∫B"};
    private DefaultTableModel tableModel;
    private JTable table;

    private void init(){
        tableModel = new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        tableModel.setColumnIdentifiers(header2);

        //allows creation of JTable with "groupable" headers
        JTable table = new JTable(tableModel){
            @Override
            protected JTableHeader createDefaultTableHeader(){
                return new GroupableTableHeader(columnModel);
            }
        };

        TableColumnModel cm = table.getColumnModel();

        //add sub-column-headers for "Just Finished Event"
        ColumnGroup jfe = new ColumnGroup(header1[0]);
        for(int i = 0; i < 3; i++){
            jfe.add(cm.getColumn(i));
        }

        //add sub-column-headers for "Variables"
        ColumnGroup vars = new ColumnGroup(header1[1]);
        for(int i = 3; i < 5; i++){
            vars.add(cm.getColumn(i));
        }

        //add sub-column-headers for "Attributes"
        ColumnGroup attr = new ColumnGroup(header1[2]);
        for(int i = 5; i < 7; i++){
            attr.add(cm.getColumn(i));
        }

        //add sub-column-headers for "Statistical Accumulators"
        ColumnGroup sa = new ColumnGroup(header1[3]);
        for(int i = 7; i < header2.length; i++){
            sa.add(cm.getColumn(i));
        }

        GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
        header.addColumnGroup(jfe);
        header.addColumnGroup(vars);
        header.addColumnGroup(attr);
        header.addColumnGroup(sa);

        this.table = table;
    }

//    public void generateTable(double timeLimit){
//        double time = 0.0;
//        int howManyInQueue = 0;
//        int isServerBusy = 0;
//        Queue<Double> whoAreInQueue = new LinkedList<>();
//        double whoIsInService;
//        int finishedProducts = 0;
//        int queuePassers = 0;
//        double accumulatedWaitingTime = 0.0;
//        double highestWT = 0.0;
//        double accumulatedSystemTime = 0.0;
//        double highestST = 0.0;
//
//        for(Entity currentEntity: entitiesList){
//            Entity nextEntity;
//            //first iteration
//            if(entitiesList.indexOf(currentEntity) == 0){
//                nextEntity = entitiesList.get(0);
//                while(currentEntity.getDprtTime() > nextEntity.getArrivalTime()){
//                    //check if the simulation is under the time limit
//                    if(nextEntity.getArrivalTime() > timeLimit){
//                        break;
//                    }else{
//                        whoIsInService = nextEntity.getArrivalTime();
//                        isServerBusy = 1;
//                        queuePassers = 1;
//
//                        addRow(new Object[]{nextEntity.getEntityNo(),nextEntity.getArrivalTime(),"Arr",
//                                howManyInQueue,isServerBusy, Arrays.toString(whoAreInQueue.toArray()),
//                                whoIsInService,finishedProducts,queuePassers,
//                                accumulatedWaitingTime,highestWT,accumulatedSystemTime,highestST});
//                        nextEntity = entitiesList.get(entitiesList.indexOf(currentEntity)+1);
//                    }
//                }
//                whoIsInService = currentEntity.getArrivalTime();
//                addRow(new Object[]{currentEntity.getEntityNo(),currentEntity.getDprtTime(),"Dep",
//                        howManyInQueue,isServerBusy,Arrays.toString(whoAreInQueue.toArray()),
//                        whoIsInService,finishedProducts,queuePassers,
//                        accumulatedWaitingTime,highestWT,accumulatedSystemTime,highestST});
//            }else{//the succeeding iterations
//                nextEntity = entitiesList.get(entitiesList.indexOf(currentEntity)+1);
//                while(currentEntity.getDprtTime() > nextEntity.getArrivalTime()){
//
//                }
//            }
//        }
//    }

//    public void generateTable(double timeLimit){
//        init();
//        LinkedList<Entity> entities = entitiesList.getEntities();
//        HashMap<Double,Entity> arrivalTimeOfEntities = entitiesList.getArrivalTimeAttr();
//        SystemModel systemModel = new SystemModel();
//
//        ArrayList<Double> arrivalTimes = new ArrayList<>(arrivalTimeOfEntities.keySet());
//        double recentDepartureTime = 0.0;
//        double currentDepartureTime = entities.peek().getDprtTime();
//
//        for(Entity entity: entities){
//
//            //these are the arrivals before the entity departs
//            ArrayList<Double> arrivals = getArrivalsBeforeEntityDept(arrivalTimes,recentDepartureTime,currentDepartureTime);
//            for (Double arrival : arrivals) {
//                if(arrival >= timeLimit){
//                    break;
//                }
//
//                Entity arrivedEntity = arrivalTimeOfEntities.get(arrival);
//                systemModel.enterSystem(arrivedEntity);
//                addRow(new Object[]{arrivedEntity.getEntityNo(),arrival,"Arr",
//                                    systemModel.getQueueLength(),systemModel.serverStatus(),
//                                    systemModel.getEntitiesInQueue(),systemModel.getEntityInService().getArrivalTime(),
//                                    systemModel.noOfFinishedEntities(),systemModel.noOfQueuePassers(),
//                                    systemModel.getAccumulatedWaitingTime(),systemModel.getHighestWaitingTime(),
//                                    systemModel.getHighestWaitingTime(),systemModel.getHighestWaitingTime()});
//            }
//            Entity recentlyFinishedEntity = systemModel.departSystem();
//            addRow(new Object[]{recentlyFinishedEntity.getEntityNo(),recentlyFinishedEntity.getDprtTime(),"Dep",
//                                systemModel.getQueueLength(),systemModel.serverStatus(),
//                                systemModel.getEntitiesInQueue(),systemModel.getEntityInService()==null?"[]":systemModel.getEntityInService().getArrivalTime(),
//                                systemModel.noOfFinishedEntities(),systemModel.noOfQueuePassers(),
//                                systemModel.getAccumulatedWaitingTime(),systemModel.getHighestWaitingTime(),
//                                systemModel.getAccumulatedSystemTime(),systemModel.getHighestSystemTime()});
//            if(entity.getArrivalTime() >= timeLimit){
//                break;
//            }
//
//            currentDepartureTime = systemModel.getEntityInService()==null?entities.get(entities.indexOf(entity)+1).getArrivalTime():systemModel.getEntityInService().getDprtTime();
//            recentDepartureTime = recentlyFinishedEntity.getDprtTime();
//        }
//    }

    public void generateTable(double timeLimit){
        init();
        systemModel.startSimulation(entitiesList.getEntities());
        ArrayList<Object[]> tableSimulation = systemModel.getSimulationTable();

        for(Object[] values: tableSimulation){
            if(Double.parseDouble(values[1].toString()) <= timeLimit){
                addRow(values);
            }
        }
    }

    private void addRow(Object[] row){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(row);
    }

    /* filter out the arrivals that came before the
     * entity-in-service's departure time but after the
     * recently-finished entity's departure time
     */
    private ArrayList<Double> getArrivalsBeforeEntityDept(ArrayList<Double> arrivals, double recentDeptTime,
                                                          double currentDeptTime){

        ArrayList<Double> filtered = new ArrayList<>();
        for(Double arrivalTime: arrivals){
            if(arrivalTime > recentDeptTime && arrivalTime < currentDeptTime){
                filtered.add(arrivalTime);
            }
        }
        return filtered;
    }

    public JTable getTable(){
        return table;
    }
}
