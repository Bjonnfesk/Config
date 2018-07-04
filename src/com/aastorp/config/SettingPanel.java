package com.aastorp.config;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A JPanel for a Setting and its Label (and/or other related Components) to display in. Having this ensures that there
 *  will be no line breaks between a Setting's Label and the Setting itself.  
 */
public class SettingPanel extends JPanel {
	/** The Label for this Setting. */
	private JLabel label;
	
	/** The Setting for this Setting (Must implement the Setting interface). */
	private Setting setting;
	
	public SettingPanel(Setting setting) {
		this.label = setting.getJLabel();
		this.setting = setting;
		this.add("lbl" + this.getSetting().getName(), this.getLabel());
		this.add("stg" + this.getSetting().getName(), (Component) this.getSetting());
	}

	/**
	 * @return The label of the SettingPanel
	 */
	public JLabel getLabel() {
		return label;
	}

	/**
	 * @param label The new label to set.
	 */
	public void setLabel(JLabel label) {
		this.label = label;
	}

	/**
	 * @return The setting of the SettingPanel
	 */
	public Setting getSetting() {
		return setting;
	}

	/**
	 * @param setting The new setting to set.
	 */
	public void setSetting(Setting setting) {
		this.setting = setting;
	}
}
