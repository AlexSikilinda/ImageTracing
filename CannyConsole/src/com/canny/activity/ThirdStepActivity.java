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
import com.canny.algorithmsteps.EdgesDetector;

public class ThirdStepActivity extends JFrame {
	private JPanel sourceImagePanel;
	private JPanel resultImagePanel;

	private JLabel imageSourceLabel;
	private JLabel imageResultLable;
	private static final String IMAGE_SOURCE = "test.png";
	private static final String IMAGE_RESULT = "step3.png";
	private static final String IMAGE_SOURCE_SECOND_STEP = "step2.png";

	private EdgesDetector convertor = new EdgesDetector(2.5f, 7.5f);

	/**
	 * Create the panel.
	 */
	public ThirdStepActivity() {
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
		imageSourceLabel.setIcon(new ImageIcon(IMAGE_SOURCE_SECOND_STEP));

		resultImagePanel = new JPanel();
		resultImagePanel.setBounds(481, 30, 293, 447);
		getContentPane().add(resultImagePanel);

		imageResultLable = new JLabel("");
		resultImagePanel.add(imageResultLable);

		JButton btnProcess = new JButton("Process");
		btnProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ImageUtil.step3(2f, 16, 2.f, 7.5f, false, IMAGE_SOURCE,
							IMAGE_RESULT);

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
