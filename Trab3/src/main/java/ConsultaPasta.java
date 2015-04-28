package main.java;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import main.business.Usuario;

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
		montaFormCadastro();
				
		rebuildFrame();
		
	}
	
	private void montaCabecalho() {
		JPanel cabecalho = Cabecalho.MontaCabecalho(usuario);
		allPane.add(cabecalho);				
	}

	public void montaFormCadastro() {
		
		JPanel formCadastro = new JPanel();
		formCadastro.setBounds(12, 135, 426, 276);
		formCadastro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		formCadastro.setLayout(null);
		
		JLabel lbMenu = new JLabel("Consulta pasta: ");
		lbMenu.setBounds(12, 12, 402, 15);
		formCadastro.add(lbMenu);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(12, 239, 147, 25);
		formCadastro.add(btnCadastrar);
		
		JButton btnMenu = new JButton("Menu principal");
		btnMenu.setBounds(260, 239, 154, 25);
		btnMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menu = new MenuPrincipal(usuario, frame);
				return;
			}
		});
		allPane.setLayout(null);
		formCadastro.add(btnMenu);
		
		allPane.add(formCadastro);
	}
	
	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}

}