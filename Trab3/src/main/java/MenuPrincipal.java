package main.java;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import main.business.Grupo;
import main.business.Registros;
import main.business.Usuario;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class MenuPrincipal{

	/**
	 * 
	 */
	private JFrame frame;
	private Usuario usuario;
	private JPanel allPane;
		
	public MenuPrincipal(Usuario fusuario, JFrame fframe){

		usuario = fusuario;
		frame   = fframe;
		
		frame.setBounds(100,100,600, 510);
		frame.getContentPane().removeAll();		
		
		Registros.adicionarRegistro(new int[]{5001} , new Usuario[]{usuario}, new String[]{null});
		
		allPane = new JPanel();
		frame.setContentPane(allPane);
		
		montaCabecalho();
		montaCorpo1();
		montaCorpo2();
				
		rebuildFrame();

	}
	
	private void montaCabecalho() {
		JPanel cabecalho = Cabecalho.MontaCabecalho(usuario);
		allPane.add(cabecalho);				
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
		lbTotalAcessos.setBounds(12, 12, 546, 15);
		corpo1.add(lbTotalAcessos);
		
		allPane.add(corpo1);
	}

	private void montaCorpo2() {
		JPanel corpo2 = new JPanel();
		corpo2.setBackground(new Color(255, 255, 224));
		corpo2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		corpo2.setBounds(12, 159, 569, 308);
		corpo2.setLayout(null);
		
		JLabel lbMenu = new JLabel("Menu principal: ");
		lbMenu.setBounds(12, 12, 113, 15);
		corpo2.add(lbMenu);
		
		JButton btnNovoUsuario = new JButton("1 - Cadastrar um novo usuário");
		btnNovoUsuario.setBounds(12, 40, 247, 25);
		btnNovoUsuario.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Registros.adicionarRegistro(new int[]{5002} , new Usuario[]{usuario}, new String[]{null});
				CadastrarUsuario cadUsuario = new CadastrarUsuario(usuario, frame);
				cadUsuario.montaFormCadastro();
				return;
			}
		});
		corpo2.add(btnNovoUsuario);
		if (usuario.getUser_group_fk() == 2) {
			btnNovoUsuario.setVisible(false);
		}
		
		JButton btnConsultaPasta = new JButton("2 - Consulta pasta de arquivos secretos do usuário");
		btnConsultaPasta.setBounds(12, 77, 396, 25);
		btnConsultaPasta.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Registros.adicionarRegistro(new int[]{5003} , new Usuario[]{usuario}, new String[]{null});
				usuario.setNum_consultas(usuario.getNum_consultas() + 1);
				Usuario.update(usuario);
				ConsultaPasta conPasta = new ConsultaPasta(usuario, frame);
				conPasta.montaFormConsulta();
				return;
			}
		});
		corpo2.add(btnConsultaPasta);
		
		
		JButton btnSair = new JButton("3 - Sair do sistema");
		btnSair.setBounds(12, 114, 165, 25);
		btnSair.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				Registros.adicionarRegistro(new int[]{5004} , new Usuario[]{usuario}, new String[]{null});
				SairSistema saida = new SairSistema(usuario, frame);
				saida.montaFormCadastro();
				return;
			}
		});
		corpo2.add(btnSair);
				
		allPane.add(corpo2);
	}

	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}

}
