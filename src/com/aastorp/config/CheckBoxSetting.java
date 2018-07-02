package com.aastorp.config;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import com.aastorp.bibliothecaaastorpiana.common.Common;

// TODO: Auto-generated Javadoc
/**
 * The Class CheckBoxSetting.
 */
public class CheckBoxSetting extends JCheckBox implements Setting {
	
	/** The anti recursion. */
	private boolean antiRecursion = false;
	
	/** The SettingCategory of the setting. This controls which tab the setting gets added to. */
	private SettingCategory settingCategory;
	
	/**
	 * Instantiates a new check box setting.
	 *
	 * @param name the name
	 * @param value the value
	 * @param friendlyName the friendly name
	 * @param settingCategoryId the setting category id
	 */
	public CheckBoxSetting(String name, Object value, String friendlyName, int settingCategoryId) {
		this.setName(name);
		this.setValue(value);
		this.setFriendlyName(friendlyName);
		this.setSettingCategory(new SettingCategory(settingCategoryId));
	}
	
	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getFriendlyName()
	 */
	@Override
	public String getFriendlyName() {
		return this.getText();
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setFriendlyName(java.lang.String)
	 */
	@Override
	public void setFriendlyName(String friendlyName) {
		this.setText(friendlyName);
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getValue()
	 */
	@Override
	public Object getValue() {
		if ((boolean)super.isSelected() == true) {
			return 1;
		} else {
			return 0;
		}
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if (value.getClass() == int.class || value instanceof Integer) {
			Common.confirm("setValue", value + " is Integer: " + (int)value);
			if ((int)value == 1) {
				super.setSelected(true);
			} else {
				super.setSelected(false);
			}
		} else if (value.getClass() == boolean.class || value instanceof Boolean) {
			Common.confirm("setValue", value + " is Boolean: " + (boolean)value);
			super.setSelected((boolean)value);
		} else if (value instanceof String) {
			int intValue = Integer.valueOf((String)value);
			if (intValue == 1) {
				super.setSelected(true);
			} else {
				super.setSelected(false);
			}
		} else {
			throw new IllegalArgumentException("Value " + String.valueOf(value) + " for setting " + this.getName() + " is invalid!");
		}
	}
	
	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getJLabel()
	 */
	@Override
	public JLabel getJLabel() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#setJLabel(javax.swing.JLabel)
	 */
	@Override
	public void setJLabel(JLabel label) {
		this.setFriendlyName(label.getText());
	}

	/**
	 * Gets the setting category.
	 *
	 * @return the settingCategory
	 */
	public SettingCategory getSettingCategory() {
		return this.settingCategory;
	}

	/**
	 * Sets the setting category.
	 *
	 * @param settingCategory the settingCategory to set
	 */
	public void setSettingCategory(SettingCategory settingCategory) {
		this.settingCategory = settingCategory;
	}
	
	/* (non-Javadoc)
	 * @see com.aastorp.config.Setting#getSettingType()
	 */
	@Override
	public Class<?> getSettingType() {
		return this.getClass();
	}

}
