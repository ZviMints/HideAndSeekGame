/**
 * This Class represent Path From p0 ---> p1 in the game
 * @author Tzvi Mints and Or Abuhazira
 */
package Path;
import java.awt.Color;

import Fruit.Fruit;
import Geom.Point3D;
import Pacman.Pacman;

public class Path {
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	public double x0; // p0.x in Lat
	public double y0; // p0.y in Lon
	public double z0; // p0.z in Alt
	public double x1; // p1.x in Lat
	public double y1; // p1.y in Lon
	public double z1; // p0.z in Alt
	public Color color; // Color of the Pacman
	public String ID; // ID of the Pacman
	public double time; // time to get from (x0,y0) to (x1,y1)
	public Point3D vec; // Vector from p0 to p1
	public Fruit fruit; // In Order to Remove From GUI
	/* * * * * * * * * * * * * *  Constructor * * * * * * * * * * * * * * * */
	public Path(String ID,double x0, double y0, double z0, double x1, double y1, double z1, Color color, double time, Point3D vec, Fruit fruit)
	{
		this.ID = ID;
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
		this.z0 = z0;
		this.z1 = z1;
		this.color = color;
		this.time = time;
		this.vec = vec;
		this.fruit = fruit;
	}
	/* * * * * * * * * * * * * *  toString * * * * * * * * * * * * * * * */
	public String toString()
	{
		return "ID:" + ID +" " + time +" ("+x0+","+y0+","+z0+")"+ " --> " + "("+x1+","+y1+","+z1+")\n";
	}
}
