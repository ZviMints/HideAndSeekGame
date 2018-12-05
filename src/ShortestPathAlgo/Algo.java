package ShortestPathAlgo;
import java.util.ArrayList;
import java.util.List;

import Coords.MyCoords;
import Fruit.Fruit;
import Game.Game;
import Geom.Point3D;
import Map.Map;
import Pacman.Pacman;
import Path.Path;
import myFrame.DotsAndLines;
import myFrame.MyFrame;

public class Algo {
	/* * * * * * * * * * *   Private Constants * * * * * * * * * * * * * * * */
	private List<Path> solution;
	private double time = 0;
	private  List<Fruit> FruitsList = new ArrayList<Fruit>();
	private  List<Pacman> PacmansList = new ArrayList<Pacman>();

	/* * * * * * * * * * * * * *  Getters and Setters * * * * * * * * * * * * * * * */
	public List<Path> getSolution() { return solution; }

	/* * * * * * * * * * * * * *  Algo * * * * * * * * * * * * * * * */
	public Algo(Game game)
	{
		solution = new ArrayList<Path>();
		for(Fruit f : game.getFruitList())
			FruitsList.add(new Fruit(f));
		for(Pacman p : game.getPacmanList())
			PacmansList.add(new Pacman(p));
		
		Calculate();
	}

	private void Calculate() {
		MyCoords coords = new MyCoords();

		while(!FruitsList.isEmpty())
		{
			Pacman pac = NextPacman();
			Point3D pac_point = (Point3D)pac.getGeom();
			Fruit Closest = findClosest2Pac(pac_point);
			Point3D Closest_point = (Point3D)Closest.getGeom();

			double distance2ClosestPoint = coords.distance3d(pac_point, Closest_point);
			double speed = Double.parseDouble(pac.getInfo().getSpeed());
			double radius = Double.parseDouble(pac.getInfo().getRadius());
			setTime(getTime() + ( distance2ClosestPoint - radius ) / speed);
			Point3D vec = new Point3D(coords.vector3D(pac_point,Closest_point));
			
			Path path = new Path(pac.getInfo().getID(),
					(pac_point).x(),
					(pac_point).y(),
					Closest_point.x(),
					Closest_point.y(),
					pac.getInfo().color
					,( distance2ClosestPoint - radius ) / speed
					,vec);

			pac.translate(vec);	
			solution.add(path);
			FruitsList.remove(Closest);
		}
	}

	private Pacman NextPacman() {
		double Min_time = Double.MAX_VALUE;
		MyCoords coords = new MyCoords();
		Pacman ans = null;	
		for(int i=0; i< PacmansList.size() ; i++)
		{
			Pacman pacman = PacmansList.get(i);
			for(int j=0; j<FruitsList.size(); j++)
			{
				Fruit fruit = FruitsList.get(j);
				Point3D fruit_point = (Point3D) fruit.getGeom(); 
				Point3D pacman_point = (Point3D) pacman.getGeom();
				double distance = coords.distance3d(pacman_point, fruit_point);
				double radius = Double.parseDouble(pacman.getInfo().getRadius());
				double speed = Double.parseDouble(pacman.getInfo().getSpeed());
				double Pacman_time = (distance - radius) / speed ;
				if(Min_time > Pacman_time)
				{
					Min_time = Pacman_time;
					ans = pacman;
				}
			}
		}
		return ans;
	}


	private Fruit findClosest2Pac(Point3D pacman_point) {
		double min = Double.MAX_VALUE;
		Fruit ans = null;
		MyCoords coords = new MyCoords();
		for(int i=0; i<FruitsList.size(); i++)
		{
			Fruit fruit = FruitsList.get(i);
			Point3D fruit_point = (Point3D) fruit.getGeom(); 
			double distance = coords.distance3d(fruit_point, pacman_point);
			if(min > distance)
			{
				min = distance;
				ans = fruit;
			}
		}
		return ans;
	}

	public double getTime() { return time; }
	public void setTime(double time) { this.time = time; }
}
