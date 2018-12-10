package Testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import Geom.Point3D;
import Map.Map;

class MapTest {
	// p00(32.105848,35.202429) **  p01(32.105848,35.212541) //
	//                          **                           //
	//                          **                           //
	// p10(32.101951,35.202429) **  p11(32.101951,35.212541) //
	static Point3D p00 = new Point3D(32.105848,35.202429);
	static Point3D p01 = new Point3D(32.105848,35.212541);
	static Point3D p10 = new Point3D(32.101951,35.202429);
	static Point3D p11 = new Point3D(32.101951,35.212541);

	static Map map = new Map("./img/Background.png", p00, p01, p10, p11, 1433,642); 
	@Test
	void testGetPixelFromCord() {
		Point3D answer = new Point3D(32.1045513,35.2035022,10);
		Point3D expected = new Point3D(152.08619540095842,213.62109329569026,10);
		assertEquals(expected.toString(), map.getPixelFromCord(answer).toString());
	}

	@Test
	void testGetCordFromPixel() {
		Point3D expected = new Point3D(32.1045513,35.2035022,10);
		Point3D answer = new Point3D(152.08619540095842,213.62109329569026,10);
		assertEquals(expected.toString(), map.getCordFromPixel(answer).toString());
		}

}
