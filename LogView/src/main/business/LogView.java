package main.business;

import java.util.ArrayList;
import java.util.List;

import main.dao.LogViewDao;

public class LogView {
	
	private int num_controle;
	private String mensagem;
	private String login_name;
	private String arq_name;
	private String msg_full;
	private int id_msg;

	public int getNum_controle() {
		return num_controle;
	}

	public void setNum_controle(int num_controle) {
		this.num_controle = num_controle;
	}
	
	public static List<LogView> buscarTodos() {
		LogViewDao lgDao = new LogViewDao();
		List<LogView> lgItens = lgDao.buscarTodos();
		return lgItens;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public static List<LogView> buscarPorControle(int num_controle2) {
		LogViewDao lgDao = new LogViewDao();
		List<LogView> lgItens = lgDao.buscarPorControle(num_controle2);
		return lgItens;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getArq_name() {
		return arq_name;
	}

	public void setArq_name(String arq_name) {
		this.arq_name = arq_name;
	}

	public String getMsg_full() {
		return msg_full;
	}

	public void setMsg_full() {
		String msg_original = this.getMensagem();
		if (this.getLogin_name() != null) {
			msg_original = msg_original.replace("<login_name>", this.getLogin_name());
		}
		if (this.getArq_name() != null) {
			msg_original = msg_original.replace("<arq_name>", this.getArq_name());
		}
		this.msg_full = msg_original;
	}

	public int getId_msg() {
		return id_msg;
	}

	public void setId_msg(int id_msg) {
		this.id_msg = id_msg;
	}
	
	
	public List<LogView> MontaGrid(List<String> fileList){
		
		
		List<LogView> fileUList = new ArrayList<LogView>();
		for (int i=0; i < fileList.size(); i++) {
			if(fileList.get(i).length()>1) {
				try {
					LogView fileU = new LogView();
					fileU.setArq_name("teste");
					fileU.setId_msg(i);
					fileUList.add(fileU);
						
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return fileUList;
	}
	
	

}