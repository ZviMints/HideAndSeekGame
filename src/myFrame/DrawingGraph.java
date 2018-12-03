package myFrame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import Fruit.Fruit;
import Fruit.GIS_Fruit;
import Geom.Point3D;
import Pacman.GIS_Pacman;
public class DrawingGraph extends Canvas {

	/* * * * * * * * * * * * * * * * * * Private Constants * * * * * * * * * * * * * * * */
	private Listener listener;
	private Set<GIS_Fruit> FruitSet = MyFrame.game.getFruitSet();

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public DrawingGraph()
	{
		listener = new Listener();
		this.addMouseListener(listener);
	}

	/* * * * * * * * * * * * * * * * * * paint * * * * * * * * * * * * * * * */
	public void paint(Graphics g) {
		Iterator<GIS_Pacman> it_P = MyFrame.game.getPacmanSet().iterator();
		g.setColor(Color.RED);
		while(it_P.hasNext())
		{
			Point3D p = (Point3D) it_P.next().getGeom();
			int x = (int) p.x();
			int y = (int) p.y();
			g.fillOval(x, y, 15, 15);
		}

		Iterator<GIS_Fruit> it_F = FruitSet.iterator();
		g.setColor(Color.GREEN);
		while(it_F.hasNext())
		{
			Point3D p = (Point3D) it_F.next().getGeom();
			int x = (int) p.x();
			int y = (int) p.y();
			g.fillOval(x - 7, y - 7, 15, 15);
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
				FruitSet.add(fruit);
				System.out.println("New " + fruit);
				repaint();
			}
		}
		/* * * * * * * * * * * * * * * * * * Not Used * * * * * * * * * * * * * * * */
		@Override public void mouseClicked(MouseEvent arg0) { }
		@Override public void mouseEntered(MouseEvent arg0) { }
		@Override public void mouseExited(MouseEvent arg0) { }
		@Override public void mouseReleased(MouseEvent arg0) { }
	}
}

