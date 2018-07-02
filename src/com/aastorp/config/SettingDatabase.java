package com.aastorp.config;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.aastorp.bibliothecaaastorpiana.databases.SelectQuery;
import com.aastorp.bibliothecaaastorpiana.databases.SqliteDatabase;
import com.aastorp.logger.Logger;

// TODO: Auto-generated Javadoc
/**
 * Extends {@link com.aastorp.common.databases.Database Database} by providing
 * functionality specific to Config's Setting Databases. 
 */
public class SettingDatabase extends SqliteDatabase {

	/** The Logger. */
	Logger l;

	/**
	 * Instantiates a new SettingDatabase from the given databaseFile.
	 *
	 * @param databaseFile The database file to read settings from.
	 */
	public SettingDatabase(File databaseFile) {
		super(databaseFile);
		final String F = "__constructor";
		this.l = new Logger(this.getClass(), Logger.INFO, true, new File("config.log"));

		this.buildIfNotExisting();
	}
	
	/**
	 * Builds the Database's required Tables if they do not exist.
	 */
	protected void buildIfNotExisting() {
		final String F = "buildIfNotExisting";
		String sql;
		if (!tableExists("setting")) {
			sql = "CREATE TABLE \"setting\" (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `value` TEXT, `friendlyName` TEXT, `settingCategory` INTEGER, `settingType` TEXT, `data` TEXT)";
			l.d(F, "Table setting doesn't exist, creating...");
			state(sql);
			insert(
					"setting",
					new String[] {
							"name",
							"value",
							"friendlyName",
							"settingCategory",
							"settingType",
							"data"
					},
					new Object[] {
							"logLevel",
							"4",
							"Log Level",
							1,
							"Spinner",
							"{validValues: [0, 1, 2, 3, 4]}"
					});
			insert(
					"setting",
					new String[] {
							"name",
							"value",
							"friendlyName",
							"settingCategory",
							"settingType",
							"data"
					},
					new Object[] {
							"test",
							"1",
							"testing",
							2,
							"Spinner",
							"{validValues: [0, 1, 2, 3, 4]}"
					});
		} else {
			l.i(F, "Table setting already exists.");
		}
		if (!tableExists("settingCategory")) {
			sql = "CREATE TABLE \"settingCategory\" ( `id` INTEGER PRIMARY KEY AUTOINCREMENT, `name` TEXT, `friendlyName` TEXT )";
			state(sql);
			l.d(F, "Table settingCategory doesn't exist, creating...");
			insert(
					"settingCategory",
					new String[] {
							"name",
							"friendlyName"
					},
					new Object[] {
							"general",
							"General"
					});
		}
	}
	
	/**
	 * Gets the settings.
	 *
	 * @return The settings.
	 */
	public Settings getSettings() {
		final String F = "getSettings";
		ResultSet rs;
		Settings settings = new Settings(); 
		SelectQuery selectQuery = new SelectQuery(
				"setting",
				new String[] {
						"settingType",
						"name",
						"value",
						"friendlyName",
						"settingCategory",
						"data"
				});
		rs = this.select(selectQuery);
		while(true) {

				try {
					while(rs.next()) {
						settings.add((Setting)SettingFactory.createSetting(rs.getString("settingType"), rs.getString("name"), rs.getObject("value"), rs.getString("friendlyName"), rs.getInt("settingCategory")));
					}
					break; //quit the loop when all Settings are added to the list
				} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
						| IllegalArgumentException | ClassNotFoundException | InvocationTargetException
						| SQLException e) {
					try {
						if (e.getClass() == InvocationTargetException.class) {
							l.e(F, "InvocationTargetException on setting " + rs.getString("name") + ": " + e.getCause().getClass() + "::" + e.getCause().getMessage() + ". Ensure setting database is correctly formatted.");
						} else {
							l.e(F, e, " on setting " + rs.getString("name") + ". Ensure setting database is correctly formatted.");
						}
					} catch (SQLException e1) {
						l.e(F, e, " on a setting, and SQLException on getting the name of the offending setting: ");
						l.e(F, e1, ". Ensure setting database is correctly formatted.");
					}
					continue;
				}

		}
		
		return settings;
	}

}