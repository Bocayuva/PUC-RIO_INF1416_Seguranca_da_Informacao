package main.java;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import main.business.Grupo;
import main.business.TanList;
import main.business.Usuario;
import main.dao.GrupoDao;
import main.helper.FileCript;
import main.helper.Utility;

public class CadastrarUsuario{

	private Usuario usuario;
	private JFrame frame;
	private JPanel allPane;	
	private JLabel lbTotalAcessos;

	public CadastrarUsuario(Usuario fusuario, JFrame fframe) {
		
		usuario = fusuario;
		frame   = fframe;
		
		frame.setBounds(100,100,600, 510);
		frame.getContentPane().removeAll();		
		
		allPane = new JPanel();
		frame.setContentPane(allPane);
		
		montaCabecalho();
		montaCorpo1();
		montaFormCadastro();
				
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
				
		lbTotalAcessos = new JLabel();
		lbTotalAcessos.setText("Total de usuários do sistema: " + Utility.qtdeUsuariosSistema());
		lbTotalAcessos.setBounds(12, 12, 546, 15);
		corpo1.add(lbTotalAcessos);
		
		allPane.add(corpo1);
		
	}


	public void montaFormCadastro() {
		
		JPanel formCadastro = new JPanel();
		formCadastro.setBackground(new Color(255, 255, 224));
		formCadastro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		formCadastro.setBounds(12, 159, 569, 308);
		formCadastro.setLayout(null);
		
		final JLabel lbMsgErro = new JLabel("");
		lbMsgErro.setForeground(Color.RED);
		lbMsgErro.setBounds(12, 226, 406, 25);
		formCadastro.add(lbMsgErro);
		
		JLabel lbMenu = new JLabel("Formulário de cadastro");
		lbMenu.setBounds(12, 12, 402, 15);
		formCadastro.add(lbMenu);
		
		JLabel lbUserName = new JLabel("Nome do usuário: ");
		lbUserName.setBounds(12, 39, 232, 15);
		formCadastro.add(lbUserName);
		
		final JTextField txtUserName = new JTextField();
		txtUserName.setBounds(12, 55, 545, 25);
		
		formCadastro.add(txtUserName);
		
		JLabel lbLoginName = new JLabel("Login name: ");
		lbLoginName.setBounds(12, 81, 232, 16);
		formCadastro.add(lbLoginName);
		
		final JTextField txtLoginName = new JTextField();
		txtLoginName.setBounds(12, 101, 194, 25);
		formCadastro.add(txtLoginName);
		
		JLabel lbGrupo = new JLabel("Grupo de usuário:  ");
		lbGrupo.setBounds(224, 81, 232, 16);
		formCadastro.add(lbGrupo);
		
		List<Grupo> grItens          = Grupo.buscarTodos();
		final JComboBox<String> comboGrupo = new JComboBox<String>();
		for (int i = 0; i < grItens.size(); i++) {
			comboGrupo.insertItemAt(grItens.get(i).getGrupo_name(), i);
		}
		comboGrupo.setSelectedIndex(0);
		comboGrupo.setBounds(224, 101, 333, 25);
		formCadastro.add(comboGrupo);
		
		JLabel lbSenhaPessoal = new JLabel("Senha pessoal:");
		lbSenhaPessoal.setBounds(12, 127, 194, 16);
		formCadastro.add(lbSenhaPessoal);
		
		final JPasswordField pwdSenhaPessoal = new JPasswordField();
		pwdSenhaPessoal.setBounds(12, 148, 232, 25);
		formCadastro.add(pwdSenhaPessoal);
		
		JLabel lbConfirmaSenha = new JLabel("Confirme a senha:");
		lbConfirmaSenha.setBounds(325, 127, 190, 16);
		formCadastro.add(lbConfirmaSenha);
		
		final JPasswordField pwdConfirmaSenha = new JPasswordField();
		pwdConfirmaSenha.setBounds(325, 148, 232, 25);
		formCadastro.add(pwdConfirmaSenha);
	
		
		JLabel lbUrl = new JLabel("Caminho da chave pública:");
		lbUrl.setBounds(12, 174, 350, 15);
		formCadastro.add(lbUrl);
		
		final JTextField txtUrl = new JTextField();
		txtUrl.setBounds(12, 191, 350, 25);
		txtUrl.setEnabled(false);
		formCadastro.add(txtUrl);
		
		JButton btnChooseFile = new JButton("->");
		btnChooseFile.setBounds(369, 189, 49, 25);
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = chooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = chooser.getSelectedFile();
				    txtUrl.setText( selectedFile.getAbsolutePath());
				}
				
			}
		});
		formCadastro.add(btnChooseFile);
		
		JLabel lbTan = new JLabel("Tamanho da lista:");
		lbTan.setBounds(424, 174, 133, 15);
		formCadastro.add(lbTan);
		
		final JTextField txtTan = new JTextField();
		txtTan.setBounds(424, 191, 133, 25);
		formCadastro.add(txtTan);
				
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(12, 263, 147, 25);
		btnCadastrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (txtUserName.getText().equals("")) {
					lbMsgErro.setText("Forneça o nome do usuário!");
					return;
				}
				if (txtUserName.getText().length() > 50) {
					lbMsgErro.setText("Nome do usuário não deve ser maior que 50 caracteres");
					return;
				}
				if (txtLoginName.getText().equals("")) {
					lbMsgErro.setText("Forneça o login!");
					return;
				}
				if (txtLoginName.getText().length() > 20) {
					lbMsgErro.setText("Login name não deve ser maior que 20 caracteres");
					return;
				}
				Usuario novo_usuario = Usuario.buscarPorLogin(txtLoginName.getText());
				if (novo_usuario.getId() != 0) {
					lbMsgErro.setText("Login existente!");
					return;
				}	
				Usuario usuario = new Usuario();
				usuario.setLogin_name(txtLoginName.getText());
				usuario.setUser_name(txtUserName.getText());
				
				if(pwdSenhaPessoal.getPassword().length > 10 || pwdSenhaPessoal.getPassword().length < 8){
					lbMsgErro.setText("Senha pessoal inválida!");					
					pwdConfirmaSenha.setText("");
					pwdSenhaPessoal.setText("");
					return;
				}
				String senha_pessoal  = new String(pwdSenhaPessoal.getPassword());
				String senha_confirma = new String(pwdConfirmaSenha.getPassword());
				if (!senha_pessoal.equals(senha_confirma)) {
					lbMsgErro.setText("Senha de confirmação não válida!");
					pwdConfirmaSenha.setText("");
					pwdSenhaPessoal.setText("");
					return;
				}
				if (Utility.VerificaPadraoInvalido(pwdSenhaPessoal.getPassword())) {
					lbMsgErro.setText("Senha pessoal inválida! Ex: 11 ou 12 são senhas inválidas");
					pwdConfirmaSenha.setText("");
					pwdSenhaPessoal.setText("");
					return;
				}				
				if (txtTan.getText().equals("")) {
					lbMsgErro.setText("Informe o número de códigos a serem gerados!");
					return;
				}
				int tanNumItems = Integer.parseInt(txtTan.getText());
				if (tanNumItems < 1 || tanNumItems > 99) {
					lbMsgErro.setText("Quantidade de one-time password inválida!");
					return;
				}
				if (txtUrl.getText().equals("")) {
					lbMsgErro.setText("Informe o caminho da chave pública");
					return;
				}
				byte[] pub = Utility.getBytesFromFile(txtUrl.getText());
				if (pub == null) {
					lbMsgErro.setText("Caminho da chave pública inválido!");
					return;
				}
				String salt = Utility.geraRandomString();
				usuario.setSalt(salt);
				usuario.setUser_pwd(Utility.geraSenha(senha_pessoal + usuario.getSalt()));
				usuario.setUser_group_fk(Grupo.buscarPorNome(comboGrupo.getSelectedItem().toString()));
				usuario.setDisabled(false);
				usuario.setUser_tan_list(Integer.parseInt(txtTan.getText()));
				usuario.setUser_url_pub(pub);
				
				try {
					Usuario.incluir(usuario);
				} catch (Exception e) {
					lbMsgErro.setText("Erro ao incluir usuário no sistema, verifique os dados informados e tente novamente!");
				}
				
				try {
					TanList tanItem = new TanList();
					tanItem.criarItens(usuario.getLogin_name(), usuario.getUser_tan_list());
					limpaForm();
				} catch (Exception e) {
					lbMsgErro.setText("Erro ao criar tan list do usuário!");
				}
									
				
			}

			private void limpaForm() {
				
				lbMsgErro.setText("");
				txtUserName.setText("");
				txtLoginName.setText("");
				comboGrupo.setSelectedIndex(0);
				pwdSenhaPessoal.setText("");
				pwdConfirmaSenha.setText("");
				txtUrl.setText("");
				txtTan.setText("");
				lbTotalAcessos.setText("Total de usuários do sistema: " + Utility.qtdeUsuariosSistema());
				
			}
		});
		formCadastro.add(btnCadastrar);
		
		JButton btnMenu = new JButton("Menu principal");
		btnMenu.setBounds(403, 263, 154, 25);
		btnMenu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menu = new MenuPrincipal(usuario, frame);
				return;
			}
		});
		formCadastro.add(btnMenu);
		
		allPane.setLayout(null);
		allPane.add(formCadastro);
		
		
	}
	
	private void rebuildFrame(){
		frame.revalidate();
		frame.repaint();
	}
}
