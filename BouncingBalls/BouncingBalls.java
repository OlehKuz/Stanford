
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;

import acm.program.*;

public class BouncingBalls extends GraphicsProgram {

	private static final int DELAY = 3;
	
	private ArrayList<Ball> balls = new ArrayList<Ball>();
		
	public void init() {
		JButton ballButton = new JButton("Add ball");
		add(ballButton, SOUTH);
		addActionListeners();
	}
	
	public void run() {
		while(true) {
			for(Ball ball : balls) {
				ball.heartbeat(getWidth(), getHeight());
			}
			pause(DELAY);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		Ball ball = new Ball(getWidth(), getHeight());
		// to GCanvas i add GOval object that is a part  of Ball object, i dont add the whole Ball
		add(ball.getGOval());
		balls.add(ball);
	}

}