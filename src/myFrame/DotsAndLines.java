package myFrame;
import java.awt.BasicStroke;
import java.awt.Color;
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
import Path.Path;
import ShortestPathAlgo.Algo;

public class DotsAndLines extends JPanel implements MouseListener{
	Vector<Path> Solutions = new Vector<Path>(); // Must be Synchronized
	private List<Fruit> FruitsList = new ArrayList<Fruit>();
	private List<Pacman> PacmansList = new ArrayList<Pacman>();
	volatile List<Boolean> finished = new ArrayList<Boolean>();


	private List<PacmanThread> threads = new ArrayList<PacmanThread>();
	private Game game;
	private Algo algo;
	private Map map;
	private Image FruitImage = Toolkit.getDefaultToolkit().getImage("./img/Fruit.png");
	private Image PacmanImage = Toolkit.getDefaultToolkit().getImage("./img/Pacman.png");
	private Image bgImage = Toolkit.getDefaultToolkit().getImage("./img/background.png");
	private Image bgImageHover = Toolkit.getDefaultToolkit().getImage("./img/BackGroundHover.png");


	public DotsAndLines(String path, Game game, Map map)
	{	
		this.map = map;
		this.game = game;
		FruitsList.addAll(this.game.getFruitList());
		PacmansList.addAll(this.game.getPacmanList());
		addMouseListener(this); // Mouse Clicks
		setFocusable(true);
	}
	/* * * * * * * * * * * * * * * * * *   getAlgoTime * * * * * * * * * * * * * * * */
	public double getAlgoTime() { return this.algo.getTime(); }

	/* * * * * * * * * * * * * * * * * *   AddSolution * * * * * * * * * * * * * * * */
	public void AddSolution(Path path)
	{
		Solutions.add(path);
	}
	/* * * * * * * * * * * * * * * * * *  paintComponent * * * * * * * * * * * * * * * */
	public void paintComponent(Graphics g)
	{        
		super.paintComponent(g); // Reprint
		g.drawImage(bgImage , 0, 0, this);
		g.drawImage(bgImageHover , 0, 0, this);

		for(Fruit fruit : MyFrame.game.getFruitList())
		{
			Point3D p = (Point3D) fruit.getGeom();
			Point3D p_pixels = map.getPixelFromCord(p);
			int x = (int) p_pixels.x();
			int y = (int) p_pixels.y();
			g.drawImage(FruitImage, x-25, y-25, this);
		}

		for(Path path : Solutions)
		{
			g.setColor(path.color);
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(7));
			Point3D path_point1_in_pixels = map.getPixelFromCord(new Point3D(path.x0,path.y0,0));
			Point3D path_point2_in_pixels = map.getPixelFromCord(new Point3D(path.x1,path.y1,0));
			g2.draw(new Line2D.Double(path_point1_in_pixels.x(), path_point1_in_pixels.y(),
					path_point2_in_pixels.x(), path_point2_in_pixels.y()));    
		}

		for(Pacman pacman : MyFrame.game.getPacmanList())
		{
			Point3D p = (Point3D) pacman.getGeom();
			Point3D p_pixels = map.getPixelFromCord(p);
			int x = (int) p_pixels.x();
			int y = (int) p_pixels.y();
			g.drawImage(PacmanImage, x-25, y-25, this);
		}
		if(finished.size() == threads.size() && !threads.isEmpty())
		{
			MyFrame.VisableAllButtons();
			threads.clear();
			finished.clear();
		}
	}

	/* * * * * * * * * * * * * * * * * *  Solve * * * * * * * * * * * * * * * */
	public void Solve() {
		algo = new Algo(this.game);
		List<Path> lines = algo.getSolution();
		for(Pacman pac : PacmansList)
		{
			threads.add(new PacmanThread(this,pac,lines));	
		}
		for(Thread thread : threads)
		{
			thread.start();
		}
	}
	/* * * * * * * * * * * * * * * * * *  Clear * * * * * * * * * * * * * * * */
	public void Clear() {
		Solutions.clear();
		repaint();
	}
	/* * * * * * * * * * * * * * * * * * Mouse Listener * * * * * * * * * * * * * * * */
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && this.Solutions.isEmpty()  && !game.getPacmanList().isEmpty()) // Left Click
		{
			Point3D p = map.getCordFromPixel(new Point3D(e.getX(),e.getY(),0));	
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Fruit fruit = new Fruit(timeStamp, p);
			if(!MyFrame.game.HasF(fruit)) 
			{
				MyFrame.game.getFruitList().add(fruit);
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON2) // Right click
		{
			MyFrame.game.getPacmanList().clear();
			MyFrame.game.getFruitList().clear();
			Solutions = new Vector<Path>();
			repaint();

		}

		else if(e.getButton() == MouseEvent.BUTTON3 && this.Solutions.isEmpty()) // Right click
		{
			Point3D p = map.getCordFromPixel(new Point3D(e.getX(),e.getY(),0));
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Pacman pacman = new Pacman(timeStamp, "1", "1", p);
			if(!MyFrame.game.HasP(pacman)) 
			{
				MyFrame.game.getPacmanList().add(pacman);
			}
		}
		repaint();

	}

	/* * * * * * * * * * * * * * * * * * Clear Fruits * * * * * * * * * * * * * * * */
	public void clearH() {
		MyFrame.game.getFruitList().clear();
		Clear();
	}
	/* * * * * * * * * * * * * * * * * * Not Used * * * * * * * * * * * * * * * */
	@Override public void mouseClicked(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent arg0) { }
	@Override public void mouseExited(MouseEvent arg0) { }
	@Override public void mouseReleased(MouseEvent arg0) { }
}