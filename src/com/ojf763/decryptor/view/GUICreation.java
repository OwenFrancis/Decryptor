package com.ojf763.decryptor.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.event.*;
import java.io.File; 
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import com.ojf763.decryptor.model.*;

public class GUICreation{
	
	private JFrame frame;
	private FileReader in1;
	private FileReader in2;
	private FileWriter out1;
	private FileWriter out2;
	private FileWriter out3;
	private boolean frequenciesSelected = false;
	private boolean cipherSelected = false;
	
	public static void main (String[] args) {
		GUICreation g = new GUICreation();
	}
	
	
	public GUICreation () {
		frame = new JFrame("GUI Creation");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		JPanel subPanel1 = new JPanel(new BorderLayout());
		
		JPanel subPanel2 = new JPanel(new BorderLayout());
		
		
		JButton button1 = new JButton("Load Letter Frequncies Text File");
		JButton button2 = new JButton("Load Cipher Text File");
		JButton button3 = new JButton("Decrypt Using First Method - Match Most Frequent With Most Frequent");
		JButton button4 = new JButton("Decrypt Using Second Method - Match With Closest Possible Frequency");
		
		
		JLabel label1 = new JLabel("Decryption complete");
		label1.setPreferredSize(new Dimension(100, 100));
		label1.setFont(new Font("Serif", Font.PLAIN, 40));
		label1.setOpaque(false);
		
		
		button1.setFont(new Font("Serif", Font.PLAIN, 16));
		button2.setFont(new Font("Serif", Font.PLAIN, 16));
		button3.setFont(new Font("Serif", Font.PLAIN, 16));
		button4.setFont(new Font("Serif", Font.PLAIN, 16));
		
		button1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				frequenciesSelected = true;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					try{
						
						File frequencyFile = fileChooser.getSelectedFile();
						in1 = new FileReader(frequencyFile.getName());
						out1 = new FileWriter("chosenFrequencies.txt");
						int c;
						while((c = in1.read()) != -1){
							out1.write(c);
						}
						out1.close();
						in1.close();
					}
					catch (FileNotFoundException f){
						System.out.println("File not found");
					}
					catch (IOException f){
						System.out.println("I/O Error");
					}
				}        
			}
		});
		button2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				cipherSelected = true;
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					try{
						
						File cipherFile = fileChooser.getSelectedFile();
						in2 = new FileReader(cipherFile.getName());
						out2 = new FileWriter("chosenCipher.txt");
						int c;
						while((c = in2.read()) != -1){
							out2.write(c);
						}
						out2.close();
						in2.close();
					}
					catch (FileNotFoundException f){
						System.out.println("File not found");
					}
					catch (IOException f){
						System.out.println("I/O Error");
					}
				}
			}
		});
		button3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(frequenciesSelected == true && cipherSelected == true){
					
					FirstDecryptionMethod decryption = new FirstDecryptionMethod();
					
					
				}
				
			}
		});
		button4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(frequenciesSelected == true && cipherSelected == true){
					
					SecondDecryptionMethod decryption = new SecondDecryptionMethod();
				}
			}
		});
		
		subPanel1.add(button1, BorderLayout.NORTH);
		subPanel1.add(button2, BorderLayout.SOUTH);
		mainPanel.add(subPanel1,BorderLayout.NORTH);
		mainPanel.add(button3, BorderLayout.WEST);
		mainPanel.add(button4, BorderLayout.EAST);
		subPanel2.add(label1, BorderLayout.SOUTH);
		//Add the second sub panel to the frame once a decryption button is pressed?
		
		frame.add(mainPanel);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	
	
}