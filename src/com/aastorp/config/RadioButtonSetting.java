package com.aastorp.config;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.SpinnerListModel;

import com.aastorp.bibliothecaaastorpiana.databases.SelectQuery;
import com.aastorp.bibliothecaaastorpiana.databases.WhereClause;
import com.aastorp.bibliothecaaastorpiana.interaction.MessageBoxes;
import com.aastorp.logger.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

// TODO: Auto-generated Javadoc
/**
 * The Class RadioButtonSetting.
 */
public class RadioButtonSetting extends JRadioButton implements Setting {
	
	private Logger l = new Logger(this.getClass(), Logger.INFO, true, new File("config.log"));
	
	/** The anti recursion. */
	private boolean antiRecursion = false;
	
	/** The SettingCategory of the setting. This controls which tab the setting gets added to. */
	private SettingCategory settingCategory;
	
	/**
	 * Instantiates a new radio button setting.
	 *
	 * @param name the name
	 * @param value the value
	 * @param friendlyName the friendly name
	 * @param settingCategoryId the setting category id
	 */
	public RadioButtonSetting(String name, Object value, String friendlyName, int settingCategoryId) {
		//throw new NotImplementedException();
		this.setName(name);		
		this.setFriendlyName(friendlyName);
		this.setSettingCategory(new SettingCategory(settingCategoryId));
		
		SelectQuery selectQuery = new SelectQuery(
				"setting",
				new String[] {
						"data" 
					}, 
				new WhereClause(
						"name", 
						name
					));
		ResultSet dataRs = Config.getInstance().getSettingDatabase().select(selectQuery);
		if (dataRs == null) {
			//data field does not exist in this Setting's database entry
			throw new IllegalArgumentException("The data field is required for RadioButtonSettings, and it is not defined for Setting " + this.getClass() + "::" + this.getName());
		} else {
			ObjectMapper om = new ObjectMapper();
			try {
				dataRs.next();
				String json = dataRs.getString("data");
				Map<String, Object> settingData = om.readValue(json, new TypeReference<Map<String, Object>>() {});
				ConfigButtonGroup buttonGroup = ButtonGroups.getInstance().addButtonGroup(String.valueOf(settingData.get("buttonGroup")));
				buttonGroup.add(this);
			} catch (JsonProcessingException | SQLException | IllegalArgumentException e) {
				l.e("__constructor", e);
			} catch (IOException e) {
				l.e("__constructor", e);
			}
		}
		if (Integer.valueOf((String) value) == 1) {
			this.setSelected(true);
		}
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
			if ((int)value == 0) {
				super.setSelected(false);
			} else {
				super.setSelected(true);
			}
		} else if (value.getClass() == boolean.class || value instanceof Boolean) {
			super.setSelected((boolean)value);
		} else if (value instanceof String) {
			super.setSelected(Boolean.valueOf((String)value));
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
