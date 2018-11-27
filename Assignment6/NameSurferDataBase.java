/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */
import java.io.*;
import acm.util.*;
import java.util.*;

public class NameSurferDataBase implements NameSurferConstants {
	private HashMap<String, NameSurferEntry> names = new HashMap<String, NameSurferEntry>();
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		// You fill this in //
		try {
			Scanner input = new Scanner(new File(filename));
			while(input.hasNextLine()) {
				String line = input.nextLine();
				NameSurferEntry nameData = new NameSurferEntry(line);
				names.put(nameData.getName(), nameData);
			}
			input.close();
		}
		catch(FileNotFoundException  ex) {
			throw new ErrorException(ex);
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		NameSurferEntry entry = null;
		if(names.containsKey(name)) {
			entry = names.get(name);
		}
		return entry;
	}
}

