/*
 This program allows the user to create a set of boxes with labels
 and then drag them around in the window and remove labeled boxes by calling remove on their label.
 */

import acm.program.*;
import acm.graphics.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map.*;

public class Hangman extends GraphicsProgram {
	private HashMap <GLabel, GCompound> mp = new HashMap <GLabel, GCompound>();
	private JTextField f;
	private GPoint last;
	
	
	//initialize interactors
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
	
	// if we pressed actual object, stores our current position, which we use when shifting a rectangle
	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		ob = getElementAt(last);
	}
	
	// moves rectangle if we pressed an object and now are dragging our mouse
	public void mouseDragged(MouseEvent e) {
		if(ob!=null) {
			ob.move(e.getX()-last.getX(), e.getY()-last.getY());
			last = new GPoint(e.getPoint());
		}
	}
	
	// manages functions of buttons, calls apropriate methods that perform behaviour expected from a button click
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Add")) {
			addRect();
		}
		else if(cmd.equals("Remove")) {
			removeLabeledRect();
		}
		else if(cmd.equals("Clear")) {
			removeAllRect();
		}
	}
	
	// removes GCompounds(rect + its label) stored in HashMap 
	private void removeAllRect() {
		for(GCompound labelRect : mp.values()) {
			remove(labelRect);
		}
	}
	// removes one rect if its label == text typed
	private void removeLabeledRect() {
		for(GLabel label : mp.keySet()) {
			if(label.getLabel().equals(f.getText())) {
				remove(mp.get(label));
			}
		}
	}
	
	// add GComponent that consists of rect + its label
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
		mp.put(lb, c);
	}
	
	
	
	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 50;
	private GObject ob; 	
}

    
