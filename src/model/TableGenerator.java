package model;

import model.groupheaderapi.ColumnGroup;
import model.groupheaderapi.GroupableTableHeader;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class TableGenerator {

    private final String[] header1 = {"Just Finished Event","Variables","Attributes","Statistical Accumulators"};
    private final String[] header2 = {"Entity No.","Time t","Event type","Q(t)","B(t)","Arrival Time in queue",
    "Arrival Time in Service","P","N","ΣWQ","WQ*","ΣTS","TS*","∫","Q*","∫B"};
    private DefaultTableModel tableModel;
    private JTable table;

    public TableGenerator(){
        tableModel = new DefaultTableModel();
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

    public void addRow(Object[] row){
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(row);
    }

    public JTable getTable(){
        return table;
    }

}
