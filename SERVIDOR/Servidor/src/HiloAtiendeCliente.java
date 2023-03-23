import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.*;

// Hilo encargado de atender a un cliente que se acaba de conectar
public class HiloAtiendeCliente extends Thread implements ActionListener
{
    DefaultListModel conversacion = new DefaultListModel(); //Lista en la que se guarda toda la charla con el cliente
    Socket clienteConectado; //Socket al que está conectado el cliente
    DataInputStream cadenaRecibida; // Se encarga de lectura de datos recibidos del cliente
    DataOutputStream cadenaEnviada; //Se encarga de escritura de datos enviados al cliente
    VentanaServidor ventanaServidor;
    int id;


    //El constructor HiloCliente lanza la interfaz del servidor encargada de atender a cada hilo cliente
    public HiloAtiendeCliente(int id, Socket socket)
    {
        this.id=id;
        this.clienteConectado = socket;
        try
        {
            mostrarVentanaServidor(id);
            cadenaRecibida = new DataInputStream(socket.getInputStream());
            cadenaEnviada = new DataOutputStream(socket.getOutputStream());

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

     /*Entra en bucle while y se mantiene a la escucha de las cadenas que lleguen del hilo cliente,
    estas cadenas las añade a la interfaz del servidor.*/
    public void run()
    {
        try
        {
            while (true)
            {
                String textoCliente = cadenaRecibida.readUTF();
                if(textoCliente.equals("F")){
                    clienteConectado.close();
                    break;
                }
                synchronized (conversacion)
                {
                    ventanaServidor.añadirTextoCliente(textoCliente);
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*Método que muestra la ventana del Servidor que se comunica con el cliente.
    Recibe de parámetro un id para saber con qué cliente se está comunicando*/
    private void mostrarVentanaServidor(int id)
    {
        JFrame window = new JFrame("Servidor atiende a Cliente "+id);
        ventanaServidor = new VentanaServidor(window.getContentPane());
        ventanaServidor.addActionListener(this);
        window.pack();
        window.setVisible(true);

        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //Método que se ejecuta cuando el servidor hace click en botón enviar o en Enter
    public void actionPerformed(ActionEvent evento)
    {
        try
        {
            String cadenaServidor = ventanaServidor.getTexto();
            ventanaServidor.añadirTextoServidor(cadenaServidor);
            cadenaEnviada.writeUTF(cadenaServidor);
        } catch (Exception excepcion)
        {
            excepcion.printStackTrace();
        }
    }
}
