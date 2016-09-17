package gui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import components.mathsolver.Tree;
import components.mathsolver.TreeBuilder;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CodeTester extends JPanel {
	private JTextField InputField;
	private JTextField NumberOfOuts;
	private JTextField outputField;

	/**
	 * Create the panel.
	 */
	public CodeTester() {
		setLayout(null);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(63, 124, 423, 329);
		add(textArea);

		InputField = new JTextField();
		InputField.setBounds(63, 65, 219, 26);
		add(InputField);
		InputField.setColumns(10);

		JButton btnCompute = new JButton("Compute");
		btnCompute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] inputStrings = InputField.getText().split(",");
				double[] inputs = new double[inputStrings.length];
				for (int i = 0; i < inputs.length; i++) {
					inputs[i] = Double.valueOf(inputStrings[i]);
				}

				Integer numberOfOutputs = Integer.parseInt(NumberOfOuts.getText());
				Tree tree = TreeBuilder.getTree(textArea.getText(), inputs.length, numberOfOutputs);
				Double[] results = tree.execute(inputs);
				StringBuilder outputString = new StringBuilder();
				for(Double d: results){
					outputString.append(","+d);
				}
				outputString.replace(0, 1, "");
				outputField.setText(outputString.toString());
			}
		});
		btnCompute.setBounds(371, 79, 115, 29);
		add(btnCompute);

		NumberOfOuts = new JTextField();
		NumberOfOuts.setBounds(63, 35, 51, 26);
		add(NumberOfOuts);
		NumberOfOuts.setColumns(10);

		outputField = new JTextField();
		outputField.setBounds(63, 493, 423, 26);
		add(outputField);
		outputField.setColumns(10);

		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(287, 68, 69, 20);
		add(lblInput);

		JLabel lblOfOutput = new JLabel("# of Outputs");
		lblOfOutput.setBounds(129, 38, 115, 20);
		add(lblOfOutput);

		JLabel lblScriptToRun = new JLabel("Script to Run");
		lblScriptToRun.setBounds(184, 101, 156, 20);
		add(lblScriptToRun);

		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(213, 469, 69, 20);
		add(lblOutput);

	}
}
