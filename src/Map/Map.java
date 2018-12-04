package Map;
import java.util.Collection;

import Coords.MyCoords;
import Geom.Point3D;
import Pacman.Pacman;

public class Map {
	/* * * * * * * * * * * * * * * * * * Private constants * * * * * * * * * * * * * * * */
	private String img;
	private int width;
	private int height;

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public Map(int width, int height) {
		this.img = "";
		this.setWidth(width);
		this.setHeight(height);
	}
	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public String getBackground() { return img; }
	public int getHeight() { return height;}
	public void setHeight(int height) { this.height = height; }
	public int getWidth() { return width; }
	public void setWidth(int width) { this.width = width;}

	/* * * * * * * * * * * * * * * * * * GetCord * * * * * * * * * * * * * * * */
	public Point3D getPointFromPoint(Point3D input) // Output Point3D will return in Pixels;
	{	
		// p00(32.105812,35.202190) ******  p01(32.105848,35.212541) //
		//                          ******                           //
		//                          ******                           //
		// p10(32.101942,35.202429) ******  p11(32.101951,35.212134) //
		Point3D p00=new Point3D("32.105812,35.202190,0");
		Point3D p01=new Point3D("32.105848,35.212541,0");
		Point3D p10=new Point3D("32.101942,35.202429,0");
		MyCoords coords = new MyCoords();
		
		double width = coords.distance3d(p00, p01);
		double height = coords.distance3d(p00, p10);

		double dx =  input.x() - p00.x();
		double dy =  input.y() - p00.y() ;
		
		double meter_x = coords.DTM_x(dx);
		double meter_y = coords.DTM_y(dy, p00.x());

		double x = Math.abs((meter_x/width)*this.width);
		double y = Math.abs((meter_y/height)*this.height);
		Point3D ans = new Point3D(x,y,0);
		return ans;
	}
}