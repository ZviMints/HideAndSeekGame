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
import java.util.Map;

import javax.swing.JPanel;

import Fruit.Fruit;
import Game.Game;
import Geom.Point3D;
import Pacman.Pacman;
import ShortestPathAlgo.Algo;

public class DotsAndLines extends JPanel implements MouseListener{
	private List<Path> lines;
	private List<Fruit> FruitsList = new ArrayList<Fruit>();
	private List<Pacman> PacmansList = new ArrayList<Pacman>();
	private Game game;
	private Algo algo;
	private Image FruitImage = Toolkit.getDefaultToolkit().getImage("./img/Fruit.png");
	private Image PacmanImage = Toolkit.getDefaultToolkit().getImage("./img/Pacman.png");
	private Image bgImage = Toolkit.getDefaultToolkit().getImage("./img/background.png");

	public DotsAndLines (String path, Game game)
	{	
		lines = new ArrayList<Path>();
		this.game = game;
		FruitsList.addAll(this.game.getFruitList());
		PacmansList.addAll(this.game.getPacmanList());
		addMouseListener(this); // Mouse Clicks
		setFocusable(true);
	}
	/* * * * * * * * * * * * * * * * * *   getAlgoTime * * * * * * * * * * * * * * * */
	public double getAlgoTime() { return this.algo.getTime(); }
	
	/* * * * * * * * * * * * * * * * * *  paintComponent * * * * * * * * * * * * * * * */
	public void paintComponent(Graphics g)
	{        
		super.paintComponent(g); // Reprint
        g.drawImage(bgImage , 0, 0, this);

		for(Pacman pacman : MyFrame.game.getPacmanList())
		{
			Point3D p = (Point3D) pacman.getGeom();
			int x = (int) p.x();
			int y = (int) p.y();
			g.drawImage(PacmanImage, x-25, y-25, this);
		}
		for (Path path : lines) {
			g.setColor(path.color);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(7));
            g2.draw(new Line2D.Float(path.x0, path.y0, path.x1, path.y1));
            
		}
		g.setColor(Color.GREEN);
		for(Fruit fruit : MyFrame.game.getFruitList())
		{
			Point3D p = (Point3D) fruit.getGeom();
			int x = (int) p.x();
			int y = (int) p.y();
			g.drawImage(FruitImage, x-25, y-25, this);
		}
	}
	/* * * * * * * * * * * * * * * * * *  Update * * * * * * * * * * * * * * * */
	public void update() {
		algo = new Algo(this.game);
		lines = algo.getSolution();
		repaint();
	}
	/* * * * * * * * * * * * * * * * * *  Clear * * * * * * * * * * * * * * * */
	public void clear() {
		lines = new ArrayList<Path>();
		repaint();
	}
	/* * * * * * * * * * * * * * * * * * Mouse Listener * * * * * * * * * * * * * * * */
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && this.lines.isEmpty()) // Left Click
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