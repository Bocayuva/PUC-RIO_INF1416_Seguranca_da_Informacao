package main.java;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import main.helper.FileUnitCript;

public class TableModel extends AbstractTableModel {

	private static final int COL_NOME_SECRETO = 0; 
	private static final int COL_NOME_CODIGO  = 1; 
	private static final int COL_STATUS       = 2;

	private String[] colunas = new String[]{"Nome secreto", "Nome código", "Status"}; 
	private String[][] linhas = new String[100][3]; 
	
	@Override
	public int getColumnCount() {
		return this.colunas.length;
	}

	@Override
	public int getRowCount() {
		return this.linhas.length;
	}

	@Override
	public String getValueAt(int row, int col) {
		return this.linhas[row][col];
	}

	public void addValue(int row, FileUnitCript item){
		this.linhas[row][0] = item.getNomeSecreto();
		this.linhas[row][1] = item.getNomeCodigo();
		this.linhas[row][2] = item.getStatus();
	}

	
}