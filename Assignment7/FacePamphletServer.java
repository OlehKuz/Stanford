/*
 * File: FacePamphletServer.java
 * ------------------------------
 * When it is finished, this program will implement a basic
 * social network management server.  Remember to update this comment!
 */

import java.util.*;

import acm.graphics.GImage;
import acm.program.*;

public class FacePamphletServer extends ConsoleProgram 
					implements SimpleServerListener {
	
	/* The internet port to listen to requests on */
	private static final int PORT = 8000;
	private HashMap <String, FacePamphletProfile> usersDatabase = new HashMap <String, FacePamphletProfile>();
	
	
	/* The server object. All you need to do is start it */
	private SimpleServer server = new SimpleServer(this, PORT);

	/**
	 * Starts the server running so that when a program sends
	 * a request to this computer, the method requestMade is
	 * called.
	 */
	public void run() {
		println("Starting server on port " + PORT);
		server.start();
	}

	/**
	 * When a request is sent to this computer, this method is
	 * called. It must return a String.
	 */
	public String requestMade(Request request) {
		String cmd = request.getCommand();
		println(request.toString());
		
		if(cmd.equals("ping")) {
			return "Hello, internet";
		}
		
		if(cmd.equals("addProfile")) {
			String result;
			String user = request.getParam("name");
			if(!hasThisUser(user)) {
				FacePamphletProfile newUser = new FacePamphletProfile(user);
				usersDatabase.put(user, newUser);
				result = "success";
			}
			else {
				result = "Error: Database already contains a user with name " + user;
			}
			return result;
			
		}
		
		if(cmd.equals("containsProfile")) {
			String user = request.getParam("name");
			return String.valueOf(hasThisUser(user));
		}
		
		// removes profile from the database and deletes this profile from friendlist of all its friends
		if(cmd.equals("deleteProfile")) {
			String result;
			String user = request.getParam("name");
			if(hasThisUser(user)) {
				FacePamphletProfile profile = usersDatabase.get(user);
				ArrayList<String> friends = profile.getFriends();
				for(String friend : friends) {
					FacePamphletProfile profileFriend = usersDatabase.get(friend);
					profileFriend.removeFriend(user);
				}
				usersDatabase.remove(user);
				result = "success";
			}
			else {
				result = "Error: Database contains no user with name " + user;
			}
			return result;
		}
		if(cmd.equals("setStatus")) {
			String user = request.getParam("name");
			if(hasThisUser(user)) {
				String status = request.getParam("status");
				FacePamphletProfile profile = usersDatabase.get(user);
				profile.setStatus(status);
				return "success";
			}
			else {
				return "Error: Database contains no user with name "+user;
			}
		}
		
		if(cmd.equals("getStatus")) {
			String user = request.getParam("name");
			if(hasThisUser(user)) {
				FacePamphletProfile profile = usersDatabase.get(user);
				return profile.getStatus();
			}
			else {
				return "Error: Database contains no user with name " + user;
			}
		}
		
		if(cmd.equals("setImage")) {
			String user = request.getParam("name");
			if(hasThisUser(user)) {
				String img = request.getParam("imageString");
				GImage image = SimpleServer.stringToImage(img);
				FacePamphletProfile profile = usersDatabase.get(user);
				profile.setImage(image);
				return "success";
			}
			else {
				return "Error: Database contains no user with name " + user;
			}
		}
		
		if(cmd.equals("getImage")) {
			String user = request.getParam("name");
			if(hasThisUser(user)) {
				FacePamphletProfile profile = usersDatabase.get(user);
				GImage img = profile.getImage();
				String imageString = SimpleServer.imageToString(img);
				return imageString;
			}
			else {
				return "Error: Database contains no user with name " + user;
			}
		}
		
		if(cmd.equals("addFriend")) {
			String user1 = request.getParam("name1");
			String user2 = request.getParam("name2");
			if(hasThisUser(user1)&&hasThisUser(user2)) {
				if(user1.equalsIgnoreCase(user2)) {
					return "Error: This is the same person ";
				}
				FacePamphletProfile profile1 = usersDatabase.get(user1);
				boolean addedFriend = profile1.addFriend(user2);
				if(addedFriend) {
					FacePamphletProfile profile2 = usersDatabase.get(user2);
					profile2.addFriend(user1);
					return "success";
				}
				return "Error: We are already friends ";
				
			}
			return "Error: Either "+ user1+" or "+user2+" are not in database ";
		}
		
		if(cmd.equals("getFriends")) {
			String user = request.getParam("name");
			if(hasThisUser(user)) {
				FacePamphletProfile profile = usersDatabase.get(user);
				ArrayList<String> friends = profile.getFriends();
				return friends.toString();
			}
			return "Error: Database contains no user with name " + user;
		}
		
		return "Error: Unknown command " + cmd + ".";
	}
	
	// Checks if we have the user with a supplied name in our database ignoring letter case
	private boolean hasThisUser(String user) {
		boolean has = false;
		Iterator <String> allUsers = usersDatabase.keySet().iterator();
		while(allUsers.hasNext()) {
			if(allUsers.next().equalsIgnoreCase(user)) {
				has = true;
				return has;
			}
		}
		return has;
	}

}
