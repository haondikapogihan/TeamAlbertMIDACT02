package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class AppView extends JFrame{
    private JPanel mainPanel;
    private JScrollPane spForTable;
    private JTextPane textPane;
    private JButton genBtn;
    private JTextField inputField;
    private JLabel errMessage;

    public AppView(){
        this.setTitle("Single-channel Queueing System with Statistical Accumulators");
        this.setContentPane(mainPanel);
        this.setSize(new Dimension(580,300));
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

    public String getInput(){
        return inputField.getText();
    }
}
