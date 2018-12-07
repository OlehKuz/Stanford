
import java.awt.event.ActionEvent;
import java.util.*;

import javax.swing.*;

import acm.program.*;

public class BouncingBalls extends GraphicsProgram {

	private static final int DELAY = 3;
	private ArrayList<Ball> balsOnScreen = new ArrayList<Ball>();

		
	public void init() {
		JButton ballButton = new JButton("Add ball");
		JButton remove = new JButton("Remove ball");
		add(ballButton, SOUTH);
		add(remove, SOUTH);
		
		addActionListeners();
	}
	
	/* 
	 * if i dont want to remove ball, i can add each ball to GCanvas when i am adding it to arrayList. 
	 * othewise, when removing ball from arrayList i also need to remove it from GCanvas, that is why i reset 
	 * screen each time by removing all balls and then redrawing balls that are still in arrayList
	 * */ 
	public void run() {
		while(true) {
			for(Ball ball:balsOnScreen) {
				/* to GCanvas i add GOval object that is a graphical representation of ball, 
				 * because ball is not a graphical object, so it cannot be added to GCanvas*/
				add(ball.getGOval());
				ball.heartbeat(getWidth(),getHeight());
			}
			pause(DELAY);
		}
	}
	
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Add ball")) {
			Ball create = new Ball(getWidth(),getHeight());
			balsOnScreen.add(create);
		}
		if(cmd.equals("Remove ball")) {
			balsOnScreen.remove(balsOnScreen.size()-1);
			removeAll();
		}
	}

}