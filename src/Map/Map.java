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
	/* * * * * * * * * * * * * * * * * * Get Latitude Ratio * * * * * * * * * * * * * * * */
	public double getLatRatio()
	{
		double ans = getWidth() / ( 100 - 0 );
		return ans;
	}
	/* * * * * * * * * * * * * * * * * * Get Longitude Ratio * * * * * * * * * * * * * * * */
	public double getLonRatio()
	{
		double ans = getHeight() / ( 100 - 0 );
		return ans;
	}

	/* * * * * * * * * * * * * * * * * * GetCord * * * * * * * * * * * * * * * */
	public Point3D getPointFromPoint(Point3D input) // Output Point3D will return in Pixels;
	{	
		// p00(32.105812,35.202190) ****************  p01(32.105848,35.212541) //
		//                          ****************                           //
		//                          ****************                           //
		// p10(32.101942,35.202429) ****************  p11(32.101951,35.212134) //
		int x =  (int) ((getWidth()/360.0) * (180 + input.x()));
		int y =  (int) ((getHeight()/180.0) * (90 - input.y()));
		return new Point3D(x,y,0);
	}
}
