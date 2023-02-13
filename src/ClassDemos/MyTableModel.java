package ClassDemos;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class MyTableModel extends AbstractTableModel
{
    ArrayList<Object[]> questions;

    String[] header;

    int col;

    MyTableModel(ArrayList<Object[]> questions, String[] header)
    {
        this.questions=questions;
        this.header=header;
        col=this.findColumn("Sent");
    }


    @Override
    public int getRowCount() {
        return questions.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return questions.get(rowIndex)[columnIndex];
    }

    public String getColumnName(int index)
    {
        return header[index];
    }

    public Class getColumnClass(int columnIndex)
    {
        if (columnIndex == col)
        {
            return Boolean.class; // For every cell in column 7, set its class to Boolean.class
        }
        return super.getColumnClass(columnIndex); // Otherwise, set it to the default class

    }

    void add(String word1, String word2, String word3)
    {
        // make it an array[3] as this is the way it is stored in the ArrayList
        // (not best design but we want simplicity)
        Object[] item = new Object[3];
        item[0] = word1;
        item[1] = word2;
        item[2] = word3;
        questions.add(item);
        // inform the GUI that I have change
        fireTableDataChanged();
    }

}
