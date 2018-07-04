package com.aastorp.config;

import javax.swing.JLabel;



// TODO: Auto-generated Javadoc
/**
 * The Interface Setting.
 */
public interface Setting {
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name);
	
	/**
	 * Gets the friendly name.
	 *
	 * @return the friendly name
	 */
	public String getFriendlyName();
	
	/**
	 * Sets the friendly name.
	 *
	 * @param friendlyName the new friendly name
	 */
	public void setFriendlyName(String friendlyName);
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue();
	
	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(Object value);
	
	/**
	 * Gets the j label.
	 *
	 * @return the j label
	 */
	public JLabel getJLabel();
	
	/**
	 * Sets the j label.
	 *
	 * @param label the new j label
	 */
	public void setJLabel(JLabel label);

	/**
	 * Gets the setting category.
	 *
	 * @return the setting category
	 */
	public SettingCategory getSettingCategory();
	
	/**
	 * Sets the setting category.
	 *
	 * @param settingCategory the new setting category
	 */
	public void setSettingCategory(SettingCategory settingCategory);

	/**
	 * Gets the setting type.
	 *
	 * @return the setting type
	 */
	public Class<?> getSettingType();
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString();
	
}
