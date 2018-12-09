package Path;
import java.awt.Color;

import Fruit.Fruit;
import Geom.Point3D;
import Pacman.Pacman;

public class Path {
	public double x0;
	public double y0;
	public double x1;
	public double y1;
	public Color color;
	public String ID;
	public double time; // time to get from (x0,y0) to (x1,y1)
	public Point3D vec;
	public Path(String ID,double x0, double y0, double x1, double y1, Color color, double time, Point3D vec)
	{
		this.ID = ID;
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		this.color = color;
		this.time = time;
		this.vec = vec;
	}
	
	public String toString()
	{
		return "Pacman ID:" + ID + " ("+x0+","+y0+")"+ " ----> " + "("+x1+","+y1+")\n";
	}
}
