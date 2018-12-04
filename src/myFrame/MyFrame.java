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
import javax.swing.JTextField;

import Game.Game;
import Map.Map;

public class MyFrame{	
	static Map map;
	static  JFrame frame;
	static Game game;
	static JTextField ScoreTextField;
	static DotsAndLines panel;

	public MyFrame (String path)
	{
		frame = new JFrame();
		game = new Game(path);
		initialize();
	}
	/* * * * * * * * * * * * * * * * * * Initialize Window * * * * * * * * * * * * * * * */
	private void initialize() {
		JFrame frame = new JFrame();	
		frame.setSize(1650, 697); // Set Size to JFrame
		
		map = new Map(frame.getWidth(),frame.getHeight());   
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
		
		
		//ClearH JButton
		JLabel ClearH = new JLabel(new ImageIcon("./img/ClearH.png"));
		ClearH.setVisible(true);
		frame.getContentPane().add(ClearH);
		ClearH.setBounds(1433, 20 + 20 + 56 + 56 +56 + 56 + 20 + 20 + 20, 188, 56);
		
		//Mouse Info 
		JLabel Info = new JLabel(new ImageIcon("./img/Info.png"));
		Info.setVisible(true);
		frame.getContentPane().add(Info);
		Info.setBounds(1433, 20 + 20 + 56 + 56 + 20 + 56 +56 + 56 + 20 + 20 + 20, 245, 178);

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
		ScoreTextField.setFont(new Font("Courier New", Font.PLAIN, 15));
		ScoreTextField.setForeground(Color.WHITE);
		ScoreTextField.setBounds(1433 + 8, 697 - 120 , 170, 56);
		ScoreTextField.setEditable(false);
		frame.getContentPane().add(ScoreTextField);

		// On Click "Solve":
		Solve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panel.Solve();
				UpdateTime(panel.getAlgoTime());
			}});

		// On Click "ClearH":
		ClearH.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						panel.clearH();
						UpdateTime(0);
					}});
		
		// On Click "Clear":
		Clear.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						panel.Clear();
						UpdateTime(0);
					}});
		
		panel = new DotsAndLines("./data/game_chk.csv",game,this.map);
		panel.setBounds(0, 0, 1433, 642);
		frame.getContentPane().add(panel);
		frame.setTitle("T&O OP_3 Exercise"); // Set Title
		frame.setLocationRelativeTo(null); // Puts on the center of the Screen		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Will Close the JFame on [X]
		frame.setVisible(true); // Set visibility to True
	}
	/* * * * * * * * * * * * * * Solve * * * * * * * * * * * * * * * */
	public static void Solve()
	{
		panel.Solve();
	}
	/* * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */

	public static double getWidth() { return frame.getWidth(); }
	public static double getHeight() { return frame.getHeight(); }
	public static void UpdateTime(double time) { ScoreTextField.setText(" "+time); }
}
