package main.java;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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

	public CadastrarUsuario(Usuario fusuario, JFrame fframe) {
		
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
		formCadastro.setBounds(12, 135, 426, 300);
		formCadastro.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		formCadastro.setLayout(null);
		
		final JLabel lbMsgErro = new JLabel("-");
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
		txtUserName.setBounds(12, 55, 402, 25);
		formCadastro.add(txtUserName);
		
		JLabel lbLoginName = new JLabel("Login name: ");
		lbLoginName.setBounds(12, 81, 232, 16);
		formCadastro.add(lbLoginName);
		
		final JTextField txtLoginName = new JTextField();
		txtLoginName.setBounds(12, 101, 194, 25);
		formCadastro.add(txtLoginName);
		
		JLabel lbGrupo = new JLabel("Grupo: ");
		lbGrupo.setBounds(224, 81, 232, 16);
		formCadastro.add(lbGrupo);
		
		List<Grupo> grItens          = Grupo.buscarTodos();
		final JComboBox<String> comboGrupo = new JComboBox<String>();
		for (int i = 0; i < grItens.size(); i++) {
			comboGrupo.insertItemAt(grItens.get(i).getGrupo_name(), i);
		}
		comboGrupo.setSelectedIndex(0);
		comboGrupo.setBounds(224, 101, 194, 25);
		formCadastro.add(comboGrupo);
		
		JLabel lbSenhaPessoal = new JLabel("Senha pessoal:");
		lbSenhaPessoal.setBounds(12, 127, 194, 16);
		formCadastro.add(lbSenhaPessoal);
		
		final JPasswordField pwdSenhaPessoal = new JPasswordField();
		pwdSenhaPessoal.setBounds(12, 148, 194, 25);
		formCadastro.add(pwdSenhaPessoal);
		
		JLabel lbConfirmaSenha = new JLabel("Confirme a senha:");
		lbConfirmaSenha.setBounds(224, 127, 190, 16);
		formCadastro.add(lbConfirmaSenha);
		
		final JPasswordField pwdConfirmaSenha = new JPasswordField();
		pwdConfirmaSenha.setBounds(224, 148, 194, 25);
		formCadastro.add(pwdConfirmaSenha);
	
		
		JLabel lbUrl = new JLabel("Caminho:");
		lbUrl.setBounds(12, 174, 194, 15);
		formCadastro.add(lbUrl);
		
		final JTextField txtUrl = new JTextField();
		txtUrl.setBounds(12, 191, 194, 25);
		formCadastro.add(txtUrl);
		
		JLabel lbTan = new JLabel("Tamanho da lista:");
		lbTan.setBounds(224, 174, 194, 15);
		formCadastro.add(lbTan);
		
		final JTextField txtTan = new JTextField();
		txtTan.setBounds(224, 191, 194, 25);
		formCadastro.add(txtTan);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setBounds(12, 263, 147, 25);
		btnCadastrar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if (txtUserName.getText().equals("")) {
					lbMsgErro.setText("Forneça o nome do usuário!");
					return;
				}
				if (txtLoginName.getText().equals("")) {
					lbMsgErro.setText("Forneça o login!");
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
				byte[] pub = FileCript.getBytesFromFile(txtUrl.getText());
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
					FileCript.criptoPrivateKey("Keys/userpriv");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Usuario.incluir(usuario);	
				TanList tanItem = new TanList();
				tanItem.criarItens(usuario.getLogin_name(), usuario.getUser_tan_list());
				montaFormCadastro();
			}
		});
		formCadastro.add(btnCadastrar);
		
		JButton btnMenu = new JButton("Menu principal");
		btnMenu.setBounds(264, 263, 154, 25);
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
