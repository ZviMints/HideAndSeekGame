package Path;
import java.awt.Color;

import Pacman.Pacman;

public class Path {
	public double x0;
	public double y0;
	public double x1;
	public double y1;
	public Color color;
	public Pacman pai;
	public Path(Pacman pac,double x0, double y0, double x1, double y1, Color color)
	{
		this.pai = pac;
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		this.color = color;
	}
	
	public String toString()
	{
		return "("+x0+","+y0+")"+ " ----> " + "("+x1+","+y1+")";
	}
}
