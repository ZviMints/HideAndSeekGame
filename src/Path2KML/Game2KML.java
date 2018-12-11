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
import java.util.Date;

import Game.Game;
import Pacman.Pacman;
import Path.Path;
import ShortestPathAlgo.Algo;

public class Game2KML {
	private Algo algo; // האלגריתם על המשחק
	private Game game; // המשחק הנוכחי
	private String KML_BODY; // kml-תוכן של ה 
	private String KML_HEAD; // kml-שורת ההתחלה של ה 
	private String KML_TAIL; // kml-שורת הסוף של ה 
	public String TimeSave; // זמן שמירת הקובץ
	public String SavePath; // שם הקובץ בזמן השמירה

	/* * * * * * * * * * * * * * * * * * Constructor * * * * * * * * * * * * * * * */
	/**
	 * Constructor of the KML file. make Header, ConvertPath and make Tail.
	 * @throws ParseException 
	 */
	public Game2KML(Game game,Algo algo) 
	{
		this.algo = algo; //קבלת האלגוריתם הנוכחי
		this.game = game; //קבלת המשחק הנוכחי 
		this.SavePath = null;
		MakeHead(); // בניית תוכן ראשי של הקובץ
		try {
			ConvertPath(algo,game); // בניית תוכן המסלול של הפקמן ויצירת נקודות של הפירות
		} catch (ParseException e1) {
			e1.printStackTrace();
		} 
		MakeTail(); // בניית תוכן סופי של הקובץ
	}
	/* * * * * * * * * * * * * * * * * * Convert * * * * * * * * * * * * * * * */
	/**
	 * This method is responsible to Convert Game to KML.
	 * @throws ParseException 
	 */
	private void ConvertPath(Algo algo,Game game) throws ParseException {
		String AlgoStartTime = getUTC(algo.StartGameTime); // השעה שהאלגוריתם התחיל
		for(Pacman pacman : game.getPacmanList()) { // ריצה על כל הפקמנים 
			KML_BODY += CreateFolder(pacman.getInfo().getID(),algo.getSolution(),AlgoStartTime);//בניית גוף ע"י שליחת שם הפקמן,פתרון האלגוריתם,וזמן התחלת האלגוריתם
		}
	}
	/* * * * * * * * * * * * * * * Make Head,Body,Tail * * * * * * * * * * * * * * * */
	/**
	 * פונקציה שבונה את התחלת הקובץ 
	 * @throws ParseException 
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
				+Style;

	}
	
	/* * * * * * * * * * * * * * * * * * KML Convert * * * * * * * * * * * * * * * */
	/**
	 * פונקציה שמקבלת שם שם של פקמן ורשימה של מסלולים וזמן התחלת של האלגורתים ומחזירה את המסלול בקובץ 
	 * @throws ParseException 
	 */
	public String CreateFolder(String Name, List<Path> list, String AlgoStartTime) throws ParseException
	{
		String body = "<Folder>" + "\n" 
				+"<name>"+ Name +"</name>" + "\n" //שם הפקמן
				+PointFruit(Name, list) // הצגת הפירות על המפה
				+"<Placemark>"
				+ "<name>"+ Name +"</name>" + "\n" 
				+ "<styleUrl>#multiTrack</styleUrl>"
				+ "<gx:Track>"
				+ GetWhenFromPacman(Name,list,AlgoStartTime) // הצגה של המסלול ע"י זמן ומסלול שהפקמן עובר
				+ "</gx:Track>" + "\n"
				+ "</Placemark>" + "\n"
				+"</Folder>";
		return body;
	}
	/**
	 * הפונקציה מקבלת שם של פקמן עוברת על המסלול ובונה עבור הפקמן את המסלול והזמן שהוא עבר  
	 * @return
	 * @throws ParseException 
	 */
	private String GetWhenFromPacman(String Name, List<Path> list, String AlgoStartTime) throws ParseException {
		String ans =""; // בניית מחרוזת שמאכסנת את כל הקוד של המסלול והזמן
		for (Path path :list) { // עובר על כל המסלול 
			if(path.ID.equals(Name)) { //בדיקה אם הפקמן נמצא בתא הנוכחי ברשימה 
				if(!ans.contains(path.y0 +" "+path.x0)){//עבור המיקום ההתחלתי של הפקמן
					ans+="<when>"+TimeFormatKml(AlgoStartTime)+"</when>"+ "\n" //הוספת הזמן והנקודת עבור הפורמט
							+"<gx:coord>"+path.y0 +" "+path.x0 +" "+path.z0+"</gx:coord>"+ "\n";
				}
				AlgoStartTime = Time(AlgoStartTime,(int)path.time);//הוספת הזמן שלקח לפקמן להגיע לפרי לזמן הנוכחי של האלגוריתם
				ans+="<when>"+TimeFormatKml(AlgoStartTime)+"</when>"+ "\n" 
						+"<gx:coord>"+path.y1 +" "+path.x1+" "+path.z0+"</gx:coord>"+ "\n";
			}
		}
		return ans;
	}
	/**
	 * פונקציה שמקבלת של של פקמן רשימה של מסלול האלגוריתם ומחזירה את מיקום הפירות במפה  
	 * @return
	 */
	public String PointFruit(String Name , List<Path> list) {
		String fruit=""; //מחזורת בשביל להכניס את כל הפרמטרים כל הפירות
		for (Path path :list) { //ריצה על המסלול בכדי לקחת את מיקום הפירות
			if(path.ID.equals(Name)){ //בדיקה אם פקמן מסויים נמצא ברשימה
				fruit+="<Placemark>\n" 
						+"<styleUrl>#red</styleUrl>\n"  
						+"<Point>\n"  
						+"<coordinates>"+path.y1 +" "+path.x1+" "+path.z0+"</coordinates>\n" //הוספת המיקום של הפרי 
						+"</Point>\n" 
						+"</Placemark>\n";
			}

		}
		return fruit;
	}
	private void MakeTail() {
		KML_TAIL = "</Document>\n</kml>";
	}
	/* * * * * * * * * * * * * * * * * * Time Format* * * * * * * * * * * * * * * */
	/**
	 * פונקציה שמקבלת זמן מסויים באלגוריתם ושניות שלקח לפקמן ללכת ומוסיפה את הזמן של הפקמן לזמן של האלגוריתם 
	 * @throws Exception
	 */
	public String Time(String AlgoTime , int second) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//פורמט של הזמן 
		Date d = df.parse(AlgoTime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, second);
		String newTime = df.format(cal.getTime());
		return newTime;
	}
	/**
	 * פונקציה שמקבלת זמן וממירה אותו לפורמט אחר 
	 */
	public String TimeFormatKml(String time) {
		//KML-מחליפה את המחרוזת כך שהיא תתאים ל
		time=time.replaceAll("\\s","T");
		time+="Z";
		return time;
	}
	/**
	 * UTC-פונקציה שמקבלת זמן וממירה אותו ל
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
	 * @param startGameTime 
	 * @throws Exception
	 */
	public void MakeFile() throws Exception
	{
		TimeSave = new SimpleDateFormat("HH-mm-ss").format(Calendar.getInstance().getTime());// זמן של שמירת המשחק 
		SavePath = "./data/"+TimeSave+".kml";//קריאה לקובץ בשם של הזמן
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
