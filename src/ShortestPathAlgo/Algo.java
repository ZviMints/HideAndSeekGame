/**
 * This Class is responsible to find the best greedy way to
 * the shortest road by time
 * @author Tzvi Mints and Or Abuhazira
 */
package ShortestPathAlgo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Coords.MyCoords;
import Fruit.Fruit;
import Game.Game;
import Geom.Point3D;
import Map.Map;
import Pacman.Pacman;
import Pacman.PacmanThread;
import Path.Path;
import myFrame.DotsAndLines;
import myFrame.Menu;

public class Algo {
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	private List<Path> solution;
	private  List<Fruit> FruitsList = new ArrayList<Fruit>();
	private  List<Pacman> PacmansList = new ArrayList<Pacman>();
	private Fruit arrF[];
	private double arrT[];

	/* * * * * * * * * * * * * *  Getters and Setters * * * * * * * * * * * * * * * */
	public List<Path> getSolution() { return solution; }

	/* * * * * * * * * * * * * *  Calculate * * * * * * * * * * * * * * * */
	public Algo(Game game)
	{
		solution = new ArrayList<Path>();
		for(Fruit f : game.getFruitList())
			FruitsList.add(new Fruit(f));
		for(Pacman p : game.getPacmanList())
			PacmansList.add(new Pacman(p));

		arrF = new Fruit[PacmansList.size()];
		arrT = new double[PacmansList.size()];
		Greedy();
	}
	/* * * * * * * * * * * * * *  Brute Force - O(n!) * * * * * * * * * * * * * * * */
	// Not Implement yet
	/* * * * * * * * * * * * * *  Greedy Algorithm * * * * * * * * * * * * * * * */
	private void Greedy() {
		// Initialization Coords for Calculation //
		// O(1) //
		MyCoords coords = new MyCoords();

		// Initialization All Pacmans Time to 0 //
		// O(|P|) : when |P| is the number of total Pacmans //
		for(Pacman pac : PacmansList)
			pac.getInfo().setTime(0);

		// Algorithm //
		// Total : O(|F| * |P| * |F| )
		while(!FruitsList.isEmpty()) // O(|F|) : when |F| is the number of total Fruits //
		{
			/// Find the Closest Fruit To Each Pacman //
			for(int i=0; i < PacmansList.size() ; i ++) // O(|P|)
				FindTimeToNextClosestFruit(PacmansList.get(i),i); // O(|F|)

			/// Find the Min_Time to get To the Closes Fruit From ^^^ //
			double Min_Time = Double.MAX_VALUE;
			int index = -1 ;
			for(int i=0; i<arrT.length; i++) // O(|P|)
				if(Min_Time > arrT[i])
				{
					Min_Time = arrT[i];
					index = i;
				}

			/// Now we have the Index [Saved as Index] of our GREEDY choice //
			Pacman pac = PacmansList.get(index);
			Point3D pac_point = (Point3D)pac.getGeom();
			Fruit Fruit = arrF[index];
			Point3D Closest_point = (Point3D)Fruit.getGeom();
			double distancePacman2Fruit = coords.distance3d(pac_point, Closest_point);
			double speed = Double.parseDouble(pac.getInfo().getSpeed());
			double radius = Double.parseDouble(pac.getInfo().getRadius());
			pac.getInfo().setTime(pac.getInfo().getTime() + ( distancePacman2Fruit - radius ) / speed);
			Point3D vec = new Point3D(coords.vector3D(pac_point,Closest_point));
			/// Make Path From Pacman ---> Fruit //
			Path path = new Path(pac.getInfo().getID(),
					(pac_point).x(),
					(pac_point).y(),
					Closest_point.x(),
					Closest_point.y(),
					pac.getInfo().color
					,( distancePacman2Fruit - radius ) / speed
					,vec);
			/// Translate Pacman to  ---> Fruit //
			pac.translate(vec);	
			/// Addes Path to Solutions List //
			solution.add(path);
			/// Remove the Fruit that has been eaten from the List //
			FruitsList.remove(Fruit);
		}
	}
	/**
	 * The method is repsonsible to find the time from input pacman to the closest fruit by time
	 * @param pacman is the input pacman
	 * @param index is the index of the PacmansList to the current pacman
	 */
	private void FindTimeToNextClosestFruit(Pacman pacman, int index) {	
		double Min_Time = Double.MAX_VALUE;
		Fruit ans = null;
		MyCoords coords = new MyCoords();
		Point3D pacman_point = (Point3D) pacman.getGeom();
		for(int i=0; i<FruitsList.size(); i++)
		{
			Fruit fruit = FruitsList.get(i);
			Point3D fruit_point = (Point3D) fruit.getGeom(); 
			double distance = coords.distance3d(pacman_point, fruit_point);
			double radius = Double.parseDouble(pacman.getInfo().getRadius());
			double speed = Double.parseDouble(pacman.getInfo().getSpeed());
			double current_time = pacman.getInfo().getTime();

			double time2CurrentFruit = current_time + (distance - radius) / speed;
			if(Min_Time > time2CurrentFruit)
			{
				Min_Time = time2CurrentFruit;
				ans = fruit;
			}
		}
		arrF[index] = ans;
		arrT[index] = Min_Time;
	}
	/* * * * * * * * * * * * * *  getGreedyAlgoTime * * * * * * * * * * * * * * * */
	/**
	 * @return the Time of Greedy Algo
	 */
	public double getGreedyAlgoTime() {
		double max = Double.MIN_VALUE ;
		for(int i = 0; i <PacmansList.size(); i++)
		{
			if( max < PacmansList.get(i).getInfo().getTime())
			{
				max =  PacmansList.get(i).getInfo().getTime();
			}
		}
		return max;
	}
	/* * * * * * * * * * * * * *  Run new Algorithm * * * * * * * * * * * * * * * */
	public void setGame(Game game) {
		solution.clear();
		FruitsList.clear();
		PacmansList.clear();
		for(Fruit f : game.getFruitList())
			FruitsList.add(new Fruit(f));
		for(Pacman p : game.getPacmanList())
			PacmansList.add(new Pacman(p));
		arrF = new Fruit[PacmansList.size()];
		arrT = new double[PacmansList.size()];
		Greedy();
	}
}
