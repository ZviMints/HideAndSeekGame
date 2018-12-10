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
		int i=1;
		String time = algo.StartGameTime;
		System.out.println(time);
		for(Pacman p : game.getPacmanList()) {
			String s ="pac "+i;
			KML_BODY += CreateFolder(p.getInfo().getID(),s, p.getInfo().getTime(),algo.getSolution(),time);
			i++;
		}
	}
	/* * * * * * * * * * * * * * * Make Head,Body,Tail * * * * * * * * * * * * * * * */
	private void MakeHead() {
		   KML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" 
				    + "<kml xmlns=\"http://www.opengis.net/kml/2.2\" "
				    + "xmlns:gx=\"http://www.google.com/kml/ext/2.2\">\n"
				    + "<Document>\n";
					
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
	public String CreateFolder(String Name,String ID, double d, List<Path> list, String time) throws ParseException
	{
		String body="<Folder>" + "\n" 
				+"<name>"+ ID +"</name>" + "\n"
				+"<Placemark>"
				+ "<name>"+ ID +"</name>" + "\n"
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
		for (Path p :list) {
			if(p.ID==Name) {
				String NewTime = Time(time,(int)p.time);
				NewTime=NewTime.replaceAll("\\s","T");
				NewTime+="Z";
				ans+="<when>"+NewTime+"</when>"+ "\n"
				  +"<gx:coord>"+p.y0 +" "+p.x0+"</gx:coord>"+ "\n";
			}
		}
		return ans;
	
	}
	/* * * * * * * * * * * * * * * * * * File Writer * * * * * * * * * * * * * * * */
	/**
	 * This method responsible to Make the KML file
	 * @throws Exception
	 */
	public void MakeFile(Game game) throws Exception
	{
		System.out.println(game.NameFile);
		String SavePath = game.NameFile.replaceAll(".csv", ".kml");
		PrintWriter pw = new PrintWriter(new File(SavePath));
		StringBuilder sb = new StringBuilder();	
		sb.append(KML_HEAD + KML_BODY + KML_TAIL);
		pw.write(sb.toString());
		pw.close();
		System.out.println("Success! file on path: " +game.NameFile);
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

}
