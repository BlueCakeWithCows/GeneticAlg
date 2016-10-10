package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import core.Settings;
import core.Settings.SettingsValue;

@SuppressWarnings("serial")
public class SettingsWindow extends JPanel {
	private final String defaultSettingsLocation = FileSystemView.getFileSystemView().getDefaultDirectory().toString()
			+ "\\Functions\\default.txt";

	private Root root;

	private JTextField fileField;
	private JButton reloadButton, saveButton;
	private JComponent[] settingsFields;
	private JComponent[] labelFields;


	public SettingsWindow(Root root) {
		this.setFocusCycleRoot(true);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLayout(null);
		this.root = root;

	}

	public void init() {
		this.reloadButton = new JButton("Load");
		this.reloadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadSettingsFromFile();
			}
		});
		this.reloadButton.setBounds(370, 5, 80, 30);
		this.add(reloadButton);
		this.saveButton = new JButton("Save");
		this.saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveSettingsToFile();
			}
		});
		this.saveButton.setBounds(470, 5, 80, 30);
		this.add(saveButton);

		this.fileField = new JTextField(this.defaultSettingsLocation);
		this.fileField.setBounds(0, 5, 350, 30);
		this.fileField.setEditable(false);
		this.add(fileField);

		this.loadSettingsFromFile();
	}

	protected void saveSettingsToFile() {
		// TODO Auto-generated method stub

	}

	protected void loadSettingsFromFile() {
		File file = new File(defaultSettingsLocation);
		if (file.exists() && !file.isDirectory())
			root.settings = SettingsLoader.loadSettingsFrom(file);
		if (root.settings == null)
			root.settings = new Settings();
		else {
			for (Object c : this.settingsFields)
				this.remove((JComponent)c);
		}
		List<SettingsValue<?>> values = root.settings.getSettings();
		settingsFields = new JComponent[values.size()];
		labelFields = new JComponent[values.size()];
		int spacing = 2;
		for (int i = 0; i < settingsFields.length; i++) {
		
			JComponent comp = null;
			SettingsValue<?> v = values.get(i);
			JLabel label = new JLabel(v.ID);
			if (v.possibilities != null) {
				comp = new JComboBox<Object> (v.possibilities);
				((JComboBox<Object>) comp).setSelectedIndex(0);
			} else {
				comp = new JTextField(String.valueOf(v.getValue()));
			}
			
			comp.setBounds(0, 50 + i * (spacing + 19), 120, 19);
			label.setBounds(125, 50 + i * (spacing + 19), 300, 19);
			System.out.println(comp);
			settingsFields[i] = (JComponent)comp;
			labelFields[i] = label;
			this.add(label);
			this.add(comp);
		}

	}
}
