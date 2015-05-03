package main.java;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import main.business.Registros;
import main.business.Usuario;

public class SairSistema{

	private Usuario usuario;
	private JFrame frame;
	private JPanel allPane;
	
	public SairSistema(Usuario fusuario, JFrame fframe) {
		
		usuario = fusuario;
		frame   = fframe;
		
		frame.setBounds(100,100,600, 510);
		frame.getContentPane().removeAll();		
		
		Registros.adicionarRegistro(new int[]{8001} , new Usuario[]{usuario}, new String[]{null});
		
		allPane = new JPanel();
		frame.setContentPane(allPane);
		
		montaCabecalho();
		montaCorpo1();
		montaFormCadastro();
				
		rebuildFrame();
		
	}
	
	private void montaCorpo1() {
		
		allPane.setLayout(null);
		JPanel corpo1 = new JPanel();
		corpo1.setBackground(new Color(255, 255, 224));
		corpo1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		corpo1.setLocation(12, 113);
		corpo1.setSize(569, 34);
		corpo1.setLayout(null);
		
		JLabel lbTotalAcessos = new JLabel("Total de acessos do usuário: " + usuario.getNum_acessos());
		lbTotalAcessos.setBounds(12, 12, 402, 15);
		corpo1.add(lbTotalAcessos);
		
		allPane.add(corpo1);
		
	}

	private void montaCabecalho() {
		JPanel cabecalho = Cabecalho.MontaCabecalho(usuario);
		allPane.add(cabecalho);				
	}

	public void montaFormCadastro() {
		
		JPanel formCadastro = new JPanel();		
		formCadastro.setBackground(new Color(255, 255, 224));
		formCadastro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		formCadastro.setBounds(12, 159, 569, 308);
		formCadastro.setLayout(null);
		
		JLabel lbSaida = new JLabel("Saída do sistema: ");
		lbSaida.setBounds(12, 12, 545, 15);
		formCadastro.add(lbSaida);
		
		JLabel lbMsgSaida = new JLabel("Mensagem de saída: ");
		lbMsgSaida.setBounds(12, 50, 402, 15);
		
		JButton btnCadastrar = new JButton("Sair");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Registros.adicionarRegistro(new int[]{8002,1002} , new Usuario[]{usuario,null}, new String[]{null,null});	
				System.exit(0);
			}
		});
		btnCadastrar.setBounds(12, 271, 147, 25);
		formCadastro.add(btnCadastrar);
		
		JButton btnMenu = new JButton("Menu principal");
		btnMenu.setBounds(403, 271, 154, 25);
		btnMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Registros.adicionarRegistro(new int[]{8003} , new Usuario[]{usuario}, new String[]{null});
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
