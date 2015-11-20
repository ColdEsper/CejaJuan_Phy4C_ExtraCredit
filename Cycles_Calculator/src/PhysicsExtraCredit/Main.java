package PhysicsExtraCredit;

import java.util.ArrayList;
import java.awt.*;
import java.io.IOException;
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
	public static void main(String args[]) throws IOException {
		if (args.length > 0 && args[0].toLowerCase().equals("gui")) {
			JFrame frame = new JFrame("Cycle Calculation");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			GridBagLayout layout = new GridBagLayout();
			frame.setLayout(layout);
			GridBagConstraints layoutRules = new GridBagConstraints();
			layoutRules.weightx=1.0;
			layoutRules.weighty=1.0;
			layoutRules.gridwidth = 1;
			layoutRules.gridheight = 1;
			layoutRules.gridy = 0;
			JFileChooser chooser = new JFileChooser();
			int option = chooser.showOpenDialog(frame);
			CycleData data;
			if (option == JFileChooser.APPROVE_OPTION) {
				String fileName = chooser.getSelectedFile().toString();
				try {
					data = Cycle.loadFile(fileName);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
					frame.dispose();
					return;
				}
			} else {
				JOptionPane.showMessageDialog(null,"File was not chosen. Program will exit.");
				frame.dispose();
				return;
			}
			Cycle cyc = new Cycle(data);
			/*Graph graph = new Graph("Volume","Pressure",5,5,10,10,10);
			graph.addLine(15,15,25,25);
			frame.add(graph);*/
			for (int i=0;i<cyc.processes.size();++i) {
				CycleProcess proc = cyc.processes.get(i);
				JPanel procPanel = new JPanel ();
				switch (proc.type) {
					case ADIABATIC:
						procPanel.add(new JLabel("Adiabatic"));
						break;
					case ISOTHERMAL:
						procPanel.add(new JLabel("Isothermal"));
						break;
					case ISOBARIC:
						procPanel.add(new JLabel("Isobaric"));
						break;
					case ISOCHORIC:
						procPanel.add(new JLabel("Isochoric"));
						break;
				}
				procPanel.add(new JLabel("Start: "+proc.start.name));
				procPanel.add(new JLabel("End: "+proc.end.name));
				procPanel.add(new JLabel("Heat: "+proc.heatChange));
				procPanel.add(new JLabel("Work: "+proc.heatChange));
				procPanel.add(new JLabel("Energy: "+proc.energyChange));
				layout.setConstraints(procPanel,layoutRules);
				frame.add(procPanel,layoutRules);
				layoutRules.gridy+=1;
			}
			frame.setSize(640,640);
			frame.setVisible(true);
		} else {
			Cycles_2nd_Law.commandLineMain(args);
		}
	}
}

