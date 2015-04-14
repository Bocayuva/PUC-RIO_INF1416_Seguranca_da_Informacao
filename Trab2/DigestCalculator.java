/*
* INF1416 - Segurança da Informação
* GRUPO 1
* Autores - 1320614 - Alexandre Werneck
*           1111060 - Carlos Fernando Bocayuva
*
*/
import java.io.*;
import java.security.*;
import java.util.*;

class DigestCalculator {

    /* Lista de arquivos com seus respectivos digests em MD5 ou SHA1 */
    public List<ArquivoDigest> lista_arquivos;

    /* Lista de digests gerados via linha de comando */
    public List<ListCommandDigest> digest_command;

    public static void main(String[] args) {

      if (args.length < 3) {
            System.out.println("Erro na passagem de argumentos para o método, verifique e tente novamente!");
            System.exit(1);
        }else{

          String tipo_digest = args[0];
          if (!tipo_digest.equals("MD5") && !tipo_digest.equals("SHA1")) {
            System.out.println("Tipo de digest informado inválido!" + tipo_digest);
            System.exit(1);
          }

          /* Caminho do arquivo com a lista de digest salvo */
          String caminho_lista = args[args.length - 1];
          if (!caminho_lista.equals("ListDigests")) {
            System.out.println("Arquivo com a lista de digests conhecido não informado, ListDigests!");
            System.exit(1);
          }

          DigestCalculator digCalc    = new DigestCalculator();
          digCalc.lista_arquivos      = new ArrayList<ArquivoDigest>();          

          /* 
          * Le o arquivo e armazena na lista de digest do arquivo
          * cada objeto do tipo ArquivoDigest
          */
          try{
            FileInputStream file = new FileInputStream(caminho_lista);
            BufferedReader br    = new BufferedReader(new InputStreamReader(file));
            String strLine;
            while ((strLine = br.readLine()) != null)   {
                ArquivoDigest arq_dig = ArquivoDigest.getInstance(strLine);
                digCalc.lista_arquivos.add(arq_dig);
            }
            br.close();
          }catch(Exception e){
            System.out.println("Erro na leitura do arquivo com a lista de digest : " + e.toString());
          }     

          /* 
          * Le cada parametro do tipo caminho de arquivo,
          * cria para cada um objeto do tipo ListCommandDigest,
          * e armazena na lista do tipo ListCommandDigest
          */
          try{
            digCalc.digest_command      = new ArrayList<ListCommandDigest>();
            for(int i=1; i<args.length -1; i++){
                File f = new File(args[i]);

                if (!f.isFile()) {
                  System.out.println("Arquivo fornecido não existe : " + args[i]);
                  System.exit(1);
                }

                byte[] dig                 = digCalc.geraDigest(tipo_digest, f);
                String result              = digCalc.getDigest(dig);
                String f_name_file         = digCalc.getFileName(args[i]);

                

                if (digCalc.verificaDuplicidadeLinhaComando(f_name_file
                                                            , digCalc.digest_command)) {
            
                    System.out.println("O seguinte arquivo foi fornecido em duplicidade via linha de comando : " + f_name_file);
                    System.exit(1);

                }else{

                    ListCommandDigest list_dig = ListCommandDigest.getInstance(
                                                                f_name_file + " " +
                                                                result + " " + tipo_digest
                                                                  );
                    digCalc.digest_command.add(list_dig);

                }
                

            }
          }catch(Exception e){
            System.out.println("Erro no acumulo de digest via linha de comando : " + e.toString());
          }          

          /* Verificação de cada arquivo informado na linha de comando */
          for(int i=1; i < args.length -1; i++){
              String file_name       = digCalc.getFileName(args[i]);
              ArquivoDigest f_digest = digCalc.recuperaArquivoDigest(file_name, digCalc.lista_arquivos);

              if (f_digest != null) {
                /* STATUS podem ser: OK / NOT OK / COLISION / NOT FOUND */
                if (f_digest.getTipoDigest().equals(tipo_digest) || f_digest.getTipoDigest2().equals(tipo_digest)) {

                    if (f_digest.getDigest().equals(digCalc.digest_command.get(i-1).getDigest())) {
                    
                      if(digCalc.checaColisaoOk(f_digest
                                        ,digCalc.digest_command)){

                        String result = file_name + " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                        System.out.println("=>" + result + " COLISION");

                      }else{
                          String result = file_name + " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                          System.out.println("=>" + result + " OK");
                      }
                      

                    }else if (f_digest.getDigest2().equals(digCalc.digest_command.get(i-1).getDigest())) {
                    
                      if(digCalc.checaColisaoOk(f_digest
                                        ,digCalc.digest_command)){

                        String result = file_name + " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                        System.out.println("=>" + result + " COLISION");

                      }else{
                          String result = file_name + " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                          System.out.println("=>" + result + " OK");
                      }

                    }else{

                      if(digCalc.checaColisaoOk(f_digest
                                        ,digCalc.digest_command)){

                        String result = file_name + " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                        System.out.println("=>" + result + " COLISION");

                      }else{
                          String result = file_name + " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                          System.out.println("=>" + result + " NOT OK");
                      }


                    }
                    

                }else{

                  String to_file = " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                  String result = file_name + to_file;

                  if(digCalc.checaColisaoNotFound(digCalc.digest_command.get(i-1)
                                        ,digCalc.digest_command)){

                     System.out.println("=>" + result + " COLISION");

                  }else{
                      System.out.println("=>" + result + " NOT FOUND");
                      digCalc.adicionaDentroArquivo(caminho_lista, digCalc.digest_command.get(i-1).getNome(), to_file);
                  }
                  

                }
                
              }else{
                /* STATUS pode ser: NOT FOUND */  
                
                String result = file_name + " " + tipo_digest + " " + digCalc.digest_command.get(i-1).getDigest();
                
                if(digCalc.checaColisaoNotFound(digCalc.digest_command.get(i-1)
                                        ,digCalc.digest_command)){
                   
                   System.out.println("=>" + result + " COLISION");

                }else{

                    if(digCalc.checaColisaoNotFoundFile(digCalc.digest_command.get(i-1)
                                        ,digCalc.lista_arquivos)){
                   
                       System.out.println("=>" + result + " COLISION");

                    }else{

                        System.out.println("=>" + result + " NOT FOUND");
                        digCalc.adicionaFinalArquivo(caminho_lista, result);

                    }

                }
                
              }
          }

        }

    }

