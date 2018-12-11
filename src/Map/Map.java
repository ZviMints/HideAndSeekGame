/**
 * This class represent a Map in the game
 * This class can convert Pixel point to Geo Point and back.
 * @author Tzvi Mints and Or Abuhazira
 */
package Map;

import java.awt.Image;
import java.awt.Toolkit;

import Coords.MyCoords;
import Geom.Point3D;

public class Map {
	/* * * * * * * * * * * * * * * * * * Private constants * * * * * * * * * * * * * * * */
	private int width;
	private int height;
	private Image bgImage;
	private Image bgImageHover = Toolkit.getDefaultToolkit().getImage("./img/BackGroundHover.png");
	// p00     p01 //
	//     **      //
	//     **      //
	// p10     p11 //
	private Point3D p00;
	private Point3D p01;
	private Point3D p10; // NOTE! we do not need 4 Points, 2 Points is enough,
	private Point3D p11; // we left 4 points to next version
	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public Map(String ImagePath, Point3D p00, Point3D p01, Point3D p10, Point3D p11, int d, int e) {
		this.bgImage = Toolkit.getDefaultToolkit().getImage(ImagePath);
		this.p00 = p00;
		this.p01= p01;
		this.p10 = p10;
		this.p11 = p11; 
		this.setWidth(d);
		this.setHeight(e);
	}
	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public int getHeight() { return height;}
	public void setHeight(int e) { this.height = e;}
	public int getWidth() { return width; }
	public void setWidth(int d) { this.width = d;}
	public Image getBgImage() { return bgImage; }
	public void setBgImage(Image bgImage) { this.bgImage = bgImage; }
	public Image getBgImageHover() { return bgImageHover; }
	public void setBgImageHover(Image bgImageHover) { this.bgImageHover = bgImageHover; }

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

		Point3D ans = new Point3D(x,y,input.z());
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

		Point3D ans = new Point3D(dx,dy,input.z());
		return ans;
	}
}