/**
 * This Class is responsible to find the best greedy way to find
 * the shortest road by time
 * @author Tzvi Mints and Or Abuhazira
 */
package ShortestPathAlgo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
	private  List<Fruit> FruitsList = new ArrayList<Fruit>(); // Fruits List
	private  List<Pacman> PacmansList = new ArrayList<Pacman>(); // Pacmans List
	private Fruit arrF[]; // Array that represent the Fruits
	private double arrT[]; // Array that represent the Distances
	public String StartGameTime; // Start Time of the Algorithm

	/* * * * * * * * * * * * * *  Getters and Setters * * * * * * * * * * * * * * * */
	public List<Path> getSolution() { return solution; }

	/* * * * * * * * * * * * * *  Constructors * * * * * * * * * * * * * * * */
	public Algo(Game game) // For the First time
	{
		StartGameTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		solution = new ArrayList<Path>();
		for(Fruit f : game.getFruitList())
			FruitsList.add(new Fruit(f));
		for(Pacman p : game.getPacmanList())
			PacmansList.add(new Pacman(p));

		arrF = new Fruit[PacmansList.size()];
		arrT = new double[PacmansList.size()];
		Greedy();
	}
	/* * * * * * * * * * * * * *  Run new Algorithm * * * * * * * * * * * * * * * */
	public void setGame(Game game) {
		StartGameTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
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
	/* * * * * * * * * * * * * *  Brute Force - O(n!) * * * * * * * * * * * * * * * */
	// Not Implement yet, Maybe at 4.0 version
	/* * * * * * * * * * * * * *  Greedy Algorithm * * * * * * * * * * * * * * * */
	/**
	 * The Greedy Algorithm Of The Game
	 */
	private void Greedy() {

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

			/// Find the Min_Time to get To the Closes Fruit From All Fruits//
			int Min_Time_Index = FindIndexOfMinTime(); // O(|P|)

			/// Now we have the Index [Saved as Min_Time_Index] of our GREEDY choice //
			Pacman Pacman = PacmansList.get(Min_Time_Index);
			Fruit Fruit = arrF[Min_Time_Index];
			MakePathAndTransfer(Pacman,Fruit);


			/// Remove the Fruit that has been eaten from the List //
			FruitsList.remove(Fruit);
		}
	}
	/* * * * * * * * * * * * * *  MakePathAndTransfer * * * * * * * * * * * * * * * */
	/**
	 * This Method is responsible to Make Path From Point3D (fit to eaten Fruit)
	 * and Pacman, moreover, responsible to transfer Pacman to fruit point
	 * @param Pacman is the current Pacman
	 * @param Fruit_Point3D is the current Point of Fruit
	 */
	private void MakePathAndTransfer(Pacman Pacman, Fruit fruit) {
		//Init Fruit Point
		Point3D Fruit_Point3D = (Point3D)fruit.getGeom();
		// Initialize Coords
		MyCoords coords = new MyCoords();

		// Initialize current Pacman Point3D
		Point3D Pacman_Point3D = (Point3D)Pacman.getGeom();

		// Initialize relevant information about Pacman
		double speed = Double.parseDouble(Pacman.getInfo().getSpeed());
		double radius = Double.parseDouble(Pacman.getInfo().getRadius());

		//info[0,1,2] = [azimuth,elevation,distance]
		double[] info = coords.azimuth_elevation_dist(Pacman_Point3D, Fruit_Point3D); 

		//Initialize Vector
		Point3D vec;
		double time;
		if(info[2] <= radius)
		{
			vec = new Point3D(0,0,0);
			time = 0;
		}
		else
		{
			double x = Math.cos(Math.toRadians(info[0])) * (info[2] - radius);
			double y = Math.sin(Math.toRadians(info[0])) * (info[2] - radius);
			double z = Math.sin(Math.toRadians(info[1]))

					* (info[2] - radius);
			if(Double.isNaN(z)) z = 0 ;
			time = (info[2] - radius ) / speed;
			vec = new Point3D(x,y,z);

		}

		// Set time to the Pacman, ( time that the path is taking)
		Pacman.getInfo().setTime(Pacman.getInfo().getTime() + time);

		//Initialize destination point
		Point3D dist = coords.add(Pacman_Point3D, vec);

		/// Make Path From Pacman ---> destination //
		Path path = new Path(Pacman.getInfo().getID(), // Pacman ID
				(Pacman_Point3D).x(), // From Where Lat
				(Pacman_Point3D).y(), // From Where Lon
				(Pacman_Point3D).z(), // From Where Alt
				dist.x(),   // To Where Lat
				dist.y(),   // To Where Lon
				dist.z(),   // To Where Alt
				Pacman.getInfo().color, // Pacman Color ( for the path color )
				time,
				vec, // The Vector
				fruit
				);

		/// Translate Pacman to  ---> destination //
		Pacman.translate(vec);	
		/// Addes Path to Solutions List //
		solution.add(path);		
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
				max =  PacmansList.get(i).getInfo().getTime();
		}
		if(max == Double.MIN_VALUE) return 0;
		else
			return max;
	}
	/* * * * * * * * * * * * * *  FindIndexOfMinTime * * * * * * * * * * * * * * * */	
	/**
	 * Return the index of the Minimum value of arrT
	 */
	private int FindIndexOfMinTime() {
		double Min_Time = Double.MAX_VALUE;
		int index = -1 ;
		for(int i=0; i<arrT.length; i++) // O(|P|)
			if(Min_Time > arrT[i])
			{
				Min_Time = arrT[i];
				index = i;
			}	
		return index;
	}
}
