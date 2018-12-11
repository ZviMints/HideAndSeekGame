/**
 * This Class Converting Game into KML file.
 */
package Path2KML;
import java.io.File;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.Date;

import Game.Game;
import Geom.Point3D;
import Pacman.Pacman;
import Path.Path;
import ShortestPathAlgo.Algo;

public class Game2KML {
	private Algo algo;
	private Game game;
	private String KML_BODY;
	private String KML_HEAD;
	private String KML_TAIL;
	public String TimeSave;
	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	/**
	 * Constructor of the KML file. make Header, make Body and make Tail.
	 * @param obj is the current Object, can be layer, element or project.
	 * @param path is the path to *put* the KML file
	 * @throws ParseException 
	 */
	public Game2KML(Game game,Algo algo) throws ParseException
	{
		this.algo = algo;
		this.game = game;
		MakeHead();
		ConvertPath(algo,game);
		MakeTail();
		try {
			MakeFile(game);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* * * * * * * * * * * * * * * * * * Convert * * * * * * * * * * * * * * * */
	/**
	 * This method is responsible to Convert Element to KML.
	 * @throws ParseException 
	 */
	private void ConvertPath(Algo algo,Game game) throws ParseException {
		String AlgoStartTime = algo.StartGameTime; // ���� ���������� �����
		for(Pacman pacman : game.getPacmanList()) { // ���� �� �� ������� 
			KML_BODY += CreateFolder(pacman.getInfo().getID(),algo.getSolution(),AlgoStartTime);//����� ��� �"� ����� �� �����,����� ���������,���� ����� ���������
		}
	}
	/* * * * * * * * * * * * * * * Make Head,Body,Tail * * * * * * * * * * * * * * * */
	private void MakeHead() {
		KML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" 
				+ "<kml xmlns=\"http://www.opengis.net/kml/2.2\" "
				+ "xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n"
				+ "<Document>\n"
				+"<Style id=\"red\"><IconStyle><Icon>\n" 
				+"<href>http://maps.google.com/mapfiles/ms/icons/red-dot.png</href>\n"  
				+ "</Icon></IconStyle></Style>\n" 
				+ "<Style id=\"yellow\"><IconStyle><Icon>\n" 
				+ "<href>http://maps.google.com/mapfiles/ms/icons/yellow-dot.png</href>\n" 
				+ "</Icon></IconStyle></Style>\n" 
				+"<Style id=\"green\"><IconStyle><Icon>\n" 
				+ "<href>http://maps.google.com/mapfiles/ms/icons/green-dot.png</href>\n" 
				+"</Icon></IconStyle></Style>"
				+Style;

	}
	private void MakeTail() {
		KML_TAIL = "</Document>\n</kml>";
	}
	/* * * * * * * * * * * * * * * * * * KML Convert * * * * * * * * * * * * * * * */
	/**
	 * ������� ������ �� �� �� ���� ������ �� ������� ���� ����� �� ��������� ������� �� ������ ������ 
	 * @throws ParseException 
	 */
	public String CreateFolder(String Name, List<Path> list, String AlgoStartTime) throws ParseException
	{
		String body = "<Folder>" + "\n" 
				+"<name>"+ Name +"</name>" + "\n" //�� �����
				+fruit(Name, list) // ���� ������ �� ����
				+"<Placemark>"
				+ "<name>"+ Name +"</name>" + "\n" 
				+ "<styleUrl>#multiTrack</styleUrl>"
				+ "<gx:Track>"
				+ GetWhenFromPacman(Name,list,AlgoStartTime) // ���� �� ������ �"� ��� ������ ������ ����
				+ "</gx:Track>" + "\n"
				+ "</Placemark>" + "\n"
				+"</Folder>";
		return body;
	}
	/**
	 * �������� ����� �� �� ���� ����� �� ������ ����� ���� ����� �� ������ ����� ���� ���  
	 * @return
	 * @throws ParseException 
	 */
	private String GetWhenFromPacman(String Name, List<Path> list, String AlgoStartTime) throws ParseException {
		String ans =""; // ����� ������ ������� �� �� ���� �� ������ �����
		for (Path path :list) { // ���� �� �� ������ 
			if(path.ID.equals(Name)) { //����� �� ����� ���� ��� ������ ������ 
				if(!ans.contains(path.y0 +" "+path.x0)){//���� ������ ������� �� �����
					ans+="<when>"+TimeFormatKml(AlgoStartTime)+"</when>"+ "\n" //����� ���� ������� ���� ������
							+"<gx:coord>"+path.y0 +" "+path.x0 +" "+path.z0+"</gx:coord>"+ "\n";
				}
				AlgoStartTime = Time(AlgoStartTime,(int)path.time);//����� ���� ���� ����� ����� ���� ���� ������ �� ���������
				ans+="<when>"+TimeFormatKml(AlgoStartTime)+"</when>"+ "\n" 
						+"<gx:coord>"+path.y1 +" "+path.x1+" "+path.z0+"</gx:coord>"+ "\n";
			}
		}
		return ans;
	}
	/**
	 * ������� ������ �� �� ���� ����� �� ����� ��������� ������� �� ����� ������ ����  
	 * @return
	 * @throws ParseException 
	 */
	public String fruit(String Name , List<Path> list) {
		String fruit=""; //������ ����� ������ �� �� �������� �� ������
		for (Path path :list) { //���� �� ������ ���� ���� �� ����� ������
			if(path.ID.equals(Name)){ //����� �� ���� ������ ���� ������
				fruit+="<Placemark>\n" 
						+"<styleUrl>#red</styleUrl>\n"  
						+"<Point>\n"  
						+"<coordinates>"+path.y1 +" "+path.x1+" "+path.z0+"</coordinates>\n" //����� ������ �� ���� 
						+"</Point>\n" 
						+"</Placemark>\n";
			}

		}
		return fruit;
	}
	/* * * * * * * * * * * * * * * * * * File Writer * * * * * * * * * * * * * * * */
	/**
	 * This method responsible to Make the KML file
	 * @param startGameTime 
	 * @throws Exception
	 */
	public void MakeFile(Game game) throws Exception
	{
		TimeSave = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());// ��� �� ����� ����� 
		String SavePath = "./data/"+TimeSave+".kml";//����� ����� ��� �� ����
		PrintWriter pw = new PrintWriter(new File(SavePath));
		StringBuilder sb = new StringBuilder();	
		sb.append(KML_HEAD + KML_BODY + KML_TAIL);
		pw.write(sb.toString());
		pw.close();

	}
	/* * * * * * * * * * * * * * * toString * * * * * * * * * * * * * * * */
	public String toString()
	{
		return KML_HEAD + KML_BODY + KML_TAIL;
	}
	/**
	 * ������� ������ ��� ������ ��������� ������ ���� ����� ���� ������� �� ���� �� ����� ���� �� ��������� 
	 * @throws Exception
	 */
	public String Time(String AlgoTime , int second) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//����� �� ���� 
		Date d = df.parse(AlgoTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, second);
		String newTime = df.format(cal.getTime());
		return newTime;
	}
	/**
	 * ������� ������ ��� ������ ���� ������ ��� 
	 * @throws Exception
	 */
	public String TimeFormatKml(String time) {
		//������ �� ������� �� ���� ����� �-KML
		time=time.replaceAll("\\s","T");
		time+="Z";
		return time;
	}
	
//	public static String getUTFromString(String time)
//	{
//		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+2")); // Israel Time Zone
//		try {
//			unixTime = dateFormat.parse(time).getTime();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		System.out.println(unixTime);
//		unixTime = unixTime / 1000;
//		return unixTime;
//		
//	}
	
