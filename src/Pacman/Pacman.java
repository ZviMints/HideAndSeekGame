/**
 * This Class represent Fruit that implements GIS_Fruit
 * each Element is a Line,
 * each Element have Geom and ID.
 * @author Tzvi Mints and Or Abuhazira
 */
package Pacman;
import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class Pacman implements GIS_Pacman{
	private Geom_element geo;
	private PacmanData PacmanData;

	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public PacmanData getInfo() { return PacmanData; }
	
	/* * * * * * * * * * * * * * * * * * Constructors * * * * * * * * * * * * * * * */
	public Pacman(String id,String speed,String radius, Geom_element geo) { 
		// ************ initialize Geom_element ************ //
		this.geo = geo;
		// ************ initialize Pacman Data ************ //
		PacmanData = new PacmanData(id,speed,radius);
	}
	/* * * * * * * * * * * * * * * * * * toString * * * * * * * * * * * * * * * */
	public String toString()
	{
		String ans = "Pacman:" + "--> ";
		ans += PacmanData.toString();
		ans +=  "," + "Geom element" + ":" + geo;
		return ans;
	}
	/* * * * * * * * * * * * * * * * * * Override * * * * * * * * * * * * * * * */
	@Override
	public Geom_element getGeom() {
		return geo;
	}

	@Override
	public Meta_data getData() {
		return PacmanData;
	}
	@Override
	public void translate(Point3D vec) {
		Point3D p = (Point3D)this.getGeom();
		MyCoords coords = new MyCoords();
		geo = new Point3D(coords.add(p, vec));
	}
}
