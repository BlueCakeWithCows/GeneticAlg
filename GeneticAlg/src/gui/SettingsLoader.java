package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import core.Settings;
import core.Settings.SettingsValue;

public class SettingsLoader {

	public static Settings loadSettingsFrom(File file) {
		Settings settings = new Settings();

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = br.readLine();

			while (line != null) {
				line = br.readLine();
				String key =line.split(":")[0];
				String value = line.split(":")[1];
				for(SettingsValue<?> s:settings.getSettings()){
					if(s.ID.equalsIgnoreCase(key)){
						if(s.clas.isAssignableFrom(Boolean.class))
							s.setValue(Boolean.valueOf(value));
						if(s.clas.isAssignableFrom(Integer.class))
							s.setValue(Integer.valueOf(value));
						if(s.clas.isAssignableFrom(Double.class))
							s.setValue(Double.valueOf(value));
						if(s.clas.isAssignableFrom(String.class))
							s.setValue(String.valueOf(value));
						if(s.clas.isAssignableFrom(Long.class))
							s.setValue(Long.valueOf(value));
					}
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return settings;
	}

}
