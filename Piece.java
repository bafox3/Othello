
import java.awt.*;

public class Piece {
	private int x;							
	private int y;
	private int width;
	private int height;
	private Color color;
	
	public Piece(int x1, int y1, int width1, int height1, Color color1){
		x = x1;
		y = y1;
		width = width1;
		height = height1;
		color = color1;
	}
	
	
	public void draw(Graphics page){
		
		page.setColor(color);
		page.fillOval(x, y, width, height);
		
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

}
