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
import components.mathsolver.Tree;
import core.Manager;
import core.Settings;
import core.SimpleSaveLoad;

public class MainWindow extends JPanel {

	private JTextField currentSettingsFileTextField;
	private JFileChooser chooser;
	private File trainingFile;
	private JButton saveSettingsToFileButton;
	private JButton generateInitialPopulationButton;
	private JTextField TrainingFileField;
	private JTextArea generationDisplayTextArea;
	private Label generationCounterLabel;
	private Label numberOfRunsInputLabel;
	private Label untilAccuracyInputLabel, resultsLabelForTextArea;
	private JTextField UntilField;
	private JTextField RunsField;
	Button runGenerationsButton;
	private Settings settings;
	private Manager manager;
	private Button stopRunningButton;
	private JButton selectTrainingFileButton;
	public final int START_BUTTON_Y = 500;
	private boolean stopRequested = false;
	private JTextField TestDataToUse;

	public MainWindow() {
		this.setFocusCycleRoot(true);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
	}

	public void init() {
		JButton loadSettingsButton = new JButton("...");
		loadSettingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.showOpenDialog(null);
				File file = chooser.getSelectedFile();
				if (file != null) {
					currentSettingsFileTextField.setText(file.getAbsolutePath());
					try {
						settings = new Settings(SimpleSaveLoad.load(file));
						copyFromSettings();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}

		});

		loadSettingsButton.setBounds(332, 16, 24, 27);
		this.add(loadSettingsButton);

		currentSettingsFileTextField = new JTextField();
		currentSettingsFileTextField.setBounds(15, 16, 319, 27);
		currentSettingsFileTextField.setColumns(10);
		currentSettingsFileTextField.setEditable(false);
		this.add(currentSettingsFileTextField);

		TrainingFileField = new JTextField();
		TrainingFileField.setBounds(15, 50, 319, 27);
		TrainingFileField.setColumns(10);
		TrainingFileField.setEditable(false);
		this.add(TrainingFileField);
		generateInitialPopulationButton = new JButton("Start");
		generateInitialPopulationButton.addActionListener(new ActionListener() {
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
		generateInitialPopulationButton.setBounds(0, START_BUTTON_Y, 206, 29);
		this.add(generateInitialPopulationButton);

		saveSettingsToFileButton = new JButton("Save Settings");
		saveSettingsToFileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooser.showSaveDialog(null);
				File file = chooser.getSelectedFile();
				prepSettings();
				if (file != null) {
					settings.save(file);
				}
			}
		});
		saveSettingsToFileButton.setBounds(0,
				generateInitialPopulationButton.getHeight() + generateInitialPopulationButton.getY(), 206, 29);
		this.add(saveSettingsToFileButton);

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

		File defT = new File(files.getAbsolutePath() + "/default.txt");
		try {
			settings = new Settings(SimpleSaveLoad.load(defT));
			setFile(settings.getURL());
		} catch (IOException e2) {
		}
		this.copyFromSettings();
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
	int settingsBoxHeight = 20;
	int spacing = 2;
	int settingsLabelWidth = 260;
	private List<Label> settingsLabels;
	private List<JTextField> settingsTextFields;

	protected void copyFromSettings() {
		if (settings == null)
			return;
		TrainingFileField.setText(String.valueOf(settings.getURL()));
		int iX = TrainingFileField.getX();
		int iY = TrainingFileField.getY() + TrainingFileField.getHeight();

		cleanSettingsFields();
		List<Label> labels = new ArrayList<Label>();
		List<JTextField> fields = new ArrayList<JTextField>();
		int i = 0;
		for (String s : settings.map.keySet()) {
			if (!s.equals(Settings.URL)) {
				Label label = new Label(s);
				label.setBounds(iX + settingsBoxWidth + spacing, iY + i * (spacing + settingsBoxHeight),
						settingsLabelWidth, settingsBoxHeight);
				labels.add(label);
				this.add(label);
				JTextField field = new JTextField(settings.map.get(s));
				field.setBounds(iX, iY + i * (spacing + settingsBoxHeight), settingsBoxWidth, settingsBoxHeight);
				fields.add(field);
				this.add(field);
				i++;
			}
		}
		this.settingsLabels = labels;
		this.settingsTextFields = fields;
	}

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

	protected void prepSettings() {
		settings = new Settings();
		settings.set(Settings.URL, this.TrainingFileField.getText());

		int i = 0;
		for (Label label : settingsLabels) {
			settings.set(label.getText(), settingsTextFields.get(i).getText());
			i++;
		}
	}
}
