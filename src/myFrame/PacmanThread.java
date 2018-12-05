package myFrame;

import java.util.List;

import Pacman.Pacman;
import Path.Path;

public class PacmanThread extends Thread{
	DotsAndLines canvas;
	Pacman pacman;
	List<Path> lines;
	public PacmanThread(DotsAndLines canvas, Pacman pacman, List<Path> lines)
	{
		this.canvas = canvas;
		this.pacman = pacman;
		this.lines = lines;
	}
	@Override
	public void run() {	
		for(int i=0; i<lines.size(); i++)
		{
			Path path = lines.get(i);
			if(path.ID.equals(pacman.getInfo().getID()))
			{
				try {
					Thread.sleep((long) (path.time * 100));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				MyFrame.UpdateTime(MyFrame.getTime() + path.time);
				canvas.AddSolution(path);
				pacman.translate(path.vec);
				canvas.repaint();
			}
		}
	}
}
