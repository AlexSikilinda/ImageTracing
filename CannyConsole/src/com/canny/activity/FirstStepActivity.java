package com.canny.activity;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.canny.ImageUtil;
import com.canny.algorithmsteps.AlgorithmStep;
import com.canny.algorithmsteps.LuminanceConverter;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FirstStepActivity extends JFrame {
	private AlgorithmStep converter = new LuminanceConverter();
	private JPanel sourceImagePanel;
	private JPanel resultImagePanel;
	private String imageResource;
	private JLabel imageSourceLabel;
	private JLabel imageResultLable;
	
	private static final String IMAGE_RESULT ="step1.png";

	/**
	 * Create the panel.
	 */
	public FirstStepActivity(String imageResource) {
		getContentPane().setLayout(null);
		this.imageResource = imageResource;
		initialize();
	}

	private void initialize() {
		this.setBounds(100, 100, 800, 600);

		sourceImagePanel = new JPanel();
		sourceImagePanel.setBounds(10, 30, 293, 447);
		getContentPane().add(sourceImagePanel);

		imageSourceLabel = new JLabel("");
		sourceImagePanel.add(imageSourceLabel);
		imageSourceLabel.setIcon(new ImageIcon(imageResource));

		resultImagePanel = new JPanel();
		resultImagePanel.setBounds(481, 30, 293, 447);
		getContentPane().add(resultImagePanel);
		
		imageResultLable = new JLabel("");
		resultImagePanel.add(imageResultLable);

		JButton btnProcess = new JButton("Process");
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					BufferedImage bufferedImage = ImageUtil
							.getBufferedImage(imageResource);
					BufferedImage bufferedResult = converter
							.process(bufferedImage);
					ImageUtil.convertToImage(IMAGE_RESULT, bufferedResult);
					imageResultLable.setIcon(new ImageIcon(IMAGE_RESULT));
					update();
					

				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnProcess.setBounds(346, 79, 89, 23);
		getContentPane().add(btnProcess);

		update();
	}

	private void update() {
		getContentPane().revalidate();
		getContentPane().repaint();
		getContentPane().revalidate();
	}

}
