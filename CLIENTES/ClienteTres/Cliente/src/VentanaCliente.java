import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.*;

public class VentanaCliente extends JFrame
{
    JScrollPane scroll_Cliente;
    JTextArea textArea_Conversacion;
    JTextField textField_Cliente;
    JButton botonEnviar;

    //Constructor que crea la ventana del Cliente con todos sus componentes
    public VentanaCliente(Container contenedor)
    {
        contenedor.setLayout(new BorderLayout());
        textArea_Conversacion = new JTextArea();
        scroll_Cliente = new JScrollPane(textArea_Conversacion);

        JPanel panel = new JPanel(new FlowLayout());
        textField_Cliente = new JTextField(50);
        botonEnviar = new JButton("Enviar");
        panel.add(textField_Cliente);
        panel.add(botonEnviar);

        contenedor.add(scroll_Cliente, BorderLayout.CENTER);
        contenedor.add(panel, BorderLayout.SOUTH);
    }

    /* Añade el actionListener que se le pasa tanto al pulsar Intro en el textField
     * como al pulsar el botón Enviar */
    public void addActionListener(ActionListener accion)
    {
        textField_Cliente.addActionListener(accion);
        botonEnviar.addActionListener(accion);

    }

    public void añadirTextoServidor(String texto) //Añade las cadenas del Servidor a la conversación
    {
        String espacio= "                ";
        String id = "SERVIDOR: ";
        String textoTabulado = espacio+id+texto+"\n"+"\n";
        textArea_Conversacion.append(textoTabulado);
    }

    public void añadirTextoCliente(String texto) //Añade las cadenas del Cliente a la conversación
    {
        String id = "YO: ";
        String textoCliente = id+texto+"\n"+"\n";
        textArea_Conversacion.append(textoCliente);
    }

    public String getTexto() //Método que devuelve el texto escrito por el Cliente en el textField y lo borra
    {
        String texto = textField_Cliente.getText();
        textField_Cliente.setText("");
        return texto;
    }
}
