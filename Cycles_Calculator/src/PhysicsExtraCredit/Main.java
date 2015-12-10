package PhysicsExtraCredit;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
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
		add(new JLabel("Work: "+process.workChange),layout);
		layout.gridx+=1;
		add(new JLabel("Energy: "+process.energyChange),layout);
	}
}

class NodePanel extends Panel{
	public NodePanel (CycleNode node) {
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
		add(new JLabel("Node: "+node.name),layout);
		layout.ipady=1;
		layout.gridy+=1;
		add(new JLabel("Pressure: "+node.pressure),layout);
		layout.gridy+=1;
		add(new JLabel("Volume: "+node.volume),layout);
		layout.gridy+=1;
		add(new JLabel("Temperature: "+node.temperature),layout);
	}
}

public class Main {
	Cycle cyc;
	public static void main(String args[]) {
		if (args.length > 0 && args[0].toLowerCase().equals("cli")) {
			Cycles_2nd_Law.commandLineMain(args);
		} else {
			JFrame frame = new JFrame("Cycle Calculation");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setPreferredSize(
					new Dimension(640,640));
			frame.setLayout(new BorderLayout());
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
			GridBagLayout gridBag = new GridBagLayout();
			GridBagConstraints layout = new GridBagConstraints();
			layout.weightx=1.0;
			layout.weighty=1.0;
			layout.gridwidth = 1;
			layout.gridheight = 1;
			layout.gridx = 0;
			layout.gridy = 0;
			layout.ipadx = 10;
			Cycle cyc;
			try {
				cyc = new Cycle(data);
			} catch (PhysicsException e) {
				JFrame errorFrame= new JFrame("ERROR");
				errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				errorFrame.add(new JLabel(e.getMessage()));
				errorFrame.pack();
				errorFrame.setVisible(true);
				frame.dispose();
				return;
			}
			JPanel dataPanel = new JPanel(gridBag);
			layout.gridx=0;
			dataPanel.add(new JLabel("moles: "+cyc.moles),layout);
			layout.gridx+=1;
			dataPanel.add(new JLabel("gamma: "+cyc.heatCapacityRatio),layout);
			layout.gridx=0;
			layout.gridy+=1;
			dataPanel.add(new JLabel("Cv: "+cyc.heatCapacityV),layout);
			layout.gridx+=1;
			dataPanel.add(new JLabel("Cp: "+cyc.heatCapacityP),layout);
			layout.gridy+=1;
			int gridStarty = layout.gridy;
			layout.gridx=0;
			for (int i=0;i<cyc.processes.size();++i) {
				CycleProcess proc = cyc.processes.get(i);
				dataPanel.add(new ProcessPanel(proc),layout);
				layout.gridy+=1;
			}
			layout.gridy=gridStarty;
			layout.gridx=2;
			for (String key: cyc.nodes.keySet()) {
				CycleNode node = cyc.nodes.get(key);
				dataPanel.add(new NodePanel(node),layout);
				layout.gridy+=1;
			}
			JScrollPane scrollPane = new JScrollPane(dataPanel);
			frame.add(scrollPane);
			frame.pack();
			frame.setVisible(true);
		}
	}
}

