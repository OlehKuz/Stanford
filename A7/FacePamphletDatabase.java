/*
 * File: FacePamphletDatabase.java
 * -------------------------------
 * This class keeps track of the profiles of all users in the
 * FacePamphlet application.  Note that profile names are case
 * sensitive, so that "ALICE" and "alice" are NOT the same name.
 */

import java.util.*;

public class FacePamphletDatabase implements FacePamphletConstants {
	private HashMap <String, FacePamphletProfile> usersDatabase;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the database.
	 */
	public FacePamphletDatabase() {
		usersDatabase = new HashMap <String, FacePamphletProfile>();
	}
	
	
	/** 
	 * This method adds the given profile to the database.  If the 
	 * name associated with the profile is the same as an existing 
	 * name in the database, the existing profile is replaced by 
	 * the new profile passed in.
	 */
	public void addProfile(FacePamphletProfile profile) {
		usersDatabase.put(profile.getName(), profile);
	}

	
	/** 
	 * This method returns the profile associated with the given name 
	 * in the database.  If there is no profile in the database with 
	 * the given name, the method returns null.
	 */
	public FacePamphletProfile getProfile(String name) {
		FacePamphletProfile profile = null;
		if(containsProfile(name)) {
			profile = usersDatabase.get(name);
		}
		return profile;
	}
	
	
	/** 
	 * This method removes the profile associated with the given name
	 * from the database.  It also updates the list of friends of all
	 * other profiles in the database to make sure that this name is
	 * removed from the list of friends of any other profile.
	 * 
	 * If there is no profile in the database with the given name, then
	 * the database is unchanged after calling this method.
	 */
	public void deleteProfile(String name) {
		if(containsProfile(name)) {
			FacePamphletProfile profile = usersDatabase.get(name);
			Iterator <String> friends = profile.getFriends();
			while(friends.hasNext()) {
				FacePamphletProfile profileFriend = usersDatabase.get(friends.next());
				profileFriend.removeFriend(name);
			}
			usersDatabase.remove(name);
			}
	}

	
	/** 
	 * This method returns true if there is a profile in the database 
	 * that has the given name.  It returns false otherwise.
	 */
	public boolean containsProfile(String name) {
		boolean has = false;
		Iterator <String> allUsers = usersDatabase.keySet().iterator();
		while(allUsers.hasNext()) {
			if(allUsers.next().equalsIgnoreCase(name)) {
				has = true;
				return has;
			}
		}
		return has;
	}
	
	
	
}
