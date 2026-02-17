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
import java.awt.event.*;
import javax.swing.*;
import Interfaces.Game;

public class TicTacToe implements Game{
    
    private static final int puntajeX = 0;
    private static final int puntajeO = 0;
    
    int boardWidth = 600;
    int boardHeight = 650;
    
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel pBoard = new JPanel();
    
    JButton[][] board = new JButton[3][3];
    String player1 = "X";
    String player2 = "O";
    String currentPlayer = player1;
    
    boolean gameOver = false;
    int turns = 0;
    
    JButton restart = new JButton();
    
    JFrame frame = new JFrame(getNombre());
    
    @Override
    public String getNombre()
    {
        return "Tic Tac Toe";
    }
    
    @Override
    public void iniciarJuego()
    {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());  
        
        label.setBackground(Color.darkGray);
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD,50));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setText("Tic Tac Toe");
        label.setOpaque(true);
        
        panel.setLayout(new BorderLayout());
        panel.add(label);
        frame.add(panel, BorderLayout.NORTH);
        
        pBoard.setLayout(new GridLayout(3,3));
        pBoard.setBackground(Color.darkGray);
        frame.add(pBoard);
    }
    
    public TicTacToe()
    {
        
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                JButton tile = new JButton();
                board[i][j] = tile;
                pBoard.add(tile);
                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD,120));
                tile.setFocusable(false);
                //tile.setText(currentPlayer);
                
                tile.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if(gameOver)return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText().equals("")) 
                        {
                            tile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer == player1 ? player2 : player1;
                                label.setText(currentPlayer + "'s turn.");

                                // Llama al bot si es su turno
                                if (currentPlayer.equals(player2)) {
                                    Timer timer = new Timer(500, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            botPlay();
                                        }
                                    });
                                    timer.setRepeats(false);
                                    timer.start(); // espera 500ms antes de hacer el turno del bot (mejor UX)
                                }
                            }
                        }

//                        if(tile.getText() == "")
//                        {
//                            tile.setText(currentPlayer);
//                            turns++;
//                            checkWinner();
//                            if(!gameOver)
//                            {
//                                currentPlayer = currentPlayer == player1 ? player2 : player1;
//                                label.setText(currentPlayer + "'s turn.");
//                            }
//                        }
                    }
                });
            }
        }
    }
    
    public void botPlay() 
    {
        if (gameOver || currentPlayer != player2) return;

        java.util.List<JButton> emptyTiles = new java.util.ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].getText().equals("")) {
                    emptyTiles.add(board[i][j]);
                }
            }
        }

        if (!emptyTiles.isEmpty()) {
            JButton chosenTile = emptyTiles.get(new java.util.Random().nextInt(emptyTiles.size()));
            chosenTile.setText(currentPlayer);
            turns++;
            checkWinner();
            if (!gameOver) {
                currentPlayer = player1;
                label.setText(currentPlayer + "'s turn.");
            }
        }
    }

    
    public void checkWinner()
    {
        //Horizontal
        for(int i = 0; i<3 ; i++)
        {
            if(board[i][0].getText() == "")continue;
            
            if(board[i][0].getText() == board[i][1].getText()&&
               board[i][1].getText() == board[i][2].getText())
            {
                for(int j = 0; j < 3; j++)
                {
                    setWinner(board[i][j]);
                }
                gameOver = true;
                reiniciar(gameOver);
                return;
            }
            
        }
        
        
        //Vertical
        for(int c = 0; c<3; c++)
        {
            if(board[0][c].getText() == "") continue;
            
            if(board[0][c].getText() == board[1][c].getText() &&
               board[1][c].getText() == board [2][c].getText())
            {
                for(int m = 0; m < 3; m++)
                {
                    setWinner(board[m][c]);
                }
                gameOver = true;
                reiniciar(gameOver);
                return;
                
            }
        }
        
        //Diagonal
        if(board[0][0].getText() == board[1][1].getText() &&
           board[1][1].getText() == board[2][2].getText()&&
           board[0][0].getText() != "")
        {
            for(int i = 0; i < 3; i++)
            {
                setWinner(board[i][i]);
            }
            gameOver = true;
            reiniciar(gameOver);
            return;
        }
        
        //Diagonal 2
        if(board[0][2].getText() == board[1][1].getText() &&
           board[1][1].getText() == board[2][0].getText()&&
           board[0][2].getText() != "")
        {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            reiniciar(gameOver);
            return;
        }
        
        //Empate
        if(turns == 9)
        {
            for(int r = 0; r < 3; r++)
            {
                for(int c = 0; c<3; c++)
                {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
            reiniciar(gameOver);
        }
    }
    
    public void setTie(JButton tile)
    {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        label.setText("Empate");
    }
    
    public void setWinner(JButton tile)
    {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        label.setText(currentPlayer + " es el ganador!!");
    }
    
    public void reiniciar(boolean gameOver)
    {
       
        JFrame rFrame = new JFrame("Fin del juego");
        JPanel rPanel = new JPanel();
        JPanel rPanel2 = new JPanel();
        JLabel mensaje = new JLabel();
        
        
        rFrame.setVisible(true);
        rFrame.setSize(250, 200);
        rFrame.setLocationRelativeTo(null);
        rFrame.setResizable(false);
        rFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        mensaje.setBackground(Color.darkGray);
        mensaje.setForeground(Color.white);
        mensaje.setFont(new Font("Arial", Font.BOLD,20));
        mensaje.setHorizontalAlignment(JLabel.CENTER);
        mensaje.setText("Deseas jugar de nuevo?");
        mensaje.setOpaque(true);
        
        restart.setSize(150, 100);
        
        rPanel2.setLayout(new GridBagLayout());
        rPanel2.add(restart);
        rPanel2.setBackground(Color.darkGray);
        rPanel2.setForeground(Color.white);        
        
        rPanel.setLayout(new BorderLayout());
        rPanel.add(rPanel2, BorderLayout.CENTER);
        rPanel.add(mensaje, BorderLayout.NORTH);
        rPanel.setBackground(Color.darkGray);
        rPanel.setForeground(Color.white);
        rPanel.setFont(new Font("Arial", Font.BOLD,15));
        rPanel.setFocusable(false);  
        
        rFrame.add(rPanel);
        
        restart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(gameOver == true)
                {
                    TicTacToe tt = new TicTacToe();
                    tt.iniciarJuego();         
                }
            }
        });

    }
}
