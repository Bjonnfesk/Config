package com.aastorp.config;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import com.aastorp.bibliothecaaastorpiana.common.Common;
import com.aastorp.bibliothecaaastorpiana.databases.SqliteDatabase;
import com.aastorp.bibliothecaaastorpiana.layouts.MagicGridLayout;
import com.aastorp.logger.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 */
public class Config {

	/** The logger. */
	private static Logger l;

	/** The settings, which doubles as the JTabbedPane for the Config dialog. */
	private Settings settings;

	/** The setting database. */
	private SettingDatabase sdb;

	/** The single instance of Config */
	private static Config instance = null;

	/**
	 * Private constructor for singleton.
	 */
	private Config() {
		Config.l = new Logger(Config.class, Integer.valueOf(Logger.INFO), true, new File("config.log"));
	}

	/**
	 * Inits the Config, loading the settings from the specified database file.
	 *
	 * @param settingDatabaseFile the setting database file
	 * @return The resulting Config
	 */
	private static Config init(File settingDatabaseFile) throws SQLException, ClassNotFoundException, NullPointerException, Exception {
		instance = new Config();
		Config.getInstance().sdb = new SettingDatabase(settingDatabaseFile);
		Config.getInstance().readSettings();
		if (Config.getInstance().settings == null) 
			Common.confirm("WTF‽", "Config.getInstance().settings is still null, this should not happen...");
		Config.getInstance().settings.addChangeListener(new OmniListener());
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
		Setting[] settings = null;
		try {
			settings = this.getSettings().getSettings().toArray(new Setting[this.getSettings().getSettings().size()]);
		} catch (NullPointerException e) {
			l.e(F, "NullPointerException! Sadly, no more information can be given, as Java does not provide it even to developers (Oracle, *hint* *hint*).");
			return;
		}
		l.d(F, "\t<Settings>");
		for (Setting setting : settings) {
			try {
//			<horribleXmlLoggingHack>
				l.i(F, "\t\t<" + setting.getName() + ">");
				l.i(F, "\t\t\t<value>");
				l.i(F, "\t\t\t\t" + String.valueOf(setting.getValue()));
				l.i(F, "\t\t\t</value>");
				l.i(F, "\t\t\t<settingCategory>");
				l.i(F, "\t\t\t\t" + String.valueOf(setting.getSettingCategory().getName()));
				l.i(F, "\t\t\t</settingCategory>");
				l.i(F, "\t\t\t<settingType>");
				l.i(F, "\t\t\t\t" + String.valueOf(setting.getSettingType()));
				l.i(F, "\t\t\t</settingType>");
				l.i(F, "\t\t</" + setting.getName() + ">");
//			</horribleXmlLoggingHack>
			} catch (NullPointerException e) {
				l.w(F, "Got a Setting that is null! Sadly, no more information can be given, as Java does not provide it even to developers (Oracle, *hint* *hint*).");
			}

		}
		l.d(F, "\t</Settings>");
		
	}
	
	public void saveSettings() {
		final String F = "saveSettings";
	}

	/**
	 * Gets the single instance of Config.
	 *
	 * @return The Application Config.
	 */
	public static Config getInstance() {
		if (l == null)
			l = new Logger(Config.class, Logger.ERROR, true, new File("config.log"));
		if (instance == null) 
			try {
				instance = init(new File("settings.sqlite"));
			} catch (SQLException e) {
				l.e("getInstance", "Could not get instance of Config: " + e.getMessage());
			} catch (ClassNotFoundException e) {
				l.e("getInstance", "Database driver not found: " + e.getMessage());
			} catch (NullPointerException e) {
				l.e("getInstance", "NullPointerException while initialising Config! " + e.getMessage());
			} catch (Exception e) {
				l.e("getInstance", "Unknown error:\r\n" + e.getClass().getName() + ": " + e.getMessage());
			}
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
	
	public void resizeParent(Component child) {
		if (child == null) {
			throw new NullPointerException("Cannot resize the parent window of a Component that is null!");
		}
		Window window = SwingUtilities.getWindowAncestor(child);
		if (window == null) {
			l.w("resizeParent", "Cannot resize a window that is null. In other words, the Component `child` passed to `resizeParent()` has no parent window. Function will return.");
			return;
		}
		window.setPreferredSize(null); //force a recalculation of the preferred size.
		Dimension wd = window.getPreferredSize();
		Dimension jtd = settings.getPreferredSize();
		
		Dimension d = new Dimension(Math.max(wd.width, jtd.width + 25), wd.height);
		
		window.setPreferredSize(d);

		window.pack();
		window.repaint();
	}

	/**
	 * Gets the specified setting. If multiple settings have the same name,
	 * this will only return the first one; having multiple settings by the same name can 
	 * therefore be considered unsupported.
	 *
	 * @param name The name of the setting to retrieve from the database.
	 * @return The value of the setting if successful, null on failure.
	 */
	public Setting getSetting(String name) {
		Setting setting = this.settings.getSettingByName(name);
		if (setting == null) {
			l.e("getSetting", "Could not get setting " + name);
			return null;
		}
		l.i("getSetting", "Setting " + name + " has a value of " + String.valueOf(setting.getValue()));
		return setting;
	}

	/**
	 * Sets the setting.
	 *
	 * @param friendlyName the friendly name
	 * @param value the value
	 */
	public void setSetting(String friendlyName, Object value) {
		final String F = "setSetting";
		if (friendlyName == null) throw new IllegalArgumentException("friendlyName is null!");
		l.i(F, "Setting " + friendlyName + " to " + String.valueOf(value) + "...");
		HashMap<String, Object> whereClauses = new HashMap<String, Object>();
		whereClauses.put("friendlyName", friendlyName);
		l.i(F, "done.");
		
		l.i(F, "Saving new value " + String.valueOf(value) + " of setting " + friendlyName + " to database...");
		this.sdb.update("setting", whereClauses, "value", String.valueOf(value)); 
		l.i(F, "done.");
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
	 * Gets the setting database.
	 *
	 * @return the sdb
	 */
	public SqliteDatabase getSettingDatabase() {
		return sdb;
	}
	
	/**
	 * Gets the logger.
	 * 
	 * @return the logger.
	 */
	public Logger getLogger() {
		return this.l;
	}

}