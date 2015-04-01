import java.security.Provider;
import java.security.Security;

  //
  // lista os providers registrados no Java Runtime
  //
  public class ListProvidersExample
  {
      public static void main(String[]  args)
      {
       Provider[] providers = Security.getProviders();

       for (int i = 0; i != providers.length; i++)
       {
        System.out.println("Name: " + providers[i].getName()  
               + "      Version: " + providers[i].getVersion());
       }
      }
  }