    /*
    * @GeraDigest
    * @params
    *         tipo - tipo de digest (SHA1 / MD5)
    *         file - arquivo para geração do digest
    * @return byte[] - array de bytes com o digest gerado
    *
    */  
    private byte[] geraDigest(String tipo, File file){

      int file_len = (int) file.length();
      int offset   = 0;
      int len      = 1024;
      int len_read = 0;

      byte[] bFile            = new byte[file_len];
      MessageDigest md        = null; 
      try{
        InputStream inpStream   = new FileInputStream(file);
        md                      = MessageDigest.getInstance(tipo);

        while (offset < file_len && len_read >= 0) {
          if ((offset+len) > file_len) {
            len = file_len - offset;
          }
          len_read = inpStream.read(bFile, offset, len);
          md.update(bFile, offset, len);
          offset   += len_read;       
        }        
        
        inpStream.close();
      }catch(Exception e){
        System.out.println("Erro gerando digest: " + e.toString());
      }   
      return md.digest();
      
    }

    /*
    * @getDigest
    * @params
    *         byte[] - digest gerado
    * @return digest - Digest em HEX
    *
    */  
    private String getDigest(byte[] digest){
        StringBuffer buf = new StringBuffer();
        for(int i = 0; i < digest.length; i++) {
           String hex = Integer.toHexString(0x0100 + (digest[i] & 0x00FF)).substring(1);
           buf.append((hex.length() < 2 ? "0" : "") + hex);
        }
        return buf.toString();
    }

    /*
    * @getFileName
    * @params
    *         f_name - String do nome do arquivo 
                       fornecido via linha de comando
                       Ex: arquivos/file1
    * @return name   - String com o nome do arquivo
                       Ex: file1
    *
    */  
    private String getFileName(String f_name){

      String[] full_name = f_name.split("/");
      String name = full_name[full_name.length-1];
      return name;
    }

    /*
    * @recuperaArquivoDigest
    * @params
    *         file_name - String do nome do arquivo 
              lista     - lista com os digests do arquivo digests
    * @return ArqDigest   - objeto do tipo Arquivo digest
    *
    */ 
    private ArquivoDigest recuperaArquivoDigest(String file_name, List<ArquivoDigest> lista){

      for(int i = 0; i < lista.size(); i++) {
           ArquivoDigest dig = lista.get(i);
           if (file_name.equals(dig.getNome())) {
              return dig;
           }
        }
        return null;

    }
    
