package main.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;

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
		
		return false;
	}
	
}
