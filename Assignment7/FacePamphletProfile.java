/*
 * File: FacePamphletProfile.java
 * ------------------------------
 * When it is finished, this class will define a new variable type
 * that represents a single FacePamphlet profile. Remember to update this comment!
 */

import java.util.*;
import acm.graphics.*;


public class FacePamphletProfile {
	private ArrayList<String> friends = new ArrayList<String>();
	private String nameP;
	private GImage img;
	private String statusP;

	// your code here

	/** 
	 * Constructor
	 * This method takes care of any initialization needed for
	 * the profile.
	 */
	public FacePamphletProfile(String name) {
		nameP = name;
		img = null;
		statusP = "";
	}

	/** This method returns the name associated with the profile. */ 
	public String getName() {
		return nameP;
	}


	public GImage getImage() {
		return img;
	}

	/** This method sets the image associated with the profile. */ 
	public void setImage(GImage image) {
		img = image;
	}

	/** 
	 * This method returns the status associated with the profile.
	 * If there is no status associated with the profile, the method
	 * returns the empty string ("").
	 */ 
	public String getStatus() {
		return statusP;
	}

	/** This method sets the status associated with the profile. */ 
	public void setStatus(String status) {
		statusP = status;
	}

	/** 
	 * This method adds the named friend to this profile's list of 
	 * friends.  It returns true if the friend's name was not already
	 * in the list of friends for this profile (and the name is added 
	 * to the list).  The method returns false if the given friend name
	 * was already in the list of friends for this profile (in which 
	 * case, the given friend name is not added to the list of friends 
	 * a second time.)
	 */
	public boolean addFriend(String friend) {
		Iterator<String> i = friends.iterator();
		while(i.hasNext()) {
			if(i.next().equalsIgnoreCase(friend)) {
				return false;
			}
		}
		friends.add(friend);
		return true;
	}

	/** 
	 * This method removes the named friend from this profile's list
	 * of friends.  It returns true if the friend's name was in the 
	 * list of friends for this profile (and the name was removed from
	 * the list).  The method returns false if the given friend name 
	 * was not in the list of friends for this profile (in which case,
	 * the given friend name could not be removed.)
	 */
	public boolean removeFriend(String friend) {
		Iterator<String> i = friends.iterator();
		while(i.hasNext()) {
			if(i.next().equalsIgnoreCase(friend)) {
				friends.remove(friend);
				return true;
			}
		}		
		return false;
	}

	/** 
	 * This method returns an ArrayList of friends 
	 * associated with the profile.
	 */ 
	public ArrayList<String> getFriends() {
		return friends;
	}

}
