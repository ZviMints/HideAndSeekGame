/**
 * This class represent a Map in the game
 * This class can convert Pixel point to Geo Point and back.
 * @author Tzvi Mints and Or Abuhazira
 */
package Map;

import Coords.MyCoords;
import Geom.Point3D;

public class Map {
	/* * * * * * * * * * * * * * * * * * Private constants * * * * * * * * * * * * * * * */
	private double width;
	private double height;
	// p00(32.105848,35.202429) **  p01(32.105848,35.212541) //
	//                          **                           //
	//                          **                           //
	// p10(32.101951,35.202429) **  p11(32.101951,35.212541) //
	private Point3D p00 = new Point3D("32.105848,35.202429,0");
	private Point3D p01=new Point3D("32.105848,35.212541,0");
	private Point3D p10=new Point3D("32.101951,35.202429,0");


	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public Map(double d, double e) {
		this.setWidth(d);
		this.setHeight(e);
	}
	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public double getHeight() { return height;}
	public void setHeight(double e) { this.height = e; }
	public double getWidth() { return width; }
	public void setWidth(double d) { this.width = d;}

	/* * * * * * * * * * * * * * * * * * GetCord * * * * * * * * * * * * * * * */
	/**
	 * This Method gets a Point in Geo Coords and convert it into Pixel point
	 * @param input is the point in Geo Coords ( load from csv file )
	 * @return current point in Pixel point (pixel_x,pixel_y)
	 */
	public Point3D getPixelFromCord(Point3D input) // Output Point3D will return in Pixels;
	{	
		MyCoords coords = new MyCoords();

		double TotalXInMeters = coords.distance2d(p00, p01);
		double TotalYInMeters = coords.distance2d(p00, p10);

		Point3D find_x = new Point3D(32.105848,input.y(),0);
		double xInMeters = coords.distance2d(p00,find_x);

		Point3D find_y = new Point3D(input.x(),35.202429,0);
		double yInMeters = coords.distance2d(p00,find_y);

		double x = ( xInMeters / TotalXInMeters ) * getWidth();
		double y = ( yInMeters / TotalYInMeters ) * getHeight();

		Point3D ans = new Point3D(x,y,0);
		return ans;
	}
	/**
	 * This Method gets a Point in Pixels and convert it into Geo Coord
	 * @param input is the point in Pixels ( after click on the screen )
	 * @return current point in Geo coords (Lat,Lon,Alt)
	 */
	public Point3D getCordFromPixel(Point3D input) {
		MyCoords coords = new MyCoords();

		double TotalXInMeters = coords.distance2d(p00, p01);
		double TotalYInMeters = coords.distance2d(p00, p10);

		double xInMeters = Math.abs(( input.x() * TotalXInMeters ) / getWidth());
		double yInMeters = Math.abs(( input.y() * TotalYInMeters ) / getHeight());

		double x = coords.MTD_x(yInMeters);
		double y = coords.MTD_y(xInMeters, p00.x());

		double dx =   p00.x() - x ;
		double dy =   p00.y() + y ;

		Point3D ans = new Point3D(dx,dy,0);
		return ans;
	}
}