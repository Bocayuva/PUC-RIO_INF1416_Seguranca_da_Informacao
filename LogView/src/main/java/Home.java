package main.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import main.business.LogView;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableModel;

public class Home {

	protected JFrame frame;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Home window = new Home();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Home() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 617, 464);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Log View - Consulta LOG");
		
		primeiraetapa();
		
	}

	
	private void primeiraetapa() {
		
		frame.getContentPane().removeAll();
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
				
		JLabel lbUsuarioInfo = new JLabel("Selecione o número de controle: ");
		lbUsuarioInfo.setBounds(12, 10, 420, 15);
		panel.add(lbUsuarioInfo);
		
		List<LogView> grItens          = LogView.buscarTodos();
		final JComboBox<String> comboGrupo = new JComboBox<String>();
		for (int i = 0; i < grItens.size(); i++) {
			comboGrupo.insertItemAt("" + grItens.get(i).getNum_controle(), i);
		}
		comboGrupo.setSelectedIndex(0);
		comboGrupo.setBounds(12, 37, 587, 25);
		panel.add(comboGrupo);
				
		final JLabel lbMsgErro = new JLabel(" ");
		lbMsgErro.setForeground(Color.RED);
		lbMsgErro.setBounds(12, 246, 420, 15);
		panel.add(lbMsgErro);
		
		final MyModel modeloTable = new MyModel();
		final JTable table = new JTable(modeloTable);
		
		final JScrollPane scrollpane = new JScrollPane(table); 
		scrollpane.setBounds(12, 110, 587, 315);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
		scrollpane.setVisible(false);	
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableColumn tbColum = null;
		tbColum = table.getColumnModel().getColumn(0);
		tbColum.setPreferredWidth(70);
		
		tbColum = table.getColumnModel().getColumn(1);
		tbColum.setPreferredWidth(600);
		
		JButton btnListar = new JButton("Listar");
		btnListar.setBounds(482, 73, 117, 25);
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int num_controle = Integer.parseInt((String) comboGrupo.getSelectedItem()); 
				List<LogView> itens = LogView.buscarPorControle(num_controle);
					
				modeloTable.removeAll();
				for (int i = 0; i < itens.size(); i++) {					
					modeloTable.addElement(itens.get(i));
				}
				
				scrollpane.setVisible(true);
				scrollpane.repaint();
			}
		});		
		panel.add(btnListar);
		panel.add(scrollpane, BorderLayout.CENTER);
		
		rebuildFrame();
	}
	
	
		
	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}
}
