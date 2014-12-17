package com.canny.activity;

import javax.swing.JFrame;
import javax.swing.JTextField;


public class FirstStepActivity extends JFrame{
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public FirstStepActivity() {
		getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(26, 67, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

	}
}
