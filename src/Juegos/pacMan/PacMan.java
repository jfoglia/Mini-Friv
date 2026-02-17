/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.pacMan;

/**
 *
 * @author Juli
 */
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import javax.swing.*;
import Interfaces.Game;
import java.io.File;
import javax.sound.sampled.*;
import java.io.InputStream;
import java.io.IOException;
import javax.sound.sampled.*;


public class PacMan extends JPanel implements ActionListener, KeyListener, Game
{
    @Override
    public String getNombre()
    {
        return "Pacman";
    }
    
    @Override
    public void iniciarJuego()
    {
        
        reproducirIntroEnSegundoPlano();
        int rowCount = 21;
        int columnCount = 19;
        int tileSize2 = 32;
        int boardWidth = columnCount * tileSize2;
        int boardHeight = rowCount * tileSize2;

        
        // frame.setVisible(true);
        JFrame frame = new JFrame("Pac Man");
        
        frame.setSize(boardWidth, boardHeight+50);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        PacMan pacmanGame = new PacMan();
        frame.add(pacmanGame, BorderLayout.CENTER);
        
        JButton botonDeReinicio = new JButton("Reiniciar");
        botonDeReinicio.addActionListener((ActionEvent e) -> {
            pacmanGame.reiniciarJuego();
        });
        frame.add(botonDeReinicio, BorderLayout.SOUTH);
        frame.setVisible(true);
        
        frame.pack();
        pacmanGame.requestFocus();
    }        
    
    private class Bloques
    {
        int x;
        int y;
        int ancho;
        int alto;
        Image imagen;
        
        boolean vulnerable = false;


        int startX;
        int startY;
        char direccion = 'U'; // U D L R
        int velocidadX = 0;
        int velocidadY = 0;
        
        char direccionPendiente = ' ';

        Bloques(Image image, int x, int y, int ancho, int alto)
        {
            this.imagen = image;
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.alto = alto;
            this.startX = x;
            this.startY = y;
        }

        void actualizarDireccion(char direccion) 
        {
            char direccionPrevia = this.direccion;
            this.direccion = direccion;
            actualizarVelocidad();
            this.x += this.velocidadX;
            this.y += this.velocidadY;
            for (Bloques wall : walls)
            {
                if (colision(this, wall))
                {
                    this.x -= this.velocidadX;
                    this.y -= this.velocidadY;
                    this.direccion = direccionPrevia;
                    actualizarVelocidad();
                }
            }
        }

        private void actualizarVelocidad() 
        {
            if (this.direccion == 'U')
            {
                this.velocidadX = 0;
                this.velocidadY = -tileSize/4;
            }
            else if (this.direccion == 'D') 
            {
                this.velocidadX = 0;
                this.velocidadY = tileSize/4;
            }
            else if (this.direccion == 'L') 
            {
                this.velocidadX = -tileSize/4;
                this.velocidadY = 0;
            }
            else if (this.direccion == 'R') 
            {
                this.velocidadX = tileSize/4;
                this.velocidadY = 0;
            }
        }

        private void resetearPos() 
        {
            this.x = this.startX;
            this.y = this.startY;
        }
    }

    private final int contadorDeFilas = 21;
    private final int ContadorDeColumns = 19;
    private int tileSize = 32;
    private final int anchoDelTablero = ContadorDeColumns * tileSize;
    private final int altoDelTablero = contadorDeFilas * tileSize;

    private final Image imagenDelBloque;
    private Image fantasmaAzul;
    private Image fantasmaNaranja;
    private Image fantasmaRosado;
    private Image fantasmaRojo;
    private final Image fantasmaAsustado;

    private final Image pacmanUpImage;
    private final Image pacmanDownImage;
    private final Image pacmanLeftImage;
    private final Image pacmanRightImage;
    private final Image powerFood;
    
    private Timer modoVulnerableTimer;


    //X = wall, O = skip, P = pac man, ' ' = food
    //Ghosts: b = blue, o = orange, p = pink, r = red
    private String[] tileMap = 
    {
        "XXXXXXXXXXXXXXXXXXX",
        "X        X        X",
        "XMXX XXX X XXX XXMX",
        "X                 X",
        "X XX X XXXXX X XX X",
        "X    X       X    X",
        "XXXX XXXX XXXX XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXrXX X XXXX",
        "O       bpo       O",
        "XXXX X XXXXX X XXXX",
        "OOOX X       X XOOO",
        "XXXX X XXXXX X XXXX",
        "X        X        X",
        "X XX XXX X XXX XX X",
        "XM X     P     X MX",
        "XX X X XXXXX X X XX",
        "X    X   X   X    X",
        "X XXXXXX X XXXXXX X",
        "X                 X",
        "XXXXXXXXXXXXXXXXXXX" 
    };

