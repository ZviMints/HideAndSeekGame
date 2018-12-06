package Map;
import org.junit.jupiter.api.Test;
import Geom.Point3D;
class MapTest {

	@Test
	void TestGetPixelFromCoords() {
		Map map = new Map(1433, 642);
		Point3D p00=new Point3D("32.105848,35.202429,0");
		Point3D p01=new Point3D("32.105848,35.212541,0");
		Point3D p10=new Point3D("32.101951,35.202429,0");
		Point3D p11=new Point3D("32.101951,35.212541,0");

		map.getPixelFromCord(p00);
		map.getPixelFromCord(p01);
		map.getPixelFromCord(p10);
		map.getPixelFromCord(p11);
		
		
	}

}
