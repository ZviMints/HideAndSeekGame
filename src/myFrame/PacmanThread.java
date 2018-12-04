package myFrame;

import Pacman.Pacman;

public class PacmanThread extends Thread{
	DotsAndLines canvas;
	Pacman pacman;
	PacmanThread(DotsAndLines canvas, Pacman pacman)
	{
		this.canvas = canvas;
		this.pacman = pacman;
	}
	@Override
	public void run() {
		while(true)
		{
			canvas.repaint();
			try 
			{
				Thread.sleep(Integer.parseInt(pacman.getInfo().getSpeed()) * 1000);
			} 
			catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
	}

}
