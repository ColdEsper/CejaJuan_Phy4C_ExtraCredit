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
			layoutRules.gridx = 0;
			layoutRules.gridy = 0;
			layoutRules.ipadx=2;
			layoutRules.ipady=1;
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
			JPanel procPanel = new JPanel(layout);
			for (int i=0;i<cyc.processes.size();++i) {
				CycleProcess proc = cyc.processes.get(i);
				layoutRules.ipady=20;
				switch (proc.type) {
					case ADIABATIC:
						procPanel.add(new JLabel("Adiabatic"),layoutRules);
						break;
					case ISOTHERMAL:
						procPanel.add(new JLabel("Isothermal"),layoutRules);
						break;
					case ISOBARIC:
						procPanel.add(new JLabel("Isobaric"),layoutRules);
						break;
					case ISOCHORIC:
						procPanel.add(new JLabel("Isochoric"),layoutRules);
						break;
				}
				layoutRules.ipady=1;
				layoutRules.gridy+=1;
				procPanel.add(new JLabel("Start: "+proc.start.name),layoutRules);
				layoutRules.gridx+=1;
				procPanel.add(new JLabel("End: "+proc.end.name),layoutRules);
				layoutRules.gridx=0;
				layoutRules.gridy+=1;
				procPanel.add(new JLabel("Heat: "+proc.heatChange),layoutRules);
				layoutRules.gridx+=1;
				procPanel.add(new JLabel("Work: "+proc.heatChange),layoutRules);
				layoutRules.gridx+=1;
				procPanel.add(new JLabel("Energy: "+proc.energyChange),layoutRules);
				layoutRules.gridx=0;
				layoutRules.gridy+=1;
			}
			layoutRules.gridx=0;
			layoutRules.gridy=0;
			layoutRules.anchor=GridBagConstraints.NORTH;
			frame.add(procPanel,layoutRules);
			frame.setSize(640,640);
			frame.setVisible(true);
		} else {
			Cycles_2nd_Law.commandLineMain(args);
		}
	}
}

