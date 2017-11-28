//Brandon's modified version to assist development
import sun.plugin2.util.ColorUtil;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private Color currentColor;
    private CanvasPanel canvas;
    private JPanel leftPanel;
    private JButton clear;
    private int gridWidth;
    private int gridHeight;
    private ArrayList<Piece> pieceList;
    private boolean blackTurn = true;
    private Piece newRectangle;
    private Color green = new Color(34, 139, 34);// added by Brandon

    public GamePanel() {
        pieceList = new ArrayList<Piece>();

        clear = new JButton("Clear");

        leftPanel = new JPanel(new GridLayout(1, 1));

        leftPanel.add(clear);

        canvas = new CanvasPanel();
        //registration
        clear.addActionListener(new ButtonListener());

        JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, canvas);

        setLayout(new BorderLayout());
        add(sp);
    }

    private class CanvasPanel extends JPanel {
        public CanvasPanel() {
            addMouseListener(new PointListener());

        }

        public void paintComponent(Graphics page) {
            super.paintComponent(page);
            setBackground(green);//edited by Brandon

            double gridDimension = 16.0;
            gridWidth = (int) (this.getSize().getWidth() / (gridDimension));
            gridHeight = (int) (this.getSize().getHeight() / (gridDimension));
            for (int i = 1; i < gridDimension; i++) {
                page.drawLine(0, gridHeight * i, (int) (getSize().getWidth()), gridHeight * i);
                page.drawLine(gridWidth * i, 0, gridWidth * i, (int) (getSize().getHeight()));
            }

            if(pieceList.isEmpty()){
                pieceList.add(new Piece(3*gridWidth, 3*gridHeight, gridWidth, gridHeight, Color.BLACK));//top left grouping

                pieceList.add(new Piece(4*gridWidth, 4*gridHeight, gridWidth, gridHeight, Color.BLACK));

                pieceList.add(new Piece(4*gridWidth, 3*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(3*gridWidth, 4*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(11*gridWidth, 3*gridHeight, gridWidth, gridHeight, Color.BLACK));//top right grouping

                pieceList.add(new Piece(12*gridWidth, 4*gridHeight, gridWidth, gridHeight, Color.BLACK));

                pieceList.add(new Piece(12*gridWidth, 3*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(11*gridWidth, 4*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(7*gridWidth, 7*gridHeight, gridWidth, gridHeight, Color.BLACK));//center grouping

                pieceList.add(new Piece(8*gridWidth, 8*gridHeight, gridWidth, gridHeight, Color.BLACK));

                pieceList.add(new Piece(8*gridWidth, 7*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(7*gridWidth, 8*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(11*gridWidth, 11*gridHeight, gridWidth, gridHeight, Color.BLACK));//bottom right grouping

                pieceList.add(new Piece(12*gridWidth, 12*gridHeight, gridWidth, gridHeight, Color.BLACK));

                pieceList.add(new Piece(12*gridWidth, 11*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(11*gridWidth, 12*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(3*gridWidth, 11*gridHeight, gridWidth, gridHeight, Color.BLACK));//bottom left grouping

                pieceList.add(new Piece(4*gridWidth, 12*gridHeight, gridWidth, gridHeight, Color.BLACK));

                pieceList.add(new Piece(4*gridWidth, 11*gridHeight, gridWidth, gridHeight, Color.WHITE));

                pieceList.add(new Piece(3*gridWidth, 12*gridHeight, gridWidth, gridHeight, Color.WHITE));

            }

            for (int i = 0; i < pieceList.size(); i++) {
                if (pieceList.get(i) != null) {
                    newRectangle = pieceList.get(i);
                    newRectangle.draw(page);

                }
            }
        }
    }
    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            if (event.getSource() == clear) {
                pieceList.clear();

                repaint();

            }
        }
    }


    public class PointListener implements MouseListener {
        public void mousePressed(MouseEvent event) {
            if(blackTurn){ //replace with switch case?
                currentColor = Color.BLACK;
            }
            else{
                currentColor = Color.WHITE;
            }

            int x = event.getPoint().x;
            int y = event.getPoint().y;



            //dividing then multiplying by the same value may seem repetitive, but it is used to
            //align the click to the grid. The same thing may be achieved by modulating then subtracting the remainder
            int xOffset = (x / gridWidth) * gridWidth;
            int yOffset = (y / gridHeight) * gridHeight;
            //TODO: ensure no overriding during this stage
            Piece newPiece = new Piece(xOffset, yOffset, gridWidth, gridHeight, currentColor);
            //
            if (isLegalMove(pieceList, newPiece)) {
                pieceList.add(newPiece);
                blackTurn = !blackTurn;
            }
            repaint();
        }

        public void mouseReleased(MouseEvent event) {
        }

        public void mouseClicked(MouseEvent event) {
        }

        public void mouseEntered(MouseEvent event) {
        }

        public void mouseExited(MouseEvent event) {
        }

	    //not final(will be in a separate class its here for reference)
        public boolean isLegalMove (ArrayList<Piece> pieceList, Piece move)
        {
            boolean result = true;
            boolean adjacent = false;
            for (int i = 0; i < pieceList.size(); i++) {
                if (pieceList.get(i) != null) {
                    //check for a piece in that location
                    if(move.getX() == pieceList.get(i).getX() && move.getY() == pieceList.get(i).getY())
                    {
                        result = false;
                    }
                }
            }
            if (!hasAdjacent(pieceList, move))
                result = false;
            return result;
        }

        //check if there are adjacent pieces, if not the move is invalid not final(will be in a separate class its here for reference)
        public boolean hasAdjacent (ArrayList<Piece> pieceList, Piece move)
        {
            boolean adjacent = false;

            for (int i = 0; i < pieceList.size(); i++) {
                if (pieceList.get(i) != null) {
                    if (move.getGridX() - 1 == pieceList.get(i).getGridX() && move.getGridY() + 1 == pieceList.get(i).getGridY()) adjacent = true;
                    else if (move.getGridX() == pieceList.get(i).getGridX() && move.getGridY() + 1 == pieceList.get(i).getGridY()) adjacent = true;
                    else if (move.getGridX() + 1 == pieceList.get(i).getGridX() && move.getGridY() + 1 == pieceList.get(i).getGridY()) adjacent = true;
                    else if (move.getGridX() - 1 == pieceList.get(i).getGridX() && move.getGridY() == pieceList.get(i).getGridY()) adjacent = true;
                    else if (move.getGridX() + 1 == pieceList.get(i).getGridX() && move.getGridY() == pieceList.get(i).getGridY()) adjacent = true;
                    else if (move.getGridX() - 1 == pieceList.get(i).getGridX() && move.getGridY() - 1 == pieceList.get(i).getGridY()) adjacent = true;
                    else if (move.getGridX() == pieceList.get(i).getGridX() && move.getGridY() - 1 == pieceList.get(i).getGridY()) adjacent = true;
                    else if (move.getGridX() + 1 == pieceList.get(i).getGridX() && move.getGridY() - 1 == pieceList.get(i).getGridY()) adjacent = true;
                }
            }

            return adjacent;
        }

    }

}
