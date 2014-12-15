package com.canny;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainActivity {
	
	public static final String IMAGE_ORIGINAL="test.png";
	public static final String IMAGE_EDGE="edge.png";

	private JFrame frame;

	private JPanel imagePanel;
	private JLabel imageLabel ;

	private JButton btnClear;
	private JButton btnProcess;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainActivity window = new MainActivity();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainActivity() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		imagePanel = new JPanel();
		imagePanel.setBounds(10, 30, 293, 447);
		frame.getContentPane().add(imagePanel);
		
		
		imageLabel= new JLabel("");
		imageLabel.setIcon(new ImageIcon(IMAGE_ORIGINAL));
		imagePanel.add(imageLabel);	
		
		

		btnClear = new JButton("Clear");		
		btnClear.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				imageLabel.setIcon(new ImageIcon(IMAGE_ORIGINAL));
				imagePanel.add(imageLabel);					
				update();
				
			}
		});
		btnClear.setBounds(504, 30, 89, 23);
		frame.getContentPane().add(btnClear);

		btnProcess = new JButton("Process");
		btnProcess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					ImageUtil.generateImageEdge();
					imageLabel.setIcon(new ImageIcon(IMAGE_EDGE));
					imagePanel.add(imageLabel);	
					
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		btnProcess.setBounds(504, 76, 89, 23);
		frame.getContentPane().add(btnProcess);
	}
	
	
	private void update(){		
		frame.revalidate();
		frame.repaint();
		frame.revalidate();
	}
}
