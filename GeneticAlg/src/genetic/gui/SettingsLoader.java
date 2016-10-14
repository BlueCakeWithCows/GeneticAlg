package genetic.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import genetic.core.Settings;
import genetic.core.Settings.SettingsValue;

public class SettingsLoader {

	public static Settings loadSettingsFrom(File file) {
		Settings settings = new Settings();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();

			while (line != null) {
				String key = line.split(":")[0];
				String value = line.split(":")[1];
				for (SettingsValue<?> s : settings.getSettings()) {
					if (s.ID.equalsIgnoreCase(key)) {
						if (s.clas.isAssignableFrom(Boolean.class))
							s.setValue(Boolean.valueOf(value));
						else if (s.clas.isAssignableFrom(Long.class))
							s.setValue(Long.valueOf(value));
						else if (s.clas.isAssignableFrom(Integer.class))
							s.setValue(Integer.valueOf(value));
						else if (s.clas.isAssignableFrom(Double.class))
							s.setValue(Double.valueOf(value));
						else if (s.clas.isAssignableFrom(String.class))
							s.setValue(String.valueOf(value));

					}
				}
				line = br.readLine();
			}

		} catch (

		FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return settings;
	}
	
	

	public static void save(Settings settings, File file) {
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			PrintWriter writer = new PrintWriter(file, "UTF-8");

			for (SettingsValue v : settings.getSettings())
				writer.println(v.ID + ":" + v.getValue().toString());
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
