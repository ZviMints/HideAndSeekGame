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
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Game.Game;
import Game.GameToCSV;
import Path2KML.Game2KML;


public class Menu extends JPanel{
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	private static JLabel Solve;
	private static JLabel ClearH;
	private static JLabel Clear;
	private static JLabel Load;
	private static JLabel SaveKML;
	private static JLabel SaveCSV;
	private static JTextField TotalTF;
	private static String fileName;
	private static JLabel Info;
	private static Game2KML kml;
	private static GameToCSV csv;
	public MyFrame MainFrame;
	public DotsAndLines panel;
	public String fileNameCSV;
	public String fileNameKML;

	public static JTextField InProgress;
	public static  JTextField ScoreTextField; 


	/* * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public synchronized static void UpdateScoreTime(double time) { ScoreTextField.setText(time + ""); }
	public synchronized double getTime() { return Double.parseDouble(ScoreTextField.getText()); }
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
		SaveKML.setVisible(true);		
		SaveCSV.setVisible(true);
		Info.setVisible(true);
		InProgress.setVisible(false);
		TotalTF.setVisible(false);

	}
	/* * * * * * * * * * * * * *  VisableFalseButtons  * * * * * * * * * * * * * * * */
	/**
	 * This Method is responsible to make all Buttons Visable To False
	 * Used while running the Game
	 */
	private void VisableFalseButtons() {
		Solve.setVisible(false);
		ScoreTextField.setVisible(true);
		ClearH.setVisible(false);
		Clear.setVisible(false);
		Load.setVisible(false);
		SaveKML.setVisible(false);
		SaveCSV.setVisible(false);
		Info.setVisible(false);
		TotalTF.setVisible(true);
		InProgress.setVisible(true);				
	}
	/* * * * * * * * * * * * * *  Constructor  * * * * * * * * * * * * * * * */
	public Menu(MyFrame myFrame)
	{
		this.MainFrame = myFrame;
		this.panel = myFrame.panel; 
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


		//Save kml file JButton
		SaveKML = new JLabel(new ImageIcon("./img/SaveKML.png"));
		SaveKML.setVisible(true);
		SaveKML.setBounds(0, 20 + 20 + 56 + 56 +56 + 56 + 20 + 20 + 20, 188, 56);
		this.add(SaveKML);
		SaveKML.addMouseListener(new MouseAdapter() { 		// ************** On Click Load
			public void mouseClicked(MouseEvent e)  {
				if(!panel.Solutions.isEmpty())
				{	
					JFileChooser fileChooser = new JFileChooser();
					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						fileNameKML = fileChooser.getSelectedFile().getAbsolutePath();
					}
					if(fileNameKML!=null) {
						try {
							kml = new Game2KML(MyFrame.game, panel.algo,fileNameKML);
							kml.MakeFile();
							JOptionPane.showMessageDialog(null, "Success! Saved on path: "+kml.SavePath ); 
						} catch (ParseException e1) {
							e1.printStackTrace();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Error! Start Game First");

			}
		});
		//Save csv file JButton
		SaveCSV = new JLabel(new ImageIcon("./img/SaveCSV.png"));
		SaveCSV.setVisible(true);
		SaveCSV.setBounds(0, 76 + 20 + 20 + 56 + 56 +56 + 56 + 20 + 20 + 20, 188, 56);
		this.add(SaveCSV);
		SaveCSV.addMouseListener(new MouseAdapter() { 		// ************** On Click Load
			public void mouseClicked(MouseEvent e)  {
				if(!MyFrame.game.getFruitList().isEmpty() || !MyFrame.game.getPacmanList().isEmpty())
				{
					JFileChooser fileChooser = new JFileChooser();
					if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						fileNameCSV = fileChooser.getSelectedFile().getAbsolutePath();
					}
					if(fileNameCSV!=null) {
						try {
							csv = new GameToCSV(MainFrame.game , fileNameCSV);
							csv.MakeCSV();
							JOptionPane.showMessageDialog(null, "Success! Saved on path: "+csv.SaveCSV ); 
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Error! Cant Save Empty File");

			}
		});

		//Load JButton
		Load = new JLabel(new ImageIcon("./img/Load.png"));
		Load.setVisible(true);

		Load.setBounds(0, 20 + 20 + 56, 188, 56);
		this.add(Load);

		Load.addMouseListener(new MouseAdapter() { 		// ************** On Click Load
			public void mouseClicked(MouseEvent e)  {
				JFileChooser chooser = new JFileChooser();
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
				{
					fileName = chooser.getSelectedFile().getAbsolutePath();
					if(fileName.contains(".csv")) 
					{
						MyFrame.game = new Game(fileName);
						MainFrame.mainSplittedPane.invalidate();
						MainFrame.mainSplittedPane.setVisible(false);
						MainFrame.mainSplittedPane.removeAll();
						MainFrame.panel = new DotsAndLines(MyFrame.game,MainFrame.map);
						MainFrame.StartPanel();
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
			e1.printStackTrace();
		}


		//Clear JButton
		Clear = new JLabel(new ImageIcon("./img/Clear.png"));
		Clear.setVisible(true);
		this.add(Clear);
		Clear.setBounds(0, 20 + 20 + 56 + 56 + 20, 188, 56);

		//ClearH JButton
		ClearH = new JLabel(new ImageIcon("./img/ClearH.png"));
		ClearH.setVisible(true);
		this.add(ClearH);
		ClearH.setBounds(0, 20 + 20 + 56 + 56 +56 + 20 + 20, 188, 56);

		//Mouse Info 
		Info = new JLabel(new ImageIcon("./img/Info.png"));
		Info.setVisible(true);
		this.add(Info);
		Info.setBounds(0, 60 + 20 + 20 + 56 + 56 + 20 + 56 +56 + 56 + 20 + 20 + 20, 245, 178);

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
		Solve.addMouseListener(new MouseAdapter() { 		// ************** On Click "Solve":
			public void mouseClicked(MouseEvent e) {
				if(MyFrame.game.getFruitList().size() > 0
						&& panel.FinishedAlgo )
				{
					TotalTF.setText(MyFrame.game.getFruitList().size()+","+MyFrame.game.getPacmanList().size());
					panel.Clear();
					VisableFalseButtons();
					panel.Solve();
					UpdateScoreTime(0);
				}
			}
		});

		ClearH.addMouseListener(new MouseAdapter() { 		// ************** On Click "ClearH":
			public void mouseClicked(MouseEvent e) {
				panel.clearH();
				UpdateScoreTime(0);
			}});

		Clear.addMouseListener(new MouseAdapter() { 		// ************** On Click "Clear":
			public void mouseClicked(MouseEvent e) {
				panel.Clear();
				UpdateScoreTime(0);
			}});
	}
}
