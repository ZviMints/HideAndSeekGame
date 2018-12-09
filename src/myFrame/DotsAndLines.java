/**
 * The Method is the main Panel of the game
 * @author Tzvi Mints and Or Abuhazira
 */
package myFrame;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import Fruit.Fruit;
import Game.Game;
import Geom.Point3D;
import Map.Map;
import Pacman.Pacman;
import Pacman.PacmanThread;
import Path.Path;
import ShortestPathAlgo.Algo;

public class DotsAndLines extends JPanel implements MouseListener{

	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	public volatile List<Boolean> finished = new ArrayList<Boolean>(); // Boolean List for the threads. Responsible to say if thread in index[i] is finished
	public Vector<Path> Solutions = new Vector<Path>(); // Must be Synchronized
	public List<PacmanThread> threads = new ArrayList<PacmanThread>(); // List of all Pacman Threads
	public volatile boolean FinishedAlgo = true;
	private List<Fruit> FruitsList; // All the Fruits
	private List<Pacman> PacmansList;	 // All the Pacmans
	private Game game; // This Game Database
	private Algo algo; // Algorithm of the current game
	private Map map; // Map of current game 
	private Image FruitImage = Toolkit.getDefaultToolkit().getImage("./img/Fruit.png");
	private Image PacmanImage = Toolkit.getDefaultToolkit().getImage("./img/Pacman.png");
	private Image bgImage = Toolkit.getDefaultToolkit().getImage("./img/background.png");
	private Image bgImageHover = Toolkit.getDefaultToolkit().getImage("./img/BackGroundHover.png");
	private Image Finished = Toolkit.getDefaultToolkit().getImage("./img/Finished.png");
	private String newPacman ="New Pacman From Mouse Click #";
	private String newFruit ="New Fruit From Mouse Click #";



	/* * * * * * * * * * * * * * * * * *   Constructor * * * * * * * * * * * * * * * */
	public DotsAndLines(Game game, Map map)
	{	
		this.game = game;
		this.map = map;
		FruitsList = this.game.getFruitList();
		PacmansList = this.game.getPacmanList();
		addMouseListener(this); // Mouse Clicks
		setFocusable(true);
	}
	/* * * * * * * * * * * * * * * * * *   AddSolution * * * * * * * * * * * * * * * */
	/**
	 * The Method is Responsible to add Path to Solutions.
	 * used will Thread is running
	 */
	public synchronized void AddSolution(Path path)
	{
		Solutions.add(path);
	}
	/* * * * * * * * * * * * * * * Main Paint Method! * * * * * * * * * * * * * * * */
	public void paintComponent(Graphics g)
	{        
		super.paintComponent(g); // Reprint
		g.drawImage(bgImage , 0, 0,map.getWidth(),map.getHeight(), this);
		g.drawImage(bgImageHover , 0, 0,map.getWidth(),map.getHeight(), this);


		// ** Print all Fruits that load from .CSV file ** //
		for(Fruit fruit : FruitsList)
		{
			Point3D p = (Point3D) fruit.getGeom();
			Point3D p_pixels = map.getPixelFromCord(p);
			int x = (int) p_pixels.x();
			int y = (int) p_pixels.y();
			g.drawImage(FruitImage, x-25, y-25, this);
		}
		// ** Print all the Path ( Solutions ) ** //
		for(int i = 0;i<Solutions.size();i++)
		{
			Path path = Solutions.get(i);
			g.setColor(path.color);
			Graphics2D g2 = (Graphics2D) g;

			g2.setStroke(new BasicStroke(10));
			Point3D path_point1_in_pixels = map.getPixelFromCord(new Point3D(path.x0,path.y0,0));
			Point3D path_point2_in_pixels = map.getPixelFromCord(new Point3D(path.x1,path.y1,0));
			g2.draw(new Line2D.Double(path_point1_in_pixels.x(), path_point1_in_pixels.y(),
					path_point2_in_pixels.x(), path_point2_in_pixels.y()));    
		}
		// ** Print all Pacmans that load from .CSV file ** //
		for(Pacman pacman : PacmansList)
		{
			Point3D p = (Point3D) pacman.getGeom();
			Point3D p_pixels = map.getPixelFromCord(p);
			int x = (int) p_pixels.x();
			int y = (int) p_pixels.y();
			g.setColor(pacman.getInfo().color);
			g.fillOval(x-13, y-5, 30, 30);
			g.drawImage(PacmanImage, x-25, y-25, this);
		}
		// ** Print all the Hats the Finish the Algo ** //
		for(PacmanThread thread : threads)
		{
			if(!thread.isAlive())
			{
				Point3D p = (Point3D) thread.pacman.getGeom();
				Point3D p_pixels = map.getPixelFromCord(p);
				int x = (int) p_pixels.x();
				int y = (int) p_pixels.y();
				g.drawImage(Finished, x-30, y-47, this);
			}
		}
	}
	/* * * * * * * * * * * * * * * * * *  Solve * * * * * * * * * * * * * * * */
	/**
	 * This Method is responsible to Solve the current game using Algo Class
	 * Will start working after click on "Start" at Frame.
	 */
	public void Solve() {
		threads.clear(); // Remove the Hats
		 FinishedAlgo = false;
		algo = new Algo(this.game);
		List<Path> lines = algo.getSolution();
		double max_time = algo.getGreedyAlgoTime();
		
		for(Pacman pac : PacmansList)
			threads.add(new PacmanThread(this,pac,lines,max_time));	
		
		for(Thread thread : threads)
			thread.start();
	}

