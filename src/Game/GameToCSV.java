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
	public String TimeSave; // ��� ����� �����
	public String SaveCSV; // �� �����


	public GameToCSV(Game game) {
		this.game=game; // ����� ����� ������
	}
	/**
	 * ����� ����� ������� �� ������� �����
	 * @throws ParseException 
	 */
	public StringBuilder SetHeaderCSV() { 
		StringBuilder header = new StringBuilder(); 
		header.append("Type"); // ��� ����� ���� �� �����
		header.append(",");
		header.append("ID"); // ���� ������
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
	 * ����� �� ������� �� ���� ���� �����
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
	 * ����� �� ������ �� ���� ���� �����
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
		TimeSave = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());// ��� �� ����� ����� 
		SaveCSV = "./data/"+TimeSave+".csv";// ����� ����� ��� �� ���� ���
		PrintWriter pw = new PrintWriter(new File(SaveCSV));
		StringBuilder csv = new StringBuilder();
		csv.append(SetHeaderCSV()); // ����� ���� ����� �� �����
		csv.append(SetPacmanCSV()); //����� ������ �����
		csv.append(SetFruitCSV()); // ����� ����� �����
		pw.write(csv.toString());
		pw.close();
	}
	
}


