package gui;

import java.awt.Button;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import components.CaseReader;
import components.basic.Tree;
import core.Manager;
import core.Settings;
import core.SimpleSaveLoad;

public class MainWindow extends JPanel {

	private JFileChooser chooser;
	private File trainingFile;
	private JButton generateInitialPopulationButton, uSettings;
	private JTextField TrainingFileField;
	private JTextArea generationDisplayTextArea;
	private Label generationCounterLabel;
	private Label numberOfRunsInputLabel;
	private Label untilAccuracyInputLabel, resultsLabelForTextArea;
	private JTextField UntilField;
	private JTextField RunsField;
	Button runGenerationsButton;
	private Manager manager;
	private Button stopRunningButton;
	private JButton selectTrainingFileButton;
	public final int START_BUTTON_Y = 500;
	private boolean stopRequested = false;
	private JTextField TestDataToUse;
	private Root root;

	public MainWindow(Root root) {
		this.setFocusCycleRoot(true);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.root = root;
	}

	public void init() {

		TrainingFileField = new JTextField();
		TrainingFileField.setBounds(15, 50, 319, 27);
		TrainingFileField.setColumns(10);
		TrainingFileField.setEditable(false);
		this.add(TrainingFileField);
		generateInitialPopulationButton = new JButton("Start");
		generateInitialPopulationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				CaseReader reader = new CaseReader(trainingFile);
				try {
					manager = new Manager(root.settings, reader.getTestCases());
					manager.generatePopulation();
					manager.scorePopulation();
					displayScore(5);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		});
		generateInitialPopulationButton.setBounds(0, START_BUTTON_Y, 206, 29);
		this.add(generateInitialPopulationButton);

		uSettings = new JButton("Update Settings");
		uSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (manager != null)
					manager.applySettings(root.settings);
			}

		});
		uSettings.setBounds(0, START_BUTTON_Y + 40, 206, 29);
		this.add(uSettings);

		generationDisplayTextArea = new JTextArea("hello");
		generationDisplayTextArea.setFocusTraversalKeysEnabled(false);
		JScrollPane generationDisplayTextAreaScrollPaneWrapper = new JScrollPane(generationDisplayTextArea);
		generationDisplayTextAreaScrollPaneWrapper.setFocusTraversalKeysEnabled(false);
		generationDisplayTextAreaScrollPaneWrapper.setBounds(424, 147, 297, 320);
		this.add(generationDisplayTextAreaScrollPaneWrapper);

		resultsLabelForTextArea = new Label("Time");
		resultsLabelForTextArea.setBounds(424, 118, 82, 27);
		this.add(resultsLabelForTextArea);

		runGenerationsButton = new Button("Go");
		runGenerationsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						RunsField.setEnabled(false);
						UntilField.setEnabled(false);
						runGenerationsButton.setEnabled(false);

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
						try {
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
								displayScore(5);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						runGenerationsButton.setEnabled(true);
						RunsField.setEnabled(true);
						UntilField.setEnabled(true);
					}
				}

				).start();
			}
		});
		runGenerationsButton.setBounds(415, 84, 91, 27);
		this.add(runGenerationsButton);

		generationCounterLabel = new Label("Generation");
		generationCounterLabel.setBounds(560, 117, 139, 27);
		this.add(generationCounterLabel);

		numberOfRunsInputLabel = new Label("Number of Runs");
		numberOfRunsInputLabel.setBounds(513, 16, 160, 27);
		this.add(numberOfRunsInputLabel);

		untilAccuracyInputLabel = new Label("Until Accuracy (100 max)");
		untilAccuracyInputLabel.setBounds(513, 47, 186, 27);
		this.add(untilAccuracyInputLabel);

		UntilField = new JTextField();
		UntilField.setColumns(10);
		UntilField.setBounds(424, 48, 83, 26);
		this.add(UntilField);

		RunsField = new JTextField();
		RunsField.setColumns(10);
		RunsField.setBounds(424, 16, 83, 26);
		this.add(RunsField);

		stopRunningButton = new Button("Stop");
		stopRunningButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		stopRunningButton.setBounds(523, 84, 91, 27);
		this.add(stopRunningButton);

		selectTrainingFileButton = new JButton("...");
		selectTrainingFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.showOpenDialog(null);
				if (chooser.getSelectedFile() != null) {

					TrainingFileField.setText(chooser.getSelectedFile().getAbsolutePath());
					setFile(chooser.getSelectedFile().getAbsolutePath());
				}

			}
		});
		selectTrainingFileButton.setBounds((int) TrainingFileField.getBounds().getMaxX(),
				(int) TrainingFileField.getBounds().getY(), 24, 27);
		this.add(selectTrainingFileButton);

		chooser = new JFileChooser();
		File files = new File(chooser.getCurrentDirectory().getPath() + "/_BlueGeneticProgramming");
		files.mkdir();
		chooser.setCurrentDirectory(files);
	}

	public void setFile(String url) {
		trainingFile = new File(url);
	}

	private void stop() {
		stopRequested = true;
	}

	public void displayScore(int number) {
		StringBuilder builder = new StringBuilder();

		for (Tree tree : manager.getTop(number)) {
			builder.append(tree.toString() + System.lineSeparator() + System.lineSeparator());
		}

		generationDisplayTextArea.setText(builder.toString());
		generationCounterLabel.setText("Generation " + manager.getGeneration());
		resultsLabelForTextArea.setText("Time " + manager.getRunningTime());

	}

	int settingsBoxWidth = 140;
	int settingsBoxHeight = 17;
	int spacing = 1;
	int settingsLabelWidth = 260;
	private List<Label> settingsLabels;
	private List<JTextField> settingsTextFields;

	public void cleanSettingsFields() {
		if (settingsLabels != null)
			for (Label l : settingsLabels) {
				this.remove(l);
			}
		if (settingsTextFields != null)
			for (JTextField l : settingsTextFields) {
				this.remove(l);
			}
		settingsTextFields = new ArrayList<JTextField>();
		settingsLabels = new ArrayList<Label>();
	}
}
