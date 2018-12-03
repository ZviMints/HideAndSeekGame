package ShortestPathAlgo;
import java.util.ArrayList;
import java.util.List;

import Coords.MyCoords;
import Fruit.Fruit;
import Game.Game;
import Geom.Point3D;
import Pacman.Pacman;
import myFrame.Path;

public class Algo {
	/* * * * * * * * * * *   Private Constants * * * * * * * * * * * * * * * */
	private List<Path> solution;
	private double time = 0;
	private  List<Fruit> FruitsList = new ArrayList<Fruit>();;
	private  List<Pacman> PacmansList = new ArrayList<Pacman>();;

	/* * * * * * * * * * * * * *  Getters and Setters * * * * * * * * * * * * * * * */
	public List<Path> getSolution() { return solution; }

	/* * * * * * * * * * * * * *  Algo * * * * * * * * * * * * * * * */
	public Algo(Game game)
	{
		solution = new ArrayList<Path>();
		FruitsList.addAll(game.getFruitList());
		PacmansList.addAll(game.getPacmanList());
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
			
			double distance2ClosestPoint = (pac_point).distance3D(Closest_point);
			double speed = Double.parseDouble(pac.getInfo().getSpeed());
			setTime(getTime() + distance2ClosestPoint / speed);
			
			Path path = new Path((int)(pac_point).x(),
					(int)(pac_point).y(),
					(int)((Point3D)Closest.getGeom()).x(),
					(int) ((Point3D)Closest.getGeom()).y(),
					pac.getInfo().color);

//			Point3D vec = new Point3D(coords.vector3D(Closest_point, pac_point));
//			pac.translate(vec);	
			solution.add(path);
			FruitsList.remove(Closest);
		}
	}

	private Pacman NextPacman() {
		double Min_time = Double.MAX_VALUE;
		Pacman ans = null;	
		for(Pacman pacman : PacmansList)
		{
			for(Fruit fruit : FruitsList)
			{
				Point3D fruit_point = (Point3D) fruit.getGeom(); 
				Point3D pacman_point = (Point3D) pacman.getGeom();
				double Pacman_time = fruit_point.distance3D(pacman_point) /
						              Double.parseDouble(pacman.getInfo().getSpeed());
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
		for(Fruit fruit : FruitsList)
		{
			Point3D fruit_point = (Point3D) fruit.getGeom(); 
			double distance = fruit_point.distance3D(pacman_point);
			if(min > distance)
			{
				min = distance;
				ans = fruit;
			}
		}
		return ans;
	}

	public double getTime() { 	return time; }
	public void setTime(double time) { this.time = time; }
}
