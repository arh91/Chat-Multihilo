import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ClienteChat
{
    Socket socketCliente;
    VentanaCliente ventanaCliente;

    //Arranca el Cliente deL chat.
    public static void main(String[] args)
    {

        new ClienteChat();
    }

    //El constructor del cliente crea la ventana, establece la conexión e instancia un hilo cliente.
    public ClienteChat()
    {
        try
        {
            mostrarVentanaCliente();
            socketCliente = new Socket("localhost", 6000); //Conexión en local a través del puerto 6000
            HiloCliente hc = new HiloCliente(socketCliente, ventanaCliente);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //Crea la interfaz del cliente y la muestra
    public void mostrarVentanaCliente()
    {
        JFrame window = new JFrame("Cliente 1");
        ventanaCliente = new VentanaCliente(window.getContentPane());
        window.pack();
        window.setVisible(true);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
