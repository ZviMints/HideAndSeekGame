
package myFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import Game.Game;
import Map.Map;

public class MyFrame extends JFrame {

	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	static JSplitPane mainSplittedPane;
	static Map map; // The Map of the game
	static Game game; // Represent the Game Data base
	static DotsAndLines panel; // Represent the Game
	static JPanel MenuPanel; // Game Panel

	/* * * * * * * * * * * * * * * * * * Constructors * * * * * * * * * * * * * * * */
	public MyFrame(String path) {
		game = new Game(path);
		StartPanel();
	}
	public MyFrame() 
	{
		game = new Game("./data/Start.csv");
		StartPanel();
	}
	/* * * * * * * * * * * * * * * * * * Initialize Window * * * * * * * * * * * * * * * */ 
	public void StartPanel()
	{
		// ******** Map ******** ///
		map = new Map(1433,642);   
		panel = new DotsAndLines(game, map);

		// ******** Menu ******** ///
		MenuPanel = new JPanel();
		MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.X_AXIS));
		Menu menu = new Menu(panel);
		menu.setAutoscrolls(true);
		menu.setFocusable(false);
		menu.setLayout(null);
		menu.setAlignmentX(Component.RIGHT_ALIGNMENT);
		menu.setBackground(Color.WHITE);
		MenuPanel.add(menu);


		// ******** Make JSplitPlane ******** ///
		ImageIcon icon = new ImageIcon("./img/icon.png"); // Set Icon to Frame
		this.setIconImage(icon.getImage());
		this.setTitle("T&O OP_3 Exercise"); // Set Title
		mainSplittedPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panel,MenuPanel);
		mainSplittedPane.setOneTouchExpandable(true);
		getContentPane().add(mainSplittedPane, BorderLayout.CENTER);
		setPreferredSize(new Dimension(1645, 700));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainSplittedPane.setResizeWeight(1);                            
		mainSplittedPane.setDividerLocation(1433);
		setVisible(true);
		pack();

		/* * * * * * * * * * * * * * Make Resize able - Divider * * * * * * * * * * * * * * * */   
		mainSplittedPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent changeEvent) {
				if (changeEvent.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
					map.setWidth(mainSplittedPane.getWidth() - mainSplittedPane.getWidth() + mainSplittedPane.getDividerLocation());
				}
			} 
		});

		/* * * * * * * * * * * * * * Make Resize able - Panel * * * * * * * * * * * * * * * */   
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				map.setWidth(mainSplittedPane.getWidth() - mainSplittedPane.getWidth() + mainSplittedPane.getDividerLocation());
				map.setHeight(mainSplittedPane.getSize().height);
			}
		});
	}

	/* * * * * * * * * * * * * * Main * * * * * * * * * * * * * * * */   
	public static void main(String[] args) {
		MyFrame Game = new MyFrame("./data/game_1543684662657.csv");
	}
}