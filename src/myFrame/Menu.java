/**
 * Will class represent the GUI Menu of the Project.
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
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Game.Game;


public class Menu extends JPanel{
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	public static JLabel Solve;
	public static JLabel ClearH;
	public static JLabel Clear;
	public static JLabel Load;
	public static JLabel Save;
	public static JTextField ScoreTextField; 
	public static JTextField InProgress;
	public static JTextField TotalTF;
	public static String fileName;
	public static JLabel Info;
	
	public static DotsAndLines panel;

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
	/* * * * * * * * * * * * * *  Constructor  * * * * * * * * * * * * * * * */
	public Menu(DotsAndLines panel)
	{
		this.panel = panel; 
		initialize();
	}
	/* * * * * * * * * * * * * * * * * * Initialize Window * * * * * * * * * * * * * * * */
	private void initialize() {
		//Solve JButton
		Solve = new JLabel(new ImageIcon("./img/Solve.png"));
		Solve.setVisible(true);
		Solve.setBounds(0, 20, 188, 56);
		this.add(Solve);

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
		TotalTF.setBounds(2, 250, 181, 137);
		TotalTF.setEditable(false);
		TotalTF.setHorizontalAlignment(JTextField.CENTER);
		this.add(TotalTF);



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
		InProgress.setBounds(15, 20, 162, 259);
		InProgress.setEditable(false);
		this.add(InProgress);


		//Save JButton
		Save = new JLabel(new ImageIcon("./img/Save.png"));
		Save.setVisible(true);
		Save.setBounds(0, 20 + 20 + 56, 188, 56);
		this.add(Save);

		//Load JButton
		Load = new JLabel(new ImageIcon("./img/Load.png"));
		Load.setVisible(true);
		Load.setBounds(0, 20 + 20 + 56 + 56 + 20, 188, 56);
		this.add(Load);

		//On Click Load
		Load.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e)  {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				{
					fileName = chooser.getSelectedFile().getAbsolutePath();
					if(fileName.contains(".csv")) 
					{	
						MyFrame.game = new Game(fileName);
					}
					else {
						JOptionPane.showMessageDialog(null, "This File is not .CSV file");
					}
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


		//Clear JButton
		Clear = new JLabel(new ImageIcon("./img/Clear.png"));
		Clear.setVisible(true);
		this.add(Clear);
		Clear.setBounds(0, 20 + 20 + 56 + 56 +56 + 20 + 20, 188, 56);

		//ClearH JButton
		ClearH = new JLabel(new ImageIcon("./img/ClearH.png"));
		ClearH.setVisible(true);
		this.add(ClearH);
		ClearH.setBounds(0, 20 + 20 + 56 + 56 +56 + 56 + 20 + 20 + 20, 188, 56);

		//Mouse Info 
		Info = new JLabel(new ImageIcon("./img/Info.png"));
		Info.setVisible(true);
		this.add(Info);
		Info.setBounds(0, 20 + 20 + 56 + 56 + 20 + 56 +56 + 56 + 20 + 20 + 20, 245, 178);

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
		ScoreTextField.setHorizontalAlignment(JTextField.CENTER);
		ScoreTextField.setBorder(null);
		ScoreTextField.setVisible(false);
		ScoreTextField.setText("0.0");
		ScoreTextField.setFont(new Font("Courier New", Font.PLAIN, 15));
		ScoreTextField.setForeground(Color.WHITE);
		ScoreTextField.setBounds(0 + 8, 697 - 120 , 170, 56);
		ScoreTextField.setEditable(false);
		this.add(ScoreTextField);
		// On Click "Solve":
		Solve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(MyFrame.game.getFruitList().size() > 0
						&& panel.FinishedAlgo )
				{
					TotalTF.setText(MyFrame.game.getFruitList().size()+","+MyFrame.game.getPacmanList().size());
					panel.Clear();
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

	}
}
