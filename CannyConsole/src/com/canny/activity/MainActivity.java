package com.canny.activity;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.canny.ImageUtil;

public class MainActivity {

	public static final String IMAGE_ORIGINAL = "test.png";
	public static final String IMAGE_EDGE = "edge.png";

	private JFrame frame;

	private JPanel imagePanel;
	private JLabel imageLabel;

	private JButton btnClear;
	private JButton btnProcess;
	private JTextField gaussianKernelRadius;
	private JTextField gaussianKernelWidth;
	private JTextField lowThreshold;
	private JTextField highThreshold;

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

		imageLabel = new JLabel("");
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
		btnClear.setBounds(685, 64, 89, 23);
		frame.getContentPane().add(btnClear);

		btnProcess = new JButton("Process");
		btnProcess.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					float lowThresholdValue = Float.parseFloat(lowThreshold
							.getText());
					float highThresholdValue = Float.parseFloat(highThreshold
							.getText());
					;
					float gaussianKernelRadiusValue = Float
							.parseFloat(gaussianKernelRadius.getText());
					;
					int gaussianKernelWidthValue = Integer
							.parseInt(gaussianKernelWidth.getText());
					boolean contrastNormalizedValue = false;

					ImageUtil.generateImageEdge(gaussianKernelRadiusValue,
							lowThresholdValue, highThresholdValue,
							gaussianKernelWidthValue, contrastNormalizedValue);
					imageLabel.setIcon(new ImageIcon((ImageUtil.count - 1)
							+ IMAGE_EDGE));
					imagePanel.add(imageLabel);

				} catch (IOException e1) {

					e1.printStackTrace();
				}

			}
		});
		btnProcess.setBounds(685, 30, 89, 23);
		frame.getContentPane().add(btnProcess);
		init();

	}

	private void update() {
		frame.revalidate();
		frame.repaint();
		frame.revalidate();
	}

	private void init() {
		gaussianKernelRadius = new JTextField();
		gaussianKernelRadius.setText("2");
		gaussianKernelRadius.setBounds(340, 98, 86, 20);
		frame.getContentPane().add(gaussianKernelRadius);
		gaussianKernelRadius.setColumns(10);

		JLabel lblNewLabel = new JLabel("Gaussian Kernel Radius");
		lblNewLabel.setBounds(453, 101, 321, 14);
		frame.getContentPane().add(lblNewLabel);

		gaussianKernelWidth = new JTextField();
		gaussianKernelWidth.setText("16");
		gaussianKernelWidth.setBounds(340, 129, 86, 20);
		frame.getContentPane().add(gaussianKernelWidth);
		gaussianKernelWidth.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Gaussian Kernel Width");
		lblNewLabel_1.setBounds(453, 132, 321, 14);
		frame.getContentPane().add(lblNewLabel_1);

		lowThreshold = new JTextField();
		lowThreshold.setText("2.5");
		lowThreshold.setBounds(340, 160, 86, 20);
		frame.getContentPane().add(lowThreshold);
		lowThreshold.setColumns(10);

		JLabel lblLowthreshold = new JLabel("Low Threshold");
		lblLowthreshold.setBounds(453, 163, 321, 14);
		frame.getContentPane().add(lblLowthreshold);

		highThreshold = new JTextField();
		highThreshold.setText("7.5");
		highThreshold.setBounds(340, 201, 86, 20);
		frame.getContentPane().add(highThreshold);
		highThreshold.setColumns(10);

		JLabel lblHighthreshold = new JLabel("High Threshold");
		lblHighthreshold.setBounds(453, 204, 321, 14);
		frame.getContentPane().add(lblHighthreshold);

		JButton btnFirstStep = new JButton("First Step");
		btnFirstStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FirstStepActivity firstStepActivity = new FirstStepActivity(
						IMAGE_ORIGINAL);
				firstStepActivity.setSize(800, 600);
				firstStepActivity.setTitle("First step");
				firstStepActivity.setVisible(true);

			}
		});
		btnFirstStep.setBounds(515, 269, 224, 23);
		frame.getContentPane().add(btnFirstStep);

		JButton btnSecondStep = new JButton("Second Step");
		btnSecondStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SecondStepActivity secondStepActivity = new SecondStepActivity();
				secondStepActivity.setSize(800, 600);
				secondStepActivity.setTitle("Second step");
				secondStepActivity.setVisible(true);
			}
		});
		btnSecondStep.setBounds(515, 318, 224, 23);
		frame.getContentPane().add(btnSecondStep);

		JButton btnThre = new JButton("Third Step");
		btnThre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ThirdStepActivity thirdStepActivity = new ThirdStepActivity();
				thirdStepActivity.setSize(800, 600);
				thirdStepActivity.setTitle("Third step");
				thirdStepActivity.setVisible(true);
			}
		});
		btnThre.setBounds(515, 367, 224, 23);
		frame.getContentPane().add(btnThre);
	}
}
