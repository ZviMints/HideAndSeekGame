package ShortestPathAlgo;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import Fruit.Fruit;
import Game.Game;
import Geom.Point3D;
import Pacman.Pacman;
import myFrame.Path;

public class Algo {
	private List<Path> solution;
	private double time = 0;
	private  List<Fruit> FruitsList = new ArrayList<Fruit>();;
	private  List<Pacman> PacmansList = new ArrayList<Pacman>();;


	public List<Path> getSolution() { return solution; }


	public Algo(Game game)
	{
		solution = new ArrayList<Path>();
		FruitsList.addAll(game.getFruitList());
		PacmansList.addAll(game.getPacmanList());
		Calculate();
	}

	private void Calculate() {
		Pacman pac = PacmansList.get(0);
		Point3D pac_point = (Point3D)PacmansList.get(0).getGeom();
		while(!FruitsList.isEmpty())
		{
			Fruit Closest = findClosest2Pac(pac_point);
			double distance2ClosestPoint = (pac_point).distance3D((Point3D)Closest.getGeom());
			double speed = Double.parseDouble(pac.getInfo().getSpeed());
			setTime(getTime() + distance2ClosestPoint / speed);
			Path path = new Path((int)(pac_point).x(),
					(int)(pac_point).y(),
					(int)((Point3D)Closest.getGeom()).x(),
					(int) ((Point3D)Closest.getGeom()).y(),
					Color.BLACK);
			System.out.println(path);
			pac_point = (Point3D)Closest.getGeom();
			solution.add(path);
			FruitsList.remove(Closest);
		}			
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
