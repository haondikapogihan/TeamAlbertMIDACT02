package controller;

import model.TableGenerator;
import view.AppView;

public class AppController {

    private TableGenerator tg;
    private AppView view;

    public AppController(){
        tg = new TableGenerator();
        view = new AppView();

        //testing
        Object[] newRow = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        for(int i = 0; i < 5; i++){
            tg.addRow(newRow);
        }
        view.addTable(tg.getTable());
    }
}
