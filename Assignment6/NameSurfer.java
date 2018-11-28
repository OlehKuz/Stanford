/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private JTextField nameInput;
	private NameSurferGraph graph;
	private NameSurferDataBase base;
/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the top of the window.
 */
	public void init() {
		setSize(APPLICATION_WIDTH,APPLICATION_HEIGHT);
		
		add(new JLabel("Name:    "), NORTH);
		
		nameInput = new JTextField(TEXT_FIELD_WIDTH);
		nameInput.setActionCommand("Graph");
		add(nameInput, NORTH);
		nameInput.addActionListener(this);
		
		add(new JButton("Graph"), NORTH);	
		add(new JButton("Clear"), NORTH);	
		addActionListeners();
		
		graph = new NameSurferGraph();
		add(graph, CENTER);
		
		base = new NameSurferDataBase(NAMES_DATA_FILE);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Graph")) {			
			NameSurferEntry entry = base.findEntry(nameInput.getText());
			if(entry!=null) {
				graph.addEntry(entry);
			}
			graph.update();
		}
		if(cmd.equals("Clear")) {
			graph.clear();
		}
	}
}