    /*
    * @adicionaFinalArquivo
    * @params
    *         filename  - String do nome do arquivo 
              nem_line  - String com a linha a ser inserida ao final do arquivo
    * @return 
    *
    */ 
    private void adicionaFinalArquivo(String filename, String new_line){
      try{
        FileWriter fw = new FileWriter(filename,true);
        fw.write(new_line + "\n");
        fw.close();
      }catch(Exception e){
        System.out.println("Erro ao adicionar digest com status NOT FOUND! ");
      }      

    }

    /*
    * @adicionaDentroArquivo
    * @params
    *         file      - String do nome do arquivo 
              filename  - String da linha do arquivo para adicionar 
                          Ex: input => file1 MD5 aaa
                              output => file1 MD5 aaa SHA1 bbb 
              add_line  - String para ser acrescida a filename
                          Ex: SHA1 bbb
    * @return 
    *
    */ 
    private void adicionaDentroArquivo(String file, String filename, String add_line){

      File inputFile = null;
      File tempFile  = null;
      try{
        inputFile = new File(file);
        tempFile  = new File("tmpFile");
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String[] lineToRemove = filename.split(" ");
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String[] trimmedLine = currentLine.trim().split(" ");
            if(trimmedLine[0].equals(lineToRemove[0])){
              writer.write(currentLine + add_line + System.getProperty("line.separator"));
            }else{
              writer.write(currentLine + System.getProperty("line.separator"));  
            }          
        }
        writer.close(); 
        reader.close(); 

      }catch(Exception e){

      }
      
      boolean successful = tempFile.renameTo(inputFile);

    }

    /*
    * @checaColisaoOk
    * @params
    *         digest      - Digest presente no arquivo
              list_dig    - Lista dos digests da linha de comando
    * @return 
              true/false  - true = caso existe colisao entre digest no arquivo
                            e na lista de comando
    *
    */ 
    private boolean checaColisaoOk( ArquivoDigest digest, List<ListCommandDigest> list_dig){
        for(int i = 0; i < list_dig.size(); i++) {
            ListCommandDigest item_dig = list_dig.get(i); 

            if (!digest.getNome().equals(item_dig.getNome())) {  
                  
               if (digest.getDigest().equals(item_dig.getDigest())) {
                  return true;
               }
               if (digest.getDigest2().equals(item_dig.getDigest())) {
                  return true;
               } 

            }
          
        }
        return false;
    }

    /*
    * @checaColisaoNotFound
    * @params
    *         digest      - Digest da linha de comando
              list_dig    - Lista dos digests da linha de comando
    * @return 
              true/false  - true = caso existe colisao entre digest na linha de comando
                            e na lista de comando
    *
    */ 
    private boolean checaColisaoNotFound( ListCommandDigest digest, List<ListCommandDigest> list_dig){
        for(int i = 0; i < list_dig.size(); i++) {
            ListCommandDigest item_dig = list_dig.get(i); 

            if (!digest.getNome().equals(item_dig.getNome())) {  
                  
               if (digest.getDigest().equals(item_dig.getDigest())) {
                  return true;
               }

            }
          
        }
        return false;
    }

    /*
    * @checaColisaoNotFoundFile
    * @params
    *         digest      - Digest da linha de comando
              list_dig    - Lista dos digests do arquivo
    * @return 
              true/false  - true = caso existe colisao entre digest na linha de comando
                            e na lista de arquivo
    *
    */ 
    private boolean checaColisaoNotFoundFile( ListCommandDigest digest, List<ArquivoDigest> list_dig){
        for(int i = 0; i < list_dig.size(); i++) {
            ArquivoDigest item_dig = list_dig.get(i); 

            if (!digest.getNome().equals(item_dig.getNome())) {  
                  
               if (digest.getDigest().equals(item_dig.getDigest())) {
                  return true;
               }
               if (digest.getDigest().equals(item_dig.getDigest2())) {
                  return true;
               }

            }
          
        }
        return false;
    }

    /*
    * @checaColisaoNotFoundFile
    * @params
    *         filename      - String do nome do arquivo
              list_dig    - Lista dos digests da linha de comando
    * @return 
              true/false  - true = caso exista arquivo igual fornecido na linha de comando
    *
    */ 
    private boolean verificaDuplicidadeLinhaComando(String filename, List<ListCommandDigest> list_dig){

        for(int i = 0; i < list_dig.size(); i++) {
            ListCommandDigest item_dig = list_dig.get(i); 

            if (filename.equals(item_dig.getNome())) {  
                  
               return true;

            }
          
        }
        return false;

    }


}