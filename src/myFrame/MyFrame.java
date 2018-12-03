package myFrame;

import java.awt.Canvas;
import javax.swing.JFrame;
import Game.Game;
import Map.Map;
import ShortestPathAlgo.Algo;

public class MyFrame{	
	public static Map map;
	public static  JFrame frame;
	public static Game game;
	public static Canvas canvas;
	public static Algo algo;
//	private static ImageIcon image_pacman = new ImageIcon("./img/Pacman.jpg");

	public MyFrame (String path)
	{
		frame = new JFrame();
		game = new Game(path);
		algo = new Algo(game);
		initialize();
	}
	/* * * * * * * * * * * * * * * * * * Initialize Window * * * * * * * * * * * * * * * */
	private static void initialize() {
		map = new Map(frame.getWidth(),frame.getHeight());
		
//		JLabel img = new JLabel(new ImageIcon(map.getBackground()));
//		frame.setContentPane(img);
		
        canvas = new DrawingGraph();
        frame.add(canvas);

		frame.setTitle("T&O OP_3 Exercise"); // Set Title
		frame.setSize(1455, 698); // Set Size to JFrame
		frame.setLocationRelativeTo(null); // Puts on the center of the Screen		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Will Close the JFame on [X]
		frame.setVisible(true); // Set visibility to True
	}

	/* * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public double getWidth() { return frame.getWidth(); }
	public double getHeight() { return frame.getHeight(); }
}

