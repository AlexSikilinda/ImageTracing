package com.canny.activity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.canny.ImageUtil;
import com.canny.algorithmsteps.GradientCalculator;

public class SecondStepActivity extends JFrame {
	private JPanel sourceImagePanel;
	private JPanel resultImagePanel;
	
	private JLabel imageSourceLabel;
	private JLabel imageResultLable;
	private static final String IMAGE_SOURCE = "test.png";
	private static final String IMAGE_RESULT = "step2.png";
	private static final String IMAGE_SOURCE_FIST_STEP = "step1.png";

	GradientCalculator convertor = new GradientCalculator(2f, 16);

	/**
	 * Create the panel.
	 */
	public SecondStepActivity() {
		getContentPane().setLayout(null);

		initialize();

	}

	private void initialize() {
		this.setBounds(100, 100, 800, 600);

		sourceImagePanel = new JPanel();
		sourceImagePanel.setBounds(10, 30, 293, 447);
		getContentPane().add(sourceImagePanel);

		imageSourceLabel = new JLabel("");
		sourceImagePanel.add(imageSourceLabel);
		imageSourceLabel.setIcon(new ImageIcon(IMAGE_SOURCE_FIST_STEP));

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
							.getBufferedImage(IMAGE_SOURCE);
					BufferedImage bufferedResult = convertor
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
