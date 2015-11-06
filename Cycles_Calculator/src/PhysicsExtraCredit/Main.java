package PhysicsExtraCredit;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

class Graph extends JPanel {
	String XAxis;
	String YAxis;
	int gridSpace;
	// how much difference in unit used when you move one square over on the graph
	int unitXValue;
	int unitYValue;
	// values at origin of grid (bottom left)
	int unitXStart;
	int unitYStart;
	public ArrayList<Line> lines;
	public class Line {
		public Line (int unitStartX, int unitStartY,int unitEndX, int unitEndY) {
			startX = unitStartX;
			startY = unitStartY;
			endX = unitEndX;
			endY = unitEndY;
		}
		int startX;
		int startY;
		int endX;
		int endY;
	}
	public Graph (String XAxisName, String YAxisName, int newUnitXValue, int newUnitYValue, 
			int newUnitXStart, int newUnitYStart, int gridSpacing) {
		XAxis = XAxisName;
		YAxis = YAxisName;
		gridSpace = gridSpacing;
		unitXValue = newUnitXValue;
		unitYValue = newUnitYValue;
		unitXStart = newUnitXStart;
		unitYStart = newUnitYStart;
		lines = new ArrayList<Line>();
	}
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		FontMetrics metrics = g.getFontMetrics();
		int width = getWidth();
		int height = getHeight();
		int gridXStart = metrics.stringWidth(YAxis)+metrics.stringWidth("1000");
		int gridYStart = height-metrics.getHeight()*2;
		g.drawString(XAxis,width/2,height);
		g.drawString(YAxis,0,height/2);
		//draw grid of graph
		for (int x=gridXStart;x<width-gridSpace;x+=gridSpace) {
			for (int y=gridYStart;y>metrics.getHeight()*2-gridSpace;y-=gridSpace) {
				g.drawLine(x,y,x+gridSpace,y);
				g.drawLine(x+gridSpace,y,x+gridSpace,y-gridSpace);
				g.drawLine(x,y,x,y-gridSpace);
				g.drawLine(x,y-gridSpace,x+gridSpace,y-gridSpace);
			}
		}
		//draw lines on grid
		g.setColor(Color.RED);
		for (Line line : lines) {
			//y must be turned negative since coordinate system of window has 
			//y start at top left and increase downward
			g.drawLine(line.startX+gridXStart,
					-line.startY+gridYStart,
					line.endX+gridXStart,
					-line.endY+gridYStart);
		}
	}
	public boolean addLine (int xUnitsOne, int yUnitsOne,int xUnitsTwo, int yUnitsTwo) {
		lines.add(new Line((int)((float)(xUnitsOne-unitXStart)/(float)unitXValue)*gridSpace,
					(int)((float)(yUnitsOne-unitYStart)/(float)unitYValue)*gridSpace,
					(int)((float)(xUnitsTwo-unitXStart)/(float)unitXValue)*gridSpace,
					(int)((float)(yUnitsTwo-unitYStart)/(float)unitYValue)*gridSpace));
		return true;
	}
}

public class Main {
	Cycle cyc;
	public static void main(String args[]) {
		if (args.length > 0 && args[0].toLowerCase().equals("gui")) {
			JFrame frame = new JFrame("Cycle Calculation");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Graph graph = new Graph("Volume","Pressure",5,5,10,10,10);
			graph.addLine(15,15,25,25);
			frame.add(graph);
			frame.setSize(640,640);
			frame.setVisible(true);
		} else {
			Cycles_2nd_Law.commandLineMain(args);
		}
	}
}

