package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import components.CaseReader;
import components.mathsolver.Tree;
import core.Manager;
import core.Settings;
import core.SimpleSaveLoad;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Label;
import java.awt.Button;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JFileChooser chooser;
	private File trainingFile;
	private JButton button;
	private JButton Start;
	private JTextField MutationChanceField;
	private JLabel lblSeed;
	private JLabel label;
	private JTextField SeedField;
	private JTextField PopulationField;
	private JTextField TopPercentField;
	private JTextField InitialDepthMinField;
	private JLabel label_3;
	private JTextField InitialDepthMaxField;
	private JLabel label_4;
	private JTextField TrainingFileField;
	private JLabel label_5;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JTextField MaxTreeSizeField;
	private Label generationCounter;
	private Label label_9;
	private Label label_10;
	private JTextField UntilField;
	private JTextField RunsField;
	Button button_1;
	private Settings settings;
	private Manager manager;
	private Button StopButton;
	private JButton button_2;

	/**
	 * Launch the application.
	 */

	private boolean stopRequested = false;
	private JTextField TestDataToUse;
	private JLabel label_8;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 760, 540);
		contentPane = new JPanel();
		contentPane.setFocusCycleRoot(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				if (file != null) {
					textField.setText(file.getAbsolutePath());
					try {
						settings = new Settings(SimpleSaveLoad.load(file));
						copyFromSettings();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});

		btnNewButton.setBounds(332, 16, 24, 27);
		contentPane.add(btnNewButton);

		textField = new JTextField();
		textField.setBounds(15, 16, 319, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setEditable(false);
		chooser = new JFileChooser();
		File files = new File(chooser.getCurrentDirectory().getPath() + "/_BlueGeneticProgramming");
		files.mkdir();
		chooser.setCurrentDirectory(files);

		button = new JButton("Save Settings");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.showSaveDialog(null);
				File file = chooser.getSelectedFile();
				prepSettings();
				if (file != null) {
					settings.save(file);
				}
			}
		});
		button.setBounds(0, 438, 206, 29);
		contentPane.add(button);

		Start = new JButton("Start");
		Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				prepSettings();
				CaseReader reader = new CaseReader(trainingFile);
				try {
					manager = new Manager(settings, reader.getTestCases());
					manager.generatePopulation();
					manager.scorePopulation();
					displayScore(5);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		Start.setBounds(0, 406, 206, 29);
		contentPane.add(Start);

		MutationChanceField = new JTextField();
		MutationChanceField.setBounds(0, 118, 83, 26);
		contentPane.add(MutationChanceField);
		MutationChanceField.setColumns(10);

		lblSeed = new JLabel("Seed");
		lblSeed.setBounds(91, 84, 69, 20);
		contentPane.add(lblSeed);

		label = new JLabel("Mutation Chance");
		label.setBounds(91, 121, 146, 20);
		contentPane.add(label);

		SeedField = new JTextField();
		SeedField.setColumns(10);
		SeedField.setBounds(0, 81, 83, 26);
		contentPane.add(SeedField);

		PopulationField = new JTextField();
		PopulationField.setColumns(10);
		PopulationField.setBounds(0, 160, 83, 26);
		contentPane.add(PopulationField);

		JLabel label_1 = new JLabel("Population");
		label_1.setBounds(91, 163, 146, 20);
		contentPane.add(label_1);

		TopPercentField = new JTextField();
		TopPercentField.setColumns(10);
		TopPercentField.setBounds(0, 200, 83, 26);
		contentPane.add(TopPercentField);

		JLabel label_2 = new JLabel("Top Percent");
		label_2.setBounds(91, 203, 146, 20);
		contentPane.add(label_2);

		InitialDepthMinField = new JTextField();
		InitialDepthMinField.setColumns(10);
		InitialDepthMinField.setBounds(0, 242, 83, 26);
		contentPane.add(InitialDepthMinField);

		label_3 = new JLabel("Initial Depth Min");
		label_3.setBounds(91, 245, 146, 20);
		contentPane.add(label_3);

		InitialDepthMaxField = new JTextField();
		InitialDepthMaxField.setColumns(10);
		InitialDepthMaxField.setBounds(0, 282, 83, 26);
		contentPane.add(InitialDepthMaxField);

		label_4 = new JLabel("Initial Depth Max");
		label_4.setBounds(91, 285, 146, 20);
		contentPane.add(label_4);

		TrainingFileField = new JTextField();
		TrainingFileField.setBounds(0, 48, 83, 26);
		contentPane.add(TrainingFileField);
		TrainingFileField.setColumns(10);

		label_5 = new JLabel("File");
		label_5.setBounds(113, 51, 69, 20);
		contentPane.add(label_5);

		textArea = new JTextArea("hello");
		textArea.setFocusTraversalKeysEnabled(false);
		JScrollPane areaScrollPane = new JScrollPane(textArea);
		areaScrollPane.setFocusTraversalKeysEnabled(false);
		areaScrollPane.setBounds(424, 147, 297, 320);

		contentPane.add(areaScrollPane);

		Label label_6 = new Label("Results");
		label_6.setBounds(424, 118, 82, 27);
		contentPane.add(label_6);

		button_1 = new Button("Go");
		button_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				new Thread(new Runnable() {

					@Override
					public void run() {

						RunsField.setEnabled(false);
						UntilField.setEnabled(false);
						boolean runs = false;
						double untilPercent = 101;
						try {
							Integer.parseInt(RunsField.getText());
							runs = true;
						} catch (Exception e) {
						}
						try {
							untilPercent = Double.parseDouble(UntilField.getText());
						} catch (Exception e) {

						}
						stopRequested = false;
						while (!stopRequested) {

							if (runs) {
								int i = Integer.parseInt(RunsField.getText());
								if (i < 1)
									break;
								RunsField.setText(String.valueOf((i - 1)));
							}
							if (manager.getBest().score >= untilPercent)
								break;
							manager.doGeneration();
							generationCounter.setText("Generation " + manager.getGeneration());
							displayScore(5);
						}

						stop();
					}

				}).start();
			}
		});
		button_1.setBounds(415, 84, 91, 27);
		contentPane.add(button_1);

		MaxTreeSizeField = new JTextField();
		MaxTreeSizeField.setColumns(10);
		MaxTreeSizeField.setBounds(0, 324, 83, 26);
		contentPane.add(MaxTreeSizeField);

		JLabel label_7 = new JLabel("Max Tree Size");
		label_7.setBounds(91, 327, 146, 20);
		contentPane.add(label_7);

		generationCounter = new Label("Generation");
		generationCounter.setBounds(560, 117, 139, 27);
		contentPane.add(generationCounter);

		label_9 = new Label("Number of Runs");
		label_9.setBounds(513, 16, 160, 27);
		contentPane.add(label_9);

		label_10 = new Label("Until Accuracy (100 max)");
		label_10.setBounds(513, 47, 186, 27);
		contentPane.add(label_10);

		UntilField = new JTextField();
		UntilField.setColumns(10);
		UntilField.setBounds(424, 48, 83, 26);
		contentPane.add(UntilField);

		RunsField = new JTextField();
		RunsField.setColumns(10);
		RunsField.setBounds(424, 16, 83, 26);
		contentPane.add(RunsField);
		TrainingFileField.setEditable(false);
		StopButton = new Button("Stop");
		StopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		StopButton.setBounds(523, 84, 91, 27);
		contentPane.add(StopButton);

		button_2 = new JButton("New button");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.showOpenDialog(null);
				if (chooser.getSelectedFile() != null) {

					TrainingFileField.setText(chooser.getSelectedFile().getAbsolutePath());
					setFile(chooser.getSelectedFile().getAbsolutePath());
				}

			}
		});
		button_2.setBounds(78, 48, 24, 27);
		contentPane.add(button_2);

		TestDataToUse = new JTextField();
		TestDataToUse.setColumns(10);
		TestDataToUse.setBounds(0, 353, 83, 26);
		contentPane.add(TestDataToUse);

		label_8 = new JLabel("Test Data Percent");
		label_8.setBounds(91, 353, 146, 20);
		contentPane.add(label_8);

		File defT = new File(files.getAbsolutePath() + "/default.txt");
		try {
			settings = new Settings(SimpleSaveLoad.load(defT));
		} catch (IOException e2) {
		}
		this.copyFromSettings();

	}

	protected void copyFromSettings() {
		if (settings == null)
			return;

		PopulationField.setText(String.valueOf(settings.getPopulation()));
		SeedField.setText(String.valueOf(settings.getSeed()));
		MutationChanceField.setText(String.valueOf(settings.getMutationChance()));
		TopPercentField.setText(String.valueOf(settings.getTopPercent()));
		InitialDepthMinField.setText(String.valueOf(settings.getMinInitPop()));
		InitialDepthMaxField.setText(String.valueOf(settings.getMaxInitPop()));
		MaxTreeSizeField.setText(String.valueOf(settings.getMaxTreeSize()));
		TrainingFileField.setText(String.valueOf(settings.getURL()));
		TestDataToUse.setText(String.valueOf(settings.getTestDataToUse()));
		this.setFile(String.valueOf(settings.getURL()));
	}

	public void setFile(String url) {
		trainingFile = new File(url);
	}

	private void stop() {
		stopRequested = true;
		button_1.setEnabled(true);
		RunsField.setEnabled(true);
		UntilField.setEnabled(true);
	}

	public void displayScore(int number) {
		StringBuilder builder = new StringBuilder();

		for (Tree tree : manager.getBest(number)) {
			builder.append(tree.toString() + System.lineSeparator() + System.lineSeparator());
		}

		textArea.setText(builder.toString());
	}

	protected void prepSettings() {
		settings = new Settings();
		settings.set(Settings.POPULATION, PopulationField.getText());
		settings.set(Settings.SEED, SeedField.getText());
		settings.set(Settings.MUTATION_CHANCE, MutationChanceField.getText());
		settings.set(Settings.TOP_PERCENT, TopPercentField.getText());
		settings.set(Settings.INITIAL_MIN, InitialDepthMinField.getText());
		settings.set(Settings.INITIAL_MAX, InitialDepthMaxField.getText());
		settings.set(Settings.MAX_TREE_SIZE, this.MaxTreeSizeField.getText());
		settings.set(Settings.URL, this.TrainingFileField.getText());
		settings.set(Settings.TEST_DATA_TO_USE, this.TestDataToUse.getText());
	}
}
