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
import java.awt.Color;

public class Login {

	protected JFrame frame;
	protected Usuario usuario;

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
		frame.getContentPane().setLocation(0, 0);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("List View Program - Gerenciador de arquivos");
		
		primeiraetapa();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void primeiraetapa() {
		frame.getContentPane().removeAll();
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
				
		JLabel lbUsuarioInfo = new JLabel("Informe o nome do usuário: ");
		lbUsuarioInfo.setBounds(25, 10, 407, 15);
		panel.add(lbUsuarioInfo);
		
		final JLabel lbMsgErro = new JLabel(" ");
		lbMsgErro.setForeground(Color.RED);
		lbMsgErro.setBounds(25, 111, 407, 15);
		panel.add(lbMsgErro);
		
		final JTextField txtLoginName = new JTextField();
		txtLoginName.setBounds(25, 37, 407, 25);
		panel.add(txtLoginName);
		txtLoginName.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(359, 74, 73, 25);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lbMsgErro.setText("");
				String lgName = txtLoginName.getText();
				if (lgName.equals("")){
					lbMsgErro.setText("Informe o usuário!");
					return;
				}
				if (lgName.length() > 20) {
					lbMsgErro.setText("Login name inválido!");
					return;
				}
				usuario = Usuario.buscarPorLogin(lgName);
				if(usuario == null){
					lbMsgErro.setText("Usuário não localizado!");
					return;
				}
				if(usuario.isDisabled()){
					lbMsgErro.setText("Usuário está bloqueado!");
					return;
				}
				Calendar calendar = Calendar.getInstance();
				Timestamp data_atual = new Timestamp(calendar.getTime().getTime());
				
				if(usuario.getBlocked_at().after(data_atual)){
					lbMsgErro.setText("Usuário está bloqueado!");
				}else{
					lbMsgErro.setText("Usuário encontrado!");
					segundaetapa();
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
		panel.setLayout(null);
		
		JLabel lbMsgInfo = new JLabel("Informe sua senha pessoal");
		lbMsgInfo.setBounds(12, 12, 401, 15);
		panel.add(lbMsgInfo);
		
		final JLabel lbMsgErro = new JLabel("-");
		lbMsgErro.setForeground(Color.RED);
		lbMsgErro.setBounds(12, 134, 420, 15);
		panel.add(lbMsgErro);
		
		final JPasswordField txtPassWd = new JPasswordField();
		txtPassWd.setBounds(12, 31, 401, 25);
		txtPassWd.setVisible(true);
		txtPassWd.setEnabled(false);
		txtPassWd.setColumns(10);
		panel.add(txtPassWd);
		
		final JTextField passWd_Hidden = new JTextField();
		passWd_Hidden.setBounds(303, 103, 129, 19);
		passWd_Hidden.setVisible(false);
		panel.add(passWd_Hidden);
		
		final JTextField Erros_segunda_etapa = new JTextField();
		Erros_segunda_etapa.setBounds(420, 37, 12, 19);
		Erros_segunda_etapa.setVisible(false);
		panel.add(Erros_segunda_etapa);
		Erros_segunda_etapa.setText("0");
		
		final JButton[] botoes = Utility.geraBotoes();
				
		for (int i = 0; i < botoes.length; i++) {
			final int operador = i;
			botoes[operador].setBounds(12 + (80 * i), 60, 80, 25);
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
		btnLimpar.setBounds(127, 97, 82, 25);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtPassWd.setText("");
				passWd_Hidden.setText("");
			}
			
		});
		panel.add(btnLimpar);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(12, 97, 103, 25);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String salt = usuario.getSalt();
				if (passWd_Hidden.getText().equals("")) {
					lbMsgErro.setText("forneça a senha!");
					return;
				}
				
				boolean isTrue = Utility.verificaSenhaTeclado(salt, passWd_Hidden.getText(), usuario.getUser_pwd());
				if (isTrue) {
					terceiraetapa();
					return;
				}
				
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
					
			
		});
		panel.add(btnConfirmar);
		
		rebuildFrame();
	}
	
	private void terceiraetapa() {

		frame.getContentPane().removeAll();		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		final JLabel lbMsgErro = new JLabel("-");
		lbMsgErro.setForeground(Color.RED);
		lbMsgErro.setBounds(12, 100, 420, 15);
		panel.add(lbMsgErro);
		
		final JTextField Erros_terceira_etapa = new JTextField();
		Erros_terceira_etapa.setBounds(329, 64, 103, 19);
		Erros_terceira_etapa.setVisible(false);
		panel.add(Erros_terceira_etapa);
		Erros_terceira_etapa.setText("0");
		
		
		final List<TanList> tan_itens    = Utility.getTanItem(usuario);
		if (tan_itens.size() <= 0) {
			lbMsgErro.setText("atualize seu cadastro! faltam tan itens");
			return;
		}
		
		Random rand = new Random();
		final int posicao = rand.nextInt(tan_itens.size());
		final String tanItemADigitar = tan_itens.get(posicao).getTanItem(); 
		
		final JLabel lbTanList = new JLabel("Forneça o código na posição: " + tan_itens.get(posicao).getOrder_user() + " ");
		lbTanList.setBounds(12, 12, 407, 15);
		panel.add(lbTanList);
		
		final JTextField txtTanList = new JTextField();
		txtTanList.setBounds(12, 32, 420, 25);
		panel.add(txtTanList);
		txtTanList.setColumns(10);
		
		JButton btnTanList = new JButton("Confirmar");
		btnTanList.setBounds(12, 64, 103, 25);
		btnTanList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (txtTanList.getText().length() <= 0) {
					lbMsgErro.setText("Forneça o código de validação!");
					return;
				}
				String valor_digitado = Utility.geraSenha(txtTanList.getText() + usuario.getSalt());
				if (valor_digitado.equals(tanItemADigitar)) {						
					TanList.apagarRegistro(tan_itens.get(posicao));	
					usuario.setNum_acessos(usuario.getNum_acessos() + 1);
					Usuario.update(usuario);
					MenuPrincipal menu = new MenuPrincipal(usuario, frame);
					return;
				}
				int num_erros = Integer.parseInt(Erros_terceira_etapa.getText()) + 1;
				Erros_terceira_etapa.setText(num_erros + "");
				
				if(num_erros >= 3){
					Utility.bloquearUsuario(usuario);
					primeiraetapa();		
				}else{
					txtTanList.setText("");
					lbMsgErro.setText("O código de validação não esta válido!");
				}
			}
		});
		panel.add(btnTanList);
		
		rebuildFrame();
	}
		
	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}
	

}