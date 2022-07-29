package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * This class represents a basic operators button.
 */
public class BasicOperatiosButton extends JButton {

	private static final long serialVersionUID = 1L;
	public static final Color BUTTON_COLOUR = new Color(221,221,255);
	Font font1 = new Font("SansSerif", Font.BOLD, 15);
	private String name;
	private ActionListener event;
	
	/**
	 * Basic Constructor.
	 * @param name Button name.
	 * @param event Event.
	 */
	public BasicOperatiosButton(String name, ActionListener event) {
		this.name = name;
		this.event = event;
		initGUI();
	}

	/**
	 * This method is used to initialize the button.
	 */
	private void initGUI() {
		setBackground(BUTTON_COLOUR);
		setForeground(Color.BLACK);
		setFont(font1);
		setText(name);
		addActionListener(event);
	}
}
