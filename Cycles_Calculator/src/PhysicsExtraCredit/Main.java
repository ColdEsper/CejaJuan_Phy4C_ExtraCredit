package PhysicsExtraCredit;

import java.util.ArrayList;
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

class ProcessPanel extends Panel{
	public ProcessPanel (CycleProcess process) {
		setLayout(new GridBagLayout());
		GridBagConstraints layout = new GridBagConstraints();
		layout.weightx=1.0;
		layout.weighty=1.0;
		layout.gridwidth = 1;
		layout.gridheight = 1;
		layout.gridx = 0;
		layout.gridy = 0;
		layout.ipadx=2;
		layout.ipady=20;
		switch (process.type) {
			case ADIABATIC:
				add(new JLabel("Adiabatic"),layout);
				break;
			case ISOTHERMAL:
				add(new JLabel("Isothermal"),layout);
				break;
			case ISOBARIC:
				add(new JLabel("Isobaric"),layout);
				break;
			case ISOCHORIC:
				add(new JLabel("Isochoric"),layout);
				break;
		}
		layout.ipady=1;
		layout.gridy+=1;
		add(new JLabel("Start: "+process.start.name),layout);
		layout.gridx+=1;
		add(new JLabel("End: "+process.end.name),layout);
		layout.gridx=0;
		layout.gridy+=1;
		add(new JLabel("Heat: "+process.heatChange),layout);
		layout.gridx+=1;
		add(new JLabel("Work: "+process.heatChange),layout);
		layout.gridx+=1;
		add(new JLabel("Energy: "+process.energyChange),layout);
	}
}

public class Main {
	Cycle cyc;
	public static void main(String args[]) throws IOException {
		if (args.length > 0 && args[0].toLowerCase().equals("gui")) {
			JFrame frame = new JFrame("Cycle Calculation");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			GridBagLayout gridBag = new GridBagLayout();
			frame.setLayout(gridBag);
			GridBagConstraints layout = new GridBagConstraints();
			layout.weightx=1.0;
			layout.weighty=1.0;
			layout.gridwidth = 1;
			layout.gridheight = 1;
			layout.gridx = 0;
			layout.gridy = 0;
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
			JPanel procsPanel = new JPanel(gridBag);
			for (int i=0;i<cyc.processes.size();++i) {
				CycleProcess proc = cyc.processes.get(i);
				procsPanel.add(new ProcessPanel(proc),layout);
				layout.gridy+=1;
			}
			layout.gridx=0;
			layout.gridy=0;
			layout.anchor=GridBagConstraints.NORTH;
			frame.add(procsPanel,layout);
			frame.setSize(640,640);
			frame.setVisible(true);
		} else {
			Cycles_2nd_Law.commandLineMain(args);
		}
	}
}