    private HashSet<Bloques> walls;
    private HashSet<Bloques> foods;
    private HashSet<Bloques> fantasmas;
    private HashSet<Bloques> pellets;
    private Bloques pacman;

    private final Timer gameLoop;
    private final char[] direcciones = {'U', 'D', 'L', 'R'}; //up down left right
    private final Random random = new Random();
    private int score = 0;
    private int lives = 3;
    private boolean gameOver = false;
    

    public static void reproducirIntroEnSegundoPlano() 
    {
        new Thread(() -> {
            try {
                InputStream is = PacMan.class.getResourceAsStream("/Juegos/pacMan/audios/start-pacman.wav");
                if (is == null) {
                    System.out.println("Audio no encontrado en el classpath.");
                    return;
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();

                System.out.println("Reproduciendo audio en segundo plano...");

                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        System.out.println("Audio terminado.");
                    }
                });

            } catch (Exception e) {
                System.out.println("Error reproduciendo audio: " + e.getMessage());
            }
        }).start();
    }




    public PacMan() 
    {
        setPreferredSize(new Dimension(anchoDelTablero, altoDelTablero));
        setBackground(Color.BLACK);
        addKeyListener(this);
        setFocusable(true);

        //load images
        imagenDelBloque = new ImageIcon(getClass().getResource("/Juegos/pacMan/imagenes/wall.png")).getImage();
        fantasmaAzul = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/blueGhost.png")).getImage();
        fantasmaNaranja = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/orangeGhost.png")).getImage();
        fantasmaRosado = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/pinkGhost.png")).getImage();
        fantasmaRojo = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/redGhost.png")).getImage();
        fantasmaAsustado = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/scaredGhost.png")).getImage();

        
        powerFood = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/redGhost.png")).getImage();

        pacmanUpImage = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/pacmanUp.png")).getImage();
        pacmanDownImage = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/pacmanDown.png")).getImage();
        pacmanLeftImage = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/pacmanLeft.png")).getImage();
        pacmanRightImage = new ImageIcon(getClass().getResource("/Juegos/PacMan/Imagenes/pacmanRight.png")).getImage();

        cargarMapa();
        for (Bloques fantasma : fantasmas)
        {
            char newDirection = direcciones[random.nextInt(4)];
            fantasma.actualizarDireccion(newDirection);
        }
        //how long it takes to start timer, milliseconds gone between frames
        gameLoop = new Timer(50, this); //20fps (1000/50)
        gameLoop.start();
        
        
        modoVulnerableTimer = new Timer(10000, (ActionEvent evt) -> {
            for (Bloques ghost : fantasmas) 
            {
                ghost.vulnerable = false; 
                // Restaurar imagen original según posición de inicio
                char tipo = tileMap[ghost.startY / tileSize].charAt(ghost.startX / tileSize);
                switch (tipo)
                {
                    case 'b': ghost.imagen = fantasmaAzul; break;
                    case 'o': ghost.imagen = fantasmaNaranja; break;
                    case 'p': ghost.imagen = fantasmaRosado; break;
                    case 'r': ghost.imagen = fantasmaRojo; break;
                }
            }
            modoVulnerableTimer.stop(); // detenerlo después de ejecutarse
        });
        modoVulnerableTimer.setRepeats(false); // para que solo se ejecute una vez

    }

    private void cargarMapa() 
    {
        walls = new HashSet<>();
        foods = new HashSet<>();
        pellets = new HashSet<>();
        fantasmas = new HashSet<>();

        for (int r = 0; r < contadorDeFilas; r++) 
        {
            for (int c = 0; c < ContadorDeColumns; c++) 
            {
                String row = tileMap[r];
                char tileMapChar = row.charAt(c);

                int x = c*tileSize;
                int y = r*tileSize;

                if (tileMapChar == 'X')
                { //block wall
                    Bloques wall = new Bloques(imagenDelBloque, x, y, tileSize, tileSize);
                    walls.add(wall);
                }
                else if (tileMapChar == 'b') 
                { //blue ghost
                    Bloques ghost = new Bloques(fantasmaAzul, x, y, tileSize, tileSize);
                    fantasmas.add(ghost);
                }
                else if (tileMapChar == 'o') 
                { //orange ghost
                    Bloques ghost = new Bloques(fantasmaNaranja, x, y, tileSize, tileSize);
                    fantasmas.add(ghost);
                }
                else if (tileMapChar == 'p')
                { //pink ghost
                    Bloques ghost = new Bloques(fantasmaRosado, x, y, tileSize, tileSize);
                    fantasmas.add(ghost);
                }
                else if (tileMapChar == 'r') 
                { //red ghost
                    Bloques ghost = new Bloques(fantasmaRojo, x, y, tileSize, tileSize);
                    fantasmas.add(ghost);
                }
                else if (tileMapChar == 'P') 
                { //pacman
                    pacman = new Bloques(pacmanRightImage, x, y, tileSize, tileSize);
                }
                else if (tileMapChar == ' ')
                { //food
                    Bloques food = new Bloques(null, x + 14, y + 14, 4, 4);
                    foods.add(food);
                }
                else if(tileMapChar == 'M')
                {
                    Bloques pellet = new Bloques(powerFood, x+14, y+14, 12, 12);
                    pellets.add(pellet);
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }

    private void draw(Graphics g) 
    {
        g.drawImage(pacman.imagen, pacman.x, pacman.y, pacman.ancho, pacman.alto, null);

        for (Bloques ghost : fantasmas) 
        {
            g.drawImage(ghost.imagen, ghost.x, ghost.y, ghost.ancho, ghost.alto, null);
        }

        for (Bloques wall : walls) 
        {
            g.drawImage(wall.imagen, wall.x, wall.y, wall.ancho, wall.alto, null);
        }

        g.setColor(Color.WHITE);
        for (Bloques food : foods)
        {
            g.fillRect(food.x, food.y, food.ancho, food.alto);
        }
        
        for(Bloques pastillas : pellets)
        {
            g.fillRect(pastillas.x, pastillas.y, pastillas.ancho, pastillas.alto);
        }
        //score
        g.setFont(new Font("Arial", Font.PLAIN, 18));
        if (gameOver)
        {
            g.drawString("Game Over: " + String.valueOf(score), tileSize/2, tileSize/2);
        }
        else 
        {
            g.drawString("x" + String.valueOf(lives) + " Score: " + String.valueOf(score), tileSize/2, tileSize/2);
        }
    }

    private void move()
    {
        if(pacman.direccionPendiente != ' ')
        {
            boolean alineacionEnX = pacman.x % tileSize == 0;
            boolean alineacionEnY = pacman.y % tileSize == 0;
            
            boolean movimientoVertical = (pacman.direccionPendiente == 'U' || pacman.direccionPendiente == 'D');
            boolean movimientoHorizontal = (pacman.direccionPendiente == 'L' || pacman.direccionPendiente == 'R');
            
            if((movimientoVertical && alineacionEnX) || (movimientoHorizontal && alineacionEnY))
            {
                if(pacman.x < 32 && pacman.y == 288)
                {
                    pacman.direccionPendiente = pacman.direccion;
                }else if(pacman.x > anchoDelTablero-32 && pacman.y == 288)
                {
                    pacman.direccionPendiente = pacman.direccion;
                }
                pacman.actualizarDireccion(pacman.direccionPendiente);
            }
            
            if(pacman.direccion == pacman.direccionPendiente)
            {
                pacman.direccionPendiente = ' ';
                
                if(pacman.direccion == 'U') pacman.imagen = pacmanUpImage;
                else if(pacman.direccion == 'D') pacman.imagen = pacmanDownImage;
                else if(pacman.direccion == 'L') pacman.imagen = pacmanLeftImage;
                else if(pacman.direccion == 'R') pacman.imagen = pacmanRightImage;
            }
        }
        
        pacman.x += pacman.velocidadX;
        pacman.y += pacman.velocidadY;

        if (pacman.x >= anchoDelTablero) 
        {
            pacman.x = 0;
        }

        if (pacman.x + pacman.ancho <= 0) 
        {
            pacman.x = anchoDelTablero;
        }


        //check wall collisions
        for (Bloques wall : walls) 
        {
            if (colision(pacman, wall))
            {
                pacman.x -= pacman.velocidadX;
                pacman.y -= pacman.velocidadY;
                break;
            }
        }

        //revisa la colision con fantasmas
        for (Bloques fantasma : fantasmas) 
        {
            if (colision(fantasma, pacman)) 
            {
                if(fantasma.vulnerable)
                {
                    score += 200;
                    fantasma.resetearPos();
                    fantasma.vulnerable = false;
                    resetearSoloFantasma(fantasma);                    
                    if(tileMap[fantasma.startY / tileSize].charAt(fantasma.startX/tileSize) == 'b')
                        fantasma.imagen = fantasmaAzul;
                    else if(tileMap[fantasma.startY / tileSize].charAt(fantasma.startX/tileSize) == 'o')
                        fantasma.imagen = fantasmaNaranja;
                    else if(tileMap[fantasma.startY / tileSize].charAt(fantasma.startX/tileSize) == 'p')
                        fantasma.imagen = fantasmaRosado;
                    else if(tileMap[fantasma.startY / tileSize].charAt(fantasma.startX/tileSize) == 'r')
                        fantasma.imagen = fantasmaRojo;
                }
                else if(!fantasma.vulnerable)
                {
                    lives -= 1;
                    if (lives == 0) 
                    {
                        gameOver = true;
                        return;
                    }
                    resetearPacmanYFantasmas();
                }
            }

            if (fantasma.y == tileSize*9 && fantasma.direccion != 'U' && fantasma.direccion != 'D') 
            {
                fantasma.actualizarDireccion('U');
            }
            fantasma.x += fantasma.velocidadX;
            fantasma.y += fantasma.velocidadY;
            for (Bloques wall : walls)
            {
                if (colision(fantasma, wall) || fantasma.x <= 0 || fantasma.x + fantasma.ancho >= anchoDelTablero)
                {
                    fantasma.x -= fantasma.velocidadX;
                    fantasma.y -= fantasma.velocidadY;
                    char newDirection = direcciones[random.nextInt(4)];
                    fantasma.actualizarDireccion(newDirection);
                }
            }
        }

        //revisa la colisión con comida
        Bloques foodEaten = null;
        for (Bloques food : foods)
        {
            if (colision(pacman, food))
            {
                foodEaten = food;
                score += 10;
            }
        }
        foods.remove(foodEaten);

        if (foods.isEmpty())
        {
            cargarMapa();
            resetearpos();
        }
        
        //revisa la colicion con las pastillas
        Bloques powerFoodEaten = null;
        for (Bloques pastillas : pellets)
        {
            if (colision(pacman, pastillas))
            {
                powerFoodEaten = pastillas;
                score += 10;
                
                for(Bloques fantasma : fantasmas)
                {
                    fantasma.vulnerable = true;
                    fantasma.imagen = fantasmaAsustado;
                }

                // reiniciar el temporizador
                if (modoVulnerableTimer.isRunning()) {
                    modoVulnerableTimer.restart();
                } else {
                    modoVulnerableTimer.start();
                }

            }
        }
        pellets.remove(powerFoodEaten);
    }

    
    //revisa las colisiones
    private  boolean colision(Bloques a, Bloques b)
    {
        return  a.x < b.x + b.ancho &&
                a.x + a.ancho > b.x &&
                a.y < b.y + b.alto &&
                a.y + a.alto > b.y;
    }

    //reinicia el mapa y la posicion inicial
    private  void resetearpos() 
    {
        pacman.resetearPos();
        pacman.velocidadX = 0;
        pacman.velocidadY = 0;
        for (Bloques fantasma : fantasmas)
        {
            fantasma.resetearPos();
            char newDirection = direcciones[random.nextInt(4)];
            fantasma.actualizarDireccion(newDirection);
        }
    }
    
    //reinicia al pacman y a los fantasmas
    private void resetearPacmanYFantasmas() 
    {
        pacman.resetearPos();
        pacman.velocidadX = 0;
        pacman.velocidadY = 0;
        for (Bloques fantasma : fantasmas) 
        {
            fantasma.resetearPos();
            char newDirection = direcciones[random.nextInt(4)];
            fantasma.actualizarDireccion(newDirection);
        }   
    }
    
    //resetea la posicion de los fantasmas
    private void resetearSoloFantasma(Bloques fantasma)
    {
        fantasma.resetearPos();
        char newDirection = direcciones[random.nextInt(4)];
        fantasma.actualizarDireccion(newDirection);
    }


    private void reiniciarJuego()
    {
        cargarMapa();
        resetearpos();
        
        score = 0;
        lives = 3;
        gameOver = false;

        if (modoVulnerableTimer != null) {
            modoVulnerableTimer.stop();
        }

        gameLoop.start();
        requestFocus();
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) 
    {
        move();
        repaint();
        if (gameOver) 
        {
            gameLoop.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e)
    {
        if (gameOver)
        {
            cargarMapa();
            resetearpos();
            lives = 3;
            score = 0;
            gameOver = false;
            gameLoop.start();
        }
        // System.out.println("KeyEvent: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            pacman.direccionPendiente = 'U';
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            //pacman.ActualizarDireccion('D');
            pacman.direccionPendiente = 'D';
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
        {
            //pacman.ActualizarDireccion('L');
            pacman.direccionPendiente = 'L';
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
        {
            //pacman.ActualizarDireccion('R');
            pacman.direccionPendiente = 'R';
        }

        if (pacman.direccion == 'U')
        {
            pacman.imagen = pacmanUpImage;
        }
        else if (pacman.direccion == 'D')
        {
            pacman.imagen = pacmanDownImage;
        }
        else if (pacman.direccion == 'L')
        {
            pacman.imagen = pacmanLeftImage;
        }
        else if (pacman.direccion == 'R') 
        {
            pacman.imagen = pacmanRightImage;
        }
    }
}
