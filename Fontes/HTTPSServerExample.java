import java.io.*;
import java.net.*;
import javax.net.ssl.*;
//
// Servidor HTTPS para ilustrar a utilizacao do SSLServerSocketFactory
public class HTTPSServerExample {

  public static void main(String[] args) throws IOException {

    //
    // cria um SSL socket usando a factory e alocando a porta 8080
    SSLServerSocketFactory sslsf =
      (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
    ServerSocket ss = sslsf.createServerSocket(8080);
    //
    // loop infinito
    while (true) {
      try {
        //
        // bloqeia aguardando a conexao do cliente
        Socket s = ss.accept();
        System.out.println( "Client connection made" );
        // recebe a equisicao do cliente
        BufferedReader in = new BufferedReader(
          new InputStreamReader(s.getInputStream()));
        System.out.println(in.readLine());
        //
        // cria uma resposta no formato HTML
        PrintWriter out = new PrintWriter( s.getOutputStream() );
        out.println("<HTML><HEAD><TITLE>HTTPS Server Example</TITLE>" +
                    "</HEAD><BODY><H1>Hello World!</H1></BODY></HTML>\n");
        //
        // fecha o stream e o socket
        out.close();
        s.close();
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
  }
}
