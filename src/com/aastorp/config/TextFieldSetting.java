package com.aastorp.config;

import javax.swing.JLabel;
import javax.swing.JTextField;

// TODO: Auto-generated Javadoc
/**
 * The Class TextFieldSetting.
 */
public class TextFieldSetting extends JTextField implements Setting {
	
	/** The anti recursion. */
	private boolean antiRecursion = false;
	/** The SettingCategory of the setting. This controls which tab the setting gets added to. */
	private SettingCategory settingCategory;
	
	/**  The label that this Setting will show its friendly name in. */
	private JLabel label;
	
	/**
	 * Instantiates a new text field setting.
	 *
	 * @param name the name
	 * @param value the value
	 * @param friendlyName the friendly name
	 * @param settingCategoryId the setting category id
	 */
	public TextFieldSetting(String name, Object value, String friendlyName, int settingCategoryId) {
		this.setName(name);
		this.setValue((String)value);
		this.setFriendlyName(friendlyName);
		this.setSettingCategory(new SettingCategory(settingCategoryId));
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getFriendlyName()
	 */
	@Override
	public String getFriendlyName() {
		return this.getJLabel().getText();
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setFriendlyName(java.lang.String)
	 */
	@Override
	public void setFriendlyName(String friendlyName) {
		this.setJLabel(new JLabel(friendlyName));
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getJLabel()
	 */
	@Override
	public JLabel getJLabel() {
		return this.label;
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setJLabel(javax.swing.JLabel)
	 */
	@Override
	public void setJLabel(JLabel label) {
		this.label = label;
		this.label.setLabelFor(this);
	}
	
	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getValue()
	 */
	@Override
	public Object getValue() {
		return this.getText();
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		this.setText(String.valueOf(value));
	}

	/**
	 * Gets the setting category.
	 *
	 * @return the settingCategory
	 */
	public SettingCategory getSettingCategory() {
		return settingCategory;
	}

	/**
	 * Sets the setting category.
	 *
	 * @param settingCategory the settingCategory to set
	 */
	public void setSettingCategory(SettingCategory settingCategory) {
		this.settingCategory = settingCategory;
	}
	
	/**
	 * Gets the Type of this Setting, usually as determined by SettingClass.getClass().
	 * 
	 * @return The Type of this Setting.
	 */
	public Class<?> getSettingType() {
		return this.getClass();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TextFieldSetting [settingCategory=");
		sb.append(settingCategory);
		if (antiRecursion) {
			return sb.toString();
		}
		antiRecursion = true;
		sb.append(", label=");
		sb.append(label);
		antiRecursion = false;
		sb.append(", getValue()=");
		sb.append(getValue());
		sb.append(", getSettingCategory()=");
		sb.append(getSettingCategory());
		sb.append(", getSettingType()=");
		sb.append(getSettingType());
		sb.append("]");
		return sb.toString();
	}

}
