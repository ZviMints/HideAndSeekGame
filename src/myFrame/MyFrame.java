package myFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Game.Game;
import Map.Map;
import ShortestPathAlgo.Algo;
import javax.swing.JTextArea;

public class MyFrame{	
	static Map map;
	static  JFrame frame;
	static Game game;
	static DotsAndLines panel;
	static Algo algo;
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

		JFrame frame = new JFrame();

		//Solve JButton
		JLabel Solve = new JLabel(new ImageIcon("./img/Solve.png"));
		Solve.setVisible(true);
		frame.add(Solve);
		Solve.setBounds(250, 40, 168, 56);
		// On Click "Login":
		Solve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Solve.setVisible(false);
				panel.update();
			}});
		

		panel = new DotsAndLines("./data/game_chk.csv", game);
		frame.getContentPane().add(panel);
		frame.setTitle("T&O OP_3 Exercise"); // Set Title
		frame.setSize(1455, 698); // Set Size to JFrame
		frame.setLocationRelativeTo(null); // Puts on the center of the Screen		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Will Close the JFame on [X]
		frame.setVisible(true); // Set visibility to True




		//		JLabel img = new JLabel(new ImageIcon(map.getBackground()));
		//		frame.setContentPane(img);
	}
	/* * * * * * * * * * * * * * Solve * * * * * * * * * * * * * * * */
	public static void Solve()
	{
		panel.update();
	}
	/* * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */

	public double getWidth() { return frame.getWidth(); }
	public double getHeight() { return frame.getHeight(); }
}
