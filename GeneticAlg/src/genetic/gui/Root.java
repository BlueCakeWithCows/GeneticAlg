package genetic.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import genetic.core.Settings;

import javax.swing.JTabbedPane;

public class Root extends JFrame {
	public Settings settings;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Root frame = new Root();

					frame.setVisible(true);
					frame.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Root() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 770, 700);

	}

	public void init() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);

		MainWindow panel = new MainWindow(this );
		panel.init();
		tabbedPane.addTab("Main", null, panel, null);

		CodeTester panel_1 = new CodeTester();
		tabbedPane.addTab("Tester", null, panel_1, null);

		SettingsWindow panel_2 = new SettingsWindow(this);
		tabbedPane.addTab("Settings", null, panel_2, null);
		panel_2.init();
	}

}
