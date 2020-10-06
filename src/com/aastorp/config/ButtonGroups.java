package com.aastorp.config;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;

import com.aastorp.logger.Logger;

public class ButtonGroups {

	private static Logger l = new Logger(ButtonGroups.class, Logger.INFO, true, new File("config.log"));
	
	/** The single instance of ButtonGroups */
	private static ButtonGroups instance = null;
	
	/** A list of ButtonGroups to which the RadioButtonSettings in this SettingCategory can belong. Lazy-populated when required from the database. */
	public static List<ConfigButtonGroup> buttonGroups;
	
	/**
	 * Private constructor for singleton.
	 */
	private ButtonGroups() {} 
	
	/**
	 * Gets the single instance of ButtonGroups.
	 *
	 * @return The single instance of ButtonGroups.
	 */
	public static ButtonGroups getInstance() {
		if (instance == null) instance = new ButtonGroups();
		if (buttonGroups == null) 
			try {
				buttonGroups = new ArrayList<ConfigButtonGroup>();
			} catch (NullPointerException e) {
				l.e("getInstance", "NullPointerException while initialising Config! " + e.getMessage());
			} catch (Exception e) {
				l.e("getInstance", "Unknown error:\r\n" + e.getClass().getName() + ": " + e.getMessage());
			}
		return instance;
	}
	
	public ConfigButtonGroup addButtonGroup(String name) {
		final String F = "addButtonGroup";
		l.i(F, "Attempting to add ButtonGroup " + name);
		ConfigButtonGroup buttonGroup = this.getButtonGroupByName(name);
		if (buttonGroup != null) {
			l.i(F, "ButtonGroup " + name + " already exists");
			return buttonGroup;
		}
		//if we reach this point, the buttonGroup does not exist...
		l.i(F, "Added ButtonGroup " + name);
		buttonGroup = new ConfigButtonGroup(name);
		ButtonGroups.buttonGroups.add(buttonGroup);
		return buttonGroup;
	}
	
	public ConfigButtonGroup getButtonGroupByName(String name) {
		final String F = "getButtonGroupByName";
		for (ConfigButtonGroup buttonGroup : ButtonGroups.buttonGroups) {
			String buttonGroupName = buttonGroup.getName();
			l.i(F, "Comparing " + buttonGroupName + " to " + name);
			if (buttonGroupName.equals(name)) {
				l.d(F, "ButtonGroup " + buttonGroupName + " already exists");
				return buttonGroup;
			}
		}
		//if we reach this point, the buttonGroup does not exist...
		return null;
	}
}