	/* * * * * * * * * * * * * * * * * *  Clear * * * * * * * * * * * * * * * */
	/**
	 * Clear the Solutions, Remove all the Path's.
	 */
	public void Clear() {
		Solutions.clear();
		repaint();
	}
	/* * * * * * * * * * * * * * * * * * Mouse Listener * * * * * * * * * * * * * * * */
	@Override
	public void mousePressed(MouseEvent e) {
		// ** Left Mouse Click - Add Fruit ** //
		if(e.getButton() == MouseEvent.BUTTON1 
				&& FinishedAlgo
				&& !PacmansList.isEmpty()) // Left Click
		{
			Point3D p = map.getCordFromPixel(new Point3D(e.getX(),e.getY(),0));	
			Fruit fruit = new Fruit(newFruit + FruitsList.size(), p);
			if(!HasF(fruit)) 
			{
				FruitsList.add(fruit);
			}
		}
		// ** Middle Mouse Click - Clear ** //
		else if(e.getButton() == MouseEvent.BUTTON2
				&& FinishedAlgo)
		{
			PacmansList.clear();
			threads.clear();
			Menu.UpdateScoreTime(0);
			clearH();
		}
		// ** Right Mouse Click - Add Pacman ** //
		else if(e.getButton() == MouseEvent.BUTTON3 && FinishedAlgo) // Right click
		{
			Point3D p = map.getCordFromPixel(new Point3D(e.getX(),e.getY(),0));
			Pacman pacman = new Pacman(newPacman + PacmansList.size(), "1", "1", p);
			if(!HasP(pacman)) 
			{
				PacmansList.add(pacman);
			}
		}
		repaint();
	}

	/* * * * * * * * * * * * * * * * * * Clear Fruits * * * * * * * * * * * * * * * */
	/**
	 * Clear the map, Remove all the Fruits.
	 */
	public void clearH() {
		FruitsList.clear();
		Clear();
	}
	/* * * * * * * * * * * * * * * * * * Has Fruit * * * * * * * * * * * * * * * */
	/**
	 * Check if Fruit is already Exists in the Fruit List
	 * @param f is the input Fruit
	 * @return true iff Fruitslist contains list
	 */
	public boolean HasF(Fruit f)
	{

		for(Fruit current : FruitsList)
		{
			Point3D current_point = (Point3D)current.getGeom();
			Point3D f_point = (Point3D)f.getGeom();
			if(f_point.equals(current_point)) return true;
		}
		return false;	
	}
	/* * * * * * * * * * * * * * * * * * Has Pacman * * * * * * * * * * * * * * * */
	/**
	 * Check if Pacman is already Exists in the Pacman List
	 * @param f is the input Pacman
	 * @return true iff PacmansList contains list
	 */
	public boolean HasP(Pacman p)
	{

		for(Pacman current : PacmansList)
		{
			Point3D current_point = (Point3D)current.getGeom();
			Point3D f_point = (Point3D)p.getGeom();
			if(f_point.equals(current_point)) return true;
		}
		return false;	
	}
	/* * * * * * * * * * * * * * * * * * Not Used * * * * * * * * * * * * * * * */
	@Override public void mouseClicked(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent arg0) { }
	@Override public void mouseExited(MouseEvent arg0) { }
	@Override public void mouseReleased(MouseEvent arg0) { }
}