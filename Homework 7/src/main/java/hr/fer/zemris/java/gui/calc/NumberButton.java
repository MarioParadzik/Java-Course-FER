package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class NumberButton extends JButton{

	private static final long serialVersionUID = 1L;
	public static final Color BUTTON_COLOUR = new Color(221,221,255);
	Font font1 = new Font("SansSerif", Font.BOLD, 40);
	private String name;
	private ActionListener event;
	
	public NumberButton(String name, ActionListener event) {
		this.name = name;
		this.event = event;
		initGUI();
	}

	private void initGUI() {
		setBackground(BUTTON_COLOUR);
		setForeground(Color.BLACK);
		setFont(font1);
		setText(name);
		addActionListener(event);
	}
}
