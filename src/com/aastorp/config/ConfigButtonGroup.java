/**
 * 
 */
package com.aastorp.config;

import javax.swing.ButtonGroup;

/**
 * 
 * Just extends ButtonGroup to add a "name" field.
 * 
 * @author Bjørn Aastorp
 *
 */
public class ConfigButtonGroup extends ButtonGroup {
	private String name;
	
	public ConfigButtonGroup(String name) {
		super();
		this.name = name;
	}

	/**
	 * @return The name of the ConfigButtonGroup
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The new name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
