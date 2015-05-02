package main.java;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.Key;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.TabExpander;

import main.business.Usuario;
import main.helper.FileCript;
import main.helper.FileUnitCript;
import main.helper.Utility;
import javax.swing.JTable;

public class ConsultaPasta{

	private Usuario usuario;
	private JFrame frame;
	private JPanel allPane;
	
	public ConsultaPasta(Usuario fusuario, JFrame fframe) {
		
		usuario = fusuario;
		frame   = fframe;
		
		frame.setBounds(100,100,500, 500);
		frame.getContentPane().removeAll();		
		
		allPane = new JPanel();
		frame.setContentPane(allPane);
		
		montaCabecalho();
		montaCorpo1();
		montaFormConsulta();
				
		rebuildFrame();
		
	}
	
	private void montaCorpo1() {
		allPane.setLayout(null);
		JPanel corpo1 = new JPanel();
		corpo1.setLocation(12, 100);
		corpo1.setSize(426, 32);
		corpo1.setLayout(null);
		
		JLabel lbTotalAcessos = new JLabel("Total de consultas do usuário: " + usuario.getNum_consultas());
		lbTotalAcessos.setBounds(12, 12, 402, 15);
		corpo1.add(lbTotalAcessos);
		
		allPane.add(corpo1);
	}

	private void montaCabecalho() {
		JPanel cabecalho = Cabecalho.MontaCabecalho(usuario);
		allPane.add(cabecalho);				
	}

	public void montaFormConsulta() {
		
		JPanel formConsulta = new JPanel();
		formConsulta.setBounds(12, 135, 426, 325);
		formConsulta.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		formConsulta.setLayout(null);
		
		JLabel lbMenu = new JLabel("Consulta pasta");
		lbMenu.setBounds(12, 5, 402, 15);
		formConsulta.add(lbMenu);
		
		JLabel lblCamin = new JLabel("Caminho da chave privada: ");
		lblCamin.setBounds(12, 27, 402, 15);
		formConsulta.add(lblCamin);
		
		final JTextField txtKeyPriv = new JTextField();
		txtKeyPriv.setBounds(12, 43, 341, 25);
		txtKeyPriv.setEnabled(false);
		formConsulta.add(txtKeyPriv);
		
		JButton btnChooseFile = new JButton("->");
		btnChooseFile.setBounds(365, 43, 49, 25);
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = chooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = chooser.getSelectedFile();
				    txtKeyPriv.setText( selectedFile.getAbsolutePath());
				}
				
			}
		});
		formConsulta.add(btnChooseFile);
		
		JLabel lbFraseSecreta = new JLabel("Frase secreta: ");
		lbFraseSecreta.setBounds(12, 69, 402, 15);
		formConsulta.add(lbFraseSecreta);
		
		final JTextField txtFraseSecreta = new JTextField();
		txtFraseSecreta.setBounds(12, 85, 402, 25);
		formConsulta.add(txtFraseSecreta);
		
		JLabel lbCamihoPasta = new JLabel("Caminho da pasta: ");
		lbCamihoPasta.setBounds(12, 110, 402, 15);
		formConsulta.add(lbCamihoPasta);
		
		final JTextField txtCaminhoPasta = new JTextField();
		txtCaminhoPasta.setBounds(12, 127, 341, 25);
		txtCaminhoPasta.setEnabled(false);
		formConsulta.add(txtCaminhoPasta);
		
		JButton btnChoosePasta = new JButton("->");
		btnChoosePasta.setBounds(365, 127, 49, 25);
		btnChoosePasta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = chooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = chooser.getSelectedFile();
				    txtCaminhoPasta.setText( selectedFile.getAbsolutePath());
				}
				
			}
		});
		formConsulta.add(btnChoosePasta);
				
		final TableModel modeloTable = new TableModel();
	    final JTable table           = new JTable(modeloTable);
		table.setBounds(12, 164, 402, 108);
	    
		JButton btnListar = new JButton("Listar");
		btnListar.setBounds(12, 284, 147, 25);
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				FileCript fCript = new FileCript(txtFraseSecreta.getText(),
												txtKeyPriv.getText(),
												txtCaminhoPasta.getText());
				fCript.setPubKey(usuario);
				if (fCript.checarAutenticidadeIntegridade()) {
					
					try {
						
						fCript.decriptaArquivo();
						List<FileUnitCript> fileUList = fCript.MontaGrid();
						for (int i = 0; i < fileUList.size(); i++) {
							modeloTable.addValue(i, fileUList.get(i));
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}else{
					
				}
				
			}
		});
		formConsulta.add(btnListar);
		formConsulta.add(table);
		
		JButton btnMenu = new JButton("Menu principal");
		btnMenu.setBounds(260, 284, 154, 25);
		btnMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menu = new MenuPrincipal(usuario, frame);
				return;
			}
		});
		allPane.setLayout(null);
		formConsulta.add(btnMenu);
		
		allPane.add(formConsulta);
				
	}
	
	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}
}
