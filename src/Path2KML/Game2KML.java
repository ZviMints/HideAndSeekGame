/**
 * This Class Converting Game into KML file.
 */
package Path2KML;
import java.io.File;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import Fruit.Fruit;

import java.util.Date;

import Game.Game;
import Geom.Point3D;
import Pacman.Pacman;
import Path.Path;
import ShortestPathAlgo.Algo;

public class Game2KML {
	private Algo algo; // The algorithm of the game
	private Game game; // The current game
	private String KML_BODY; 
	private String KML_HEAD; 
	private String KML_TAIL; 
	public String SavePath; // The name of the KML file

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	/**
	 * Constructor of the KML file. make Header, ConvertPath and make Tail.
	 */
	public Game2KML(Game game,Algo algo ,String fileNameKML) 
	{
		this.algo = algo; // The algorithm of the game
		this.game = game; // The current game 
		this.SavePath = fileNameKML;
		MakeHead(); // Build a KML file header
		try {
			ConvertPath(algo,game); // Build Path and fruit in a KML file
		} catch (ParseException e1) {
			e1.printStackTrace();
		} 
		MakeTail(); // KML file extension
	}
	/* * * * * * * * * * * * * * * * * * Convert * * * * * * * * * * * * * * * */
	/**
	 * This method is responsible to Convert Game to KML.
	 * @throws ParseException 
	 */
	private void ConvertPath(Algo algo,Game game) throws ParseException {
		String AlgoStartTime = getUTC(algo.StartGameTime); // The start time of the algorithm in the UTC format
		for(Pacman pacman : game.getPacmanList()) { //  Loop of the Pacman 
			KML_BODY += CreateFolder(pacman.getInfo().getID(),algo.getSolution(),AlgoStartTime);
		}
	}
	/* * * * * * * * * * * * * * * Make Head,Body,Tail * * * * * * * * * * * * * * * */
	/**
	 * This method builds the head of the KML file
	 */
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
				+Style
				+ PointFruit();

	}
	
	/* * * * * * * * * * * * * * * * * * KML Convert * * * * * * * * * * * * * * * */
	/**
	 * This Method is receives a name of Pacman lists the Path and start time of the alignments and returns the Path in KML format.
	 * @param Name is the Name of Pacman from the game
	 * @param list is the Path of this Fackman
	 * @param AlgoStartTime is the start time for running the algorithm.
	 * @return Path in KML format
	 * @throws ParseException 
	 */
	public String CreateFolder(String Name, List<Path> list, String AlgoStartTime) throws ParseException
	{
		String body = "<Folder>" + "\n" 
				+"<name>"+ Name +"</name>" + "\n" // Name pacman
				+"<Placemark>"
				+ "<name>"+ Name +"</name>" + "\n" 
				+ "<styleUrl>#multiTrack</styleUrl>" + "\n" 
				+ "<gx:Track>" + "\n" 
				+ GetWhenFromPacman(Name,list,AlgoStartTime) // Path format with time and Path of Pacman
				+ "</gx:Track>" + "\n"
				+ "</Placemark>" + "\n"
				+"</Folder>";
		return body;
	}
	/**
	 * A method that builds a path for Pacman in KML
	 * @throws ParseException 
	 */
	private String GetWhenFromPacman(String Name, List<Path> list, String AlgoStartTime) throws ParseException {
		String ans ="";
		for (Path path :list) { // Loop of the Path
			if(path.ID.equals(Name)) { // If Pacman is in Path 
				if(!ans.contains(path.y0 +" "+path.x0)){ // Start point of the Pacman
					ans+="<when>"+TimeFormatKml(AlgoStartTime)+"</when>"+ "\n" // Point and time in KML format
							+"<gx:coord>"+path.y0 +" "+path.x0 +" "+path.z0+"</gx:coord>"+ "\n";
				}
				AlgoStartTime = Time(AlgoStartTime,(int)path.time); // the time after moving the pecman
				ans+="<when>"+TimeFormatKml(AlgoStartTime)+"</when>"+ "\n"  // Point and time in KML format
						+"<gx:coord>"+path.y1 +" "+path.x1+" "+path.z0+"</gx:coord>"+ "\n";
			}
		}
		return ans;
	}
	/**
	 * This method places the fruit on the map 
	 * @return Fruit list in KML format
	 */
	public String PointFruit() {
		String ans=""; 
		for (Fruit fruit : game.getFruitList()) { // Loop of the fruit 
			Point3D p = new Point3D((Point3D) fruit.getGeom()); // Point of fruit
				ans += "<Placemark>\n" 
						+"<styleUrl>#red</styleUrl>\n"  
						+"<Point>\n"  
						+"<coordinates>"+p.y() +" "+p.x()+" "+p.z()+"</coordinates>\n" //The place of the fruit
						+"</Point>\n" 
						+"</Placemark>\n";

		}
		return ans;
	}
	private void MakeTail() {
		KML_TAIL = "</Document>\n</kml>";
	}
	/* * * * * * * * * * * * * * * * * * Time Format* * * * * * * * * * * * * * * */
	/**
	 * This method takes time and seconds and adds seconds to time
	 * @throws Exception
	 */
	public String Time(String AlgoTime , int second) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = df.parse(AlgoTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, second);
		String newTime = df.format(cal.getTime());
		return newTime;
	}
	/**
	 * A method that converts time into KML format 
	 */
	public String TimeFormatKml(String time) {	
		time = time.replaceAll("\\s","T");
		time += "Z";
		return time;
	}
	/**
	 * A method that converts time to UTC
	 * @throws ParseException
	 */
	public static String getUTC(String AlgoStartTime) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date dateStr = dateFormat.parse(AlgoStartTime);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		String formattedDate = dateFormat.format(dateStr);

		return formattedDate;

	}
	/* * * * * * * * * * * * * * * * * * File Writer * * * * * * * * * * * * * * * */
	/**
	 * This method responsible to Make the KML file
	 * @throws Exception
	 */
	public void MakeFile() throws Exception
	{
		SavePath += ".kml"; // Add a KML extension to the file
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

	String Style ="<Style id=\"track_n\">\n" + "      <IconStyle>\n" + "        <scale>.5</scale>\n" +"        <Icon>\n" + "          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\n" + ""
			+ "       </Icon>\n" + "      </IconStyle>\n" + "      <LabelStyle>\n" + "        <scale>0</scale>\n" + "      </LabelStyle>\n" + "\n" + "    </Style>\n" + 
			"    <!-- Highlighted track style -->\n" + "    <Style id=\"track_h\">\n" + "      <IconStyle>\n" + "        <scale>1.2</scale>\n" + "        <Icon>\n" + 
			"          <href>http://earth.google.com/images/kml-icons/track-directional/track-none.png</href>\n" + "        </Icon>\n" + 
			"      </IconStyle>\n" + "    </Style>\n" + "    <StyleMap id=\"track\">\n" + "      <Pair>\n" + "        <key>normal</key>\n" + "        <styleUrl>#track_n</styleUrl>\n" + "      </Pair>\n" + 
			"      <Pair>\n" + "        <key>highlight</key>\n" + "        <styleUrl>#track_h</styleUrl>\n" + "      </Pair>\n" + "    </StyleMap>\n" + 
			"    <!-- Normal multiTrack style -->\n" + "    <Style id=\"multiTrack_n\">\n" + "      <IconStyle>\n" + "        <Icon>\n" + 
			"          <href>http://earth.google.com/images/kml-icons/track-directional/track-0.png</href>\n" + "        </Icon>\n" + "      </IconStyle>\n" + "      <LineStyle>\n" + "        <color>99ffac59</color>\n" + "        "
			+ "<width>6</width>\n" +	"      </LineStyle>\n" + "\n" + "    </Style>\n" + 	"    <!-- Highlighted multiTrack style -->\n" + "    <Style id=\"multiTrack_h\">\n" + "      <IconStyle>\n" + 
			"        <scale>1.2</scale>\n" + "        <Icon>\n" + "        </Icon>\n" + "      </IconStyle>\n" + "      <LineStyle>\n" + "        <color>99ffac59</color>\n" + "        <width>8</width>\n" + "      </LineStyle>\n" + "    </Style>\n" + 
			"    <StyleMap id=\"multiTrack\">\n" + "      <Pair>\n" +	"        <key>normal</key>\n" +	"        <styleUrl>#multiTrack_n</styleUrl>\n" + "      </Pair>\n" + "      <Pair>\n" + "        <key>highlight</key>\n" + "   "
			+ "     <styleUrl>#multiTrack_h</styleUrl>\n" + "      </Pair>\n" + "    </StyleMap>\n" + 
			"    <!-- Normal waypoint style -->\n" +	"    <Style id=\"waypoint_n\">\n" + "      <IconStyle>\n" + "        <Icon>\n" + 
			"          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\n" + "        </Icon>\n" + "      </IconStyle>\n" + 	"    </Style>\n" + 
			"    <!-- Highlighted waypoint style -->\n" +"    <Style id=\"waypoint_h\">\n" + "      <IconStyle>\n" + "        <scale>1.2</scale>\n" + 
			"        <Icon>\n" + "          <href>http://maps.google.com/mapfiles/kml/pal4/icon61.png</href>\n" + "        </Icon>\n" + "      </IconStyle>\n" + "    </Style>\n" + "    <StyleMap id=\"waypoint\">\n" + "      "
			+ "<Pair>\n" + "        <key>normal</key>\n" + 
			"        <styleUrl>#waypoint_n</styleUrl>\n" + "      </Pair>\n" + "      <Pair>\n" + "        <key>highlight</key>\n" + "        <styleUrl>#waypoint_h</styleUrl>\n" + 
			"      </Pair>\n" + "    </StyleMap>\n" + "    <Style id=\"lineStyle\">\n" + "      <LineStyle>\n" + "        <color>99ffac59</color>\n" + "        <width>6</width>\n" + "      </LineStyle>\n" + "    </Style>";

}
