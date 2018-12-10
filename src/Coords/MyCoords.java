/**
 * This class is responsible for actions between Objects of the kind Point3D.
 * @author Tzvi Mints and Or Abuhazira
 * @version 1.0
 */
package Coords;
import Geom.Point3D;

public class MyCoords implements coords_converter {

	/* * * * * * * * * * * * * * * * * * Constant * * * * * * * * * * * * * * * */
	/** This Constant represent radius of the globe **/
	private static final double radius = 6371000;
	/** This Constant represent Math.PI ~ 3.14 **/
	private static final double PI = Math.PI;

	/* * * * * * * * * * * * * * * * * * Override * * * * * * * * * * * * * * * */
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		if(!isValid_GPS_Point(gps)) return null; // Wrong input, will return null
		double x = MTD_x(local_vector_in_meter.x()) + gps.x();
		double y = MTD_y(local_vector_in_meter.y(),gps.x()) + gps.y();
		double z = gps.z() + local_vector_in_meter.z();
		if( y > 180 ) y = (y + 180)%360 - 180; // Check if we get point after 180°
		if ( y < -180 ) y = y + 360; // if we get point bellow -180°
			Point3D ans = new Point3D(x,y,z);
		if(isValid_GPS_Point(ans))
			return ans;
		else
			return null;
	}

	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1)) return -1; // Wrong input, will will return -1
		double dx = gps1.x() - gps0.x();
		double dy = gps1.y() - gps0.y();
		double dz  = gps1.z() - gps0.z();
		double x_m = DTM_x(dx);
		double y_m = DTM_y(dy,gps0.x()); 
		return Math.sqrt(Math.pow(x_m, 2) + Math.pow(y_m, 2) + Math.pow(dz, 2));
	}

	public double distance2d(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1)) return -1; // Wrong input, will will return -1
		double dx = gps1.x() - gps0.x();
		double dy = gps1.y() - gps0.y();
		double x_m = DTM_x(dx);
		double y_m = DTM_y(dy,gps0.x()); 
		return Math.sqrt(Math.pow(x_m, 2) + Math.pow(y_m, 2));
	}

	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1)) return null; // Wrong input, will return null
		double dx = gps1.x() - gps0.x();
		double dy = gps1.y() - gps0.y();
		double dz = gps1.z() - gps0.z();
		return new Point3D(DTM_x(dx),
				DTM_y(dy,gps0.x()), 
				dz);
	}

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		if(!isValid_GPS_Point(gps0) || !isValid_GPS_Point(gps1)) return null; // Wrong input, will return null
		double[] ans = new double[3]; // ans[0,1,2] = [azimuth,elevation,distance]
		double x0 = gps0.x(), x1 = gps1.x(),
				y0 = gps0.y(), y1 = gps1.y(), 
				z0 = gps0.z(), z1 = gps1.z();
		double distance  =  distance3d(gps0,gps1); 
		double azimuth = azimuth(x0,y0,x1,y1); // ** Notice!: We Neglecting the altitude parameter
		double elevation = Math.toDegrees(Math.asin((z1-z0)/distance)); 
		ans[2] = distance;
		ans[1] = elevation;
		ans[0] = azimuth;
		return ans;
	}
	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		// x is latitude ( ----- ) in [-90°,90°]
		// y is longitude ( | )  in [-180°,180°]
		// z is Altitude in [-450,infinity)
		double lat = p.x();
		double lon = p.y();
		double alt = p.z();
		return((lat >= -90 && lat <= 90) &&
				(lon >= -180 && lon <= 180) &&
				(alt >= -450));
	}
	/* * * * * * * * * * * * * * * * * * Azimuth * * * * * * * * * * * * * * * */
	public double azimuth(double lat,double lon,double lat2,double lon2){
		double teta1 = Math.toRadians(lat);
		double teta2 = Math.toRadians(lat2);
		double delta2 = Math.toRadians(lon2-lon);
		double y = Math.sin(delta2) * Math.cos(teta2);
		double x = Math.cos(teta1)*Math.sin(teta2) - Math.sin(teta1)*Math.cos(teta2)*Math.cos(delta2);
		double azimuth = Math.atan2(y,x);
		azimuth = Math.toDegrees(azimuth);
		azimuth = ((azimuth + 360) % 360); 
		return azimuth;
	}

	/* * * * * * * * * * * * * * * * * * Calculation * * * * * * * * * * * * * * * */
	/** This Method get the Lon_Norm from double **/
	private double getLon_Norm(double x) { return Math.cos(x * (PI/180)); }
	/** This Method convert Degrees to Radian **/
	private double DTR (double x) { return x* PI/180; }
	/** This Method convert Radian to Meter for Lat **/
	private double RTM_x (double x) { return Math.sin(x)*radius; }
	/** This Method convert Radian to Meter for Lon **/
	private double RTM_y (double y , double Lon_Norm) { return  Math.sin(y)*radius*Lon_Norm; }
	/** This Method convert Degree to Meter for Lat **/
	public double DTM_x(double x) { return RTM_x(DTR(x)); }
	/** This Method convert Degree to Meter for Lon **/
	public double DTM_y(double y, double x) { return RTM_y(DTR(y),getLon_Norm(x)); } 
	/** This Method convert Radian to Degrees **/
	private double RTD (double x) { return x* 180/PI; }
	/** This Method convert Meter to Radian for Lat **/
	private double MTR_x (double x) { return Math.asin(x/radius); }
	/** This Method convert Meter to Radian for Lon **/
	private double MTR_y (double y , double Lon_Norm) { return  Math.asin(y/(radius*Lon_Norm)); }
	/** This Method convert Meter to Degrees for Lat **/
	public double MTD_x(double x) { return (RTD(MTR_x(x))); }
	/** This Method convert Meter to Degrees for Lon **/
	public double MTD_y(double y, double x) {return RTD(MTR_y(y,getLon_Norm(x)));}
}
