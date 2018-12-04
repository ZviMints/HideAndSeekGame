package myFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Game.Game;
import Map.Map;
import ShortestPathAlgo.Algo;

public class MyFrame{	
	static Map map;
	static  JFrame frame;
	static Game game;
	static DotsAndLines panel;
	static Algo algo;
	static JTextField ScoreTextField;

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
		panel = new DotsAndLines("./data/game_chk.csv", game);
		panel.setBounds(0, 0, 1433, 642);
		frame.getContentPane().setLayout(null);


		//Solve JButton
		JLabel Solve = new JLabel(new ImageIcon("./img/Solve.png"));
		Solve.setVisible(true);
		frame.getContentPane().add(Solve);
		Solve.setBounds(1433, 20, 188, 56);


		//Save JButton
		JLabel Save = new JLabel(new ImageIcon("./img/Save.png"));
		Save.setVisible(true);
		frame.getContentPane().add(Save);
		Save.setBounds(1433, 20 + 20 + 56, 188, 56);

		//Load JButton
		JLabel Load = new JLabel(new ImageIcon("./img/Load.png"));
		Load.setVisible(true);
		frame.getContentPane().add(Load);
		Load.setBounds(1433, 20 + 20 + 56 + 56 + 20, 188, 56);
		
		//Clear JButton
		JLabel Clear = new JLabel(new ImageIcon("./img/Clear.png"));
		Clear.setVisible(true);
		frame.getContentPane().add(Clear);
		Clear.setBounds(1433, 20 + 20 + 56 + 56 +56 + 20 + 20, 188, 56);

		//Score TextField
		final ImageIcon Score_Image = new ImageIcon("./img/Time.png");
		ScoreTextField = new JTextField() { // Making TextArea From Image
			Image image = Score_Image.getImage();
			{
				setOpaque(false);
			}
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
				super.paint(g);
			}
		};	
		ScoreTextField.setBorder(null);
		ScoreTextField.setVisible(true);
		ScoreTextField.setText(" 0.0");
		ScoreTextField.setFont(new Font("Courier New", Font.PLAIN, 40));
		ScoreTextField.setForeground(Color.WHITE);
		ScoreTextField.setBounds(1433 + 8, 642 - 133 , 188, 56);
		ScoreTextField.setEditable(false);
		frame.getContentPane().add(ScoreTextField);

		// On Click "Solve":
		Solve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Solve.setVisible(false);
				panel.update();
				UpdateTime(panel.getAlgoTime());
			}});

		// On Click "Clear":
		Clear.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Solve.setVisible(true);
						panel.clear();
						UpdateTime(0);
					}});

		
		
		
		frame.getContentPane().add(panel);
		frame.setTitle("T&O OP_3 Exercise"); // Set Title
		frame.setSize(1650, 642); // Set Size to JFrame
		frame.setLocationRelativeTo(null); // Puts on the center of the Screen		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Will Close the JFame on [X]
		frame.setVisible(true); // Set visibility to True
	}
	/* * * * * * * * * * * * * * Solve * * * * * * * * * * * * * * * */
	public static void Solve()
	{
		panel.update();
	}
	/* * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */

	public static double getWidth() { return frame.getWidth(); }
	public static double getHeight() { return frame.getHeight(); }
	public static void UpdateTime(double time) { ScoreTextField.setText(" "+time); }
}