	String Style="<Style id=\"track_n\">\n" + 
			"      <IconStyle>\n" + 
			"        <scale>.5</scale>\n" + 
			"        <Icon>\n" + 
			"          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\n" + 
			"        </Icon>\n" + 
			"      </IconStyle>\n" + 
			"      <LabelStyle>\n" + 
			"        <scale>0</scale>\n" + 
			"      </LabelStyle>\n" + 
			"\n" + 
			"    </Style>\n" + 
			"    <!-- Highlighted track style -->\n" + 
			"    <Style id=\"track_h\">\n" + 
			"      <IconStyle>\n" + 
			"        <scale>1.2</scale>\n" + 
			"        <Icon>\n" + 
			"          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\n" + 
			"        </Icon>\n" + 
			"      </IconStyle>\n" + 
			"    </Style>\n" + 
			"    <StyleMap id=\"track\">\n" + 
			"      <Pair>\n" + 
			"        <key>normal</key>\n" + 
			"        <styleUrl>#track_n</styleUrl>\n" + 
			"      </Pair>\n" + 
			"      <Pair>\n" + 
			"        <key>highlight</key>\n" + 
			"        <styleUrl>#track_h</styleUrl>\n" + 
			"      </Pair>\n" + 
			"    </StyleMap>\n" + 
			"    <!-- Normal multiTrack style -->\n" + 
			"    <Style id=\"multiTrack_n\">\n" + 
			"      <IconStyle>\n" + 
			"        <Icon>\n" + 
			"          <href>http://earth.google.com/images/kml-icons/track-directional/track-0.png</href>\n" + 
			"        </Icon>\n" + 
			"      </IconStyle>\n" + 
			"      <LineStyle>\n" + 
			"        <color>99ffac59</color>\n" + 
			"        <width>6</width>\n" + 
			"      </LineStyle>\n" + 
			"\n" + 
			"    </Style>\n" + 
			"    <!-- Highlighted multiTrack style -->\n" + 
			"    <Style id=\"multiTrack_h\">\n" + 
			"      <IconStyle>\n" + 
			"        <scale>1.2</scale>\n" + 
			"        <Icon>\n" + 
			"          <href>http://earth.google.com/images/kml-icons/track-directional/track-0.png</href>\n" + 
			"        </Icon>\n" + 
			"      </IconStyle>\n" + 
			"      <LineStyle>\n" + 
			"        <color>99ffac59</color>\n" + 
			"        <width>8</width>\n" + 
			"      </LineStyle>\n" + 
			"    </Style>\n" + 
			"    <StyleMap id=\"multiTrack\">\n" + 
			"      <Pair>\n" + 
			"        <key>normal</key>\n" + 
			"        <styleUrl>#multiTrack_n</styleUrl>\n" + 
			"      </Pair>\n" + 
			"      <Pair>\n" + 
			"        <key>highlight</key>\n" + 
			"        <styleUrl>#multiTrack_h</styleUrl>\n" + 
			"      </Pair>\n" + 
			"    </StyleMap>\n" + 
			"    <!-- Normal waypoint style -->\n" + 
			"    <Style id=\"waypoint_n\">\n" + 
			"      <IconStyle>\n" + 
			"        <Icon>\n" + 
			"          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\n" + 
			"        </Icon>\n" + 
			"      </IconStyle>\n" + 
			"    </Style>\n" + 
			"    <!-- Highlighted waypoint style -->\n" + 
			"    <Style id=\"waypoint_h\">\n" + 
			"      <IconStyle>\n" + 
			"        <scale>1.2</scale>\n" + 
			"        <Icon>\n" + 
			"          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\n" + 
			"        </Icon>\n" + 
			"      </IconStyle>\n" + 
			"    </Style>\n" + 
			"    <StyleMap id=\"waypoint\">\n" + 
			"      <Pair>\n" + 
			"        <key>normal</key>\n" + 
			"        <styleUrl>#waypoint_n</styleUrl>\n" + 
			"      </Pair>\n" + 
			"      <Pair>\n" + 
			"        <key>highlight</key>\n" + 
			"        <styleUrl>#waypoint_h</styleUrl>\n" + 
			"      </Pair>\n" + 
			"    </StyleMap>\n" + 
			"    <Style id=\"lineStyle\">\n" + 
			"      <LineStyle>\n" + 
			"        <color>99ffac59</color>\n" + 
			"        <width>6</width>\n" + 
			"      </LineStyle>\n" + 
			"    </Style>";

}