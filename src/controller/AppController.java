package controller;

import model.TableGenerator2;
import view.AppView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class AppController {

    private TableGenerator2 tg;
    private AppView view;

    public AppController(){
        tg = new TableGenerator2();
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
            }catch(NumberFormatException nfex){
                view.setErrMessage("Please input a valid number");
            }
        }
    }
}
