package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import Fruit.Fruit;
import Geom.Point3D;
import Pacman.Pacman;

public class GameToCSV {
	private Game game;
	public String SaveCSV; // The name of the CSV file


	public GameToCSV(Game game , String fileNameCSV) {
		this.game=game; // The current game
		this.SaveCSV = fileNameCSV; // The file name entered by the user
	}

	/**
	 * This method builds the head of the CSV file
	 */

	public StringBuilder SetHeaderCSV() { 
		StringBuilder header = new StringBuilder(); 
		header.append("Type"); 
		header.append(",");
		header.append("ID");
		header.append(",");
		header.append("Lat"); 
		header.append(",");
		header.append("Lon");
		header.append(",");
		header.append("Alt");
		header.append(",");
		header.append("Speed");
		header.append(",");
		header.append("Radius");
		header.append('\n');

		return header;
	}

	/**
	 * This method adds the Pacman to a CSV file
	 */

	public StringBuilder SetPacmanCSV() {
		StringBuilder pac = new StringBuilder();
		for(Pacman pacman : game.getPacmanList()) {
			Point3D p = new Point3D(pacman.getGeom()); // Point of pacman
			pac.append("P");
			pac.append(",");
			pac.append(pacman.getInfo().getID()); // Number of pacman
			pac.append(",");
			pac.append(p.x());
			pac.append(",");
			pac.append(p.y());
			pac.append(",");
			pac.append(p.z());
			pac.append(",");
			pac.append(pacman.getInfo().getSpeed()); // Speed of pacman
			pac.append(",");
			pac.append(pacman.getInfo().getRadius()); // Radius of pacman
			pac.append('\n');

		}
		return pac;
	}
	
	/**
	 * This method adds the Fruit to a CSV file
	 */
	
	public StringBuilder SetFruitCSV() {
		StringBuilder fru = new StringBuilder();
		for(Fruit fruit : game.getFruitList()) {
			Point3D p = new Point3D((Point3D) fruit.getGeom()); // Point of fruit
			fru.append("F");
			fru.append(",");
			fru.append(fruit.getInfo().getID()); // Number of fruit
			fru.append(",");
			fru.append(p.x());
			fru.append(",");
			fru.append(p.y());
			fru.append(",");
			fru.append(p.z());
			fru.append('\n');

		}
		return fru;
	}
	
	/**
	 * This method responsible to Make the CSV file
	 */

	public void MakeCSV() throws FileNotFoundException {
		SaveCSV +=".csv";// // Add a CSV extension to the file
		PrintWriter pw = new PrintWriter(new File(SaveCSV));
		StringBuilder csv = new StringBuilder();
		csv.append(SetHeaderCSV()); // Add the table header
		csv.append(SetPacmanCSV()); // Add pacman to the table
		csv.append(SetFruitCSV()); // Add fruit to the table
		pw.write(csv.toString());
		pw.close();
	}

}


