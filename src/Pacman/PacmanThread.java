/**
 * This Class Represent a Thread Of Pacman that running in the map
 * @author Tzvi Mints and Or Abuhazira
 */
package Pacman;

import java.util.List;
import Path.Path;
import myFrame.DotsAndLines;
import myFrame.Menu;
import myFrame.MyFrame;
// ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! //
// NOTE!!!!: WE CAN MAKE IT IN ONE THREAD, BY SLEEP FOR MILLISECOND AND THEN MOVE EACH PACMAN TO ANOTHER
// POINT, BUT IF PACMAN PATH TAKE 1 DAY, AND WE WILL REFRESH EACH MILLI SECOND ITS TAKE A LOT OF RESOURCES
// EACH METHOD ( 1 THREAD OR A THREAD FOR EACH PACMAN ) HAVE CONS AND PROS, WE DECIDED TO DO WITH THREAD FOR
// EACH PACMAN SUCH THAT EACH PACMAN WILL SLEEP FOR THE TIME HE NEED TO GO, FOR EXAMPLE HE WILL SLEEP FOR ONE DAY
// INSTEAD OF REFRESH EACH MILLISECOND
//! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! //
public class PacmanThread extends Thread{
	/* * * * * * * * * * * * * *  Initialization Variables * * * * * * * * * * * * * * * */
	DotsAndLines canvas; // the Game Panel
	public Pacman pacman; // current pacman
	List<Path> lines; // total solution of the algorithm, all the path's.
	boolean lastest_thread = false; // if current thread is the last thread alive in the game
	double max_time; // The total time of the walkman's walk in the chosen route
	/* * * * * * * * * * * * * *  Constructor * * * * * * * * * * * * * * * */
	public PacmanThread(DotsAndLines canvas, Pacman pacman, List<Path> lines, double Max_time_To_Algo)
	{
		this.max_time = pacman.getInfo().getTime();
		this.canvas = canvas;
		this.pacman = pacman;
		this.lines = lines;
		if(max_time == Max_time_To_Algo)
			lastest_thread = true;
	}
	/* * * * * * * * * * * * * *  Run Override Method * * * * * * * * * * * * * * * */

	@Override
	public void run() {	
		double totalrun = 0;
		for(int i=0; i<lines.size(); i++)
		{
			Path path = lines.get(i);
			if(path.ID.equals(pacman.getInfo().getID()))
			{
				totalrun += path.time;
				if(path.time >= 0)
				{
					try {
						// ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! //
						Thread.sleep((long) (path.time * 7)); // Change to 1000 if you want real-time run
						// ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! //
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					path.fruit.getInfo().Eaten(); // We ate a Fruit!!! Whoooo!!
					canvas.AddSolution(path);
					pacman.translate(path.vec);
					canvas.repaint();
					Menu.UpdateScoreTime(totalrun); // Its Synchronized

				}
			}
		}
		if(lastest_thread) // if its the last thread alive
		{
		Menu.VisableAllButtons();
		Menu.InProgress.setText("0");
		canvas.finished.clear();
		canvas.FinishedAlgo = true;
		MyFrame.Popup(); // Make Popup for finishing game :)
		canvas.SetVisibleAllFruits();
		canvas.repaint(); // Will make Hat the the last pacman
		}
		else // its not, will update InProgress and Update Time
		{
			Menu.UpdateFinished();
		}
	}
}
