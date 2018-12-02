package myFrame;

import java.util.Iterator;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Fruit.GIS_Fruit;
import Game.Game;
import Geom.Point3D;
import Map.Map;
import Pacman.GIS_Pacman;

public class myFrame {
	private static Map map;
	private static JFrame frame;
	private static Game game;
	private static ImageIcon image_pacman = new ImageIcon("./img/Pacman.jpg");

	public myFrame(String path)
	{
		frame = new JFrame();
		game = new Game(path);
		initialize();
	}
	private static void initialize() {
		frame.setBounds(150, 250, 1455, 698);
		map = new Map(frame.getWidth(),frame.getHeight());
		frame.setContentPane(new JLabel(new ImageIcon(map.getBackground())));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	public static void Display() {
		Iterator<GIS_Pacman> it_P = game.getPacmanSet().iterator();
		while(it_P.hasNext())
		{
			Point3D point_pacman = (Point3D) it_P.next().getGeom();
			Point3D pacman_in_pixels = map.getPointFromPoint(point_pacman);
			Draw(pacman_in_pixels);			
		}
		Iterator<GIS_Fruit> it_F = game.getFruitSet().iterator();
		while(it_F.hasNext())
		{
			Point3D point_fruit = (Point3D) it_F.next().getGeom();
			Point3D fruit_in_pixels = map.getPointFromPoint(point_fruit);
			Draw(fruit_in_pixels);
		}
	}
	
	
	public static void Draw(Point3D p) {
		int x = (int) p.x();
		int y = (int) p.y();
        JLabel label_pacman = new JLabel(image_pacman);
        label_pacman.setBounds(0, 0, x, y);
        frame.add(label_pacman);
	}
}
