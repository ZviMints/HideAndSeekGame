/**
 * This Test Class Testing MyCoords
 * @author Tzvi Mints and Or Abuhazira
 * @version 2.0
 */
package Testing;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;
import java.text.DecimalFormat;
import org.junit.jupiter.api.Test;

import Coords.MyCoords;
import Geom.Point3D;
class MyCoordsTest {
	private MyCoords coords = new MyCoords();
	private Point3D p0;
	private Point3D p1;
	private Point3D vec;
	private DecimalFormat df = new DecimalFormat("#.#######");

	@Test
	void testAdd() {
		p0 = new Point3D(32.103315,35.209039,670);
		p1 = new Point3D(32.106352,35.205225,650);
		vec = new Point3D(337.69899206128815,-359.24920693881893,-20.0);
		assertEquals(coords.add(p0, vec).toString(),p1.toString()); 	

		Point3D N = new Point3D(90,10,0);
		Point3D E = new Point3D(0,180,0);

		Point3D vec_N = new Point3D(10000,0,0);
		Point3D vec_E = new Point3D(0,1000,0);
		try {
		if(coords.add(N, vec_N).x() == 90.0899321975193) fail("Not such Point with Lat:90.0899321975193");
		}
		catch(Exception e)
		{
			// All Good. no such point == null.
		}
		if(coords.add(E, vec_E).y() == 180.00899321609612) fail("Not such Point with Lat:180.00899321609612");
	}

	@Test
	void testDistance3d() {
		p0 = new Point3D(32.103315,35.209039,670);
		p1 = new Point3D(32.106352,35.205225,650);
		double actual = coords.distance3d(p0, p1);
		double expected =  493.4578016;
		assertEquals(expected,Double.parseDouble(df.format(actual)));
	}

	@Test
	void testDistance2d() {
		p0 = new Point3D(32.103315,35.209039,670);
		p1 = new Point3D(32.106352,35.205225,650);
		double actual = coords.distance2d(p0, p1);
		double expected =  493.0523318;
		assertEquals(expected,Double.parseDouble(df.format(actual)));
	}

	@Test
	void testVector3D() {
		p0 = new Point3D(32.103315,35.209039,670);
		p1 = new Point3D(32.106352,35.205225,650);
		vec = coords.vector3D(p0, p1);
		assertEquals(coords.add(p0, vec).toString(),p1.toString()); 
	}

	@Test
	void testAzimuth_elevation_dist() {
		p0 = new Point3D(32.103315,35.209039,670);
		p1 = new Point3D(32.106352,35.205225,650);
		double[] ans = {313.23042032646896, -2.322852232927616, 493.45780156501763};
		assertArrayEquals(ans, coords.azimuth_elevation_dist(p0, p1));
	}

	@Test
	void testIsValid_GPS_Point() {
		Point3D p1_wrong_x_1 = new Point3D(-91,0,0);
		Point3D p1_wrong_x_2 = new Point3D(91,0,0);
		Point3D p1_wrong_y_1 = new Point3D(0,181,0);
		Point3D p1_wrong_y_2 = new Point3D(0,-181,0);
		Point3D p1_wrong_z = new Point3D(0,0,-500);

		Point3D p2_true_1 = new Point3D(-90,-180,-450);
		Point3D p2_true_2 = new Point3D(90,180,Integer.MAX_VALUE);

		assertFalse(coords.isValid_GPS_Point(p1_wrong_x_1));
		assertFalse(coords.isValid_GPS_Point(p1_wrong_x_2));
		assertFalse(coords.isValid_GPS_Point(p1_wrong_y_1));
		assertFalse(coords.isValid_GPS_Point(p1_wrong_y_2));
		assertFalse(coords.isValid_GPS_Point(p1_wrong_z));
		assertTrue(coords.isValid_GPS_Point(p2_true_1));
		assertTrue(coords.isValid_GPS_Point(p2_true_2));

	}
}
