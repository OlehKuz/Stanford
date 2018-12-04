/* 
 * File: FacePamphletClient.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management client.  Remember to update this comment!
 */

import acm.graphics.*;
import acm.program.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;

import javax.swing.*;

public class FacePamphletClient extends GraphicsProgram {

	/** Number of characters for each of the text input fields */
	public static final int TEXT_FIELD_SIZE = 15;

	/** Name of font used to display the application message at the
	 *  bottom of the display canvas */
	public static final String MESSAGE_FONT = "Dialog-18";

	/** Name of font used to display the name in a user's profile */
	public static final String PROFILE_NAME_FONT = "Dialog-24";

	/** The number of pixels in the vertical margin between the bottom 
	 *  of the canvas display area and the baseline for the message 
	 *  text that appears near the bottom of the display */
	public static final double BOTTOM_MESSAGE_MARGIN = 20;

	/** The number of pixels in the hortizontal margin between the 
	 *  left side of the canvas display area and the Name, Image, and 
	 *  Status components that are display in the profile */	
	public static final double LEFT_MARGIN = 20;	

	/** The number of pixels in the vertical margin between the top 
	 *  of the canvas display area and the top (NOT the baseline) of 
	 *  the Name component that is displayed in the profile */	
	public static final double TOP_MARGIN = 20;	

	/** The address of the server that should be contacted when sending
	 * any Requests. */
	private static final String HOST = "http://localhost:8000/";

	/** 
	 * Init is called before the window is created 
	 */
	private JTextField field;
	
	public void init() {
		add(new JLabel("Name"), NORTH);
		field = new JTextField(TEXT_FIELD_SIZE);
		add(field, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		
		addActionListeners();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Add")) {
			addProfile();
		}
		if(cmd.equals("Delete")) {
			deleteProfile();
		} 
		if(cmd.equals("Lookup")) {
			containsProfile();
		}
	}
	/** 
	 * Run is called after the window is created 
	 */
	public void run() {
		pingTheServer(); // your code here
	}

	/**
	 * This method is an example of sending a request to the server.
	 * You can delete it when you are ready.
	 */
	private void pingTheServer() {
		try {
			// Lets prepare ourselves a new request with command "ping".
			Request example = new Request("ping");
			// We are ready to send our request
			String result = SimpleClient.makeRequest(HOST, example);
			drawCenteredLabel(result);
		} catch (IOException e) {
			drawCenteredLabel(e.getMessage());
		}
	}

	/**
	 * Again, this is a helper method that we wrote for the "pingTheServer"
	 * example (above). You should not include it in your final submission.
	 */
	
	private void displayProfile() {
		removeAll();
		addProfileLabel();
		addMessage("Displaying "+field.getText());
	}
	
	private void containsProfile() {
		String result = makeRequest("containsProfile");
		if(result.equals("true")) {
			displayProfile();
		}
		else {
			removeAll();
			addMessage("A profile with a name "+field.getText() + " does not exist");
		}
	}
	
	private void deleteProfile() {
		String result = makeRequest("deleteProfile");
		if(result.equals("success")) {
			removeAll();
			addMessage("Profile of "+field.getText() + " deleted");
		}
	}
	
	private void addProfile() {
		String result = makeRequest("addProfile");
		if(result.equals("success")) {
			removeAll();
			addProfileLabel();
			addMessage("New profile created");
		}
	}
	
	private String makeRequest(String command) {
		try {
			Request r = new Request(command);
			r.addParam("name", field.getText());
			return SimpleClient.makeRequest(HOST, r);			
		}
		catch (IOException e) {
			removeAll();
			addMessage(e.getMessage());
			return null;
		}
	}

	
	private void addMessage(String msg) {
		GLabel l = new GLabel(msg);
		l.setFont(MESSAGE_FONT);
		double x = (getWidth()-l.getWidth())/2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(l, x, y);
	}
	
	private void addProfileLabel() {
		GLabel l = new GLabel(field.getText());
		l.setColor(Color.BLUE);
		l.setFont(PROFILE_NAME_FONT);
		add(l,LEFT_MARGIN, TOP_MARGIN + l.getHeight());
	}
	
	private void drawCenteredLabel(String text) {
		GLabel label = new GLabel(text);
		label.setFont("courier-24");
		double x = (getWidth() - label.getWidth())/2;
		double y = (getHeight() - label.getAscent())/2;
		add(label, x, y);
	}
}
