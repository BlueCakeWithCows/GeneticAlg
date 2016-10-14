package genetic.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.JTextComponent;

import genetic.core.Settings;
import genetic.core.Settings.SettingsValue;

@SuppressWarnings("serial")
public class SettingsWindow extends JPanel {
	private final String defaultSettingsLocation = FileSystemView.getFileSystemView().getDefaultDirectory().toString()
			+ "\\Functions\\default.txt";

	private Root root;

	private JTextField fileField;
	private JButton reloadButton, saveButton, quickSaveButton;
	private JComponent[] settingsFields;
	private JLabel[] labelFields;
	private JFileChooser chooser;

	public SettingsWindow(Root root) {
		this.setFocusCycleRoot(true);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.root = root;

	}

	public void init() {
		chooser = new JFileChooser();
		chooser.setSelectedFile(new File(defaultSettingsLocation));
		this.loadSettingsFromFile();
		if(!chooser.getSelectedFile().exists()){
			chooser.getSelectedFile().getParentFile().mkdir();
			this.saveSettingsToFile();
		}

		this.reloadButton = new JButton("Load");
		this.reloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.showOpenDialog(null);
				if (chooser.getSelectedFile() != null)
					loadSettingsFromFile();
			}
		});
		this.reloadButton.setBounds(370, 5, 80, 30);
		this.add(reloadButton);
		this.saveButton = new JButton("Save");
		this.saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooser.showSaveDialog(null);
				if (chooser.getSelectedFile() != null)
					saveSettingsToFile();
			}
		});
		this.saveButton.setBounds(470, 5, 80, 30);
		this.add(saveButton);
		this.quickSaveButton = new JButton("Quick Save");
		this.quickSaveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettingsToFile();
			}
		});
		this.quickSaveButton.setBounds(570, 5, 80, 30);
		this.add(quickSaveButton);
		this.fileField = new JTextField(this.defaultSettingsLocation);
		this.fileField.setBounds(0, 5, 350, 30);
		this.fileField.setEditable(false);
		this.add(fileField);

	}

	protected void saveSettingsToFile() {

		this.updateSettings();
		SettingsLoader.save(root.settings, chooser.getSelectedFile());
	}

	private void updateSettings() {
		List<SettingsValue<?>> vals = root.settings.getSettings();
		for (int i = 0; i < settingsFields.length; i++) {
			SettingsValue<?> val = null;
			for (int i2 = 0; i2 < vals.size(); i2++) {
				if (vals.get(i2).ID.equalsIgnoreCase(this.labelFields[i].getText())) {
					val = vals.get(i2);
				}
			}
			if (settingsFields[i] instanceof JTextComponent) {
				if (val.getValue().getClass().isAssignableFrom(Boolean.class))
					val.setValue(Boolean.valueOf(((JTextComponent) settingsFields[i]).getText()));
				else if (val.getValue().getClass().isAssignableFrom(Long.class))
					val.setValue(Long.valueOf(((JTextComponent) settingsFields[i]).getText()));
				else if (val.getValue().getClass().isAssignableFrom(Double.class))
					val.setValue(Double.valueOf(((JTextComponent) settingsFields[i]).getText()));
				else if (val.getValue().getClass().isAssignableFrom(Integer.class))
					val.setValue(Integer.valueOf(((JTextComponent) settingsFields[i]).getText()));
				else if (val.getValue().getClass().isAssignableFrom(String.class))
					val.setValue(String.valueOf(((JTextComponent) settingsFields[i]).getText()));
			}
			if (settingsFields[i] instanceof JComboBox)
				val.setValue(((JComboBox<?>) settingsFields[i]).getSelectedItem());
		}
	}

	protected void loadSettingsFromFile() {
		File file = new File(defaultSettingsLocation);

		if (root.settings == null)
			root.settings = new Settings();
		else {
			for (Object c : this.settingsFields)
				this.remove((JComponent) c);
		}
		if (file.exists() && !file.isDirectory())
			root.settings = SettingsLoader.loadSettingsFrom(file);
		List<SettingsValue<?>> values = root.settings.getSettings();
		settingsFields = new JComponent[values.size()];
		labelFields = new JLabel[values.size()];
		int spacing = 2;
		for (int i = 0; i < settingsFields.length; i++) {

			JComponent comp = null;
			SettingsValue<?> v = values.get(i);
			JLabel label = new JLabel(v.ID);
			if (v.possibilities != null) {
				comp = new JComboBox<Object>(v.possibilities);
				((JComboBox<Object>) comp).setSelectedItem(v.getValue());
			} else if (v.getValue() instanceof Boolean) {
				comp = new JComboBox<Object>(new Boolean[] { true, false });
				((JComboBox<Object>) comp).setSelectedItem(v.getValue());
			} else {
				comp = new JTextField(String.valueOf(v.getValue()));
			}

			comp.setBounds(0, 50 + i * (spacing + 19), 120, 19);
			label.setBounds(125, 50 + i * (spacing + 19), 300, 19);
			settingsFields[i] = (JComponent) comp;
			labelFields[i] = label;
			this.add(label);
			this.add(comp);
		}

	}
}
