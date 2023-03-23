package model;

import model.groupheaderapi.ColumnGroup;
import model.groupheaderapi.GroupableTableHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.util.*;

public class TableGenerator {

    private final LinkedList<Entity> entitiesList = new EntitiesList().getEntities();
    private final String[] header1 = {"Just Finished Event","Variables","Attributes","Statistical Accumulators"};
    private final String[] header2 = {"Entity No.","Time t","Event type","Q(t)","B(t)","Arrival Time in queue",
    "Arrival Time in Service","P","N","ΣWQ","WQ*","ΣTS","TS*","∫","Q*","∫B"};
    private DefaultTableModel tableModel;
    private JTable table;

    //initializes the Grouped Headers
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

    //in progress
    public void generateTable(double timeLimit){
        init();
        Entity currEntity = entitiesList.peekFirst();
        double time = currEntity.getArrivalTime();
        int inQueue = 0;
        int isServerBusy = 1;
        Queue<Double> whoInQueue = new LinkedList<>();
        double[] whoInService = {currEntity.getArrivalTime()};
        int departed = 0;
        int queuePassers = 1;
        double totalWaitTime = 0;
        double highestWT = 0;
        double totalSysTime = 0;
        double highestST = 0;

        //add first initial value
        Object[] initValue = new Object[]{"-",0,"Init","-","-","-","-",0,0,0,0,0,0,0,0,0};
        addRow(initValue);

        //add the first entity's values
        Object[] firstEntity = new Object[]{currEntity.getEntityNo(),time,"Arr",inQueue,isServerBusy,
                Arrays.toString(whoInQueue.toArray()),Arrays.toString(whoInService),departed,queuePassers,totalWaitTime,
                highestWT,totalSysTime,highestST};
        addRow(firstEntity);

        //succeeding entity to be placed in the table
        Entity nextEntity = entitiesList.get(entitiesList.indexOf(currEntity)+1);

        while(time < timeLimit){
            time = nextEntity.getArrivalTime();

            while(time < currEntity.getDprtTime()){
                if(time > timeLimit){
                    break;
                }else{
                    if(whoInService.length == 0){
                        whoInService = new double[]{nextEntity.getArrivalTime()};
                        isServerBusy = 1;
                        queuePassers += 1;
                    }else{
                        whoInQueue.add(nextEntity.getArrivalTime());
                        inQueue = whoInQueue.size();
                    }

                    addRow(new Object[]{nextEntity.getEntityNo(),time,"Arr",inQueue,isServerBusy,
                        Arrays.toString(whoInQueue.toArray()),Arrays.toString(whoInService),departed,queuePassers,totalWaitTime,
                        highestWT,totalSysTime,highestST});

                    nextEntity = entitiesList.get(entitiesList.indexOf(nextEntity)+1);
                    time = nextEntity.getArrivalTime();
                }
            }
            if(whoInQueue.size() > 0){
                whoInService = new double[]{whoInQueue.remove()};
                queuePassers += 1;
            }else{
                whoInService = new double[0];
            }
            if(whoInService.length == 0){
                isServerBusy = 0;
            }
            inQueue = whoInQueue.size();
            departed += 1;

            //solve for the waiting time of the entity in queue
            double curWaitingTime = currEntity.getDprtTime() - entitiesList.get(entitiesList.indexOf(currEntity)+1).getArrivalTime();
            //set the waiting time of the entity in queue
            entitiesList.get(entitiesList.indexOf(currEntity)+1).setWaitingTime(curWaitingTime);
            totalWaitTime += curWaitingTime;
            if(highestWT < curWaitingTime){
                highestWT = curWaitingTime;
            }

            //solve for the time in the system of the just-finished entity
            double curSystemTime = currEntity.getDprtTime() - currEntity.getArrivalTime();
            totalSysTime += curSystemTime;
            if(highestST < curSystemTime){
                highestST = curSystemTime;
            }

            addRow(new Object[]{currEntity.getEntityNo(),currEntity.getDprtTime(),"Dep",inQueue,isServerBusy,
                    Arrays.toString(whoInQueue.toArray()),Arrays.toString(whoInService),departed,queuePassers,totalWaitTime,highestWT,totalSysTime,highestST});
            currEntity = entitiesList.get(entitiesList.indexOf(currEntity)+1);
        }

    }

    private void addRow(Object[] row){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(row);
    }

    public JTable getTable(){
        return table;
    }


}
