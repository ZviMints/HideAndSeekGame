package myFrame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.sound.sampled.Line;

import Fruit.Fruit;
import Geom.Point3D;
import Pacman.Pacman;
import ShortestPathAlgo.Algo;
public class DrawingGraph extends Canvas {

	/* * * * * * * * * * * * * * * * * * Private Constants * * * * * * * * * * * * * * * */
	private Listener listener;
	private List<Path> lines = MyFrame.algo.getSolution();

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public DrawingGraph()
	{
		listener = new Listener();
		this.addMouseListener(listener);
	}
	
	/* * * * * * * * * * * * * * * * * * paint * * * * * * * * * * * * * * * */
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		for(Pacman pacman : MyFrame.game.getPacmanList())
		{
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


	
	/* * * * * * * * * * * * * * * * * * Mouse Listener * * * * * * * * * * * * * * * */
	private class Listener implements MouseListener 
	{
		@Override
		public void mousePressed(MouseEvent e) {
			Point3D p = new Point3D(e.getX(),e.getY(),0);
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			Fruit fruit = new Fruit(timeStamp, p);
			if(MyFrame.game.Has(fruit)) 
			{
				MyFrame.game.getFruitList().add(fruit);
				System.out.println("New " + fruit);
			}
			MyFrame.algo = new Algo(MyFrame.game);
			lines = MyFrame.algo.getSolution();
			repaint();

		}
		/* * * * * * * * * * * * * * * * * * Not Used * * * * * * * * * * * * * * * */
		@Override public void mouseClicked(MouseEvent arg0) { }
		@Override public void mouseEntered(MouseEvent arg0) { }
		@Override public void mouseExited(MouseEvent arg0) { }
		@Override public void mouseReleased(MouseEvent arg0) { }
	}
}

