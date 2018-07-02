package com.aastorp.config;

import java.awt.Component;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JTabbedPane;

import com.aastorp.bibliothecaaastorpiana.databases.SqliteDatabase;
import com.aastorp.logger.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 */
public class Config {
	
	/** The logger. */
	private Logger l;
	
	/** The settings, which doubles as the JTabbedPane for the Config dialog. */
	private Settings settings;
	
	/** The setting database. */
	private SettingDatabase sdb;
	
	/** The single instance of Config */
	private static Config instance = null;

	/**
	 * Private constructor for singleton.
	 */
	private Config() {}
	
	/**
	 * Inits the Config, loading the settings from the specified database file.
	 *
	 * @param settingDatabaseFile the setting database file
	 * @return The resulting Config
	 */
	private static Config init(File settingDatabaseFile) {
		instance = new Config();
		Config.getInstance().sdb = new SettingDatabase(settingDatabaseFile);
		Config.getInstance().readSettings();
		Config.getInstance().l = new Logger(Config.class, Integer.valueOf((Integer)Config.getInstance().getSetting("logLevel").getValue()), true, new File("config.log"));
		return Config.getInstance();
	}
	
	/**
	 * Read settings from the database, storing them in this.settings.
	 *
	 */
	private void readSettings() {
		final String F = "readSettings";
		this.settings = sdb.getSettings();
		if (l == null) {
			l = new Logger(this.getClass(), Logger.INFO, true, new File("config.log"));
			l.d(F, "Started temporary Logger for readSettings() with logLevel INFO: Config's Logger l isn't initialised since the setting logLevel hasn't loaded yet...");
		}
		l.d(F, "\t<Settings>");
		Setting[] settings = this.getSettings().getSettings().toArray(new Setting[this.getSettings().getSettings().size()]); //?????
			for (Setting setting : settings) {
//			<horribleXmlLoggingHack>
				l.d(F, "\t\t<" + setting.getName() + ">");
				l.d(F, "\t\t\t<value>");
				l.d(F, "\t\t\t\t" + String.valueOf(setting.getValue()));
				l.d(F, "\t\t\t</value>");
				l.d(F, "\t\t\t<settingCategory>");
				l.d(F, "\t\t\t\t" + String.valueOf(setting.getSettingCategory().getName()));
				l.d(F, "\t\t\t</settingCategory>");
				l.d(F, "\t\t\t<settingType>");
				l.d(F, "\t\t\t\t" + String.valueOf(setting.getSettingType()));
				l.d(F, "\t\t\t</settingType>");
				l.d(F, "\t\t</" + setting.getName() + ">");
//			</horribleXmlLoggingHack>
				this.getSettings().add(setting);
			}
			l.d(F, "\t</Settings>");
	}

	/**
	 * Gets the single instance of Config.
	 *
	 * @return The Application Config.
	 */
	public static Config getInstance() {
		if (instance == null) 
			instance = init(new File("settings.sqlite"));
		return instance;
	}
	
	/**
	 * Force an update of the settings from the database. Useful for making settings 
	 * take effect when they change while the program is running.
	 */
	public void forceReload() {
		l.d("forceReload", "Forcing config reload");
		Config.getInstance().readSettings();
		//when this becomes observable, notify observers that config has changed.
	}
	
	/**
	 * Gets the value of the specified setting. If multiple settings have the same name,
	 * this will only return the first one; having multiple settings by the same name can 
	 * therefore be considered unsupported.
	 *
	 * @param name The name of the setting to retrieve from the database.
	 * @return The value of the setting if successful, null on failure.
	 */
	public Setting getSetting(String name) {
		Setting setting = this.settings.getSettingByName(name);
		l.i("getSetting", "Setting " + name + " has a value of " + String.valueOf(setting));
		if (setting == null) {
			l.e("getSetting", "Could not get setting " + name);
		}
		return setting;
	}
	
	/**
	 * Sets the setting.
	 *
	 * @param friendlyName the friendly name
	 * @param value the value
	 */
	public void setSetting(String friendlyName, Object value) {
		HashMap<String, Object> whereClauses = new HashMap<String, Object>();
		whereClauses.put("friendlyName", friendlyName);

		this.sdb.update("setting", whereClauses, "value", String.valueOf(value)); 
	}

	/**
	 * Gets the settings.
	 *
	 * @return the settings
	 */
	public Settings getSettings() {
		return settings;
	}
	
	/**
	 * Gets the config pane.
	 *
	 * @return the config pane
	 */
	public JTabbedPane getConfigPane() {
		return (JTabbedPane)settings;
	}

	/**
	 * Gets the setting database.
	 *
	 * @return the sdb
	 */
	public SqliteDatabase getSettingDatabase() {
		return sdb;
	}

}