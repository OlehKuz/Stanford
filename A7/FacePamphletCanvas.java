/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	private GLabel l;
	private GLabel l2;


	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		l = new GLabel("");
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		l.setLabel(msg);
		l.setFont(MESSAGE_FONT);
		double x = (getWidth()-l.getWidth())/2;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(l, x, y);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		addProfileLabel(profile.getName());
		addImg(profile.getImage());
		addStatus(profile.getName(), profile.getStatus());
		addFriends(profile.getFriends());
		
	}
	
	private void addFriends(Iterator<String> friends) {
		
		GLabel fr = new GLabel("Friends: ");
		double x = getWidth()/2;
		fr.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(fr, x,l2.getHeight()+2*TOP_MARGIN );
		double ySingle = fr.getY();
		while(friends.hasNext()) {
			GLabel singleFriend = new GLabel (friends.next());
			singleFriend.setFont(PROFILE_FRIEND_FONT);
			ySingle += singleFriend.getHeight();
			add(singleFriend, x, ySingle);
		}
	}
	
	public void addStatus(String name, String status) {
		GLabel statusS;	
		if(status.length()>=1) {
			statusS = new GLabel(name +" is "+status);		
		}
		else {
			statusS = new GLabel("No current status");
		}
		statusS.setFont(PROFILE_STATUS_FONT);
		double yStatus = l2.getHeight()+2*TOP_MARGIN + IMAGE_HEIGHT + STATUS_MARGIN + statusS.getHeight();
		add(statusS, LEFT_MARGIN, yStatus);
	}

	private void addImg(GImage img) {
		if(img!=null) {
			img.scale(IMAGE_WIDTH/img.getWidth(), IMAGE_HEIGHT/img.getHeight());
			add(img,LEFT_MARGIN, l2.getHeight()+2*TOP_MARGIN);
		}
		else {
			GRect r = new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
			add(r, LEFT_MARGIN, l2.getHeight()+2*TOP_MARGIN);
			GLabel lb = new GLabel("NO Image");
			lb.setFont(PROFILE_IMAGE_FONT);
			add(lb,(IMAGE_WIDTH-lb.getWidth())/2 + LEFT_MARGIN, l2.getHeight()+2*TOP_MARGIN + (IMAGE_HEIGHT)/2 );
		}
	}
	
	
	   private void addProfileLabel(String name) {
			l2 = new GLabel(name);
			l2.setColor(Color.BLUE);
			l2.setFont(PROFILE_NAME_FONT);
			add(l2,LEFT_MARGIN, TOP_MARGIN + l2.getHeight());
		}
	
}
