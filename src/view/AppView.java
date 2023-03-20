package view;

import javax.swing.*;
import java.awt.*;

public class AppView extends JFrame{
    private JPanel mainPanel;
    private JScrollPane spForTable;

    public AppView(){
        this.setContentPane(mainPanel);
        this.setPreferredSize(new Dimension(500,600));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void addTable(JTable table){
        spForTable.getViewport().add(table);
    }
}
