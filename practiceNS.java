/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Hangman extends GraphicsProgram {
	private JTextField f;
	private GPoint last;
	
	public void init() {
		add(new JLabel("name"), NORTH);
		f = new JTextField(10);
		add(f, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Remove"), NORTH);
		add(new JButton("Clear"), NORTH);
		
		addMouseListeners();
		addActionListeners();
	}
	
	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		ob = getElementAt(last);
	}
	
	public void mouseDragged(MouseEvent e) {
		if(ob!=null) {
			ob.move(e.getX()-last.getX(), e.getY()-last.getY());
			last = new GPoint(e.getPoint());
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Add")) {
			addRect();
		}
		else if(cmd.equals("Remove")) {
			
		}
		else if(cmd.equals("Clear")) {
			removeAll();
		}
	}
	
	private void addRect() {
		String l = f.getText();
		double w = getWidth();
		double h = getHeight();
		
		GCompound c = new GCompound();
		GRect r = new GRect(BOX_WIDTH,BOX_HEIGHT);
		c.add(r);
		GLabel lb = new GLabel(l);
		double wL = (BOX_WIDTH - lb.getWidth())/2;
		// we + in height because label coordinates are not in top, but in bottom
		double hL = (BOX_HEIGHT + lb.getHeight())/2;
		c.add(lb, wL, hL);
		add(c,(w-BOX_WIDTH)/2,(h-BOX_HEIGHT)/2);
	}
	
	
	
	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;
	private GObject ob; 	
}

    
  
    
