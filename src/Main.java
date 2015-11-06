import java.awt.*;
import javax.swing.*;

class Graph extends JPanel {
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();
		g.drawString("X-Axis",width/2,height);
		g.drawString("Y-Axis",0,height/2);
	}
}

public class Main {
	Cycle cyc;
	public static void main(String args[]) {
		JFrame frame = new JFrame("Cycle Calculation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Graph graph = new Graph();
		frame.add(graph);
		frame.setSize(640,640);
		frame.setVisible(true);
	}
}

