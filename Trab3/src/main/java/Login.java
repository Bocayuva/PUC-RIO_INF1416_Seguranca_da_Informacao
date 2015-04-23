package main.java;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;

import main.business.TanList;
import main.business.Usuario;
import main.helper.Utility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Login {

	private JFrame frame;
	private Usuario usuario;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
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
	public Login() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		primeiraetapa();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void primeiraetapa() {
		frame.getContentPane().removeAll();
		
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
					usuario = Usuario.buscarPorLogin(lgName);
					if(usuario == null){
						/* Usuario não localizado */
						lbMsgErro.setText("Usuário não localizado!");
					}else{						
						if(usuario.isDisabled()){
							lbMsgErro.setText("Usuário está bloqueado!");
						}else{
							Calendar calendar = Calendar.getInstance();
							Timestamp data_atual = new Timestamp(calendar.getTime().getTime());
							
							if(usuario.getBlocked_at().after(data_atual)){
								lbMsgErro.setText("Usuário está bloqueado!");
							}else{
								lbMsgErro.setText("Usuário encontrado!");
								segundaetapa();
							}
							
						}	
					}
					
				}
			}
		});
		panel.add(btnLogin);	
		rebuildFrame();
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
		
		final JTextField Erros_segunda_etapa = new JTextField();
		Erros_segunda_etapa.setVisible(false);
		panel.add(Erros_segunda_etapa);
		Erros_segunda_etapa.setText("0");
		
		final JButton[] botoes = Utility.geraBotoes();
		
		for (int i = 0; i < botoes.length; i++) {
			final int operador = i;
			botoes[operador].addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					String[] params = botoes[operador].getText().split(" ");
					String PassWd = new String(txtPassWd.getPassword());
					if (PassWd.length() < 10) {
						txtPassWd.setText(PassWd + ".");
						passWd_Hidden.setText(passWd_Hidden.getText() + params[0] + '_' + params[2] + '_' );
					}
				}
			});
			panel.add(botoes[operador]);
		}
		
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtPassWd.setText("");
				passWd_Hidden.setText("");
			}
			
		});
		panel.add(btnLimpar);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int salt = usuario.getSalt();
				if (passWd_Hidden.getText().equals("")) {
					lbMsgErro.setText("forneça a senha!");
				}else{	
					boolean isTrue = Utility.verificaSenhaTeclado(salt, passWd_Hidden.getText(), usuario.getUser_pwd());
					if (isTrue) {
						terceiraetapa("");
					}else{
						
						int num_erros = Integer.parseInt(Erros_segunda_etapa.getText()) + 1;
						Erros_segunda_etapa.setText(num_erros + "");
						
						if(num_erros >= 3){
							Utility.bloquearUsuario(usuario);
							primeiraetapa();		
						}else{
							txtPassWd.setText("");
							passWd_Hidden.setText("");
							lbMsgErro.setText("senha não confere!");
						}						
					}
				}
			}
					
			
		});
		panel.add(btnConfirmar);
		
		rebuildFrame();
	}
	
	private void terceiraetapa(String numErros) {

		frame.getContentPane().removeAll();		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblListViewPrograma = new JLabel("List View Programa");
		panel.add(lblListViewPrograma);
		
		final JLabel lbMsgErro = new JLabel("-");
		panel.add(lbMsgErro);
		
		final JTextField Erros_terceira_etapa = new JTextField();
		Erros_terceira_etapa.setVisible(false);
		panel.add(Erros_terceira_etapa);
		if (numErros.equals("")) {
			Erros_terceira_etapa.setText("0");
		}else{
			Erros_terceira_etapa.setText(numErros);
		}
		
		
		final List<TanList> tan_itens    = Utility.getTanItem(usuario);
		
		Random rand = new Random();
		final int posicao = rand.nextInt(tan_itens.size());
		final String tanItemADigitar = tan_itens.get(posicao).getTanItem(); 
		
		if (tan_itens.size() <= 0) {
			lbMsgErro.setText("atualize seu cadastro! faltam tan itens");
		}else{
						
			final JLabel lbTanList = new JLabel("Forneça o código na posição " + tan_itens.get(posicao).getOrder_user() + " ");
			panel.add(lbTanList);
			
			final JTextField txtTanList = new JTextField();
			panel.add(txtTanList);
			txtTanList.setColumns(10);
			
			JButton btnTanList = new JButton("Confirmar");
			btnTanList.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					String valor_digitado = Utility.geraSenha(txtTanList.getText() + usuario.getSalt());
					if (valor_digitado.equals(tanItemADigitar)) {						
						TanList.apagarRegistro(tan_itens.get(posicao));						
						TelaInicial inicio = new TelaInicial(usuario, frame);
						inicio.montaTela();
					}else{
						int num_erros = Integer.parseInt(Erros_terceira_etapa.getText()) + 1;
						Erros_terceira_etapa.setText(num_erros + "");
						
						if(num_erros >= 3){
							Utility.bloquearUsuario(usuario);
							primeiraetapa();		
						}else{
							txtTanList.setText("");
							lbMsgErro.setText("codigo errado!");
						}						
					}
				}
			});
			panel.add(btnTanList);
		}
		
		rebuildFrame();
	}
		
	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}
	

}