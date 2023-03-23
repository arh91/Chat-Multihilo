import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class VentanaServidor extends JFrame
{
    JScrollPane scroll_Servidor;
    JTextArea textArea_Conversacion;
    JTextField textField_Servidor;
    JButton botonEnviar;


    //Constructor que crea la ventana del Servidor con todos sus componentes
    public VentanaServidor(Container contenedor)
    {
        contenedor.setLayout(new BorderLayout());
        textArea_Conversacion = new JTextArea();
        scroll_Servidor = new JScrollPane(textArea_Conversacion);

        JPanel panel = new JPanel(new FlowLayout());
        textField_Servidor = new JTextField(50);
        botonEnviar = new JButton("Enviar");
        panel.add(textField_Servidor);
        panel.add(botonEnviar);

        contenedor.add(scroll_Servidor, BorderLayout.CENTER);
        contenedor.add(panel, BorderLayout.SOUTH);

    }

    /*Añade el ActionListener que se le pasa tanto al pulsar Intro en el textField
     como al pulsar el botón Enviar*/
    public void addActionListener(ActionListener accion)
    {
        textField_Servidor.addActionListener(accion);
        botonEnviar.addActionListener(accion);
    }

    public void añadirTextoCliente(String texto) //Añade las cadenas del Cliente a la conversación
    {
        String espacio= "                ";
        String id = "CLIENTE: ";
        String textoTabulado = espacio+id+texto+"\n"+"\n";
        textArea_Conversacion.append(textoTabulado);
    }

    public void añadirTextoServidor(String texto)  //Añade las cadenas del Servidor a la conversación
    {
        String id = "YO: ";
        String textoTabulado = id+texto+"\n"+"\n";
        textArea_Conversacion.append(textoTabulado);
    }

    //Método que devuelve el texto escrito por el Servidor en el textField y lo borra
    public String getTexto()
    {
        String texto = textField_Servidor.getText();
        textField_Servidor.setText("");
        return texto;
    }

}
