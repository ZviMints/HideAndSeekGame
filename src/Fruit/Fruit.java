/**
 * This Class represent Fruit that implements GIS_Fruit
 * each Fruit has Fruit Data and Geom Element
 * @author Tzvi Mints and Or Abuhazira
 */
package Fruit;
import Coords.MyCoords;
import Geom.Geom_element;
import Geom.Point3D;

public class Fruit implements GIS_Fruit{
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	private Geom_element geo;
	private FruitData FruitData;

	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public FruitData getInfo() { return FruitData; }
	
	/* * * * * * * * * * * * * * * * * * Constructors * * * * * * * * * * * * * * * */
	public Fruit(String id, Geom_element geo) { 
		// ************ initialize Geom_element ************ //
		this.geo = geo;
		// ************ initialize Fruit Data ************ //
		FruitData = new FruitData(id);
	}
	public Fruit(Fruit f) {
		this.geo = f.geo;
		this.FruitData = f.FruitData;
	}

	/* * * * * * * * * * * * * * * * * * toString * * * * * * * * * * * * * * * */
	public String toString()
	{
		String ans = "Fruit:" + "--> ";
		ans += FruitData.toString();
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
		return FruitData;
	}
	@Override
	public void translate(Point3D vec) {
		Point3D p = (Point3D)this.getGeom();
		MyCoords coords = new MyCoords();
		geo = new Point3D(coords.add(p, vec));
	}
}
