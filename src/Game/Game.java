package Game;
import java.util.*;
import File_format.*;
import Fruit.Fruit;
import Geom.Geom_element;
import Geom.Point3D;
import Pacman.Pacman;
public class Game{
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	private List<Pacman> Pacman_List;
	private List<Fruit> Fruit_List;
	/* * * * * * * * * * * * * * * * * * Setters and Getters * * * * * * * * * * * * * * * */
	public List<Pacman> getPacmanList() { return Pacman_List; }
	public List<Fruit> getFruitList() { return Fruit_List; }

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	public Game() {
		// ************ initialize Set ************ //
		Pacman_List = new ArrayList<Pacman>();
		Fruit_List = new ArrayList<Fruit>();
	}
	public Game(String path)
	{
		// ************ initialize Set ************ //
		Pacman_List = new ArrayList<Pacman>();
		Fruit_List = new ArrayList<Fruit>();

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
				Fruit_List.add(fruit);
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
				Pacman_List.add(pacman);	
			}
		}
	}
	/* * * * * * * * * * * * * * * * * * toString * * * * * * * * * * * * * * * */
	public String toString()
	{
		String ans = "Game Data:" + "\n";
		ans += Pacman_List.size() + " Pacmans:" + "\n";
		ans += Pacman_List.toString();
		ans += Fruit_List.size() + " Fruits:" + "\n";
		ans += Fruit_List.toString();
		return ans;
	}
}