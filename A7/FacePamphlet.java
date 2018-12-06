/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {
	private JTextField field;
	private JTextField fieldStatus;
	private JTextField fieldPicture;
	private JTextField fieldFriend;
	private FacePamphletDatabase database;
	private FacePamphletProfile current = null;
	private FacePamphletCanvas canvas;

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		
		canvas = new FacePamphletCanvas();
		add(canvas);
		this.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		
		add(new JLabel("Name"), NORTH);
		field = new JTextField(TEXT_FIELD_SIZE);
		add(field, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);
		
		// extensions buttons
		
		fieldStatus = new JTextField(TEXT_FIELD_SIZE);
		add(fieldStatus, WEST);
		fieldStatus.setActionCommand("Change Status");
		fieldStatus.addActionListener(this);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		fieldPicture = new JTextField(TEXT_FIELD_SIZE);
		add(fieldPicture, WEST);
		fieldPicture.setActionCommand("Change Picture");
		fieldPicture.addActionListener(this);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		fieldFriend = new JTextField(TEXT_FIELD_SIZE);
		add(fieldFriend, WEST);
		fieldFriend.setActionCommand("Add Friend");
		fieldFriend.addActionListener(this);
		add(new JButton("Add Friend"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		
		addActionListeners();
		
		database = new FacePamphletDatabase();
    }
    
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		String message = null;
		if(cmd.equals("Add")) {
			if(database.containsProfile(field.getText())) {
				message = "Profile for " + field.getText()+" already exists";
			}
			else {
				FacePamphletProfile f = new FacePamphletProfile(field.getText());
				database.addProfile(f);
				message = "Added new profile for " + field.getText();
			}
			current = database.getProfile(field.getText());
			canvas.displayProfile(current);
			canvas.showMessage(message);
		}
		
		if(cmd.equals("Delete")) {
			current = null;
			if(database.containsProfile(field.getText())) {
				database.deleteProfile(field.getText());
				canvas.removeAll();
				canvas.showMessage("Profile for " + field.getText()+" deleted");
			}
			else {
				canvas.showMessage("Profile for " + field.getText()+" does not exist");
			}
		}
		
		if(cmd.equals("Lookup")) {
			if(database.containsProfile(field.getText())) {
				current = database.getProfile(field.getText());				
				message = "Displaying " + current.getName();
			}
			else {
				current = null;
				message = "Profile for " + field.getText()+" does not exist";
			}
			canvas.displayProfile(current);
			canvas.showMessage(message);
			
		}
		if(cmd.equals("Change Status")) {
			if(current == null) {
				message = "First choose the profile to update its status";
			}
			else {
				current.setStatus(fieldStatus.getText());
				canvas.displayProfile(current);
				message = "The status of "+current.getName()+ " is changed to "+current.getStatus();
			}
			canvas.showMessage(message);
		}
		
		if(cmd.equals("Change Picture")) {
			if(current!=null) {
				GImage image = null;
				try {
					image = new GImage(fieldPicture.getText());
					current.setImage(image);
					message = "Picture for "+current.getName()+ " is updated";
					
				} catch (ErrorException ex) {
					message = "Cant open that picture: " + fieldPicture.getText();
				}
				canvas.displayProfile(current);
			}
			else {
				message = "First choose the profile to update its picture";
			}
			canvas.showMessage(message);
		}
		
		if(cmd.equals("Add Friend")) {
			if(current!=null) {
				if(database.containsProfile(fieldFriend.getText())) {
					boolean newFriend = current.addFriend(fieldFriend.getText());
					if(newFriend) {
						FacePamphletProfile addedFriend = database.getProfile(fieldFriend.getText());
						addedFriend.addFriend(current.getName());
						message = "Now "+ current.getName() + " and " + addedFriend.getName()+ " are friends!";
					}
				}
				else {
					message = "Profile for " + fieldFriend.getText()+" does not exist";
				}
				canvas.displayProfile(current);
				
			}
			
			else {
				canvas.showMessage("First choose the profile to add a friend");
			}
			canvas.showMessage(message);
		}
		
	}
    
 

}
