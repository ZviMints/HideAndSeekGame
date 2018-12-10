/**
 * This Class Represent a Thread Of Pacman that running in the map
 * @author Tzvi Mints and Or Abuhazira
 */
package Pacman;

import java.util.List;
import Path.Path;
import myFrame.DotsAndLines;
import myFrame.Menu;

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
		for(int i=0; i<lines.size(); i++)
		{
			Path path = lines.get(i);
			if(path.ID.equals(pacman.getInfo().getID()))
			{
				if(path.time >= 0)
				{
					try {
						Thread.sleep((long) (path.time * 1));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					canvas.AddSolution(path);
					pacman.translate(path.vec);
					canvas.repaint();
				}
			}
		}
		if(lastest_thread) // if its the last thread alive
		{
		Menu.VisableAllButtons();
		Menu.UpdateScoreTime(max_time);
		Menu.InProgress.setText("0");
		canvas.finished.clear();
		canvas.FinishedAlgo = true;
		canvas.repaint(); // Will make Hat the the last pacman
		}
		else // its not, will update InProgress and Update Time
		{
			Menu.UpdateFinished();
			Menu.UpdateScoreTime(Math.abs(max_time));
		}
	}
}
