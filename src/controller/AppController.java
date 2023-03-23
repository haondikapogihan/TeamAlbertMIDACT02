package controller;

import model.TableGenerator;
import view.AppView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class AppController {

    private TableGenerator tg;
    private AppView view;

    public AppController(){
        tg = new TableGenerator();
        view = new AppView();
        view.addListener(new buttonAction());
    }

    class buttonAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                tg.generateTable(Double.parseDouble(view.getInput()));
                view.addTable(tg.getTable());
                view.setLocationRelativeTo(null);
                view.setExtendedState(JFrame.MAXIMIZED_BOTH);
                view.setUndecorated(true);
            }catch(NumberFormatException nfex){
                view.setErrMessage("Please input a valid number");
            }
        }
    }
}
