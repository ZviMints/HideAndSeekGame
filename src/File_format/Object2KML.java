/**
 * This Class Converting Object into KML file.
 * The class can convery Layer,Project or Element into a KML.
 */
package File_format;
import java.io.File;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import Game.Game;
import Geom.Point3D;
import Pacman.Pacman;
import Path.Path;
import ShortestPathAlgo.Algo;

public class Object2KML {
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
	public Object2KML(Game game,Algo algo) throws ParseException
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
		String time = algo.StartGameTime;
		for(Pacman p : game.getPacmanList()) {
			KML_BODY += CreateFolder(p.getInfo().getID(), p.getInfo().getTime(),algo.getSolution(),time);
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
	/* * * * * * * * * * * * * * * * * * CSV To KML Convert * * * * * * * * * * * * * * * */
	/**
	 * This Method is Responsible to convert From CSV Line to KML line by format
	 * @param MAC is the input MAC from csv file
	 * @param SSID is the input SSID from csv file
	 * @param AuthMode is the input AuthMode from csv file
	 * @param FirstSeen is the input FirstSeen from csv file
	 * @param Frequency is the input Frequency from csv file
	 * @param RSSI is the input RSSI from csv file
	 * @param point is the input 3 coordinates ( lat, lon , alt ) from csv file
	 * @param list 
	 * @param time 
	 * @return
	 * @throws ParseException 
	 */
	public String CreateFolder(String Name, double d, List<Path> list, String time) throws ParseException
	{
		String body = "<Folder>" + "\n" 
				+"<name>"+ Name +"</name>" + "\n"
				+fruit(Name, list)
				+"<Placemark>"
				+ "<name>"+ Name +"</name>" + "\n"
				+ "<styleUrl>#multiTrack</styleUrl>"
				+ "<gx:Track>"
				+ GetWhenFromPacman(Name,list,time)
				+ "</gx:Track>" + "\n"
				+ "</Placemark>" + "\n"
				+"</Folder>";
		return body;
	}
	private String GetWhenFromPacman(String Name, List<Path> list, String time) throws ParseException {
		String ans ="";
		String NewTime = time;
		for (Path p :list) {
			if(p.ID==Name) {
				NewTime = Time(NewTime,(int)p.time);
				String time1=NewTime.replaceAll("\\s","T");
				time1+="Z";
				if(!ans.contains(p.y0 +" "+p.x0)){
					String s = time.replaceAll("\\s","T");
					s+="Z";
					ans+="<when>"+s+"</when>"+ "\n"
							+"<gx:coord>"+p.y0 +" "+p.x0 +" "+p.z0+"</gx:coord>"+ "\n";
				}
				if(!ans.contains(p.y1 +" "+p.x1)) {
					ans+="<when>"+time1+"</when>"+ "\n"
							+"<gx:coord>"+p.y1 +" "+p.x1+" "+p.z0+"</gx:coord>"+ "\n";
					
				
				}
				
			}
		}
		return ans;
	}
	public String fruit(String Name , List<Path> list) {
		String fruit="";
		for (Path p :list) {
			if(p.ID==Name) {
				if(!fruit.contains(p.y1 +" "+p.x1)) {
					fruit+="<Placemark>\n" 
							+"<styleUrl>#red</styleUrl>\n"  
							+"<Point>\n"  
							+"<coordinates>"+p.y1 +" "+p.x1+" "+p.z0+"</coordinates>\n" 
							+"</Point>\n" 
							+"</Placemark>\n";
				}
				
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
		TimeSave = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());
		String SavePath = game.NameFile.replaceAll(game.NameFile, "./data/"+TimeSave+".kml");
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
	public String Time(String time , int second) throws ParseException {
		String myTime = time;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date d = df.parse(myTime); 
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, second);
		String newTime = df.format(cal.getTime());
		return newTime;
	}
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
