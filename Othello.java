
import javax.swing.*;

public class Othello extends JApplet
{

 public void init()
  {
	 
    GamePanel gamePanel = new GamePanel();
    getContentPane().add(gamePanel);

    setSize (500, 400);
  }

}
