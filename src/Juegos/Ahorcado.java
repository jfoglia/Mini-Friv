/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos;

/**
 *
 * @author Juli
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import Interfaces.Game;

public class Ahorcado extends JFrame implements ActionListener, Game
{
    @Override
    public String getNombre()
    {
        return "Ahorcado";
    }
    
    @Override
    public void iniciarJuego()
    {
        this.setBounds(0,0,800,652);
        this.setVisible(true);
        this.setTitle("Ahorcado");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setBackground(Color.darkGray);
        this.setLocationRelativeTo(null);
    }
    
//    public static void main(String[] args) {
//        Ahorcado ah = new Ahorcado();
//        ah.setBounds(0,0,800,652);
//        ah.setVisible(true);
//        ah.setTitle("Ahorcado");
//        ah.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        ah.getContentPane().setBackground(Color.darkGray);
//    }
    public ImageIcon imm[];
    public String mensaje[] = new String[50];
    public int rang;
    public int error;
    public String restau[];
    public JButton boton[] = new JButton[27];
    public JButton boton1, boton2;
    public JTextPane texto;
    
    public Ahorcado()
    {
        setLayout(null);
        imm = new ImageIcon[6];
        
        Font font1 = new Font("Century Gothic", Font.BOLD,20);
        boton1 = new JButton();
        boton1.setBounds(600,148,131,132);
        add(boton1);
        
        texto = new JTextPane();
        texto.setBounds(250, 50, 300, 60);
        this.texto.setOpaque(true);
        texto.setBackground(Color.WHITE);
        texto.setFont(font1);
        this.add(texto);
        
        boton2 = new JButton("Reiniciar");
        boton2.setBounds(260, 210, 250, 70);
        add(boton2);
        boton2.setFont(font1);
        boton2.setBackground(Color.GRAY);
        boton2.addActionListener(this);
        
        
  
        int x = 150;
        int y = 330;

        for (int i = 0; i < 26; i++) 
        {
            char letra = (char) ('A' + i);
            boton[i + 1] = new JButton(String.valueOf(letra));
            if(i == 8 || i == 16 || i == 24)
            {
                y += 60;
                if(i == 8 || i == 16)
                {
                    x = 150; 
                }
                if(i == 24)
                {
                    x = 330;
                }
            }
            boton[i + 1].setBounds(x, y, 50, 50);
            add(boton[i + 1]);
            boton[i + 1].setBackground(Color.WHITE);
            x += 60;
        }
        
        mensaje[0] = "PROGRAMACION";
        mensaje[1] = "VARIABLE";
        mensaje[2] = "COMPILADOR";
        mensaje[3] = "CLASE";
        mensaje[4] = "OBJETO";
        mensaje[5] = "HERENCIA";
        mensaje[6] = "POLIMORFISMO";
        mensaje[7] = "JAVA";
        mensaje[8] = "PYTHON";
        mensaje[9] = "FUNCION";
        mensaje[10] = "METODO";
        mensaje[11] = "ATRIBUTO";
        mensaje[12] = "INTERFAZ";
        mensaje[13] = "BUCLE";
        mensaje[14] = "CONDICIONAL";
        mensaje[15] = "PAQUETE";
        mensaje[16] = "IMPORTAR";
        mensaje[17] = "MODULARIDAD";
        mensaje[18] = "ALGORITMO";
        mensaje[19] = "CODIGO";
        mensaje[20] = "DEBUG";
        mensaje[21] = "COMPILAR";
        mensaje[22] = "EJECUTAR";
        mensaje[23] = "EXCEPCION";
        mensaje[24] = "INSTANCIA";
        mensaje[25] = "SOLOMILLOS";
        mensaje[26] = "ENTERO";
        mensaje[27] = "BOOLEANO";
        mensaje[28] = "CADENA";
        mensaje[29] = "ARREGLO";
        mensaje[30] = "LISTA";
        mensaje[31] = "VECTOR";
        mensaje[32] = "CLONAR";
        mensaje[33] = "DINAMICO";
        mensaje[34] = "RECURSIVIDAD";
        mensaje[35] = "COMPOSICION";
        mensaje[36] = "LLAMADA";
        mensaje[37] = "BELLAKEO";
        mensaje[38] = "INGENIEBRIO";
        mensaje[39] = "CONSOLA";
        mensaje[40] = "GRAFICO";
        mensaje[41] = "EVENTO";
        mensaje[42] = "VENTANA";
        mensaje[43] = "BOTON";
        mensaje[44] = "ESCANER";
        mensaje[45] = "TECLADO";
        mensaje[46] = "PANTALLA";
        mensaje[47] = "SOBRECARGA";
        mensaje[48] = "SOBRESCRITURA";
        mensaje[49] = "COMPONENTE";

        
        for(int i = 1; i<27; i++)
        {
            boton[i].setEnabled(false);
        }
        
        imm[0] = new ImageIcon(getClass().getResource("/resources/imagenesAhorcado/Screenshot 2025-07-03 163735.png"));
        imm[1] = new ImageIcon(getClass().getResource("/resources/imagenesAhorcado/Screenshot 2025-07-03 163741.png"));
        imm[2] = new ImageIcon(getClass().getResource("/resources/imagenesAhorcado/Screenshot 2025-07-03 163748.png"));
        imm[3] = new ImageIcon(getClass().getResource("/resources/imagenesAhorcado/Screenshot 2025-07-03 163754.png"));
        imm[4] = new ImageIcon(getClass().getResource("/resources/imagenesAhorcado/Screenshot 2025-07-03 163758.png"));
        imm[5] = new ImageIcon(getClass().getResource("/resources/imagenesAhorcado/Screenshot 2025-07-03 163804.png"));

        
        boton1.setIcon(imm[0]);
        cargar();
        iniciar();
    }
    
    public void iniciar()
    {
        boton1.setIcon(imm[0]);
        error = 0;
        texto.setText("");
        
        for(int i = 1; i < 27 ; i++)
        {
            boton[i].setEnabled(true);
        }
        
        rang = (int) 0 + (int)(Math.random() * ((mensaje.length - 1)+1));
        String pal[] = mensaje[rang].split(" ");
        restau = new String[mensaje[rang].length() + 1];
        int j = 0;
        
        for(String pall : pal)
        {
            for(int i = 0; i < pall.length(); i++)
            {
                texto.setText(texto.getText() + "_ ");
                restau[j++] = "_";
            }
            texto.setText(texto.getText() + "\n");
            restau[j++] = " ";
        }
        
    }
    
    public void checkLetters(ActionEvent e)
    {
        JButton bt  = (JButton) e.getSource();
        char c[];
        for(int i = 1; i < 27; i++)
        {
            if(bt == boton[i])
            {
                c =  Character.toChars(64 + i);
                boolean aqui = false;
                for(int j = 0; j < mensaje[rang].length(); j++)
                {
                    if(c[0] == mensaje[rang].charAt(j))
                    {
                        restau[j] = c[0] + "";
                        aqui = true;
                    }
                }
                if(aqui)
                {
                    texto.setText("");
                    for(String re: restau)
                    {
                        if(" ".equals(re))
                        {
                            texto.setText(texto.getText() + "\n");
                        }
                        else
                        {
                            texto.setText(texto.getText()+ re + " ");
                        }
                    }
                    boolean usted_gano =  true;
                    for(String re: restau)
                    {
                        if(re.equals("_"))
                        {
                            usted_gano = false;
                            break;
                        }
                    }
                    if(usted_gano)
                    {
                        JOptionPane.showMessageDialog(this, "Has ganado!! Felicidades");
                        iniciar();
                        return;
                    }
                }
                else
                {
                    boton1.setIcon(imm[++error]);
                    if(error == 5)
                    {
                        JOptionPane.showMessageDialog(this, "Fallaste, la palabra era: \n" + mensaje[rang]);
                        iniciar();
                        boton1.setIcon(imm[0]);
                        return;
                    }
                }
                bt.setEnabled(true);
                break;
            }
        }
    }
    
    public void cargar()
    {
        for(int i = 1; i < 27; i++)
        {
            boton[i].addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    checkLetters(e);
                }
            });
        }
    }
@Override   
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == boton2)
        {
            
            iniciar();
        }
    }
}
