package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AppView extends JFrame{
    private JPanel mainPanel;
    private JScrollPane spForTable;
    private JButton genBtn;
    private JTextField inputField;
    private JLabel errMessage;
    private JLabel avgWaitingTime;
    private JLabel avgIAT;
    private JLabel probOfWaiting;
    private JLabel propOfIdleTime;
    private JLabel avgServiceTime;
    private JLabel avgTimeInSystem;
    private JLabel avgWaitingTimeInQueue;
    private JLabel avgTimeLengthOfQueue;
    private JLabel avgTimeLengthOfResource;

    public AppView(){
        spForTable.setVisible(false);
        this.setTitle("Single-channel Queueing System with Statistical Accumulators");
        this.setContentPane(mainPanel);
        this.setSize(new Dimension(510,270));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void addTable(JTable table){
        spForTable.getViewport().add(table);
    }

    public void addListener(ActionListener listener){
        genBtn.addActionListener(listener);
    }

    public void setErrMessage(String errMessage) {
        this.errMessage.setText(errMessage);
    }

    public void setMetrics(String avgWaitingTime, String avgIAT, String probOfWaiting, String propOfIdleTime,
                           String avgServiceTime, String avgTimeInSystem, String avgWaitingTimeInQueue,
                           String avgTimeLengthOfQueue, String avgTimeLengthOfResource){
        this.avgWaitingTime.setText("Avg. Waiting Time: " + avgWaitingTime);
        this.avgIAT.setText("Avg. Interarrival Time: " + avgIAT);
        this.probOfWaiting.setText("Probability of Waiting: "+ probOfWaiting);
        this.propOfIdleTime.setText("Proportion of Idle Time: "+ propOfIdleTime);
        this.avgServiceTime.setText("Avg. Service Time: "+ avgServiceTime);
        this.avgTimeInSystem.setText("Avg. Time in System: "+ avgTimeInSystem);
        this.avgWaitingTimeInQueue.setText("Avg. Waiting Time in Queue: "+ avgWaitingTimeInQueue);
        this.avgTimeLengthOfQueue.setText("Avg. Time Length of Queue: "+ avgTimeLengthOfQueue);
        this.avgTimeLengthOfResource.setText("Avg. Time Length of Resource: "+ avgTimeLengthOfResource);
    }

    public void showTable(){
        spForTable.setVisible(true);
    }

    public String getInput(){
        return inputField.getText();
    }
}
