import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/*Hilo que atiende el socket y las peticiones de usuario.
 * Lo que llega por el socket lo muestra en el textArea del panel, lo que
 * escribe el usuario en el panel lo envía por el socket.
 */
public class HiloCliente extends Thread implements ActionListener
{
    DataInputStream cadenaRecibida; //Se encarga de la lectura de datos recibidos del Servidor
    DataOutputStream cadenaEnviada; //Se encarga de la escritura de datos enviados al Servidor
    VentanaCliente ventanaCliente;
    ClienteChat cliente;
    Socket socketCliente;
    int contador = 0;

    /*Contruye una instancia de esta clase, lanzando un hilo para atender al
      socket.*/
    public HiloCliente(Socket socketCliente, VentanaCliente panelCliente)
    {
        this.socketCliente = socketCliente;
        this.ventanaCliente = panelCliente;
        try
        {
            cadenaRecibida = new DataInputStream(socketCliente.getInputStream());
            cadenaEnviada = new DataOutputStream(socketCliente.getOutputStream());
            panelCliente.addActionListener(this);
            Thread hilo = new Thread(this);
            hilo.start();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

     /*Recoge el texto del panel y lo envía por el socket.
     * El panel llamará a este método cuando el cliente escriba algo y
     * pulse el botón de Enviar o pulse "Enter" sobre el textfield.
     */
    public void actionPerformed(ActionEvent evento)
    {
        try
        {
            String cadenaCliente = ventanaCliente.getTexto();
            if(cadenaCliente.equals("F")){  //Si el Cliente teclea la letra F y pulsa Intro o botón Enviar, se cortará la comunicación con el Servidor
                cadenaEnviada.writeUTF(cadenaCliente);
                socketCliente.close();
                JOptionPane.showMessageDialog(null, "La conexión con el servidor se ha cortado.");
            }
            ventanaCliente.añadirTextoCliente(cadenaCliente);
            cadenaEnviada.writeUTF(cadenaCliente);
        } catch (Exception excepcion)
        {
            excepcion.printStackTrace();
        }
    }

    //En este método, el cliente capta las cadenas que le llegan del Servidor y las añade en su propia ventana
    public void run()
    {
        try
        {
            while (true)
            {
                String textoRecibido = cadenaRecibida.readUTF();
                ventanaCliente.añadirTextoServidor(textoRecibido);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
