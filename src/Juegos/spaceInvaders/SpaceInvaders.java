/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.spaceInvaders;

/**
 *
 * @author Juli
 */
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import Interfaces.Game;

public class SpaceInvaders extends JPanel implements ActionListener, KeyListener, Game{
    
    @Override
    public void iniciarJuego()
    {
        //window variables
        int tamanioBloque = 32;
        int filass = 16;
        int columns = 16;
        int hanchoTablero = tamanioBloque * columns; // 32 * 16 = 512px
        int altoTablero = tamanioBloque * filass; // 32 * 16 = 512px

        JFrame frame = new JFrame("Space Invaders");
        // frame.setVisible(true);
        frame.setSize(hanchoTablero, altoTablero);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        SpaceInvaders spaceInvaders = new SpaceInvaders();
        frame.add(spaceInvaders);
        frame.pack();
        spaceInvaders.requestFocus();
        frame.setVisible(true);
    }
    
    @Override
    public String getNombre()
    {
        return "Space invaders";
    }

    
    //board
    int bloqueTamanio = 32;
    int filas = 16;
    int columnas = 16;

    int TableroHancho = bloqueTamanio * columnas; // 32 * 16
    int TableroAlto = bloqueTamanio * filas; // 32 * 16

    Image naveImg;
    Image alienImg;
    Image alienCyanImg;
    Image alienMagentaImg;
    Image alienAmarilloImg;
    Image fondo;
    ArrayList<Image> alienImgArray;

    class Block {
        int x;
        int y;
        int width;
        int height;
        Image img;
        boolean alive = true; //used for aliens
        boolean used = false; //used for bullets
        
