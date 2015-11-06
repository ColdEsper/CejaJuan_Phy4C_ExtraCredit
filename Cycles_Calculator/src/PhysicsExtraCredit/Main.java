package PhysicsExtraCredit;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

class Graph extends JPanel {
	String XAxis;
	String YAxis;
	int gridSpace;
	public Graph (String XAxisName, String YAxisName, int gridSpacing) {
		XAxis = XAxisName;
		YAxis = YAxisName;
		gridSpace = gridSpacing;
	}
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		FontMetrics metrics = g.getFontMetrics();
		int width = getWidth();
		int height = getHeight();
		g.drawString(XAxis,width/2,height);
		g.drawString(YAxis,0,height/2);
		//draw grid of graph
		for (int x=metrics.stringWidth(YAxis)+3;x<width-gridSpace;x+=gridSpace) {
			for (int y=metrics.getHeight();y<height-metrics.getHeight()-gridSpace;y+=gridSpace) {
				g.drawLine(x,y,x+gridSpace,y);
				g.drawLine(x+gridSpace,y,x+gridSpace,y+gridSpace);
				g.drawLine(x,y,x,y+gridSpace);
				g.drawLine(x,y+gridSpace,x+gridSpace,y+gridSpace);
			}
		}
	}
}

public class Main {
	Cycle cyc;
	public static void main(String args[]) {
		if (args.length > 0 && args[0].toLowerCase().equals("gui")) {
			JFrame frame = new JFrame("Cycle Calculation");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Graph graph = new Graph("X-Axis","Y-Axis",20);
			frame.add(graph);
			frame.setSize(640,640);
			frame.setVisible(true);
		} else {
			Cycles_2nd_Law.commandLineMain(args);
		}
	}
}

