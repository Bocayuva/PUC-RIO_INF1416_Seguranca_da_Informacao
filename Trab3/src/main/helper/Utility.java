package main.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;

import main.business.TanList;
import main.business.Usuario;
import main.dao.TanListDao;

public class Utility {

	public static JButton[] geraBotoes(){
		
		JButton[] botoes = new JButton[5];
		ArrayList<Integer> list = geraRandom(0,9);
		
		botoes[0] = new JButton();
		botoes[1] = new JButton();
		botoes[2] = new JButton();
		botoes[3] = new JButton();
		botoes[4] = new JButton();
		
		botoes[0].setText(list.get(0).toString() + " ou " + list.get(1).toString());
		botoes[1].setText(list.get(2).toString() + " ou " + list.get(3).toString());
		botoes[2].setText(list.get(4).toString() + " ou " + list.get(5).toString());
		botoes[3].setText(list.get(6).toString() + " ou " + list.get(7).toString());
		botoes[4].setText(list.get(8).toString() + " ou " + list.get(9).toString());
				
		return botoes;
		
	}
	
	private static ArrayList<Integer> geraRandom(Integer inicio, Integer fim){
		ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=inicio; i<=fim; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
		return list;
	}
	
	public static String geraSenha(String senha){
		
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		byte[] senhaTxt = null;
		try {
			senhaTxt = senha.getBytes("UTF8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
	    messageDigest.update(senhaTxt);
	    byte [] digest = messageDigest.digest();
	    
	    StringBuffer buf = new StringBuffer();
	    for(int i = 0; i < digest.length; i++) {
	       String hex = Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1);
	       buf.append((hex.length() < 2 ? "0" : "") + hex);
	    }
	    
		return buf.toString();
		
	}

	public static boolean verificaSenhaTeclado(int salt, String pass_digited, String user_pwd) {
		pass_digited = pass_digited.substring(0, pass_digited.length() - 1);
		String[] elementos = pass_digited.split("_");
		
		List<String> lista = new ArrayList<String>();
		lista.add(elementos[0]);
		lista.add(elementos[1]);
		
		int n =  1;
		int num_digitos = (elementos.length) / 2;
		int left = 2;
		int right = 4;
		boolean retorno = false;
		while (n < num_digitos) {
			int tam_lista = lista.size();
			for (int i = 0; i < tam_lista; i++) {
				for (int j = left; j < right; j++) {
					lista.add(lista.get(i) + elementos[j]);
					if ( (lista.get(i) + elementos[j]).length() >= num_digitos  ) {
						String senha_dig = geraSenha(lista.get(i) + elementos[j] + salt);
						if (user_pwd.equals(senha_dig)) {
							retorno = true;
							break;
						}	
					}
				}
			}
			left  += 2;
			right += 2;
			n     += 1;
		}
		
		return retorno;
	}

	public static List<TanList> getTanItem(Usuario usuario) {
		List<TanList> tanItens = new ArrayList<TanList>();
		TanListDao tanListDao = new TanListDao();
		tanListDao.buscarItens(tanItens, usuario.getId());	
		return tanItens;
	}
	
	public static void bloquearUsuario(Usuario usuario){
		Calendar calendar = Calendar.getInstance();
		Timestamp data_block = new Timestamp(calendar.getTime().getTime());
		data_block.setTime(data_block.getTime() + 2*60*1000);
		usuario.setBlocked_at(data_block);
		Usuario.update(usuario);
	}
	
}