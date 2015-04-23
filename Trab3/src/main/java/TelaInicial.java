package main.java;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import main.business.Grupo;
import main.business.Usuario;
import java.awt.FlowLayout;
import javax.swing.JLabel;

public class TelaInicial extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private Usuario usuario;
		
	public TelaInicial(Usuario fusuario, JFrame fframe){
						
		usuario = fusuario;
		frame   = fframe;
		
	}
	
	public void montaTela() {
		
		frame.setBounds(100,100,650,650);
		frame.getContentPane().removeAll();		
		
		montaCabecalho();
		montaCorpo1();
		montaCorpo2();
				
		rebuildFrame();
		
	}

	private void montaCabecalho() {
		JPanel cabecalho = new JPanel();
		cabecalho.setBounds(100, 100, 650, 200);
		cabecalho.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		JLabel lbLogin = new JLabel("Login: " + usuario.getLogin_name());
		cabecalho.add(lbLogin);
		
		Grupo grUsuario = Grupo.buscar(usuario.getUser_group_fk());
		
		JLabel lbGrupo = new JLabel("Grupo: " + grUsuario.getGrupo_name());
		cabecalho.add(lbGrupo);
		
		frame.getContentPane().add(cabecalho);
	}
	
	private void montaCorpo1() {
		JPanel corpo1 = new JPanel();
		corpo1.setBounds(100, 100, 650, 100);
		corpo1.setLayout(new FlowLayout(FlowLayout.LEFT, 200, 0));
		
		JLabel lbTotalAcessos = new JLabel("Total de acessos do usuário: ");
		corpo1.add(lbTotalAcessos);
		
		frame.getContentPane().add(corpo1);
	}

	private void montaCorpo2() {
		JPanel corpo2 = new JPanel();
		corpo2.setBounds(100, 100, 650, 350);
		corpo2.setLayout(new FlowLayout(FlowLayout.LEFT, 300, 0));
		
		JLabel lbMenu = new JLabel("Menu principal: ");
		corpo2.add(lbMenu);
		JButton btnNovoUsuario = new JButton("1 - Cadastrar um novo usuário");
		corpo2.add(btnNovoUsuario);
		JButton btnConsultaPasta = new JButton("2 - Consulta pasta de arquivos secretos do usuário");
		corpo2.add(btnConsultaPasta);
		JButton btnSair = new JButton("3 - Sair do sistema");
		corpo2.add(btnSair);
				
		frame.getContentPane().add(corpo2);
	}
	
	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}

}
