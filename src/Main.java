import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

class Graph extends JPanel {
	String XAxis;
	String YAxis;
	public Graph (String XAxisName, String YAxisName) {
		XAxis = XAxisName;
		YAxis = YAxisName;
	}
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		g.drawString(XAxis,width/2,height);
		g.drawString(YAxis,0,height/2);
	}
}

public class Main {
	Cycle cyc;
	public static void main(String args[]) {
		JFrame frame = new JFrame("Cycle Calculation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Graph graph = new Graph("X-Axis","Y-Axis");
		frame.add(graph);
		frame.setSize(640,640);
		frame.setVisible(true);
	}
}

