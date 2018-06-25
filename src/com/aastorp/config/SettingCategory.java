/**
 * 
 */
package com.aastorp.config;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.*;

/**
 * @author Admin
 *
 */
public class SettingCategory extends JPanel {
	
	/** The database id of the SettingCategory */
	private int id;
	
	/** The name of the SettingCategory */
	private String name;
	
	/** The name of the tab the settings in this SettingCategory will display in */
	private String tabName;
	
	/** A List of the SettingGroups belonging to this SettingCategory. Each displays in its own SettingGroupBox. */ 
	//List<?> settingGroups; 
	

	public SettingCategory() {
		this(new FlowLayout(FlowLayout.LEADING, 5, 5));
		final String F = "__constructor";
	}


	public SettingCategory(LayoutManager layout) {
		super(layout);
		final String F = "__constructor";
		this.add(new JButton("Test"));
		this.add(new JCheckBox("Testing settings!!"));
		
	}
	
	@Override
	public Component add(Component comp) {
		//Complement functionality...
		return super.add(comp);
	}

	/**
	 * @return The name of the tab the settings in this SettingCategory will display in	 
	 */
	public String getTabName() {
		final String F = "getTabName";
		return tabName;
	}

	/**
	 * @param tabName The new name of the tab the settings in this SettingCategory will display in
	 */
	public void setTabName(String tabName) {
		final String F = "setTabName";
		this.tabName = tabName;
	}

	/**
	 * @return The database id of the SettingCategory
	 */
	public int getId() {
		final String F = "getId";
		return id;
	}

	/**
	 * @return The name of the SettingCategory
	 */
	public String getName() {
		final String F = "getName";
		return name;
	}

}
