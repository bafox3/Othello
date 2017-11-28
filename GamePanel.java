
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
			setBackground(Color.GREEN);

			double gridDimension = 8.0;
			gridWidth = (int) (this.getSize().getWidth() / (gridDimension)); 
			gridHeight = (int) (this.getSize().getHeight() / (gridDimension));
			for (int i = 1; i < gridDimension; i++) {
				page.drawLine(0, gridHeight * i, (int) (getSize().getWidth()), gridHeight * i);
				page.drawLine(gridWidth * i, 0, gridWidth * i, (int) (getSize().getHeight()));
			}
			
			if(pieceList.isEmpty()){
				pieceList.add(new Piece(3*gridWidth, 3*gridHeight, gridWidth, gridHeight, Color.BLACK));

				pieceList.add(new Piece(4*gridWidth, 4*gridHeight, gridWidth, gridHeight, Color.BLACK));

				pieceList.add(new Piece(4*gridWidth, 3*gridHeight, gridWidth, gridHeight, Color.WHITE));

				pieceList.add(new Piece(3*gridWidth, 4*gridHeight, gridWidth, gridHeight, Color.WHITE));
				
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
			if(blackTurn){
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
			pieceList.add(newPiece);
			
			blackTurn = !blackTurn;
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
	}
}