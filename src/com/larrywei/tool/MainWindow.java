package com.larrywei.tool;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * @author Larry.Wei(weilaiman@gmail.com)
 * @createTime 18/08/2016
 * */
public class MainWindow {

	private JFrame frame;
	private JTextArea textArea;
	private JLabel label;
	private String filePath;
	private final String METADATA_KEY = "metakey";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
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
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 456, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("browser...");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.showOpenDialog(frame);
				try {
					filePath = chooser.getSelectedFile().getAbsolutePath();
					if (filePath.toUpperCase().endsWith(".PNG")) {
						ImageIcon icon = new ImageIcon(ImageIO.read(new File(filePath)));
						label.setIcon(icon);
						String imgInfo = GraphUtil.readCustomPngMetadata(filePath, METADATA_KEY);
						textArea.setText(imgInfo);
//						System.out.println(imgInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 10, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 53, 325, 51);
		//activate auto next line
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		
		frame.getContentPane().add(textArea);
		textArea.setColumns(10);
		
		JButton button = new JButton("save");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String info = textArea.getText();
//					System.out.println(info);
					if (info != null && info.length() > 0) {
						GraphUtil.writeCustomPngMetadata(filePath, METADATA_KEY, info);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		button.setBounds(134, 10, 93, 23);
		frame.getContentPane().add(button);
		
		label = new JLabel();
		label.setBounds(10, 143, 325, 133);
		frame.getContentPane().add(label);
	}
}
