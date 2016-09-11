package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleSaveLoad {

	public static void save(File file, String stuff) {

		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write(stuff);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static HashMap<String, List<String>> stuff = new HashMap<String, List<String>>();

	public static String loadLine(String file, int fileLine) throws IOException {

		if (stuff.containsKey(file)) {
			return stuff.get(file).get(fileLine);
		}
		List<String> list = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();

			while (line != null) {
				list.add(line);
				line = br.readLine();
			}
			stuff.put(file, list);
			return loadLine(file, fileLine);
		}
	}

	public static List<String> load(File file) throws IOException {

		List<String> list = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();

			while (line != null) {
				list.add(line);
				line = br.readLine();
			}
			return list;
		}
	}

	public static void save(File file, List<String> stuff2) {
		
		FileWriter writer = null;
		try {

			writer = new FileWriter(file);
			for (String stuff : stuff2) {
				writer.append(stuff);
				writer.append(System.lineSeparator());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/** Returns true if it created a file */
	public static boolean createFileIfNoExist(File file) {
		try {
			return file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
