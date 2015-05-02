package main.java;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import main.helper.FileUnitCript;

public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1;
	private String[] columnNames = new String[]{"Nome secreto", "Nome código", "Status"};
	public List<List<String>> data = new ArrayList<List<String>>(); 
	public List<FileUnitCript> itens_data = null;
	
	public TableModel(){
		
	}	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public String getValueAt(int row, int col) {
		return data.get(row).get(col);
	}
	
	@Override
	public String getColumnName(int col) {
	    return columnNames[col];
	}
	

	public void addValue(FileUnitCript item){
		List<String> item_linha = new ArrayList<String>();
		item_linha.add(item.getNomeSecreto());
		item_linha.add(item.getNomeCodigo());
		item_linha.add(item.getStatus());
		data.add(item_linha);
	}

	public void removeAll() {
		for (int i = 0; i < data.size(); i++) {
			data.remove(i);
		}		
	}
	
	public FileUnitCript getRow(int row){
		return itens_data.get(row);		
	}
	public void setList(List<FileUnitCript> fileUList) {
		itens_data = fileUList;
	}

	
}