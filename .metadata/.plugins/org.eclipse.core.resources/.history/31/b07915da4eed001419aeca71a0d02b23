package main.helper;

import java.io.PrintWriter;

public class File {

	public static void adicionaFinalArquivo(String filename, String new_line){
      try{
    	  String workingDir = System.getProperty("user.dir");
    	  PrintWriter writer = new PrintWriter(workingDir + "/src/" + filename, "UTF-8");
    	  writer.println(new_line);
    	  writer.close();
      }catch(Exception e){
    	  System.out.println("=> Erro ao gerar a lista de chaves únicas! ");
      }      

    }
	
}
