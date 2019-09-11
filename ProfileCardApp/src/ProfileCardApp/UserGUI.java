package ProfileCardApp;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UserGUI extends JFrame implements ActionListener {
	
	//Size Variables
	final int FRAME_WIDTH = 500;
	final int FRAME_HEIGHT = 400;
	
	//JLabels
	private JLabel title;
	private JLabel nameLabel;
	private JLabel numberLabel;
	private JLabel ageLabel;
	private JLabel gradeLabel;
	private JLabel countryLabel;
	private JLabel photoLabel;
	private JLabel photoFilePathLabel;
	private JLabel bioLabel;
	private JLabel spacing;
	
	//JTextFields
	private JTextField nameField;
	private JTextField numberField;
	private JTextField ageField;
	private	JTextField gradeField;
	private JTextField countryField;
	
	//JTextArea
	private JTextArea bioTextArea;
	
	//JScrollArea
	private JScrollPane bioScrollPane;
	
	//JButton
	private JButton clear;
	private JButton submit;
	private JButton photo;
	
	//JPanels
	private JPanel grid = new JPanel(new GridLayout(4,1));
	private JPanel[][] gridPanel = new JPanel[4][1];
	private JPanel namePanel;
	private JPanel numberPanel;
	private JPanel agePanel;
	private JPanel gradePanel;
	private JPanel countryPanel;
	private JPanel photoPanel;
	private JPanel bioPanel;
	private JPanel southPanel;
	private JPanel buttonPanel;
	
	private String photoFilePath;
	
	//CONSTRUCTOR	
	public UserGUI() {
		super("Profile Card App | Promise Child");
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		//setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(true);
		
	//NEW Variables
		//Panels		
		namePanel = new JPanel();
		namePanel.setLayout(new FlowLayout());
		numberPanel = new JPanel();
		numberPanel.setLayout(new FlowLayout());
		agePanel = new JPanel();
		agePanel.setLayout(new FlowLayout());
		gradePanel = new JPanel();
		gradePanel.setLayout(new FlowLayout());
		countryPanel = new JPanel();
		countryPanel.setLayout(new FlowLayout());
		photoPanel = new JPanel();
		photoPanel.setLayout(new FlowLayout());
		bioPanel = new JPanel();
		bioPanel.setLayout(new FlowLayout());
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		
		//JLabel
		title = new JLabel("Profile Card Editor", JLabel.CENTER);
		numberLabel = new JLabel("Promise Child #: ", JLabel.LEFT);
		ageLabel = new JLabel("Age: ", JLabel.LEFT);
		gradeLabel = new JLabel("Grade: ", JLabel.LEFT);
		countryLabel = new JLabel("Country: ", JLabel.LEFT);
		photoLabel = new JLabel("Photo: ", JLabel.LEFT);
		photoFilePathLabel = new JLabel("", JLabel.LEFT);
		spacing = new JLabel("                                                ");
		bioLabel = new JLabel(" Bio", JLabel.LEFT);
		nameLabel = new JLabel("Name: ", JLabel.LEFT);
		
		//JFields
		nameField = new JTextField(20);
		numberField = new JTextField(6);
		ageField = new JTextField(2);
		gradeField = new JTextField(2);
		countryField = new JTextField(15);
		bioTextArea = new JTextArea(10,35);
		bioTextArea.setLineWrap(true);
		
		bioScrollPane = new JScrollPane(bioTextArea);
		
		//buttons
		clear = new JButton("Clear");
		submit = new JButton("Submit");
		photo = new JButton("Choose Photo");
		
		//set Fonts
		title.setFont(new Font("Arial", Font.BOLD, 15));
		nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		numberLabel.setFont(new Font("Arial", Font.BOLD, 15));
		ageLabel.setFont(new Font("Arial", Font.BOLD, 15));
		gradeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		photoLabel.setFont(new Font("Arial", Font.BOLD, 15));
		countryLabel.setFont(new Font("Arial", Font.BOLD, 15));
		bioLabel.setFont(new Font("Arial", Font.BOLD, 15));
		submit.setFont(new Font("Arial", Font.BOLD, 15));
		clear.setFont(new Font("Arial", Font.PLAIN, 15));
		
		//Panel adds
		//fill grid
		for (int x = 0; x < 4; ++x) {
			gridPanel[x][0] = new JPanel(); //panels in grid
			grid.add(gridPanel[x][0]); //add gridPanel to grid
			gridPanel[x][0].setLayout(new FlowLayout(FlowLayout.LEFT));
		}//end for
		
		//add Labels and Fields to Center Panel Grid
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		numberPanel.add(numberLabel);
		numberPanel.add(numberField);
		agePanel.add(ageLabel);
		agePanel.add(ageField);
		gradePanel.add(gradeLabel);
		gradePanel.add(gradeField);
		countryPanel.add(countryLabel);
		countryPanel.add(countryField);
		photoPanel.add(photoLabel);
		photoPanel.add(photo);
		bioPanel.add(bioScrollPane);
		buttonPanel.add(clear);
		buttonPanel.add(submit);
		
		//gridPanel adds
		gridPanel[0][0].add(namePanel);
		gridPanel[1][0].add(numberPanel);
		gridPanel[1][0].add(agePanel);
		gridPanel[1][0].add(gradePanel);
		gridPanel[2][0].add(countryPanel);
		gridPanel[2][0].add(photoPanel);
		gridPanel[3][0].add(bioLabel);
		gridPanel[3][0].add(spacing);
		gridPanel[3][0].add(photoFilePathLabel);
		
		//BorderLayout Adds
		add(title,BorderLayout.NORTH);
		add(southPanel,BorderLayout.SOUTH);
		add(grid, BorderLayout.CENTER);
		
		//South Panel is nested borderlayout within the main borderlayout.south section
		southPanel.add(buttonPanel,BorderLayout.SOUTH);
		southPanel.add(bioPanel,BorderLayout.NORTH);
		
		//add listeners
		clear.addActionListener(this);
		submit.addActionListener(this);
		photo.addActionListener(this);

	}
	
	//printCard - Calls PDFCreator Constructor
	public void printCard(String name, String number, String age, String grade, 
			String country, String bio, String filepath) {
		PDFCreator p = new PDFCreator(name,number,age,grade,country,bio,filepath);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		String name, number, age, grade, country, bio;
		try {
			if (source == submit) {
				name = nameField.getText();
				number = numberField.getText();
				age = ageField.getText();
				grade = gradeField.getText();
				country = countryField.getText();
				bio = bioTextArea.getText();
				//call Print Card
				printCard(name, number, age, grade, country, bio, photoFilePath);
			} else if (source == clear) {
				name = null;
				number = null;
				age = null;
				grade = null;
				country = null;
				bio = null;
				photoFilePath = null;
				photoFilePathLabel.setText("");
				nameField.setText("");
				numberField.setText("");
				ageField.setText("");
				gradeField.setText("");
				countryField.setText("");
				bioTextArea.setText("");
			}
			else {
				JFileChooser j = new JFileChooser(System.getProperty("user.home"));
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"Image Files", "jpg", "gif", "png", "jpeg","pdf");
				j.setFileFilter(filter);
				int r = j.showOpenDialog(null);
				if (r == JFileChooser.APPROVE_OPTION) { 
					//set the label to the path of the selected file 
					photoFilePath = j.getSelectedFile().getAbsolutePath(); 
					//set label
					photoFilePathLabel.setText(photoFilePath);
				 }//end if
			}//end else
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error Creating File, Please Try Again", 
					"Error", JOptionPane.ERROR_MESSAGE);
			if (photoFilePath == null) {
				photoFilePathLabel.setText("Please select an image");
			}
		}
	}//end actionPerformed Override function
	
}//end class
