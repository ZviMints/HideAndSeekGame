package Map;
import Coords.MyCoords;
import Geom.Point3D;

public class Map {
	/* * * * * * * * * * * * * * * * * * Private constants * * * * * * * * * * * * * * * */
	private String img;
	private double width;
	private double height;

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public Map(int width, int height) {
		this.img = "./img/background.png";
		this.setWidth(width);
		this.setHeight(height);
	}
	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public String getBackground() { return img; }
	public double getHeight() { return height;}
	public void setHeight(int height) { this.height = height; }
	public double getWidth() { return width; }
	public void setWidth(int width) { this.width = width;}

	/* * * * * * * * * * * * * * * * * * GetCord * * * * * * * * * * * * * * * */
	public Point3D getPointFromPoint(Point3D input) // Output Point3D will return in Pixels;
	{
		MyCoords coords = new MyCoords();
		Point3D input_in_meter = coords.PointInMeters(input);
		return new Point3D(Convert2PixelFromLat(input_in_meter.x()),
			               Convert2PixelFromLon(input_in_meter.y()),
						   input.z());
	}



	public double Convert2PixelFromLon(double y)
	{
		
		// (32.105812,35.202190) ****************  (32.105848,35.212541) //
		//                       ****************                        //
		//                       ****************                        //
		// (32.101942,35.202429) ****************  (32.101951,35.212134) //
		MyCoords coords = new MyCoords();
		Point3D p00 = new Point3D(32.105812,35.202190,0);
		Point3D p10 = new Point3D(32.101942,35.202429,0);
		double dy = coords.distance2d(p00, p10);
		double ratio = dy / width;
		return ratio*y;
	}

	public double Convert2PixelFromLat(double x) // x is In Meters
	{
		// (32.105812,35.202190) ****************  (32.105848,35.212541) //
		//                       ****************                        //
		//                       ****************                        //
		// (32.101942,35.202429) ****************  (32.101951,35.212134) //
		MyCoords coords = new MyCoords();
		Point3D p00 = new Point3D(32.105812,35.202190,0);
		Point3D p01 = new Point3D(32.105848,35.212541,0);
		double dx = coords.distance2d(p00, p01);
		double conts = 3779.5275590551; // Meter in Pixel
		double ratio = conts*dx / width; // f(width);
		return ratio*x;
	}
}
