package main.java;

import java.util.LinkedList;

import javax.swing.table.AbstractTableModel;

import main.business.LogView;

public class MyModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Mensagem"};
    private final LinkedList<LogView> list;

    public MyModel() {
        list = new LinkedList<LogView>();
    }

    public void addElement(LogView e) {
        list.add(e);
        fireTableRowsInserted(list.size()-1, list.size()-1);
    }

    @Override
	public String getColumnName(int col) {
	    return columnNames[col];
	}
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0: return list.get(rowIndex).getId_msg();
            case 1: return list.get(rowIndex).getMsg_full();
        }
        return null;
    }

	public void removeAll() {
		list.removeAll(list);
		fireTableRowsDeleted(0, 0);
		//fireTableRowsInserted(list.size()-1, list.size()-1);
	}

}
