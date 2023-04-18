package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

public class MetricsSolver {

    public static String avgWaitingTime;
    public static String probOfWaiting;
    public static String propOfIdleTime;
    public static String avgServiceTime;
    public static String avgIAT;
    public static String avgQueueWaitingTime;
    public static String avgSystemTime;
    public static String avgTimeLenOfQueue;
    public static String avgTimeLenOfResource;

    public void solveAvgWaitingTime(Object[] values){
        double avgWaitingTime = Double.parseDouble(values[9].toString())/Double.parseDouble(values[8].toString());
        MetricsSolver.avgWaitingTime = round(avgWaitingTime) + " minutes";
    }

    public void solveProbOfWaiting(ArrayList<Object[]> values, double timeLimit){
        double entWaitedCount = 0;
        double arrivalsCount = 0;
        int prevQueueLength = 0;

        for(Object[] value : values){
            arrivalsCount = Math.max(Integer.parseInt(value[0].toString()), arrivalsCount);

            if (timeLimit < Double.parseDouble(value[1].toString())) {
                probOfWaiting = round(entWaitedCount*100/arrivalsCount) + "%";
                break;
            } else {
                if (Integer.parseInt(value[3].toString()) > prevQueueLength){
                    entWaitedCount += 1;
                }
                prevQueueLength = Integer.parseInt(value[3].toString());
            }
        }
    }

    public void solvePropOfIdleTime(ArrayList<Object[]> values, double timeLimit){
        double idleCount = 0;
        double arrivalsCount = 0;

        for (Object[] value : values) {
            arrivalsCount = Math.max(Integer.parseInt(value[0].toString()), arrivalsCount);

            if (timeLimit < Double.parseDouble(value[1].toString())) {

                if(idleCount == 0){
                    propOfIdleTime = "100%";
                }else{
                    propOfIdleTime = round(idleCount*100/arrivalsCount) + "%";
                }
                break;
            } else if (Double.parseDouble(value[4].toString()) == 0) {
                idleCount += 1;
            }
        }
    }

    public void solveAvgServiceTime(LinkedList<Entity> entities, double timeLimit){
        double totalServiceTime = 0;

        for(int i = 0; i < entities.size(); i++){
            if(timeLimit < entities.get(i).getArrivalTime()){
                avgServiceTime = round(totalServiceTime/i) + " minutes";
                break;
            }else{
                totalServiceTime += entities.get(i).getServiceTime();
            }
        }
    }

    public void solveAvgIAT(LinkedList<Entity> entities, double timeLimit){
        double totalIAT = 0;

        for(int i = 0; i < entities.size(); i++){
            if(timeLimit < entities.get(i).getArrivalTime()){
                avgIAT = round(totalIAT/(i+1)) + " minutes";
                break;
            }else{
                totalIAT += entities.get(i).getInterarvlTime();
            }
        }
    }

    public void solveAvgQueueWaitingTime(ArrayList<Object[]> values, double timeLimit){
        int entWaitedCount = 0;
        int prevQueueLength = 0;

        for(Object[] value: values){
            if(timeLimit < Double.parseDouble(value[1].toString())){
                if(entWaitedCount == 0){
                    avgQueueWaitingTime = 0 + " minutes";
                }else{
                    double sumOfWaitingTimes =  Double.parseDouble(values.get(values.indexOf(value)-1)[9].toString());
                    avgQueueWaitingTime = sumOfWaitingTimes/entWaitedCount + " minutes";
                }
                break;
            }else{
                if (Integer.parseInt(value[3].toString()) > prevQueueLength){
                    entWaitedCount += 1;
                }
                prevQueueLength = Integer.parseInt(value[3].toString());
            }
        }
    }

    public void solveAvgSystemTime(Object[] values){
        double avgSystemTime = Double.parseDouble(values[11].toString())/Double.parseDouble(values[7].toString());
        MetricsSolver.avgSystemTime = round(avgSystemTime) + " minutes";
    }

    public void solveAvgTimeLenOfQueue(Object[] values, double timeLimit){
        MetricsSolver.avgTimeLenOfQueue = round(Double.parseDouble(values[13].toString())/timeLimit) + " minutes";
    }

    public void solveAvgTimeLenOfResource(Object[] values, double timeLimit){
        MetricsSolver.avgTimeLenOfResource = round(Double.parseDouble(values[15].toString())/timeLimit) + " minutes";
    }

    private double round(double number){
        return Math.round(number*100.0)/100.0;
    }

}
