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
	private ArrayList<NameSurferEntry> entries = new ArrayList<NameSurferEntry>();
	private int count;
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
		entries.clear();
		removeAll();
		updateGrid();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		entries.add(entry);		
	}
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		count = 1;
		removeAll();	
		updateGrid();
		
	}
	
	/*
	Redraws rank and Name for each decade for NameSurferEntry: chooses where to put 
	 * rank line dynamically according to screen dimentions. Colors each new entry in one of 4 colors 
	 * supplied by chooseColor method
	 */
	 
	private void redrawNames(int columnWidth, int height) {
		double heightOfOne = (height - (2.0*GRAPH_MARGIN_SIZE))/MAX_RANK;
		for(NameSurferEntry e:entries) {
			int x0 = 0;
			double y0 = e.getRank(0)*heightOfOne;
			if(y0 == 0) {
				y0 = height - 2.0*GRAPH_MARGIN_SIZE;
			}
			Color c = chooseColor(count);
			GLabel l = new GLabel(e.getName()+ "  "+ Integer.toString(e.getRank(0)), x0, y0+GRAPH_MARGIN_SIZE);
			l.setColor(c);
			add(l);
			for(int i = 1; i < NDECADES; i++) {
				double y1 = e.getRank(i)*heightOfOne;
				if(y1 == 0) {
					y1 = height - 2.0*GRAPH_MARGIN_SIZE;
				}
				GLine ln = new GLine(x0, y0+GRAPH_MARGIN_SIZE, x0 + columnWidth, y1+GRAPH_MARGIN_SIZE);
				ln.setColor(c);
				add(ln);
				GLabel lb = new GLabel(e.getName() + "  "+ e.getRank(i), x0 + columnWidth, y1+GRAPH_MARGIN_SIZE);
				lb.setColor(c);
				add(lb);
				x0+=columnWidth;
				y0 = y1;
				
			}
			count++;
		}
	}
	
	private Color chooseColor(int count) {
		Color c;
			if(count % 4 == 0) c = Color.MAGENTA;
			else if(count % 3 == 0) c = Color.BLUE;
			else if(count % 2 == 0) c = Color.RED;
			else c = Color.BLACK; 
		return c;
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
		redrawNames(columnWidth, height);
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
