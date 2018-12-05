package myFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Game.Game;
import Map.Map;

public class MyFrame{	
	static Map map;
	static  JFrame frame;
	static Game game;
	static JTextField ScoreTextField;
	static DotsAndLines panel;

	static  JLabel Solve;
	static  JLabel ClearH;
	static  JLabel Clear;
	static  JLabel Load;
	static  JLabel Save;
	static  JLabel Info;
	private String fileName;


	public MyFrame() {
	
		game = new Game();
		initialize();
	}
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


	

		//Solve JButton
		Solve = new JLabel(new ImageIcon("./img/Solve.png"));
		Solve.setVisible(true);
		frame.getContentPane().add(Solve);
		Solve.setBounds(1433, 20, 188, 56);


		//Save JButton
		Save = new JLabel(new ImageIcon("./img/Save.png"));
		Save.setVisible(true);
		frame.getContentPane().add(Save);
		Save.setBounds(1433, 20 + 20 + 56, 188, 56);

		
/////////////////////////////////////// or bug ///////////////////////////////////
		//Load JButton
		 Load = new JLabel(new ImageIcon("./img/Load.png"));
		Load.setVisible(true);
		frame.getContentPane().add(Load);
		Load.setBounds(1433, 20 + 20 + 56 + 56 + 20, 188, 56);
		Load.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
					fileName = chooser.getSelectedFile().getAbsolutePath();
					game = new Game(fileName);
					panel = new DotsAndLines("./data/game_chk.csv",game,MyFrame.map);
					panel.setBounds(0, 0, 1433, 642);
					frame.getContentPane().add(panel);
				}
			}
		});
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
/////////////////////////////////////// or bug ///////////////////////////////////
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
				if(game.getFruitList().size() > 1
						&& panel.Solutions.isEmpty() )

				{
					Solve.setVisible(false);
					ScoreTextField.setVisible(true);
					ClearH.setVisible(false);
					Clear.setVisible(false);
					Load.setVisible(false);
					Save.setVisible(false);
					Info.setVisible(false);
					panel.Solve();
				}
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
	public synchronized static void UpdateTime(double time) { ScoreTextField.setText(time + ""); }
	public synchronized static double getTime() { return Double.parseDouble(ScoreTextField.getText()); }
	public static void VisableAllButtons() {
		Solve.setVisible(true);
		Clear.setVisible(true);
		ClearH.setVisible(true);
		Load.setVisible(true);
		Save.setVisible(true);		
		Info.setVisible(true);

	}
/////////////////////////////////////// or bug ///////////////////////////////////
	public static void main(String[] args) {
		MyFrame frame = new MyFrame();
//		MyFrame frame = new MyFrame("C:\\Users\\���\\Desktop\\data\\game_1543684662657.csv");


/////////////////////////////////////// or bug ///////////////////////////////////

	}
}
