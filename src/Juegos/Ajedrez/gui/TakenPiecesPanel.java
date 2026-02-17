/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

import Juegos.Ajedrez.Engine.board.Move;
import Juegos.Ajedrez.Engine.pieces.Piece;
import Juegos.Ajedrez.gui.Table.MoveLog;

class TakenPiecesPanel extends JPanel {

    private final JPanel northPanel;
    private final JPanel southPanel;

    private static final long serialVersionUID = 1L;
    private static final Color PANEL_COLOR = Color.decode("0xFDF5E6");
    private static final Dimension TAKEN_PIECES_PANEL_DIMENSION = new Dimension(40, 80);
    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(Color.decode("0xFDF5E6"));
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));
        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);
        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);
        setPreferredSize(TAKEN_PIECES_PANEL_DIMENSION);
    }

    public void redo(final MoveLog moveLog) {
        southPanel.removeAll();
        northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        for (final Move move : moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getPieceAllegiance().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                } else if (takenPiece.getPieceAllegiance().isBlack()) {
                    blackTakenPieces.add(takenPiece);
                } else {
                    throw new RuntimeException("Should not reach here!");
                }
            }
        }

        whiteTakenPieces.sort(Comparator.comparingInt(Piece::getPieceValue));
        blackTakenPieces.sort(Comparator.comparingInt(Piece::getPieceValue));

        addIconsToPanel(whiteTakenPieces, southPanel);
        addIconsToPanel(blackTakenPieces, northPanel);

        validate();
    }

    private void addIconsToPanel(List<Piece> takenPieces, JPanel panel) {
        for (final Piece takenPiece : takenPieces) {
            try {
                String pieceIconPath = Table.get().getPieceIconPath(); // Usamos el path actual
                String fileName = "/Juegos/Ajedrez/gui/" + pieceIconPath
                        + takenPiece.getPieceAllegiance().toString().charAt(0)
                        + takenPiece.toString() + ".gif";

                System.out.println("Cargando imagen capturada: " + fileName); // debug

                try (InputStream is = getClass().getResourceAsStream(fileName)) {
                    if (is == null) {
                        throw new RuntimeException("No se encontró la imagen capturada: " + fileName);
                    }
                    BufferedImage image = ImageIO.read(is);
                    ImageIcon ic = new ImageIcon(image);
                    JLabel imageLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                            ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                    panel.add(imageLabel);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

