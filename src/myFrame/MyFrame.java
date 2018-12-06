/**
 * Will class represent the GUI Frame of the Project.
 * @author Tzvi Mints and Or Abuhazira
 * @version 3.0
 */
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
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	static Map map; // The Map of the game
	static  JFrame frame; // The Main Frame of the Game
	static Game game; // Represent the Game Data base
	static JTextField ScoreTextField; 
	static DotsAndLines panel;
	static  JLabel Solve;
	static  JLabel ClearH;
	static  JLabel Clear;
	static  JLabel Load;
	static  JLabel Save;
	static  JLabel Info;
	public static  JTextField InProgress;
	public static JTextField TotalTF;

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public MyFrame (String path)
	{
		frame = new JFrame();
		game = new Game(path);
		initialize();
	}
	/* * * * * * * * * * * * * * * * * * Initialize Window * * * * * * * * * * * * * * * */
	private void initialize() {
		JFrame frame = new JFrame();	
		frame.setSize(1625, 682); // Set Size to JFrame


		map = new Map(1433,642);   
		frame.getContentPane().setLayout(null);


		panel = new DotsAndLines("./data/game_chk.csv",game,this.map);
		panel.setBounds(0, 0, 1433, 642);
		frame.getContentPane().add(panel);

		//Solve JButton
		Solve = new JLabel(new ImageIcon("./img/Solve.png"));
		Solve.setVisible(true);
		frame.getContentPane().add(Solve);
		Solve.setBounds(1433, 20, 188, 56);

		//Info About Pacmans and Fruits TextField
		final ImageIcon TotalTF_Image = new ImageIcon("./img/TotalTF.png");
		TotalTF = new JTextField() { // Making TextArea From Image
			Image image = TotalTF_Image.getImage();
			{
				setOpaque(false);
			}
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
				super.paint(g);
			}
		};	
		TotalTF.setBorder(null);
		TotalTF.setVisible(false);
		TotalTF.setFont(new Font("Courier New", Font.PLAIN, 31));
		TotalTF.setBounds(1435, 250, 181, 137);
		TotalTF.setEditable(false);
		frame.getContentPane().add(TotalTF);
		TotalTF.setHorizontalAlignment(JTextField.CENTER);


		//InProgress TextField
		final ImageIcon Progress_Image = new ImageIcon("./img/InProgress.png");
		InProgress = new JTextField() { // Making TextArea From Image
			Image image = Progress_Image.getImage();
			{
				setOpaque(false);
			}
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, this);
				super.paint(g);
			}
		};	
		InProgress.setHorizontalAlignment(JTextField.CENTER);
		InProgress.setBorder(null);
		InProgress.setVisible(false);
		InProgress.setText("0");
		InProgress.setFont(new Font("Courier New", Font.PLAIN, 35));
		InProgress.setBounds(1445, 20, 162, 259);
		InProgress.setEditable(false);
		frame.getContentPane().add(InProgress);


		//Save JButton
		Save = new JLabel(new ImageIcon("./img/Save.png"));
		Save.setVisible(true);
		frame.getContentPane().add(Save);
		Save.setBounds(1433, 20 + 20 + 56, 188, 56);

		//Load JButton
		Load = new JLabel(new ImageIcon("./img/Load.png"));
		Load.setVisible(true);
		frame.getContentPane().add(Load);
		Load.setBounds(1433, 20 + 20 + 56 + 56 + 20, 188, 56);

		//Clear JButton
		Clear = new JLabel(new ImageIcon("./img/Clear.png"));
		Clear.setVisible(true);
		frame.getContentPane().add(Clear);
		Clear.setBounds(1433, 20 + 20 + 56 + 56 +56 + 20 + 20, 188, 56);


		//ClearH JButton
		ClearH = new JLabel(new ImageIcon("./img/ClearH.png"));
		ClearH.setVisible(true);
		frame.getContentPane().add(ClearH);
		ClearH.setBounds(1433, 20 + 20 + 56 + 56 +56 + 56 + 20 + 20 + 20, 188, 56);

		//Mouse Info 
		Info = new JLabel(new ImageIcon("./img/Info.png"));
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
		ScoreTextField.setVisible(false);
		ScoreTextField.setText("0.0");
		ScoreTextField.setFont(new Font("Courier New", Font.PLAIN, 15));
		ScoreTextField.setForeground(Color.WHITE);
		ScoreTextField.setBounds(1433 + 8, 697 - 120 , 170, 56);
		ScoreTextField.setEditable(false);
		frame.getContentPane().add(ScoreTextField);

		// On Click "Solve":
		Solve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(game.getFruitList().size() > 0
						&& panel.Solutions.isEmpty() )
				{
					TotalTF.setText(game.getFruitList().size()+","+game.getPacmanList().size());
					Solve.setVisible(false);
					ScoreTextField.setVisible(true);
					ClearH.setVisible(false);
					Clear.setVisible(false);
					Load.setVisible(false);
					Save.setVisible(false);
					Info.setVisible(false);
					TotalTF.setVisible(true);

					InProgress.setVisible(true);
					panel.Solve();
				}
			}});

		// On Click "ClearH":
		ClearH.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panel.clearH();
				UpdateScoreTime(0);
			}});

		// On Click "Clear":
		Clear.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				panel.Clear();
				UpdateScoreTime(0);
			}});

		ImageIcon icon = new ImageIcon("./img/icon.png"); // Set Icon to Frame
		frame.setIconImage(icon.getImage());
		frame.setResizable(false);
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
	public synchronized static void UpdateScoreTime(double time) { ScoreTextField.setText(time + ""); }
	public synchronized static double getTime() { return Double.parseDouble(ScoreTextField.getText()); }
	public synchronized static void UpdateFinished() { InProgress.setText(( Double.parseDouble(InProgress.getText()) + 1) +""); }
	/* * * * * * * * * * * * * *  VisableAllButtons  * * * * * * * * * * * * * * * */
	/**
	 * This Method is responsible to make all Buttons Visable
	 */
	public static void VisableAllButtons() {
		Solve.setVisible(true);
		Clear.setVisible(true);
		ClearH.setVisible(true);
		Load.setVisible(true);
		Save.setVisible(true);		
		Info.setVisible(true);
		InProgress.setVisible(false);
		TotalTF.setVisible(false);

	}
}
