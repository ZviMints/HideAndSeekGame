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
	public String TimeSave; // זמן שמירת הקובץ
	public String SaveCSV; // שם הקובץ


	public GameToCSV(Game game) {
		this.game=game; // שמירת המשחק הנוכחי
	}
	/**
	 * בניית השורה הראשונה של הנתונים בקובץ
	 * @throws ParseException 
	 */
	public StringBuilder SetHeaderCSV() { 
		StringBuilder header = new StringBuilder(); 
		header.append("Type"); // סוג הנתון פקמן או פירות
		header.append(",");
		header.append("ID"); // מספר סידורי
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
	 * הוספת כל הפקמנים של אותו משחק לקובץ
	 * @throws ParseException 
	 */
	public StringBuilder SetPacmanCSV() {
		StringBuilder pac = new StringBuilder();
		for(Pacman pacman : game.getPacmanList()) {
			Point3D p = new Point3D(pacman.getGeom());
			pac.append("P");
			pac.append(",");
			pac.append(pacman.getInfo().getID());
			pac.append(",");
			pac.append(p.x());
			pac.append(",");
			pac.append(p.y());
			pac.append(",");
			pac.append(p.z());
			pac.append(",");
			pac.append(pacman.getInfo().getSpeed());
			pac.append(",");
			pac.append(pacman.getInfo().getRadius());
			pac.append('\n');

		}
		return pac;
	}
	/**
	 * הוספת כל הפירות של אותו משחק לקובץ
	 * @throws ParseException 
	 */
	public StringBuilder SetFruitCSV() {
		StringBuilder fru = new StringBuilder();
		for(Fruit fruit : game.getFruitList()) {
			Point3D p = new Point3D((Point3D) fruit.getGeom());
			fru.append("F");
			fru.append(",");
			fru.append(fruit.getInfo().getID());
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


	public void MakeCSV() throws FileNotFoundException {
		TimeSave = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());// זמן של שמירת המשחק 
		SaveCSV = "./data/"+TimeSave+".csv";// קריאה לקובץ השם של אותה שעה
		PrintWriter pw = new PrintWriter(new File(SaveCSV));
		StringBuilder csv = new StringBuilder();
		csv.append(SetHeaderCSV()); // הוספת שורה ראונה של הטבלה
		csv.append(SetPacmanCSV()); //הוספת פקמנים לקובץ
		csv.append(SetFruitCSV()); // הוספת פירות לקובץ
		pw.write(csv.toString());
		pw.close();
	}
	
}


