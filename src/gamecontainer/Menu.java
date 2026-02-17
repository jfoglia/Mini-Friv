/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gamecontainer;

/**
 *
 * @author Juli
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.IOException;
import Juegos.Ahorcado;
import Juegos.TicTacToe;
import Juegos.spaceInvaders.SpaceInvaders;
import Juegos.Ajedrez.gui.Table;
import Juegos.Snake.Snake;
import Juegos.pacMan.PacMan;
import Juegos.Pong.Pong;
import java.awt.event.ActionEvent;
import Interfaces.Game;
import Juegos.BB.BrickBreaker;
import Juegos.minessweeper.Minesweeper;
import java.util.LinkedList;
//import java.awt.GradientPaint;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Color;

public class Menu 
{
    private static JFrame frame = new JFrame();
    private static JPanel panel = new ColorFondo();
    private static JLabel label = new JLabel("Mini Friv");
    private static Dimension d = new Dimension(700,700);    
    private static final Font FUENTE;
    static {
    try {
        InputStream is = Menu.class.getResourceAsStream("/resources/fuentes/ka1.ttf");
        if (is == null) {
            throw new IOException("Fuente no encontrada en el classpath");
        }

        FUENTE = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(50f);
    } catch (FontFormatException | IOException e) {
        throw new RuntimeException("Error al cargar la fuente", e);
    }
    }

    
//    private static final String[] rutas =
//    {
//        "/resources/ahorcadoImg.png",
//        "/resources/triquiImg.png",
//        "/resources/snakeImg.png",
//        "/resources/pacmanImg.png",
//        "/resources/ajedrezImg.png",
//        "/resources/spaceInvadersImg.png"
//        
//    };
    private static final ImageIcon[] imagenes =
    {
        new ImageIcon(Menu.class.getResource("/resources/ahorcadoImg.png")),
        new ImageIcon(Menu.class.getResource("/resources/triquiImg.png")),
        new ImageIcon(Menu.class.getResource("/resources/snakeImg.png")),
        new ImageIcon(Menu.class.getResource("/resources/pacmanImg.png")),
        new ImageIcon(Menu.class.getResource("/resources/ajedrezImg.png")),
        new ImageIcon(Menu.class.getResource("/resources/spaceInvadersImg.png")),  
        new ImageIcon(Menu.class.getResource("/resources/pong.png")),
        new ImageIcon(Menu.class.getResource("/resources/BrickBreaker.png")),
        new ImageIcon(Menu.class.getResource("/resources/buscaminas.png"))
    };
    
    private static final Table ajedrez = new Table();
    private static final Ahorcado ah = new Ahorcado();
    private static final TicTacToe tt = new TicTacToe();
    private static final Snake sg = new Snake();
    private static final PacMan pacMan = new PacMan();
    private static final SpaceInvaders sI = new SpaceInvaders();
    private static final Pong pong = new  Pong();
    private static final BrickBreaker bb = new BrickBreaker();
    private static final Minesweeper bM = new Minesweeper();
    
    private static final LinkedList<Game> LJuegos = new LinkedList();
    private static final LinkedList<JButton> botones = new LinkedList<>();
    
    
    
    private static void juegos()
    {
        LJuegos.add(ah);
        LJuegos.add(tt);
        LJuegos.add(sg);
        LJuegos.add(pacMan);
        LJuegos.add(ajedrez);
        LJuegos.add(sI);
        LJuegos.add(pong);
        LJuegos.add(bb);
        LJuegos.add(bM);
    }

    
    public static void pantalla()
    {
        label.setSize(300,50);
        label.setFont(FUENTE);
        label.setForeground(Color.green);
        label.setLocation(200,50);
        
        panel.setSize(d);
        panel.setLayout(null);
        panel.setBackground(new Color(87,35,100));
        panel.add(label);
        
        frame.setSize(d);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        
        frame.setVisible(true);
    }
    
    public static class ColorFondo extends JPanel
    {
        @Override
        protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D) g;
        
        GradientPaint gradiente = new GradientPaint(
            0, 0, new Color(87, 35, 100),
            0, getHeight(), Color.BLACK
        );
        
        g2d.setPaint(gradiente);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
    }
    
    
    private static void botones(JPanel panel) 
    {
        

        int columnas = 3; // cantidad de columnas
        int filas = 2; // cantidad de filas
        int ancho = 100; //ancho del botón
        int alto = 100; //alto del botón
        int espacioX = 60; //espacio vertical entre cada botón
        int espacioY = 60; //espacio horizontal entre cada botón
        int offsetX = 140; // espacio al borde de la izquierda
        int offsetY = 150; //espacio al borde de arriba

        panel.setLayout(null);

        for (int i = 0; i < imagenes.length; i++)
        {
            // Escala la imagen al tamaño del botón (100x100)
            Image originalImage = imagenes[i].getImage();
            Image scaledImage = originalImage.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            JButton boton = new JButton(scaledIcon);


            botones.add(boton);

            int fila = i / columnas; // Índice de la fila 0,0,0,1,1,1
            int columna = i % columnas; // Índice de la columna 0,1,2,0,1,2
            
            int x = offsetX + columna * (ancho + espacioX);
            int y = offsetY + fila * (alto + espacioY);

            boton.setOpaque(false);
            boton.setBounds(x, y, ancho, alto);
            panel.add(boton);
        }
        acciones();
    }
    
    private static void acciones()
    {
        for (int i = 0; i < botones.size(); i++) {
            Game juego = LJuegos.get(i);
            JButton boton = botones.get(i);

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    juego.iniciarJuego();
                }
            });
        }
    }


    
    
    public static void main(String[] args) { 
        juegos();
        botones(panel);
        pantalla();
    }  
}
