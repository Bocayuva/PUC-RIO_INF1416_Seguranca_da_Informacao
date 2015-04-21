package main.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import main.business.Usuario;
import main.helper.Utility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home {

	private JFrame frame;
	private Usuario usuario;

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
		primeiraetapa();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void primeiraetapa() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblListViewPrograma = new JLabel("List View Programa");
		panel.add(lblListViewPrograma);
		
		final JLabel lbMsgErro = new JLabel("-");
		panel.add(lbMsgErro);
		
		final JTextField txtLoginName = new JTextField();
		panel.add(txtLoginName);
		txtLoginName.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String lgName = txtLoginName.getText();
				if (lgName.length() > 20) {
					lbMsgErro.setText("Login name inválido!");
				}else{
					usuario = Usuario.buscar(lgName);
					if(usuario == null){
						/* Usuario não localizado */
						lbMsgErro.setText("Usuário não localizado!");
					}else{
						
						if(usuario.isDisabled()){
							/* Usuario está bloqueado */
							lbMsgErro.setText("Usuário está bloqueado!");
						}else{
							lbMsgErro.setText("Usuário encontrado!");
							segundaetapa();
						}					
						
					}
					
				}
			}
		});
		panel.add(btnLogin);		
	}
	
	private void segundaetapa(){
		frame.getContentPane().removeAll();
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblListViewPrograma = new JLabel("List View Programa");
		panel.add(lblListViewPrograma);
		
		final JLabel lbMsgErro = new JLabel("-");
		panel.add(lbMsgErro);
		
		final JPasswordField txtPassWd = new JPasswordField();
		txtPassWd.setVisible(true);
		txtPassWd.setEnabled(false);
		txtPassWd.setColumns(10);
		panel.add(txtPassWd);
		
		final JTextField passWd_Hidden = new JTextField();
		passWd_Hidden.setVisible(false);
		panel.add(passWd_Hidden);
		
		final JButton[] botoes = Utility.geraBotoes();
		botoes[0].addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] params = botoes[0].getText().split(" ");
				String PassWd = new String(txtPassWd.getPassword());
				if (PassWd.length() < 10) {
					txtPassWd.setText(PassWd + ".");
					passWd_Hidden.setText(passWd_Hidden.getText() + params[0] + '_' + params[1]);
				}
			}
		});
		panel.add(botoes[0]);
		
		botoes[1].addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] params = botoes[1].getText().split(" ");
				String PassWd = new String(txtPassWd.getPassword());
				if (PassWd.length() < 10) {
					txtPassWd.setText(PassWd + ".");
					passWd_Hidden.setText(passWd_Hidden.getText() + params[0] + '_' + params[1]);
				}
			}
		});
		panel.add(botoes[1]);
		
		botoes[2].addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] params = botoes[2].getText().split(" ");
				String PassWd = new String(txtPassWd.getPassword());
				if (PassWd.length() < 10) {
					txtPassWd.setText(PassWd + ".");
					passWd_Hidden.setText(passWd_Hidden.getText() + params[0] + '_' + params[1]);
				}
			}
		});
		panel.add(botoes[2]);
		
		botoes[3].addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] params = botoes[3].getText().split(" ");
				String PassWd = new String(txtPassWd.getPassword());
				if (PassWd.length() < 10) {
					txtPassWd.setText(PassWd + ".");
					passWd_Hidden.setText(passWd_Hidden.getText() + params[0] + '_' + params[1]);
				}
			}
		});
		panel.add(botoes[3]);
		
		botoes[4].addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] params = botoes[4].getText().split(" ");
				String PassWd = new String(txtPassWd.getPassword());
				if (PassWd.length() < 10) {
					txtPassWd.setText(PassWd + ".");
					passWd_Hidden.setText(passWd_Hidden.getText() + params[0] + '_' + params[1]);
				}
			}
		});
		panel.add(botoes[4]);
		
		JButton btnConfirmar = new JButton();
		btnConfirmar.setText("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int salt = usuario.getSalt();
				boolean isTrue = Utility.verificaSenhaTeclado(salt, passWd_Hidden.getText(), usuario.getUser_pwd());
				if (isTrue) {
					lbMsgErro.setText("ok, senha valida");
				}
			}
		});
		panel.add(btnConfirmar);
		
		JButton btnLimpar = new JButton();
		btnLimpar.setText("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				txtPassWd.setText("");
			}
		});
		panel.add(btnLimpar);
		
	}

}
