package PhysicsExtraCredit;

import java.util.ArrayList;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

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

