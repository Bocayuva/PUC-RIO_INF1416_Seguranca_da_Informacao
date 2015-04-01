import java.applet.Applet;
import java.awt.Graphics;
import java.security.Provider;
import java.security.Security;

public class ListProvidersAppletExample extends Applet {
  public void paint(Graphics g) {

       Provider[] providers = Security.getProviders();

       for (int i = 0; i != providers.length; i++)
       {
        g.drawString("Name: " + providers[i].getName()  
               + "      Version: " + providers[i].getVersion(),
               50, 25 * (i+1));
       }
  }
}
