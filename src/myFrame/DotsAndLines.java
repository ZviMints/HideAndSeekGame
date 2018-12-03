package myFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Fruit.Fruit;
import Game.Game;
import Geom.Point3D;
import Map.Map;
import Pacman.Pacman;
import ShortestPathAlgo.Algo;

public class DotsAndLines extends JPanel implements MouseListener{
	private List<Path> lines;
	private List<Fruit> FruitsList = new ArrayList<Fruit>();;
	private List<Pacman> PacmansList = new ArrayList<Pacman>();;
	private Game game;
	private Algo algo;

	public DotsAndLines (String path, Game game)
	{	
		lines = new ArrayList<Path>();
		this.game = game;
		FruitsList.addAll(this.game.getFruitList());
		PacmansList.addAll(this.game.getPacmanList());
		addMouseListener(this); // Mouse Clicks
		setFocusable(true);
	}
	/* * * * * * * * * * * * * * * * * *  paintComponent * * * * * * * * * * * * * * * */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); // Reprint
		for(Pacman pacman : MyFrame.game.getPacmanList())
		{
			g.setColor(pacman.getInfo().color);
			Point3D p = (Point3D) pacman.getGeom();
			int x = (int) p.x();
			int y = (int) p.y();
			g.fillOval(x, y, 15, 15);
		}
		for (Path path : lines) {
			g.setColor(path.color);
			g.drawLine(path.x0, path.y0, path.x1, path.y1);
		}
		g.setColor(Color.GREEN);
		for(Fruit fruit : MyFrame.game.getFruitList())
		{
			Point3D p = (Point3D) fruit.getGeom();
			int x = (int) p.x();
			int y = (int) p.y();
			g.fillOval(x, y, 15, 15);
		}
	}
	/* * * * * * * * * * * * * * * * * *  Update * * * * * * * * * * * * * * * */
	public void update() {
		algo = new Algo(this.game);
		lines = algo.getSolution();
		repaint();
	}
	/* * * * * * * * * * * * * * * * * * Mouse Listener * * * * * * * * * * * * * * * */
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) // Left Click
		{
			Point3D p = new Point3D(e.getX(),e.getY(),0);
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Fruit fruit = new Fruit(timeStamp, p);
			if(!MyFrame.game.Has(fruit)) 
			{
				MyFrame.game.getFruitList().add(fruit);
			}
			repaint();
		}
	}
	/* * * * * * * * * * * * * * * * * * Not Used * * * * * * * * * * * * * * * */
	@Override public void mouseClicked(MouseEvent e) { }
	@Override public void mouseEntered(MouseEvent arg0) { }
	@Override public void mouseExited(MouseEvent arg0) { }
	@Override public void mouseReleased(MouseEvent arg0) { }
}