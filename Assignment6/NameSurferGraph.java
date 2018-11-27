/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		// You fill in the rest //
	}
	
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		removeAll();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		// You fill this in //
		
	}
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		updateGrid();
		
	}
	
	private void addDecadeLabels(int decade, int x0, int height) {
		add(new GLabel(Integer.toString(decade), x0, height - DECADE_LABEL_MARGIN_SIZE));
	}
	
	private void updateGrid() {
		int decade = START_DECADE;
		int x0 = 0;
		int width = getWidth();
		int height = getHeight();
		int columnWidth = width/NDECADES;
		for(int i = 0; i < NDECADES; i++) {
			drawLine(x0, 0, x0, height);
			addDecadeLabels(decade, x0, height);
			decade+=10;
			x0 += columnWidth;
		}
		drawLine(0,GRAPH_MARGIN_SIZE, width, GRAPH_MARGIN_SIZE);
		drawLine(0,height - GRAPH_MARGIN_SIZE, width, height - GRAPH_MARGIN_SIZE);
	}
	
	private void drawLine(int x0, int y0,int x1, int y1) {
		add(new GLine(x0,y0,x1,y1));
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
