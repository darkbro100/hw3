package me.paul.hw3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import me.paul.hw3.simulation.Simulation;

/**
 * Singleton design for reading/writing to the configuration file for a
 * {@link Simulation}
 * 
 * @author Paul
 *
 */
public class SettingsManager {

	private static final SettingsManager instance = new SettingsManager();

	private static final String MIN_PREY = "minPrey";
	private static final String MAX_PREY = "maxPrey";
	private static final String MIN_PREDS = "minPredators";
	private static final String MAX_PREDS = "maxPredators";
	private static final String ROWS = "rows";
	private static final String COLS = "columns";

	private SettingsManager() { }

	private File settingsFile;
	private Map<String, Integer> configuration;

	public static final SettingsManager getInstance() {
		return instance;
	}

	/**
	 * Creates a new default configuration file if there isn't currently one in the
	 * same directory as this Application
	 */
	private void createDefaultConfig() {
		settingsFile = new File("settings.properties");
		try {
			settingsFile.createNewFile();

			Properties properties = new Properties();
			properties.setProperty(MIN_PREY, String.valueOf(1));
			properties.setProperty(MAX_PREY, String.valueOf(10));
			properties.setProperty(MIN_PREDS, String.valueOf(1));
			properties.setProperty(MAX_PREDS, String.valueOf(10));
			properties.setProperty(ROWS, String.valueOf(10));
			properties.setProperty(COLS, String.valueOf(10));
			properties.store(new FileOutputStream(settingsFile), "");

			sync(properties);
			Main.getLogger().info("Successfully created new configuration file " + settingsFile.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sync(Properties props) {
		configuration.clear();
		props.entrySet().forEach(e -> {
			String key = (String) e.getKey();
			String val = (String) e.getValue();
			
			try {
				int parsedVal = Integer.parseInt(val);
				configuration.put(key, parsedVal);
			} catch(NumberFormatException nfe) {
				Main.getLogger().error("Could not load in key " + key + " from configuration settings!");
			}
		});
	}

	/**
	 * Load the configuration settings
	 */
	public void load() {
		configuration = new HashMap<>();
		settingsFile = new File("settings.properties");
		
		if (!settingsFile.exists()) {
			Main.getLogger().warn("Configuration file doesn't exist! Creating one now...");
			createDefaultConfig();
			return;
		}

		Properties props = new Properties();
		try {
			props.load(new FileInputStream(settingsFile));
			sync(props);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Load a property from our Configuration
	 * 
	 * @param key
	 *            Key of the property we want to find the value for
	 * @return The value of the Key
	 * @throws IllegalArgumentException
	 *             Exception is thrown if a key is provided that does not exist in
	 *             our configuration
	 */
	private int getValue(String key) throws IllegalArgumentException {
		Integer val = configuration.get(key);

		if (val == null)
			throw new IllegalArgumentException("Invalid configuration key: " + key);

		return val;
	}
	
	public int getRows() {
		return getValue(ROWS);
	}
	
	public int getColumns() {
		return getValue(COLS);
	}
	
	public int getMinPrey() {
		return getValue(MIN_PREY);
	}
	
	public int getMaxPrey() {
		return getValue(MAX_PREY);
	}
	
	public int getMinPredators() {
		return getValue(MIN_PREDS);
	}
	
	public int getMaxPredators() {
		return getValue(MAX_PREDS);
	}

}
