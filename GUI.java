import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class GUI extends JFrame implements ChangeListener, ActionListener{
	// Array containing the available color options for the trajectory
	String[] colors = {"#FFFFFF", "#00FA9A", "#DC143C", "#0000FF"};
	
	// GUI controls
	protected JSlider angleSlider, speedSlider;
	protected JLabel angleLabel, speedLabel, colorLabel, fireworkLabel;
	protected JButton run;
	
	// SpinnerModel types to limit the options for my two Spinners 
	SpinnerNumberModel fireworkRange = new SpinnerNumberModel(1, 1, 5, 1);
	SpinnerListModel colorRange = new SpinnerListModel(colors);
	protected JSpinner firework, colorChoice;
	
	// variables to hold the converted values from the GUI
	protected int angle, speed, explosion;
	protected String color;
	
	protected Canvas canvas;
	
	public GUI(){
		
		// creates the frame
		setSize(850, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// creates the GUI space
		JPanel GUI = new JPanel();
		GUI.setLayout(new FlowLayout());

		angleLabel = new JLabel("Angle(45)");
		GUI.add(angleLabel);
		
		// max angle of 90 degrees to avoid firing backwards
		angleSlider = new JSlider(0, 90);
		angleSlider.addChangeListener(this);
		GUI.add(angleSlider);
		
		speedLabel = new JLabel("Speed(75)");
		GUI.add(speedLabel);
		
		// max speed of 150 m/s 
		speedSlider = new JSlider(0, 150);
		speedSlider.addChangeListener(this);
		GUI.add(speedSlider);
		
		colorLabel = new JLabel("Color:");
		GUI.add(colorLabel);
		
		// implements SpinnerListModel to limit the color choices for trajectories
		colorChoice = new JSpinner(colorRange);
		GUI.add(colorChoice);
		
		
		fireworkLabel = new JLabel("Firework (1-5):");
		GUI.add(fireworkLabel);
		
		// implements SpinnerNumberModel to limit the range of firework to 1-5
		firework = new JSpinner(fireworkRange);
		GUI.add(firework);
		
		run = new JButton("RUN");
		run.addActionListener(this);
		GUI.add(run);
		
		// converts the GUI objects into usable types
		angle = angleSlider.getValue();
		speed = speedSlider.getValue();
		explosion = (Integer) firework.getValue();
		color = (String) colorChoice.getValue();
		
		// initializes the default canvas 
		canvas= new Canvas(angle, speed, color, explosion);
		add(canvas, BorderLayout.CENTER);
		add(GUI, BorderLayout.NORTH);
		
		pack();
	}
	
	@Override 
	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == angleSlider) {
			angleLabel.setText("Angle(" + angleSlider.getValue() + ")");
		} else if (e.getSource() == speedSlider) {
			speedLabel.setText("Speed(" + speedSlider.getValue() + ")");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		angle = angleSlider.getValue();
		speed = speedSlider.getValue();
		explosion = (Integer) firework.getValue();
		color = (String) colorChoice.getValue();
		canvas= new Canvas(angle, speed, color, explosion);
		// removes and re-creates the canvas each time RUN is pressed 
		this.remove(canvas);
		this.add(canvas, BorderLayout.CENTER);
		this.setVisible(true);
	}
		
	public static void main(String[] args) {
		new GUI().setVisible(true);
	}
}
