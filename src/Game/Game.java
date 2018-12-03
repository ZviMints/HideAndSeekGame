package Game;
import java.util.*;
import File_format.*;
import Fruit.Fruit;
import Fruit.GIS_Fruit;
import Geom.Geom_element;
import Geom.Point3D;
import Pacman.GIS_Pacman;
import Pacman.Pacman;
public class Game{
	private Set<GIS_Pacman> Pacman_Set;
	private Set<GIS_Fruit> Fruit_Set;
	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public Set<GIS_Pacman> getPacmanSet() { return Pacman_Set; }
	public Set<GIS_Fruit> getFruitSet() { return Fruit_Set; }
	
	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public Game(String path)
	{
		// ************ initialize Set ************ //
		Pacman_Set = new HashSet<GIS_Pacman>();
		Fruit_Set = new HashSet<GIS_Fruit>();

		// ************ initialize Sets ************ //
		CSVToMatrix cr = new CSVToMatrix(path);
		for(int i=1; i < cr.getRowsSize(); i++)
		{
			if(cr.getRowAtIndexI(i).get(0).equals("F")) // Its Fruit!
			{
				String id = cr.getRowAtIndexI(i).get(1);
				Geom_element geo = new Point3D(Double.parseDouble(cr.getRowAtIndexI(i).get(2)) // Latitude
						                      ,Double.parseDouble(cr.getRowAtIndexI(i).get(3)) // Longitude
						                      ,Double.parseDouble(cr.getRowAtIndexI(i).get(4))); // Altitude
				Fruit fruit = new Fruit(id,geo);
				Fruit_Set.add(fruit);
			}
			else // Its Pacman!
			{
				String id = cr.getRowAtIndexI(i).get(1);
				String speed = cr.getRowAtIndexI(i).get(5);
				String radius = cr.getRowAtIndexI(i).get(6);
				Geom_element geo = new Point3D(Double.parseDouble(cr.getRowAtIndexI(i).get(2)) // Latitude
						                       ,Double.parseDouble(cr.getRowAtIndexI(i).get(3)) // Longitude
					                           ,Double.parseDouble(cr.getRowAtIndexI(i).get(4))); // Altitude
				Pacman pacman = new Pacman(id,speed, radius, geo);
				Pacman_Set.add(pacman);	
			}
		}
	}
	/* * * * * * * * * * * * * * * * * * Has Fruit * * * * * * * * * * * * * * * */
	public boolean Has(Fruit f)
	{
		Iterator<GIS_Fruit> it_F = Fruit_Set.iterator();
		while(it_F.hasNext())
		{
			Fruit current = (Fruit) it_F.next();
			Point3D current_point = (Point3D)current.getGeom();
			Point3D f_point = (Point3D)f.getGeom();
			if(f_point.equals(current_point)) return false;
		}
		return true;	
	}
	/* * * * * * * * * * * * * * * * * * toString * * * * * * * * * * * * * * * */
	public String toString()
	{
		String ans = "Game Data:" + "\n";
		ans += Pacman_Set.size() + " Pacmans:" + "\n";
		Iterator<GIS_Pacman> it_P = Pacman_Set.iterator();
		while(it_P.hasNext())
			ans += "  " + it_P.next() + "\n";

		ans += Fruit_Set.size() + " Fruits:" + "\n";
		Iterator<GIS_Fruit> it_F = Fruit_Set.iterator();
		while(it_F.hasNext())
			ans += "  " +  it_F.next() + "\n";
		return ans;
	}
}