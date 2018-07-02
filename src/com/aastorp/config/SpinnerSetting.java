/**
 * 
 */
package com.aastorp.config;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import com.aastorp.bibliothecaaastorpiana.databases.SelectQuery;
import com.aastorp.bibliothecaaastorpiana.databases.WhereClause;
import com.aastorp.logger.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

// TODO: Auto-generated Javadoc
/**
 * The Class SpinnerSetting.
 *
 * @author Bjørn Aastorp
 */
public class SpinnerSetting extends JSpinner implements Setting {

	private Logger l = new Logger(this.getClass(), Logger.INFO, true, new File("config.log"));
	
	/** The anti recursion. */
	private boolean antiRecursion = false;
	/** The SettingCategory of the setting. This controls which tab the setting gets added to. */
	private SettingCategory settingCategory;
	
	/**  The label that this Setting will show its friendly name in. */
	private JLabel label;
	
	/**
	 * Instantiates a new spinner setting.
	 *
	 * @param name the name
	 * @param value the value
	 * @param friendlyName the friendly name
	 * @param settingCategoryId the setting category id
	 */
	public SpinnerSetting(String name, Object value, String friendlyName, int settingCategoryId) {
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
			throw new IllegalArgumentException("The data field is required for SpinnerSettings, and it is not defined for Setting " + this.getClass() + "::" + this.getName());
		} else {
			ObjectMapper om = new ObjectMapper();
			try {
				dataRs.next();
				String json = dataRs.getString("data");
				Map<String, Object> settingData = om.readValue(json, new TypeReference<Map<String, Object>>() {});
				this.setModel(new SpinnerListModel(((ArrayList<?>)settingData.get("validValues"))));
			} catch (JsonProcessingException | SQLException e) {
				l.e("__constructor", e);
			} catch (IOException e) {
				l.e("__constructor", e);
			}
		}
		/*
		 * Make this more dynamic by letting the data field also define 
		 * whether the Spinner should have a SpinnerNumberModel or 
		 * SpinnerListModel.
		 */
		try {
			this.setValue(Integer.valueOf((String)value));
		} catch (NumberFormatException e) {
			this.setValue((String)value);
		}
		
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
		sb.append("SpinnerSetting [settingCategory=");
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
