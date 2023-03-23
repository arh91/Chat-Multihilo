import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* Servidor de chat.
   Acepta conexiones de clientes, crea un hilo para atenderlos, y espera la siguiente conexion.*/

public class ServidorChat
{

    VentanaServidor ventanaServidor;
    int id = 1;

    public static void main(String[] args) {
        new ServidorChat();
    }

    //El constructor se mete en un bucle infinito para atender clientes, lanzando un hilo para cada uno de ellos
    public ServidorChat()
    {
        try
        {
            ServerSocket socketServidor = new ServerSocket(6000);

            while (true)
            {
                Socket clienteConectado = null;
                try {
                    clienteConectado = socketServidor.accept(); //El servidor acepta la conexión con el Cliente y ésta se hace efectiva
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                HiloAtiendeCliente atencionCliente = new HiloAtiendeCliente(id++, clienteConectado);
                Thread hilo = new Thread(atencionCliente);
                hilo.start();  //Por cada cliente que se conecte al servidor éste lanzará un hilo nuevo (ej: si se conectan 3 clientes el servidor lanzará 3 hilos)
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

