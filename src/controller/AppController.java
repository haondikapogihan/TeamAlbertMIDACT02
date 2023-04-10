package controller;

import model.TableGenerator;
import view.AppView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController {

    private TableGenerator tg;
    private AppView view;

    public AppController(){
        tg = new TableGenerator();
        view = new AppView();
        view.addListener(new buttonAction());
    }

    //the action for the generate button in the UI
    class buttonAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            tg = new TableGenerator();
            try{
                tg.generateTable(Double.parseDouble(view.getInput()));
                view.addTable(tg.getTable());
                view.showTable();
                view.setLocationRelativeTo(null);
                view.setExtendedState(JFrame.MAXIMIZED_BOTH);
            }catch(NumberFormatException nfex){ //the error when type casting non-integers
                view.setErrMessage("Please input a valid number");
            }
        }
    }
}
