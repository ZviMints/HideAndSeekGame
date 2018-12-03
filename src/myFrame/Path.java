package myFrame;

import java.awt.Color;

public class Path {
	public int x0;
	public int y0;
	public int x1;
	public int y1;
	public Color color;
	public Path(int x0, int y0, int x1, int y1, Color color)
	{
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