        Block(int x, int y, int width, int height, Image img) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
        }
    }

    //ship
    int shipWidth = bloqueTamanio*2;
    int shipHeight = bloqueTamanio;
    int shipX = bloqueTamanio * columnas/2 - bloqueTamanio;
    int shipY = bloqueTamanio * filas - bloqueTamanio*2;
    int shipVelocityX = bloqueTamanio; //ship moving speed
    Block ship;

    //aliens
    ArrayList<Block> alienArray;
    int alienWidth = bloqueTamanio*2;
    int alienHeight = bloqueTamanio;
    int alienX = bloqueTamanio;
    int alienY = bloqueTamanio;

    int alienRows = 2;
    int alienColumns = 3;
    int alienCount = 0; //number of aliens to defeat
    int alienVelocityX = 1; //alien moving speed

    //bullets
    ArrayList<Block> bulletArray;
    int bulletWidth = bloqueTamanio/8;
    int bulletHeight = bloqueTamanio/2;
    int bulletVelocityY = -10; //bullet moving speed

    Timer gameLoop;
    boolean gameOver = false;
    int score = 0;
    
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        draw(g);
    }

    public SpaceInvaders() 
    {
        setPreferredSize(new Dimension(TableroHancho, TableroAlto));

        fondo = new ImageIcon(getClass().getResource("/Juegos/spaceInvaders/SpaceInvadersImagenes/fondo.jpg")).getImage();
        //setBackground(fondo);
        //drawImage(fondo);
        setFocusable(true);
        addKeyListener(this);

        //load images
        //"/Juegos/spaceInvaders/SpaceInvadersImagenes/fondo.jpg"
        //"C:\Users\Juli\OneDrive\Documents\NetBeansProjects\GameContainer\src\Juegos\spaceInvaders\SpaceInvadersImagenes\ship.png"
        naveImg = new ImageIcon(getClass().getResource("/Juegos/spaceInvaders/SpaceInvadersImagenes/ship.png")).getImage();
        alienImg = new ImageIcon(getClass().getResource("/Juegos/spaceInvaders/SpaceInvadersImagenes/alien.png")).getImage();
        alienCyanImg = new ImageIcon(getClass().getResource("/Juegos/spaceInvaders/SpaceInvadersImagenes/alien-cyan.png")).getImage();
        alienMagentaImg = new ImageIcon(getClass().getResource("/Juegos/spaceInvaders/SpaceInvadersImagenes/alien-magenta.png")).getImage();
        alienAmarilloImg = new ImageIcon(getClass().getResource("/Juegos/spaceInvaders/SpaceInvadersImagenes/alien-yellow.png")).getImage();


        alienImgArray = new ArrayList<Image>();
        alienImgArray.add(alienImg);
        alienImgArray.add(alienCyanImg);
        alienImgArray.add(alienMagentaImg);
        alienImgArray.add(alienAmarilloImg);

        ship = new Block(shipX, shipY, shipWidth, shipHeight, naveImg);
        alienArray = new ArrayList<Block>();
        bulletArray = new ArrayList<Block>();

        //game timer
        gameLoop = new Timer(1000/60, this); //1000/60 = 16.6
        createAliens();
        gameLoop.start();
    }

    public void draw(Graphics g) {
        //ship
        g.drawImage(ship.img, ship.x, ship.y, ship.width, ship.height, null);

        //aliens
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                g.drawImage(alien.img, alien.x, alien.y, alien.width, alien.height, null);
            }
        }

        //bullets
        g.setColor(Color.white);
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            if (!bullet.used) {
                g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);
                // g.fillRect(bullet.x, bullet.y, bullet.width, bullet.height);
            }
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
        }
        else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void move() {
        //alien
        for (int i = 0; i < alienArray.size(); i++) {
            Block alien = alienArray.get(i);
            if (alien.alive) {
                alien.x += alienVelocityX;

                //if alien touches the borders
                if (alien.x + alien.width >= TableroHancho || alien.x <= 0) {
                    alienVelocityX *= -1;
                    alien.x += alienVelocityX*2;

                    //move all aliens up by one row
                    for (int j = 0; j < alienArray.size(); j++) {
                        alienArray.get(j).y += alienHeight;
                    }
                }

                if (alien.y >= ship.y) {
                    gameOver = true;
                }
            }
        }

        //bullets
        for (int i = 0; i < bulletArray.size(); i++) {
            Block bullet = bulletArray.get(i);
            bullet.y += bulletVelocityY;

            //bullet collision with aliens
            for (int j = 0; j < alienArray.size(); j++) {
                Block alien = alienArray.get(j);
                if (!bullet.used && alien.alive && detectCollision(bullet, alien)) {
                    bullet.used = true;
                    alien.alive = false;
                    alienCount--;
                    score += 100;
                }
            }
        }

        //clear bullets
        while (bulletArray.size() > 0 && (bulletArray.get(0).used || bulletArray.get(0).y < 0)) {
            bulletArray.remove(0); //removes the first element of the array
        }

        //next level
        if (alienCount == 0) {
            //increase the number of aliens in columns and rows by 1
            score += alienColumns * alienRows * 100; //bonus points :)
            alienColumns = Math.min(alienColumns + 1, columnas/2 -2); //cap at 16/2 -2 = 6
            alienRows = Math.min(alienRows + 1, filas-6);  //cap at 16-6 = 10
            alienArray.clear();
            bulletArray.clear();
            createAliens();
        }
    }

    public void createAliens() {
        Random random = new Random();
        for (int c = 0; c < alienColumns; c++) {
            for (int r = 0; r < alienRows; r++) {
                int randomImgIndex = random.nextInt(alienImgArray.size());
                Block alien = new Block(
                    alienX + c*alienWidth, 
                    alienY + r*alienHeight, 
                    alienWidth, 
                    alienHeight,
                    alienImgArray.get(randomImgIndex)
                );
                alienArray.add(alien);
            }
        }
        alienCount = alienArray.size();
    }
    

    public boolean detectCollision(Block a, Block b) {
        return  a.x < b.x + b.width &&  //a's top left corner doesn't reach b's top right corner
                a.x + a.width > b.x &&  //a's top right corner passes b's top left corner
                a.y < b.y + b.height && //a's top left corner doesn't reach b's bottom left corner
                a.y + a.height > b.y;   //a's bottom left corner passes b's top left corner
    }
    
    private void reiniciar()
    {
        JFrame reiniciar = new JFrame("Reiniciar");
        JPanel panel = new JPanel();
        JButton boton = new JButton("Reiniciar");
        
        boton.addActionListener((ActionEvent e)-> 
        {
            iniciarJuego();
        });
        
        panel.add(boton);
        reiniciar.add(panel);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if (gameOver) { //any key to restart
            ship.x = shipX;
            bulletArray.clear();
            alienArray.clear();
            gameOver = false;
            score = 0;
            alienColumns = 3;
            alienRows = 2;
            alienVelocityX = 1;
            createAliens();
            gameLoop.start();
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT  && ship.x - shipVelocityX >= 0) {
            ship.x -= shipVelocityX; //move left one tile
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT  && ship.x + shipVelocityX + ship.width <= TableroHancho) {
            ship.x += shipVelocityX; //move right one tile
        }
        else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            //shoot bullet
            Block bullet = new Block(ship.x + shipWidth*15/32, ship.y, bulletWidth, bulletHeight, null);
            bulletArray.add(bullet);
        }
    }
}